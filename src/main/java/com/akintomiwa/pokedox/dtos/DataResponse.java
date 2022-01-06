package com.akintomiwa.pokedox.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class DataResponse<M> {

    private boolean valid;
    private M data;
    private String message;

    public DataResponse(boolean valid, M data) {
        this.valid = valid;
        this.data = data;
        this.message = valid ? "Request was successful" : "An error occurred";
    }
    public DataResponse(boolean valid, M data, String message) {
        this.valid = valid;
        this.data = data;
        this.message = message;
    }
    public static DataResponse getErrorResponse(Exception exception) {
        return new DataResponse(false, exception.getLocalizedMessage());
    }
}

