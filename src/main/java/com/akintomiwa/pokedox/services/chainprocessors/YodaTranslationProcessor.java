package com.akintomiwa.pokedox.services.chainprocessors;

import com.akintomiwa.pokedox.dtos.PokemonDetailsResponse;

import static com.akintomiwa.pokedox.helpers.URLResources.YODA;

public class YodaTranslationProcessor extends Processor {
    public YodaTranslationProcessor(Processor processor) {
        super(processor);
    }

    public String process(PokemonDetailsResponse request) {
        if (request.getHabitat().equalsIgnoreCase("cave") || request.getIsLegendary()) {
            return YODA;
        } else {
            return super.process(request);
        }
    }
}
