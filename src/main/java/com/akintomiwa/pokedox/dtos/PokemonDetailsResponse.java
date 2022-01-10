package com.akintomiwa.pokedox.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PokemonDetailsResponse {

    private String habitat;
    private String name;
    private String description;
    private Boolean isLegendary;

    public PokemonDetailsResponse(PokemonDetailsResponseBody responseBody) {
        initializeRequiredFields(responseBody);
        this.description = transformDescription(responseBody);
    }

    private String transformDescription(PokemonDetailsResponseBody responseBody) {
        if (responseBody.getFlavorTextEntriesItems() != null && !responseBody.getFlavorTextEntriesItems().isEmpty()) {
            return responseBody.getFlavorTextEntriesItems().get(0).getFlavorText() //get first item
                    .replace("\n", " ")
                    .replace("\f", " ");
        }
        return "No description";
    }

    private void initializeRequiredFields(PokemonDetailsResponseBody responseBody) {
        this.habitat = responseBody.getHabitat().getName();
        this.name = responseBody.getName();
        this.isLegendary = responseBody.isLegendary();
    }
}

