package com.crud.crudProyecto.repository;

import com.crud.crudProyecto.entity.CrudUsuario;
import com.crud.crudProyecto.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

    // Buscar proyectos de un usuario específico
    List<ProjectEntity> findByUsuario(CrudUsuario usuario);

    // Buscar un proyecto por nombre y usuario (para evitar duplicados)
    Optional<ProjectEntity> findByUsuarioAndNombreProyecto(CrudUsuario usuario, String nombreProyecto);

}
