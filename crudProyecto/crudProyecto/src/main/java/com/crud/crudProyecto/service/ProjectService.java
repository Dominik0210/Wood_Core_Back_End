package com.crud.crudProyecto.service;

import com.crud.crudProyecto.entity.CrudUsuario;
import com.crud.crudProyecto.entity.ProjectEntity;
import com.crud.crudProyecto.repository.CrudusuarioRepository;
import com.crud.crudProyecto.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private CrudusuarioRepository usuarioRepository;

    // ✅ Método para crear un proyecto validando que no esté duplicado
    public void create(Long usuarioId, ProjectEntity projectEntity) {
        System.out.println("Buscando usuario con ID: " + usuarioId);

        CrudUsuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 🔹 Verificar si ya existe un proyecto con el mismo nombre para ese usuario
        Optional<ProjectEntity> proyectoExistente = projectRepository.findByUsuarioAndNombreProyecto(usuario, projectEntity.getNombreProyecto());

        if (proyectoExistente.isPresent()) {
            throw new RuntimeException("Error: Ya existe un proyecto con este nombre.");
        }

        System.out.println("Guardando proyecto: " + projectEntity.getNombreProyecto());

        projectEntity.setUsuario(usuario);
        projectRepository.save(projectEntity);
    }

    // ✅ Obtener los proyectos de un usuario específico
    public List<ProjectEntity> getProjectsByUser(Long usuarioId) {
        CrudUsuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return projectRepository.findByUsuario(usuario);
    }

    // ✅ Obtener todos los proyectos
    public List<ProjectEntity> getAll() {
        return projectRepository.findAll();
    }

    // ✅ Método para actualizar un proyecto existente
    public void update(Long id_proyecto, ProjectEntity projectEntity) {
        ProjectEntity entityProject = projectRepository.findById(id_proyecto)
                .orElseThrow(() -> new RuntimeException("Error: Proyecto no encontrado"));

        entityProject.setNombreProyecto(projectEntity.getNombreProyecto());
        entityProject.setPrioridad(projectEntity.getPrioridad());
        entityProject.setSprint_inicio(projectEntity.getSprint_inicio());
        entityProject.setSprint_final(projectEntity.getSprint_final());
        entityProject.setEncargado_proyecto(projectEntity.getEncargado_proyecto());

        projectRepository.save(entityProject);
    }

    // ✅ Método para eliminar un proyecto
    public void delete(long id_proyecto) {
        projectRepository.deleteById(id_proyecto);
    }

    // ✅ Obtener un proyecto por ID
    public ProjectEntity getById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
    }
}
