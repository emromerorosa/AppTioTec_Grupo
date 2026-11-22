package cl.duoc.tiotec_tareas;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// @Entity le dice a Spring que esta clase es una tabla de la base de datos
@Entity
public class Tarea {

    // @Id marca la clave primaria; la base de datos genera el número sola
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Texto de la tarea preventiva
    private String title;

    // true si la tarea ya está hecha
    private boolean completed;

    // Constructor vacío (Spring lo necesita)
    public Tarea() {
    }

    // Getters y setters: sirven para leer y cambiar cada campo
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}