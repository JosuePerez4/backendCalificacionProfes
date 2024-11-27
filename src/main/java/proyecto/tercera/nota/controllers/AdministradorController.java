package proyecto.tercera.nota.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import proyecto.tercera.nota.DTO.AdministradorDTO;
import proyecto.tercera.nota.entities.Administrador;
import proyecto.tercera.nota.services.AdministradorServices;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/administradores")
@CrossOrigin(origins = "*") // Permitir solicitudes CORS
public class AdministradorController {

    @Autowired
    private AdministradorServices administradorService;

    // Obtener todos los administradores
    @GetMapping
    public List<AdministradorDTO> obtenerTodosLosAdministradores() {
        // Convertir la lista de Administrador a una lista de AdministradorDTO
        return administradorService.obtenerAdministradores().stream()
                .map(admin -> new AdministradorDTO(admin.getUsuario(), admin.getCorreo()))
                .collect(Collectors.toList());
    }

    // Obtener un administrador por su usuario
    @GetMapping("/{usuario}")
    public AdministradorDTO obtenerAdministradorPorUsuario(@PathVariable String usuario) {
        Administrador administrador = administradorService.obtenerAdministrador(usuario);
        if (administrador != null) {
            return new AdministradorDTO(administrador.getUsuario(), administrador.getCorreo());
        }
        return null; // Manejo básico, puede mejorarse para lanzar excepciones o devolver un error personalizado.
    }
}
