package com.api.kwhcalculator.controladores;

import com.api.kwhcalculator.dto.UsuarioDTO;
import com.api.kwhcalculator.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

//requestMapping mapea la url de la api Rest
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    //Mostrar todos los usuarios(se puede agregar SI SE QUIERE la cantidad de páginas a mostrar y el tamaño de elementos de cada página que se muestra)
    @GetMapping
    public List<UsuarioDTO> listarUsuarios(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int numeroDePagina,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int medidaDePagina) {
        return usuarioServicio.obtenerTodosLosUsuarios(numeroDePagina, medidaDePagina);
    }

    //Mostrar usuario por ID
    @GetMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPorId(@PathVariable (name = "idUsuario") long idUsuario) {
        return ResponseEntity.ok(usuarioServicio.obtenerUsuarioPorId(idUsuario));
    }

    //Insertar usuario (@Valid las validaciones siempre deben estar al lado de @RequestBody)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UsuarioDTO> crearUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        //si esto sale bien, se retornará un HttpStatus.Created que indica que se ha creado correctamente
        return new ResponseEntity<>(usuarioServicio.crearUsuario(usuarioDTO), HttpStatus.CREATED);
    }

    //Modificar usuario por ID (@Valid las validaciones siempre deben estar al lado de @RequestBody)
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDTO> modificarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO, @PathVariable (name = "idUsuario") long idUsuario) {
        UsuarioDTO usuarioRespuesta = usuarioServicio.modificarUsuario(usuarioDTO, idUsuario);
        return new ResponseEntity<>(usuarioRespuesta, HttpStatus.OK);
    }

    //Eliminar usuario por ID
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(("/{idUsuario}"))
    public ResponseEntity<String> eliminarUsuario(@PathVariable (name = "idUsuario") long idUsuario) {
        usuarioServicio.eliminarUsuario(idUsuario);
        return new ResponseEntity<>("Usuario eliminado exitosamente", HttpStatus.OK);
    }

}
