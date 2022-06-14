package com.api.kwhcalculator.dto;

import com.api.kwhcalculator.modelos.SectorGeneral;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

//LOS ATRIBUTOS QUE TENGA ESTE OBJETO COMPONDRÁN EL FORMATO DEL JSON QUE SE USARÁ PARA REALIZAR REQUEST
public class UsuarioDTO {

    private Long id;
    private String username;
    private String email;

    private String password;

    //añadiendo este atributo, al hacer una request se nos mostrará una lista de sectores generales asociadas al usuario
    //private Set<SectorGeneral> sectoresGenerales;

    public UsuarioDTO() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /*
    public Set<SectorGeneral> getSectoresGenerales() {
        return sectoresGenerales;
    }

    public void setSectoresGenerales(Set<SectorGeneral> sectoresGenerales) {
        this.sectoresGenerales = sectoresGenerales;
    }
    */
}
