package com.api.kwhcalculator.dto;

import java.time.LocalDate;

public class SectorGeneralDTO {

    private Long id;
    private String nomSectorGnral;
    private double valorKwh;
    private LocalDate fechaIngresoValorKwh;
    private double mtrsCuadrados; //null
    //esto de aquí no estoy seguro si mandarlo a la base de datos
    private double totalConsumoW; //null
    private double totalPesos; //null


    public SectorGeneralDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomSectorGnral() {
        return nomSectorGnral;
    }

    public void setNomSectorGnral(String nomSectorGnral) {
        this.nomSectorGnral = nomSectorGnral;
    }

    public double getMtrsCuadrados() {
        return mtrsCuadrados;
    }

    public void setMtrsCuadrados(double mtrsCuadrados) {
        this.mtrsCuadrados = mtrsCuadrados;
    }

    public double getValorKwh() {
        return valorKwh;
    }

    public void setValorKwh(double valorKwh) {
        this.valorKwh = valorKwh;
    }

    public LocalDate getFechaIngresoValorKwh() {
        return fechaIngresoValorKwh;
    }

    public void setFechaIngresoValorKwh(LocalDate fechaIngresoValorKwh) {
        this.fechaIngresoValorKwh = fechaIngresoValorKwh;
    }

    public double getTotalConsumoW() {
        return totalConsumoW;
    }

    public void setTotalConsumoW(double totalConsumoW) {
        this.totalConsumoW = totalConsumoW;
    }

    public double getTotalPesos() {
        return totalPesos;
    }

    public void setTotalPesos(double totalPesos) {
        this.totalPesos = totalPesos;
    }
}
