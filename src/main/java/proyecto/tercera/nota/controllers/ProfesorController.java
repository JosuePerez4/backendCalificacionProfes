package proyecto.tercera.nota.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import proyecto.tercera.nota.DTO.ProfesorDTO;
import proyecto.tercera.nota.services.ProfesorService;

@RestController
@RequestMapping("/api/profesores")
public class ProfesorController {

    @Autowired
    private ProfesorService profesorService;

    @GetMapping("/porUsuario/{correo}")
    public ResponseEntity<List<ProfesorDTO>> obtenerProfesoresPorUsuario(@PathVariable String correo) {
        List<ProfesorDTO> profesoresDTO = profesorService.obtenerProfesoresPorUsuario(correo);
        return ResponseEntity.ok(profesoresDTO);
    }
}
