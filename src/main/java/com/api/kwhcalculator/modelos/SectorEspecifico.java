package com.api.kwhcalculator.modelos;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sectorEspecifico")
public class SectorEspecifico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nomSectorEspecifico", nullable = false)
    private String nomSectorEspecifico;

    @Column(name = "mtrsCuadrados", nullable = true)
    private double mtrsCuadrados; //null

    @Column(name = "cantAparatosElectronicos", nullable = true)
    private int cantAparatosElectronicos;

    @Column(name = "totalConsumoW", nullable = true)
    private double totalConsumoW; //null

    @Column(name = "totalPesos", nullable = true)
    private double totalPesos; //null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idSectorGeneral", nullable = false)
    private SectorGeneral sectorGeneral;

    @OneToMany(mappedBy = "sectorEspecifico", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AparatoElectronicoUsuario> aparatosElectronicosUsuario = new HashSet<>();

    public SectorEspecifico() {
    }

    public SectorEspecifico(Long id, String nomSectorEspecifico) {
        this.id = id;
        this.nomSectorEspecifico = nomSectorEspecifico;
    }

    public int getCantAparatosElectronicos() {
        return cantAparatosElectronicos;
    }

    public void setCantAparatosElectronicos(int cantAparatosElectronicos) {
        this.cantAparatosElectronicos = cantAparatosElectronicos;
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

    public SectorGeneral getSectorGeneral() {
        return sectorGeneral;
    }

    public void setSectorGeneral(SectorGeneral sectorGeneral) {
        this.sectorGeneral = sectorGeneral;
    }

    public Set<AparatoElectronicoUsuario> getAparatosElectronicosUsuario() {
        return aparatosElectronicosUsuario;
    }

    public void setAparatosElectronicosUsuario(Set<AparatoElectronicoUsuario> aparatosElectronicosUsuario) {
        this.aparatosElectronicosUsuario = aparatosElectronicosUsuario;
    }
}