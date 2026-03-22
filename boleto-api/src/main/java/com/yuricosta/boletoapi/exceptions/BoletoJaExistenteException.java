package com.yuricosta.boletoapi.exceptions;

public class BoletoJaExistenteException extends RuntimeException {
    public BoletoJaExistenteException(String message) {
        super(message);
    }
}
