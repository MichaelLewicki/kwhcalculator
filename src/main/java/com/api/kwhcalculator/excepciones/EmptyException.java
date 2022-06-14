package com.api.kwhcalculator.excepciones;

import org.springframework.http.HttpStatus;

public class EmptyException extends RuntimeException {

    private static final long serialVersionUID = 1L;//probé si funcionaba sin esto y funciona perfecto, quizá lo saque mas adelante

    private boolean llenado;

    public EmptyException(boolean llenado) {
        super(String.format("%s",llenado));
        this.llenado = llenado;
    }

    public boolean isLlenado() {
        return llenado;
    }

    public void setLlenado(boolean llenado) {
        this.llenado = llenado;
    }
}
