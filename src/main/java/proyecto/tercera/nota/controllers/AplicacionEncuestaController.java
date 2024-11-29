package proyecto.tercera.nota.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import proyecto.tercera.nota.DTO.AplicacionEncuestaDTO;
import proyecto.tercera.nota.entities.AplicacionEncuesta;
import proyecto.tercera.nota.services.AplicacionEncuestaService;

@RestController
@RequestMapping("/api/aplicacion-encuestas")
public class AplicacionEncuestaController {
    @Autowired
    private AplicacionEncuestaService aplicacionEncuestaService;

    @PostMapping
    public ResponseEntity<AplicacionEncuesta> aplicarEncuesta(@RequestBody AplicacionEncuestaDTO aplicacionDTO) {
        AplicacionEncuesta nuevaAplicacion = aplicacionEncuestaService.guardarAplicacion(aplicacionDTO);
        return new ResponseEntity<>(nuevaAplicacion, HttpStatus.CREATED);
    }

    @GetMapping("/profesor/{profesorId}")
    public List<AplicacionEncuesta> listarAplicaciones(@PathVariable Long profesorId) {
        return aplicacionEncuestaService.listarAplicacionesPorProfesor(profesorId);
    }
}
