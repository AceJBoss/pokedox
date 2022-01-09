package com.akintomiwa.pokedox.services.impls;

import com.akintomiwa.pokedox.clients.PokemonClient;
import com.akintomiwa.pokedox.clients.TranslationClient;
import com.akintomiwa.pokedox.dtos.PokemonDetailsResponse;
import com.akintomiwa.pokedox.exception.ValidationException;
import com.akintomiwa.pokedox.services.PokedoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PokedoxServiceImpl implements PokedoxService {

    private final PokemonClient pokemonClient;
    private final TranslationClient translationClient;

    @Override
    public ResponseEntity<PokemonDetailsResponse> getPokemonDetails(String pokemonName) {
        if (pokemonName == null || pokemonName.isEmpty())
            throw new ValidationException("Please pass a valid Pokemon name");
        return new ResponseEntity<>(pokemonClient.getPokemonData(pokemonName), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PokemonDetailsResponse> getTranslatedPokemonDetails(String pokemonName) {
        if (pokemonName == null || pokemonName.isEmpty())
            throw new ValidationException("Please pass a valid Pokemon name");
        PokemonDetailsResponse pokemonDetailsResponse = pokemonClient.getPokemonData(pokemonName);
        String translation = translationClient.getTranslatedDescription(pokemonDetailsResponse);
        pokemonDetailsResponse.setDescription(translation);
        return new ResponseEntity<>(pokemonDetailsResponse, HttpStatus.OK);
    }

}
