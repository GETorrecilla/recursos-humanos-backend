package gm.rh.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "empleado")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmpleado;

    private String nombre;
    private String departamento;
    private Double sueldo;

    // -- Constructores --
    public Empleado() {
    }

    public Empleado(Long idEmpleado, String nombre, String departamento, Double sueldo) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.departamento = departamento;
        this.sueldo = sueldo;
    }

    public Empleado(String nombre, String departamento, Double sueldo) {
        this.nombre = nombre;
        this.departamento = departamento;
        this.sueldo = sueldo;
    }

}
