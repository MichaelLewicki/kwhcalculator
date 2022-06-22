package com.api.kwhcalculator.modelos;

import javax.persistence.*;

@Entity
@Table(name = "aparatoElectronicoOficial")
public class AparatoElectronicoOficial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreAparato;

    private String codigo; //null

    private String marca; //null

    private double wattsConsumo;

    public AparatoElectronicoOficial() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreAparato() {
        return nombreAparato;
    }

    public void setNombreAparato(String nombreAparato) {
        this.nombreAparato = nombreAparato;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getWattsConsumo() {
        return wattsConsumo;
    }

    public void setWattsConsumo(double wattsConsumo) {
        this.wattsConsumo = wattsConsumo;
    }
}
