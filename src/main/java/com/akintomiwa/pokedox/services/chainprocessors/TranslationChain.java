package com.akintomiwa.pokedox.services.chainprocessors;

import com.akintomiwa.pokedox.dtos.PokemonDetailsResponse;

import static com.akintomiwa.pokedox.helpers.URLResources.DOT_JSON;

public class TranslationChain {

    static Processor chain;

    private static void buildChain() {
        chain = new YodaTranslationProcessor(null);
    }

    public static String decideTranslation(PokemonDetailsResponse request) {
        buildChain();
        return chain.process(request).concat(DOT_JSON);
    }
}