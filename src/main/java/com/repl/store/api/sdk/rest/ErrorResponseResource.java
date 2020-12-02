package com.repl.store.api.sdk.rest;

import lombok.Data;

@Data
public class ErrorResponseResource {

    private String errorMessage;

    public ErrorResponseResource(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
