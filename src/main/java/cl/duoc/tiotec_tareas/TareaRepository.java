package cl.duoc.tiotec_tareas;

import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository ya trae los métodos para guardar, buscar y borrar tareas.
// <Tarea, Long> significa: trabaja con la clase Tarea y su id es de tipo Long.
public interface TareaRepository extends JpaRepository<Tarea, Long> {
}