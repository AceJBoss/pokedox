package com.akintomiwa.pokedox.services.chainprocessors;

import com.akintomiwa.pokedox.dtos.PokemonDetailsResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.akintomiwa.pokedox.helpers.URLResources.*;
import static com.akintomiwa.pokedox.services.chainprocessors.TranslationChain.decideTranslation;
import static com.michaeladonis.pokedox.util.TestURLResources.POKEMON_DESCRIPTION;
import static org.junit.jupiter.api.Assertions.*;

class TranslationChainTest {

    @DisplayName("Test that Descriptions for Legend pokemons are in Yoda ")
    @Test
    void givenLegendaryPokemon_IfTranslationIsYoda_thenSuccess() {
        PokemonDetailsResponse legendPokemon = new PokemonDetailsResponse("rare", "mewtwo", POKEMON_DESCRIPTION, true);
        String translation = decideTranslation(legendPokemon);
        assertEquals(translation, YODA.concat(DOT_JSON));
    }

    @DisplayName("Test that Descriptions for Cave pokemons are in Yoda ")
    @Test
    void givenCavePokemon_IfTranslationIsYoda_thenSuccess() {
        PokemonDetailsResponse legendPokemon = new PokemonDetailsResponse("cave", "mewtwo", POKEMON_DESCRIPTION, false);
        String translation = decideTranslation(legendPokemon);
        assertEquals(translation, YODA.concat(DOT_JSON));
    }

    @DisplayName("Test that Descriptions for Regular pokemons are in shakespeare ")
    @Test
    void givenRegularPokemon_IfTranslationIsShakespearean_thenSuccess() {
        PokemonDetailsResponse regularPokemon = new PokemonDetailsResponse("bush", "mewtwo", POKEMON_DESCRIPTION, false);
        String translation = decideTranslation(regularPokemon);
        assertEquals(translation, SHAKESPEARE.concat(DOT_JSON));
    }
}