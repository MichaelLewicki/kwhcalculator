package com.api.kwhcalculator.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    //este atributo se agrega para que compile la clase, ya que heredó comportamientos de RuntimeException
    private static final long serialVersionUID = 1L;//probé si funcionaba sin esto y funciona perfecto, quizá lo saque mas adelante

    private String nombreDelRecurso;
    private String nombreDelCampo;
    private String valorDelCampo;

    public ResourceNotFoundException(String nombreDelRecurso, String nombreDelCampo, long valorDelCampo) {
        super(String.format("%s no se encontró con : %s : '%s'", nombreDelRecurso,nombreDelCampo,valorDelCampo));
        //se asignan estos atributos sólo para que compile el método, por son se asignan después de la implementación
        this.nombreDelRecurso = nombreDelRecurso;//probé si funcionaba sin esto y funciona perfecto, quizá lo saque mas adelante
        this.nombreDelCampo = nombreDelCampo;//probé si funcionaba sin esto y funciona perfecto, quizá lo saque mas adelante
        this.valorDelCampo = String.valueOf(valorDelCampo);//probé si funcionaba sin esto y funciona perfecto, quizá lo saque mas adelante
    }

    public String getNombreDelRecurso() {
        return nombreDelRecurso;
    }

    public void setNombreDelRecurso(String nombreDelRecurso) {
        this.nombreDelRecurso = nombreDelRecurso;
    }

    public String getNombreDelCampo() {
        return nombreDelCampo;
    }

    public void setNombreDelCampo(String nombreDelCampo) {
        this.nombreDelCampo = nombreDelCampo;
    }

    public String getValorDelCampo() {
        return valorDelCampo;
    }

    public void setValorDelCampo(String valorDelCampo) {
        this.valorDelCampo = valorDelCampo;
    }

}
