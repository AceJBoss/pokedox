package com.akintomiwa.pokedox.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PokemonDetailsResponse {

    private String name;
    private String description;
    private String habitat;
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
        this.name = responseBody.getName();
        this.habitat = responseBody.getHabitat().getName();
        this.isLegendary = responseBody.getIsLegendary();
    }
}

