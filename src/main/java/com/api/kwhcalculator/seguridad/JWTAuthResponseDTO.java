package com.api.kwhcalculator.seguridad;

public class JWTAuthResponseDTO {

    //este último atributo lo pidió victor, antes no estaba. Se tuvieron que modificar los constructores (tokenAcceso, tipoToken) (crearlos de nuevo de ser necesario)
    private String username;
    private String tokenDeAcceso;
    private String tipoDeToken = "Bearer";

    public JWTAuthResponseDTO() {
    }

    public JWTAuthResponseDTO(String tokenDeAcceso) {
        this.tokenDeAcceso = tokenDeAcceso;
    }

    public JWTAuthResponseDTO(String username, String tokenDeAcceso) {
        this.username = username;
        this.tokenDeAcceso = tokenDeAcceso;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTokenDeAcceso() {
        return tokenDeAcceso;
    }

    public void setTokenDeAcceso(String tokenDeAcceso) {
        this.tokenDeAcceso = tokenDeAcceso;
    }

    public String getTipoDeToken() {
        return tipoDeToken;
    }

    public void setTipoDeToken(String tipoDeToken) {
        this.tipoDeToken = tipoDeToken;
    }
}
