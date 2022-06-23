package com.api.kwhcalculator.dto;

import java.time.LocalDate;

public class SectorEspecificoDTO {

    private Long id;
    private String nomSectorEspecifico;
    private double mtrsCuadrados; //null
    //esto de aquí no estoy seguro si mandarlo a la base de datos
    private int cantAparatosElectronicos;
    private double totalConsumoW; //null
    private double totalPesos; //null

    public SectorEspecificoDTO() {
    }

    public SectorEspecificoDTO(Long id, String nomSectorEspecifico) {
        this.id = id;
        this.nomSectorEspecifico = nomSectorEspecifico;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomSectorEspecifico() {
        return nomSectorEspecifico;
    }

    public void setNomSectorEspecifico(String nomSectorEspecifico) {
        this.nomSectorEspecifico = nomSectorEspecifico;
    }

    public double getMtrsCuadrados() {
        return mtrsCuadrados;
    }

    public void setMtrsCuadrados(double mtrsCuadrados) {
        this.mtrsCuadrados = mtrsCuadrados;
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

    public int getCantAparatosElectronicos() {
        return cantAparatosElectronicos;
    }

    public void setCantAparatosElectronicos(int cantAparatosElectronicos) {
        this.cantAparatosElectronicos = cantAparatosElectronicos;
    }
}
