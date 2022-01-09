package com.akintomiwa.pokedox.clients;

import com.akintomiwa.pokedox.dtos.PokemonDetailsResponse;
import com.akintomiwa.pokedox.dtos.TranslatorResponse;
import com.akintomiwa.pokedox.exception.ExternalServerException;
import com.akintomiwa.pokedox.helpers.MessageHelperService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.akintomiwa.pokedox.helpers.URLResources.TRANSLATE;
import static com.akintomiwa.pokedox.services.chainprocessors.TranslationChain.decideTranslation;

@Component
public class TranslationClient extends BaseClient {

    private static final String BASEURL = "https://api.funtranslations.com";
    private final WebClient webClient;
    private final MessageHelperService messageHelperService;

    public TranslationClient(WebClient.Builder webClientBuilder, MessageHelperService messageHelperService) {
        this.webClient = webClientBuilder.baseUrl(BASEURL).build();
        this.messageHelperService = messageHelperService;
    }

    @CacheEvict(cacheNames = "pokemons", key = "#pokemonDetailsResponse.name")
    @Cacheable(cacheNames = "translations", key = "#pokemonDetailsResponse.name")
    public String getTranslatedDescription(PokemonDetailsResponse pokemonDetailsResponse) {
        TranslatorResponse translatorResponse = translate(pokemonDetailsResponse);
        return translatorResponse.getContents().getTranslated();
    }

    private TranslatorResponse translate(PokemonDetailsResponse pokemonDetails) {
        WebClient.ResponseSpec responseSpec = webClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path(TRANSLATE + decideTranslation(pokemonDetails)).
                                queryParam("text", pokemonDetails.getDescription())
                                .build()).retrieve().onStatus(httpStatus -> httpStatus.value() == HttpStatus.TOO_MANY_REQUESTS.value()
                        , clientResponse -> Mono.error(new ExternalServerException(messageHelperService.getMessage("request.exhausted"))));

        return responseSpec.bodyToMono(TranslatorResponse.class)
                .onErrorReturn(new TranslatorResponse(pokemonDetails.getDescription()))
                .retry(2).block();
    }


}
