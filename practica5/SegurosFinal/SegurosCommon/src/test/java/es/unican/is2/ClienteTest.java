package es.unican.is2;
 
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClienteTest {
 
    //-----------------------------AUXILIAR-----------------------------//
 
    private Cliente clienteNormal;
    private Cliente clienteMinusvalia;

    private static final LocalDate FECHA_ANTIGUA = LocalDate.now().minusYears(2);

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
 
    //Total con ningún seguro
    @Test
    void testTotalSeguros_Vacia() {
        assertEquals(0.0, clienteNormal.totalSeguros(), 0.001);
    }
 
    //Comprobamos que funcione con un seguro
    @Test
    void testTotalSeguros_Uno() {
        Seguro s = crearSeguro("1234ABC", Cobertura.TERCEROS, 70, FECHA_ANTIGUA);
        clienteNormal.anhadeSeguro(s);
        assertEquals(400.0, clienteNormal.totalSeguros(), 0.001);
    }
 
    //Comprobamos que sume correctamente los seguros
    @Test
    void testTotalSeguros_Suma() {
        Seguro s1 = crearSeguro("1234ABC", Cobertura.TERCEROS, 70, FECHA_ANTIGUA);     
        Seguro s2 = crearSeguro("5678DEF", Cobertura.TODO_RIESGO, 150, FECHA_ANTIGUA);
        //400 + 1200
        clienteNormal.anhadeSeguro(s1);
        clienteNormal.anhadeSeguro(s2);
        assertEquals(1600.0, clienteNormal.totalSeguros(), 0.001);
    }
 
    //Comprobamos que se añade el seguro correctamente
    @Test
    void testAnhadeSeguro() {
        Seguro s = crearSeguro("1234ABC", Cobertura.TERCEROS, 70, FECHA_ANTIGUA);
        clienteNormal.anhadeSeguro(s);
        assertEquals(1, clienteNormal.getSeguros().size());
        assertTrue(clienteNormal.getSeguros().contains(s));
    }
 
    //Comprobamos que se elimina el seguro correcto
    @Test
    void testEliminaSeguro() {
        Seguro s1 = crearSeguro("1234ABC", Cobertura.TERCEROS, 70, FECHA_ANTIGUA);
        Seguro s2 = crearSeguro("5678DEF", Cobertura.TODO_RIESGO, 150, FECHA_ANTIGUA);
        clienteNormal.anhadeSeguro(s1);
        clienteNormal.anhadeSeguro(s2);
 
        clienteNormal.eliminaSeguro(s1);
 
        List<Seguro> seguros = clienteNormal.getSeguros();
        assertEquals(1, seguros.size());
        assertFalse(seguros.contains(s1));
        assertTrue(seguros.contains(s2));
    }
 
    //Intentamos buscar un seguro que si existe
    @Test
    void testBuscaSeguro_Existe() {
        Seguro s = crearSeguro("1234ABC", Cobertura.TERCEROS, 70, FECHA_ANTIGUA);
        clienteNormal.anhadeSeguro(s);
        assertSame(s, clienteNormal.buscaSeguro("1234ABC"));
    }
 
    //Intentamos buscar un seguro que no existe
    @Test
    void testBuscaSeguro_NoExiste() {
        assertNull(clienteNormal.buscaSeguro("ZZZZZZ"));
    }
 
    //Creamos tres seguros y miramos si retorna el correcto
    @Test
    void testBuscaSeguro_Varios() {
        Seguro s1 = crearSeguro("AAAA00", Cobertura.TERCEROS,     70,  FECHA_ANTIGUA);
        Seguro s2 = crearSeguro("BBBB11", Cobertura.TODO_RIESGO, 150, FECHA_ANTIGUA);
        Seguro s3 = crearSeguro("CCCC22", Cobertura.TERCEROS_LUNAS, 100, FECHA_ANTIGUA);
        clienteNormal.anhadeSeguro(s1);
        clienteNormal.anhadeSeguro(s2);
        clienteNormal.anhadeSeguro(s3);
        assertSame(s2, clienteNormal.buscaSeguro("BBBB11"));
    }
}