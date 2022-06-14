package com.api.kwhcalculator.modelos;

import javax.imageio.ImageIO;
import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;

@Entity
@Table(name = "aparatoElectronicoUsuario")
public class AparatoElectronicoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreAparato;

    private String codigo; //null

    private String marca; //null

    private double wattsConsumo;

    private LocalTime tiempoUsoDiario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idSectorEspecifico", nullable = false)
    private SectorEspecifico sectorEspecifico;

    //constructores

    public AparatoElectronicoUsuario() {
    }

    public AparatoElectronicoUsuario(Long id, String nombreAparato, double wattsConsumo, LocalTime tiempoUsoDiario) {
        this.id = id;
        this.nombreAparato = nombreAparato;
        this.wattsConsumo = wattsConsumo;
        this.tiempoUsoDiario = tiempoUsoDiario;
    }

    public AparatoElectronicoUsuario(Long id, String nombreAparato, String marca, double wattsConsumo, LocalTime tiempoUsoDiario) {
        this.id = id;
        this.nombreAparato = nombreAparato;
        this.marca = marca;
        this.wattsConsumo = wattsConsumo;
        this.tiempoUsoDiario = tiempoUsoDiario;
    }

    public AparatoElectronicoUsuario(Long id, String nombreAparato, String codigo, String marca, double wattsConsumo, LocalTime tiempoUsoDiario) {
        this.id = id;
        this.nombreAparato = nombreAparato;
        this.codigo = codigo;
        this.marca = marca;
        this.wattsConsumo = wattsConsumo;
        this.tiempoUsoDiario = tiempoUsoDiario;
    }

    //getters y setters

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

    public LocalTime getTiempoUsoDiario() {
        return tiempoUsoDiario;
    }

    public void setTiempoUsoDiario(LocalTime tiempoUsoDiario) {
        this.tiempoUsoDiario = tiempoUsoDiario;
    }

    public SectorEspecifico getSectorEspecifico() {
        return sectorEspecifico;
    }

    public void setSectorEspecifico(SectorEspecifico sectorEspecifico) {
        this.sectorEspecifico = sectorEspecifico;
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
