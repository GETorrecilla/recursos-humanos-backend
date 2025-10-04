package gm.rh.servicio;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import gm.rh.modelo.Empleado;
import gm.rh.repositorio.EmpleadoRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class EmpleadoServicioImplTest {

    @Mock
    private EmpleadoRepositorio empleadoRepositorio;

    @InjectMocks
    private EmpleadoServicioImpl empleadoServicio;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listarEmpleado_DeberiaRetornarListaEmpleados() {

        // Arrange
        Empleado e1 = new Empleado ("Danna","IT",3600.65);
        Empleado e2 = new Empleado ("Lucas","RRHH",1950.15);
        when(empleadoRepositorio.findAll()).thenReturn(Arrays.asList(e1,e2));

        // Act
        List<Empleado> empleados = empleadoServicio.listarEmpleado();

        // Assert
        assertEquals(2,empleados.size());
        verify(empleadoRepositorio, times(1)).findAll();
    }

    @Test
    void guardarEmpleado_DeberiaGuardarYRetornarEmpleado() {
        Empleado e = new Empleado("Marcos","Ventas",1950.5);
        when(empleadoRepositorio.save(e)).thenReturn(e);

        Empleado resultado = empleadoServicio.guardarEmpleado(e);

        assertNotNull(resultado);
        assertEquals("Marcos",resultado.getNombre());
        verify(empleadoRepositorio, times(1)).save(e);
    }

    @Test
    void obtenerEmpleadoPorID_CuandoExiste_DeberiaRetornarEmpleado() {
        Empleado e = new Empleado(1L,"Mariana","Finanzas",6542.4);
        when(empleadoRepositorio.findById(1L)).thenReturn(Optional.of(e));

        Empleado resultado = empleadoServicio.obtenerEmpleadoPorId(1L);

        assertNotNull(resultado);
        assertEquals("Mariana",resultado.getNombre());
    }

    @Test
    void obtenerEmpleadoPorId_CuandoNoExiste_DeberiaRetornarNull() {
        when(empleadoRepositorio.findById(99L)).thenReturn(Optional.empty());

        Empleado resultado = empleadoServicio.obtenerEmpleadoPorId(99L);

        assertNull(resultado);
    }

}
