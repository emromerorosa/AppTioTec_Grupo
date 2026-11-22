package cl.duoc.tiotec_tareas;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @RestController: esta clase responde peticiones HTTP
// @RequestMapping: todas las rutas empiezan con /tareas
@RestController
@RequestMapping("/tareas")
public class TareaController {

    private final TareaRepository repository;

    // Spring le entrega el repository automáticamente
    public TareaController(TareaRepository repository) {
        this.repository = repository;
    }

    // GET /tareas -> devuelve todas las tareas
    @GetMapping
    public List<Tarea> listar() {
        return repository.findAll();
    }

    // GET /tareas/1 -> devuelve una tarea por su id
    @GetMapping("/{id}")
    public ResponseEntity<Tarea> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /tareas -> crea una tarea nueva
    @PostMapping
    public Tarea crear(@RequestBody Tarea tarea) {
        tarea.setId(null); // para que la base de datos asigne el id
        return repository.save(tarea);
    }

    // PUT /tareas/1 -> actualiza una tarea existente
    @PutMapping("/{id}")
    public ResponseEntity<Tarea> actualizar(@PathVariable Long id, @RequestBody Tarea datos) {
        return repository.findById(id)
                .map(tarea -> {
                    tarea.setTitle(datos.getTitle());
                    tarea.setCompleted(datos.isCompleted());
                    return ResponseEntity.ok(repository.save(tarea));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /tareas/1 -> borra una tarea
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}