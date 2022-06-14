package com.api.kwhcalculator.seguridad;

public class JWTAuthResponseDTO {

    //se tuvieron que modificar los constructores (tokenAcceso, tipoToken) (crearlos de nuevo de ser necesario)
    private String usernameOrEmail;

    private Long idUsuario; //a pedido de victor

    private String tokenDeAcceso;
    private String tipoDeToken = "Bearer";

    public JWTAuthResponseDTO() {
    }

    public JWTAuthResponseDTO(String tokenDeAcceso) {
        this.tokenDeAcceso = tokenDeAcceso;
    }

    public JWTAuthResponseDTO(String usernameOrEmail, String tokenDeAcceso) {
        this.usernameOrEmail = usernameOrEmail;
        this.tokenDeAcceso = tokenDeAcceso;
    }

    public JWTAuthResponseDTO(String usernameOrEmail, Long idUsuario, String tokenDeAcceso) {
        this.usernameOrEmail = usernameOrEmail;
        this.idUsuario = idUsuario;
        this.tokenDeAcceso = tokenDeAcceso;
    }

    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
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

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
}
