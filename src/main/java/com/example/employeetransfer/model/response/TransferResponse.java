package com.example.employeetransfer.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferResponse<T> {
    private final int code;
    private final String message;
    private final T result;

    public TransferResponse(int code, String message) {
        this.code = code;
        this.message = message;
        this.result = null;
    }

    public TransferResponse(int code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }
}
