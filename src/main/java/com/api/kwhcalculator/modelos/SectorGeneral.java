package com.api.kwhcalculator.modelos;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sectorGeneral")
public class SectorGeneral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nomSectorGnral", nullable = false)
    private String nomSectorGnral;

    @Column(name = "valorKwh", nullable = false)
    private double valorKwh;
    @Column(name = "fechaIngresoValorKwh", nullable = false)
    private LocalDate fechaIngresoValorKwh;
    @Column(name = "mtrsCuadrados", nullable = true)
    private double mtrsCuadrados; //null

    //los atributos que continúan no estoy seguro de si debo mandarlo a la base de datos
    @Column(name = "totalConsumoW", nullable = true)
    private double totalConsumoW; //null

    @Column(name = "totalPesos", nullable = true)
    private double totalPesos; //null

    //muchos sectores gnrales podrán estar en un usuario (fetchType.lazy indica que el usuario se mostrará al cargar esta tabla sólo cuando lo indiquemos)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;

    //esta anotación permite que el json se muestre ordenadamente, sino se producirá una lista gigante de retornos con errores
    //@JsonBackReference
    @OneToMany(mappedBy = "sectorGeneral", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SectorEspecifico> sectoresEspecificos = new HashSet<>();

    public SectorGeneral() {
        super();
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
