package com.api.kwhcalculator.excepciones;

import org.springframework.http.HttpStatus;

//esta será la excepción global de la aplicación
public class ApiRestAppException extends RuntimeException {

    //este atributo se agrega para que compile la clase, ya que heredó comportamientos de RuntimeException
    private static final long serialVersionUID = 1L;//probé si funcionaba sin esto y funciona perfecto, quizá lo saque mas adelante

    private HttpStatus estado;
    private String mensaje;

    public ApiRestAppException(HttpStatus estado, String mensaje) {
        super(String.format("%s : %s", estado, mensaje));
        this.estado = estado;
        this.mensaje = mensaje;
    }

    public ApiRestAppException(HttpStatus estado, String mensaje, String mensaje1) {
        super();
        this.estado = estado;
        this.mensaje = mensaje;
        this.mensaje = mensaje1;
    }

    public HttpStatus getEstado() {
        return estado;
    }

    public void setEstado(HttpStatus estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
