package com.akintomiwa.pokedox.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TranslatorResponse {

    private Contents contents;
    private Success success;

    public TranslatorResponse(String textToTranslate) {
        this.contents = new Contents(textToTranslate);
    }

    public @Data
    static class Success {
        private int total;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    public @Data
    static class Contents {
        public Contents(String translation) {
            this.translation = translation;
        }

        private String translation;
        private String translated;
        private String text;
    }
}
