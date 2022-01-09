package com.akintomiwa.pokedox.services;

import com.akintomiwa.pokedox.dtos.PokemonDetailsResponse;
import org.springframework.http.ResponseEntity;

public interface PokedoxService {

    ResponseEntity<PokemonDetailsResponse> getPokemonDetails(String pokemonName);
    ResponseEntity<PokemonDetailsResponse> getTranslatedPokemonDetails(String pokemonName);
}
