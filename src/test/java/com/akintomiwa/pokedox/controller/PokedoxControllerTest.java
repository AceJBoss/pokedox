package com.akintomiwa.pokedox.controller;


import com.akintomiwa.pokedox.controllers.PokedoxController;
import com.akintomiwa.pokedox.dtos.DataResponse;
import com.akintomiwa.pokedox.dtos.PokemonDetailsResponse;
import com.akintomiwa.pokedox.helpers.MessageHelperService;
import com.akintomiwa.pokedox.services.PokedoxService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static com.akintomiwa.pokedox.util.TestURLResources.YODA_TRANSLATION;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({PokedoxController.class, MessageHelperService.class})
class PokedoxControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PokedoxService pokedoxService;

    @Autowired
    private MessageHelperService messageHelperService;


    @Test
    void givenPokemonName_ifNameExists_thenSucceed() throws Exception {

        PokemonDetailsResponse mockPokemonResponse = new PokemonDetailsResponse("rare", "mewtwo", "Some nice description", true);
        doReturn(new ResponseEntity<>(mockPokemonResponse, HttpStatus.OK)).when(pokedoxService).getPokemonDetails("mewtwo");

        mockMvc.perform(get("/pokemon/{pokemonName}", "mewtwo"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.name", is("mewtwo")))
                .andExpect(jsonPath("$.habitat", is("rare")))
                .andExpect(jsonPath("$.description", is("Some nice description")))
                .andExpect(jsonPath("$.isLegendary", is(true)));
    }

    @Test
    void givenPokemonName_ifNameNotExist_thenReturnNotFound() throws Exception {

        doReturn(new ResponseEntity<>(new DataResponse(false, messageHelperService.getMessage("pokemon.not.found")), HttpStatus.NOT_FOUND)).when(pokedoxService).getPokemonDetails("no-exist");
        mockMvc.perform(get("/pokemon/{pokemonName}", "no-exist"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data", is(messageHelperService.getMessage("pokemon.not.found"))));
    }

    @Test
    void givenPokemonName_ifNameExists_thenSucceed_Translated() throws Exception {

        PokemonDetailsResponse mockPokemonResponse = new PokemonDetailsResponse("rare", "mewtwo", YODA_TRANSLATION, true);
        doReturn(new ResponseEntity<>(mockPokemonResponse, HttpStatus.OK)).when(pokedoxService).getTranslatedPokemonDetails("mewtwo");

        mockMvc.perform(get("/pokemon/translated/{pokemonName}", "mewtwo"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.name", is("mewtwo")))
                .andExpect(jsonPath("$.habitat", is("rare")))
                .andExpect(jsonPath("$.description", is(YODA_TRANSLATION)))
                .andExpect(jsonPath("$.isLegendary", is(true)));
    }


    @Test
    void givenPokemonName_ifNameNotExist_thenReturnNotFound_Translated() throws Exception {
        doReturn(new ResponseEntity<>(new DataResponse(false, messageHelperService.getMessage("pokemon.not.found")), HttpStatus.NOT_FOUND)).when(pokedoxService).getTranslatedPokemonDetails("no-exist");
        mockMvc.perform(get("/pokemon/translated/{pokemonName}", "no-exist"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data", is(messageHelperService.getMessage("pokemon.not.found"))));
    }

}