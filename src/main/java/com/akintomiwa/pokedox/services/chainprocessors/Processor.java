package com.akintomiwa.pokedox.services.chainprocessors;

import com.akintomiwa.pokedox.dtos.PokemonDetailsResponse;

import static com.akintomiwa.pokedox.helpers.URLResources.SHAKESPEARE;

abstract class Processor {

    private Processor processor;

    public Processor(Processor processor) {
        this.processor = processor;
    }
    public String process(PokemonDetailsResponse request) {
        if (processor != null) {
            return processor.process(request);
        } else return SHAKESPEARE;
    }
}
