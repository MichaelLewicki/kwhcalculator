package com.api.kwhcalculator.controladores;

import com.api.kwhcalculator.dto.AparatoElectronicoOficialDTO;
import com.api.kwhcalculator.servicios.AparatoElectronicoOficialServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/aparatosElectronicosOficiales")
public class AparatoElectronicoOficialControlador {

    @Autowired
    private AparatoElectronicoOficialServicio aparatoElectronicoOficialServicio;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<AparatoElectronicoOficialDTO> crearAparatoElectronicoOficial(@Valid @RequestBody AparatoElectronicoOficialDTO aparatoElectronicoOficialDTO) {
        //si esto sale bien, se retornará un HttpStatus.Created que indica que se ha creado correctamente
        return new ResponseEntity<>(aparatoElectronicoOficialServicio.crearAparatoElectronicoOficial(aparatoElectronicoOficialDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public List<AparatoElectronicoOficialDTO> listarAparatosElectronicosOficiales() {
        return aparatoElectronicoOficialServicio.obtenerTodosLosAparatosElectronicosOficiales();
    }

    @GetMapping("/{idAparatoElectronicoOficial}")
    public ResponseEntity<AparatoElectronicoOficialDTO> obtenerAparatoElectronicoOficialPorId(@PathVariable (name = "idAparatoElectronicoOficial") long idAparatoElectronicoOficial) {
        return ResponseEntity.ok(aparatoElectronicoOficialServicio.obtenerAparatoElectronicoOficialPorId(idAparatoElectronicoOficial));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{idAparatoElectronicoOficial}")
    public ResponseEntity<AparatoElectronicoOficialDTO> modificarAparatoElectronicoOficial(@Valid @RequestBody AparatoElectronicoOficialDTO aparatoElectronicoOficialDTO, @PathVariable (name = "idAparatoElectronicoOficial") long idAparatoElectronicoOficial) {
        AparatoElectronicoOficialDTO aparatoElectronicoOficialRespuesta = aparatoElectronicoOficialServicio.modificarAparatoElectronicoOficial(aparatoElectronicoOficialDTO, idAparatoElectronicoOficial);
        return new ResponseEntity<>(aparatoElectronicoOficialRespuesta, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(("/{idAparatoElectronicoOficial}"))
    public ResponseEntity<String> eliminarAparatoElectronicoOficial(@PathVariable (name = "idAparatoElectronicoOficial") long idAparatoElectronicoOficial) {
        aparatoElectronicoOficialServicio.eliminarAparatoElectronicoOficial(idAparatoElectronicoOficial);
        return new ResponseEntity<>("Aparato Electronico Oficial eliminado exitosamente", HttpStatus.OK);
    }


}
