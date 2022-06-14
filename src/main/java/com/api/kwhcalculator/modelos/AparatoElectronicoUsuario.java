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

    private String Marca; //null

    private double WattsConsumo;

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
        WattsConsumo = wattsConsumo;
        this.tiempoUsoDiario = tiempoUsoDiario;
    }

    public AparatoElectronicoUsuario(Long id, String nombreAparato, String marca, double wattsConsumo, LocalTime tiempoUsoDiario) {
        this.id = id;
        this.nombreAparato = nombreAparato;
        Marca = marca;
        WattsConsumo = wattsConsumo;
        this.tiempoUsoDiario = tiempoUsoDiario;
    }

    public AparatoElectronicoUsuario(Long id, String nombreAparato, String codigo, String marca, double wattsConsumo, LocalTime tiempoUsoDiario) {
        this.id = id;
        this.nombreAparato = nombreAparato;
        this.codigo = codigo;
        Marca = marca;
        WattsConsumo = wattsConsumo;
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

    public double getWattsConsumo() {
        return WattsConsumo;
    }

    public void setWattsConsumo(double wattsConsumo) {
        WattsConsumo = wattsConsumo;
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
        return Marca;
    }

    public void setMarca(String marca) {
        Marca = marca;
    }
}
