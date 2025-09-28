package gm.rh.controlador;

import gm.rh.modelo.Empleado;
import gm.rh.servicio.EmpleadoServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/empleados")  // http://localhost:8080/api/empleados
@CrossOrigin(origins = "*") //Ajusta el origen en producción (ej. http://localhost:3000
public class EmpleadoControlador {

    private final EmpleadoServicio empleadoServicio;

    public EmpleadoControlador(EmpleadoServicio empleadoServicio) {
        this.empleadoServicio = empleadoServicio;
    }

    //GET /api/empleados -> Lista todos
    @GetMapping
    public ResponseEntity<List<Empleado>> listarEmpleados() {
        return ResponseEntity.ok(empleadoServicio.listarEmpleado());
    }

    //GET /api/empleados/{id} -> detalle por id
    @GetMapping("/{id}")
    public ResponseEntity<Empleado> buscarEmpleadoPorId(@PathVariable Long id) {
        Empleado emp = empleadoServicio.obtenerEmpleadoPorId(id);
        if (emp == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(emp);
    }

    //POST /api/empleados -> crear
    @PostMapping
    public ResponseEntity<Empleado> crearEmpleado(@RequestBody Empleado empleado) {
        //Ignoramos un id entrante para evitar conflictos
        empleado.setIdEmpleado(null);
        Empleado creado = empleadoServicio.guardarEmpleado(empleado);
        URI location = URI.create("/api/empleados/"+creado.getIdEmpleado());
        return ResponseEntity.created(location).body(creado);
    }

    //PUT /api/empleados/{id} -> actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable Long id, @RequestBody Empleado empleado) {
        Empleado existente = empleadoServicio.obtenerEmpleadoPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        //Forzamos el id del path para la actualización
        empleado.setIdEmpleado(id);
        Empleado actualizado = empleadoServicio.guardarEmpleado(empleado);
        return ResponseEntity.ok(actualizado);
    }

    //DELETE /api/empleados/{id} -> eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable Long id) {
        Empleado existente = empleadoServicio.obtenerEmpleadoPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        empleadoServicio.eliminarEmpleado(id);
        return ResponseEntity.noContent().build();
    }
}
