package com.akintomiwa.pokedox.clients;

import com.akintomiwa.pokedox.dtos.PokemonDetailsResponse;
import com.akintomiwa.pokedox.dtos.PokemonDetailsResponseBody;
import com.akintomiwa.pokedox.exception.ExternalServerException;
import com.akintomiwa.pokedox.exception.NotFoundException;
import com.akintomiwa.pokedox.helpers.MessageHelperService;
import com.akintomiwa.pokedox.helpers.URLResources;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.akintomiwa.pokedox.helpers.URLResources.V2_GET_POKEMON;

@Component
public class PokemonClient {

    private final WebClient webClient;

    private final MessageHelperService messageHelperService;

    public PokemonClient(WebClient.Builder webClientBuilder, MessageHelperService messageHelperService) {
        this.webClient = webClientBuilder.baseUrl(URLResources.POKEMON_CLIENT_URL).build();
        this.messageHelperService = messageHelperService;
    }

    private PokemonDetailsResponseBody fetchPokemonData(String pokemonName) {
        WebClient.ResponseSpec responseSpec = webClient.get().uri(V2_GET_POKEMON, pokemonName).retrieve();
        return handleRequest(responseSpec, messageHelperService.getMessage("pokemon.not.found"))
                .bodyToMono(PokemonDetailsResponseBody.class).retry(2).block();
    }

    @Cacheable(cacheNames = "pokemons", key = "#pokemonName")
    public PokemonDetailsResponse getPokemonData(String pokemonName) {
        PokemonDetailsResponseBody pokemonDetails = fetchPokemonData(pokemonName);
        return new PokemonDetailsResponse(pokemonDetails);
    }

    public WebClient.ResponseSpec handleRequest(WebClient.ResponseSpec responseSpec, String exceptionMessage) {
        return responseSpec.
                onStatus(httpStatus -> httpStatus.value() == HttpStatus.NOT_FOUND.value(),
                        clientResponse -> Mono.error(new NotFoundException(exceptionMessage)))
                .onStatus(httpStatus -> httpStatus.value() == HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        clientResponse -> Mono.error(new ExternalServerException()));

    }
}
