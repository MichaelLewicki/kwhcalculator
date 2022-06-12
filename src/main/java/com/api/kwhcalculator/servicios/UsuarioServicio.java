package com.api.kwhcalculator.servicios;

import com.api.kwhcalculator.dto.UsuarioDTO;

import java.util.List;

public interface UsuarioServicio {

    UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO);

    List<UsuarioDTO> obtenerTodosLosUsuarios(int numeroDePagina, int medidaDePagina);

    UsuarioDTO obtenerUsuarioPorId(long idUsuario);

    UsuarioDTO modificarUsuario(UsuarioDTO usuarioDTO, long idUsuario);

    void eliminarUsuario(long idUsuario);

}
