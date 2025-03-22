package com.crud.crudProyecto.controller;

import com.crud.crudProyecto.entity.ProjectEntity;
import com.crud.crudProyecto.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    // Obtener todos los proyectos de un usuario específico
    @GetMapping("/mis-proyectos/{usuarioId}")
    public ResponseEntity<List<ProjectEntity>> obtenerProyectosPorUsuario(@PathVariable Long usuarioId) {
        List<ProjectEntity> proyectos = projectService.getProjectsByUser(usuarioId);
        return ResponseEntity.ok(proyectos);
    }

    // Obtener un proyecto por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProjectEntity> getProjectById(@PathVariable Long id) {
        ProjectEntity project = projectService.getById(id);
        if (project != null) {
            return ResponseEntity.ok(project);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Crear un nuevo proyecto asociado a un usuario
    @PostMapping("/usuario/{usuarioId}")
    public ResponseEntity<String> createProject(@PathVariable Long usuarioId, @RequestBody ProjectEntity projectEntity) {
        projectService.create(usuarioId, projectEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body("Proyecto creado exitosamente");
    }

    // Actualizar un proyecto existente
    @PutMapping("/{id}")
    public ResponseEntity<String> updateProject(@PathVariable("id") Long id, @RequestBody ProjectEntity projectEntity) {
        if (projectEntity.getId_proyecto() == null || !id.equals(projectEntity.getId_proyecto())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El ID del proyecto no coincide o es inválido.");
        }
        projectService.update(id, projectEntity);
        return ResponseEntity.ok("Proyecto actualizado exitosamente");
    }

    // Eliminar un proyecto por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable("id") Long id) {
        try {
            projectService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proyecto no encontrado");
        }
    }
}
