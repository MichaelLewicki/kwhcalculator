package com.api.kwhcalculator.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegistroUsuarioDTO {

    @NotEmpty(message = "el username ingresado no puede estar vacío")
    @NotNull(message = "el username no debe ser nulo")
    @Size(min = 5, message = "El username del usuario debería tener al menos 5 carácteres")
    private String username;
    @NotEmpty(message = "el email ingresado no puede estar vacío")
    @NotNull(message = "el email no debe ser nulo")
    @Size(min = 8, message = "El email del usuario debería tener al menos 8 carácteres")
    private String email;
    @NotEmpty(message = "la password ingresada no puede estar vacía")
    @NotNull(message = "la password ingresada no debe ser nula")
    @Size(min = 6, message = "La contraseña del usuario debería tener al menos 6 carácteres")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
