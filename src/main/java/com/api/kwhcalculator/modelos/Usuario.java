package com.api.kwhcalculator.modelos;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


//entity crea una entidad en la base de datos
//no puede haber un usuario con username repetido
@Entity
@Table(name = "usuario", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"}), @UniqueConstraint(columnNames = {"email"})})
public class Usuario {

    //GeneratedValue da un valor autoincremental
    //en mysql se usa la estrategia de generación de tipo identity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //nullable es que no puede ser null en la BD
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;

    //Relación mucho a muchos con Rol, ya que cada rol contiene puede contener uno o muchos usuarios y lo mismo inversamente
    @JoinTable(name = "usuario_roles", joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "id"))
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REFRESH}) //merge y refresh no generarán nuevos roles, solo añaden el indicado de los que existen a este usuario
    private Set<Rol> roles = new HashSet<>();

    //Uno a muchos con sector general. Cada sectorGnral ingresado estará contenido en un atributo del usuario que creó este objeto. Set es una lista de sectores
    //cascade = CascadeType.ALL permite que al eliminar un Usuario de la BD, se eliminen igual los sectores y todas las instancias dependientes un usuario
    //orphanRemoval = true elimina la instancia de dirección que no sea encontrada automáticamente (si elimino un sectorGnrl, se elimina el sector de la lista que de sectores del usuario)
    @JsonBackReference //esta anotación permite que el json se muestre ordenadamente, sino se producirá una lista gigante de retornos con errores
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SectorGeneral> sectoresGenerales = new HashSet<>();

    public Usuario() {
    }

    public Usuario(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<SectorGeneral> getSectoresGenerales() {
        return sectoresGenerales;
    }

    public void setSectoresGenerales(Set<SectorGeneral> sectoresGenerales) {
        this.sectoresGenerales = sectoresGenerales;
    }

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }
}
