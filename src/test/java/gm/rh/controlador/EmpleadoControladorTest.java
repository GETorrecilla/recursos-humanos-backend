package gm.rh.controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import gm.rh.modelo.Empleado;
import gm.rh.servicio.EmpleadoServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmpleadoControlador.class)
public class EmpleadoControladorTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmpleadoServicio empleadoServicio;

    @Autowired
    private ObjectMapper objectMapper;

    private Empleado empleado1;
    private Empleado empleado2;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public EmpleadoServicio empleadoServicio() {
            return Mockito.mock(EmpleadoServicio.class);
        }
    }

    @BeforeEach
    void setUp() {
        empleado1 = new Empleado(1L,"Danna","It",2680.5);
        empleado2 = new Empleado(2L,"Lucas","Ventas",3200.0);
    }

    @Test
    void listarEmpleados_DeberiaRetornarListaEmpleados() throws Exception {
        List<Empleado> listaEmpleado = Arrays.asList(empleado1,empleado2);
        when(empleadoServicio.listarEmpleado()).thenReturn(listaEmpleado);

        mockMvc.perform(get("/api/empleados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nombre").value("Danna"))
                .andExpect(jsonPath("$[1].nombre").value("Lucas"));
    }

    @Test
    void obtenerEmpleadoPorId_CuandoExiste_DeberiaRetornarEmpleado() throws Exception {
        when(empleadoServicio.obtenerEmpleadoPorId(1L)).thenReturn(empleado1);

        mockMvc.perform(get("/api/empleados/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Danna"));
    }

    @Test
    void obtenerEmpleadoPorId_CuandoNoExiste_DeberiaRetornar404() throws Exception {
        when(empleadoServicio.obtenerEmpleadoPorId(99L)).thenReturn(null);

        mockMvc.perform(get("/api/empleados/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void guardarEmpleado_DeberiaRetornarEmpleadoGuardado() throws Exception {
        Empleado nuevo = new Empleado("Marcos","RRHH", 2700.0);
        when(empleadoServicio.guardarEmpleado(Mockito.any(Empleado.class))).thenReturn(nuevo);

        mockMvc.perform(post("/api/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Marcos"));
    }

    @Test
    void eliminarEmpleado_DeberiaRetornarNoContent() throws Exception {
        mockMvc.perform(delete("/api/empleados/1"))
                .andExpect(status().isNoContent());
    }
}
