package es.unican.is2;
 
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SeguroTest {

    //-----------------------------AUXILIAR-----------------------------//

    private Cliente clienteNormal;
    private Cliente clienteMinusvalia;

    private static final LocalDate MANANA = LocalDate.now().plusDays(1);
    private static final LocalDate FECHA_ANTIGUA = LocalDate.now().minusYears(2);
    private static final LocalDate ESTE_ANIO = LocalDate.of(LocalDate.now().getYear(), 1, 1);

    @BeforeEach
    void setUp() {
        clienteNormal = new Cliente();
        clienteNormal.setDni("63338474X");
        clienteNormal.setNombre("Diego Alonso");
        clienteNormal.setMinusvalia(false);
 
        clienteMinusvalia = new Cliente();
        clienteMinusvalia.setDni("97654756D");
        clienteMinusvalia.setNombre("Raúl Rivero");
        clienteMinusvalia.setMinusvalia(true);
    }

    //Constructor de seguro
    private Seguro crearSeguro(String matricula, Cobertura cobertura,
                               int potencia, LocalDate fechaInicio) {
        Seguro s = new Seguro();
        s.setMatricula(matricula);
        s.setCobertura(cobertura);
        s.setPotencia(potencia);
        s.setFechaInicio(fechaInicio);
        return s;
    }

    //-------------------------------TESTS-------------------------------//

    //P1
    @Test
    void testPrecio_FechaInicioNull_DevuelveCero() {
        Seguro s = crearSeguro("1234ABC", Cobertura.TERCEROS, 70, null);
        assertEquals(0.0, s.precio(clienteNormal), 0.001);
    }

    //P2
    @Test
    void testPrecio_FechaInicioFutura_DevuelveCero() {
        Seguro s = crearSeguro("1234ABC", Cobertura.TERCEROS, 70, MANANA);
        assertEquals(0.0, s.precio(clienteNormal), 0.001);
    }

    //P3
    @Test
    void testPrecio_Terceros_PotenciaBaja_MasUnAnio_SinMinusvalia() {
        Seguro s = crearSeguro("1234ABC", Cobertura.TERCEROS, 70, FECHA_ANTIGUA);
        assertEquals(400.0, s.precio(clienteNormal), 0.001);
    }

    //P4
    @Test
    void testPrecio_TercerosLunas_PotenciaMedia_MasUnAnio_SinMinusvalia() {
        Seguro s = crearSeguro("1234ABC", Cobertura.TERCEROS_LUNAS, 100, FECHA_ANTIGUA);
        assertEquals(630.0, s.precio(clienteNormal), 0.001);
    }

    //P5
    @Test
    void testPrecio_TodoRiesgo_PotenciaAlta_MasUnAnio_SinMinusvalia() {
        Seguro s = crearSeguro("1234ABC", Cobertura.TODO_RIESGO, 150, FECHA_ANTIGUA);
        assertEquals(1200.0, s.precio(clienteNormal), 0.001);
    }

    //P6
    @Test
    void testPrecio_Terceros_PotenciaBaja_MismoAnio_SinMinusvalia() {
        // mult base=1.0, resta 0.2 → mult=0.8
        Seguro s = crearSeguro("1234ABC", Cobertura.TERCEROS, 70, ESTE_ANIO);
        assertEquals(320.0, s.precio(clienteNormal), 0.001);
    }

    //P7
    @Test
    void testPrecio_Terceros_PotenciaBaja_MasUnAnio_ConMinusvalia() {
        Seguro s = crearSeguro("1234ABC", Cobertura.TERCEROS, 70, FECHA_ANTIGUA);
        assertEquals(300.0, s.precio(clienteMinusvalia), 0.001);
    }

    //P8
    @Test
    void testPrecio_LimitePotencia89_Mult1() {
        Seguro s = crearSeguro("1234ABC", Cobertura.TERCEROS, 89, FECHA_ANTIGUA);
        assertEquals(400.0, s.precio(clienteNormal), 0.001);
    }

    //P9
    @Test
    void testPrecio_LimitePotencia90_Mult105() {
        Seguro s = crearSeguro("1234ABC", Cobertura.TERCEROS, 90, FECHA_ANTIGUA);
        assertEquals(420.0, s.precio(clienteNormal), 0.001);
    }

    //P10
    @Test
    void testPrecio_LimitePotencia110_Mult105() {
        Seguro s = crearSeguro("1234ABC", Cobertura.TERCEROS, 110, FECHA_ANTIGUA);
        assertEquals(420.0, s.precio(clienteNormal), 0.001);
    }

    //P11
    @Test
    void testPrecio_LimitePotencia111_Mult12() {
        Seguro s = crearSeguro("1234ABC", Cobertura.TERCEROS, 111, FECHA_ANTIGUA);
        assertEquals(480.0, s.precio(clienteNormal), 0.001);
    }

    //P12
    @Test
    void testPrecio_TodoRiesgo_PotenciaAlta_MismoAnio_ConMinusvalia() {
        Seguro s = crearSeguro("1234ABC", Cobertura.TODO_RIESGO, 150, ESTE_ANIO);
        assertEquals(750.0, s.precio(clienteMinusvalia), 0.001);
    }
}