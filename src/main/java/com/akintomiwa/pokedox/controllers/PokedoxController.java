package com.akintomiwa.pokedox.controllers;

import com.akintomiwa.pokedox.dtos.PokemonDetailsResponse;
import com.akintomiwa.pokedox.services.PokedoxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;


@RequiredArgsConstructor
@RestController
@RequestMapping("pokemon")
@Api(tags = {"Pokedox Controller"})
@SwaggerDefinition(tags = {
        @Tag(name = "Pokemon", description = "Fetches details about a pokemon")
})
public class PokedoxController {

    private final PokedoxService pokedoxService;

    @GetMapping("/{pokemonName}")
    public ResponseEntity<PokemonDetailsResponse> getPokemon(@PathVariable("pokemonName") @NotBlank String pokemonName) {
        return pokedoxService.getPokemonDetails(pokemonName);
    }

    @GetMapping("translated/{pokemonName}")
    public ResponseEntity<PokemonDetailsResponse> getPokemonAndTranslate(@PathVariable("pokemonName") @NotBlank String pokemonName) {
        return pokedoxService.getTranslatedPokemonDetails(pokemonName);
    }

}

