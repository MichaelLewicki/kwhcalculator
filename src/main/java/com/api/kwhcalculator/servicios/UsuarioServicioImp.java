package com.api.kwhcalculator.servicios;

import com.api.kwhcalculator.dto.UsuarioDTO;
import com.api.kwhcalculator.excepciones.ResourceNotFoundException;
import com.api.kwhcalculator.modelos.Usuario;
import com.api.kwhcalculator.repositorios.UsuarioRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServicioImp implements UsuarioServicio {

    //inyectar dependencia ModelMapper
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    //insertar Usuario en la BD
    @Override
    public UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO) {
        //1. Convertimos de DTO a entidad (se convierte a una entidad para guardarlo en la base de datos)
        Usuario usuario = mapearEntidad(usuarioDTO);

        //2. Guardar en el repositorio (esta linea inserta o persiste un registro en la base de datos)
        Usuario nuevoUsuario = usuarioRepositorio.save(usuario);
        //luego del .save(), procederemos a dar una respuesta en Json

        //3. Convertimos de entidad a DTO (lo que sigue se usa para devuelver una respuesta del registro en Json,
        //el return lo usaremos para posteriormente colocar la respuesta del registro con algo como ej: el usuario con tales datos ha sido creado)
        UsuarioDTO usuarioRespuesta = mapearDTO(nuevoUsuario);

        /* Esto era así antes de implementar los métodos
        Usuario usuario = new Usuario();
        usuario.setUsername(usuarioDTO.getUsername());
        usuario.setPassword(usuarioDTO.getPassword());

        Usuario nuevoUsuario = usuarioRepositorio.save(usuario);

        UsuarioDTO usuarioRespuesta = new UsuarioDTO();
        usuarioRespuesta.setIdUsuario(nuevoUsuario.getIdUsuario());
        usuarioRespuesta.setUsername(nuevoUsuario.getUsername());
        usuarioRespuesta.setPassword(nuevoUsuario.getPassword());
        return usuarioRespuesta;
        */
        return usuarioRespuesta;
    }

    //Devolver la lista de usuarios de la BD
    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> obtenerTodosLosUsuarios(int numeroDePagina, int medidaDePagina) {
        Pageable pageable = PageRequest.of(numeroDePagina, medidaDePagina);
        Page<Usuario> usuarios = usuarioRepositorio.findAll(pageable);
        List<Usuario> listaDeUsuarios = usuarios.getContent();
        return listaDeUsuarios
                .stream().map(usuario -> mapearDTO(usuario))
                .collect(Collectors.toList());
    }

    //Devolver un usuario buscándolo por su ID (de no encontrarlo, se tirará la excepcion de recurso no encontrado)
    @Override
    @Transactional(readOnly = true)
    public UsuarioDTO obtenerUsuarioPorId(long idUsuario) {
        Usuario usuario = usuarioRepositorio.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "idUsuario", idUsuario));
        return mapearDTO(usuario);
    }

    //Modificar un usuario mediante su ID
    @Override
    public UsuarioDTO modificarUsuario(UsuarioDTO usuarioDTO, long idUsuario) {
        //buscar usuario en la BD
        Usuario usuario = usuarioRepositorio.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "idUsuario", idUsuario));
        //modificar atributos
        usuario.setUsername(usuarioDTO.getUsername());
        usuario.setPassword(usuarioDTO.getPassword());
        //insertar registro en la base de datos y obtener el resultado de la inserción
        Usuario usuarioModificado = usuarioRepositorio.save(usuario);
        //retornar mediante un objeto de transferencia de datos
        return mapearDTO(usuarioModificado);
    }

    //eliminar usuario mediante su ID
    @Override
    public void eliminarUsuario(long idUsuario) {
        Usuario usuario = usuarioRepositorio.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "idUsuario", idUsuario));
        //para eliminar, simplemente utilizar el método de JpaRepository y enviar el objeto entidad que se buscó mediante el ID
        usuarioRepositorio.delete(usuario);
    }

    //Este método convierte entidad a DTO
    private UsuarioDTO mapearDTO(Usuario usuario) {
        //establecer los valores de la entidad (Usuario) al objeto de transferencia (DTO)
        UsuarioDTO usuarioDTO = modelMapper.map(usuario, UsuarioDTO.class);
        /* Esto era así antes de implementar la dependencia Model Mapper
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setUsername(usuario.getUsername());
        usuarioDTO.setPassword(usuario.getPassword());*/
        return usuarioDTO;
    }

    //Este método convierte de DTO a entidad
    private Usuario mapearEntidad(UsuarioDTO usuarioDTO) {
        //establecer los valores del objeto de transferencia (DTO) a la entidad (Usuario)
        Usuario usuario = modelMapper.map(usuarioDTO, Usuario.class);
        return usuario;
    }
}
