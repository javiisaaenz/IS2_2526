package es.unican.is2;

import org.assertj.swing.fixture.FrameFixture;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VistaAgenteIT {

    private FrameFixture frame;

    @BeforeEach
    public void setUp() throws DataAccessException {
        H2ServerConnectionManager.getConnection();
        IClientesDAO daoClientes = new ClientesDAO();
        ISegurosDAO daoSeguros = new SegurosDAO();
        GestionSeguros gestion = new GestionSeguros(daoClientes, daoSeguros);

        VistaAgente vista = new VistaAgente(gestion, gestion, gestion);
        frame = new FrameFixture(vista);
        vista.setVisible(true);
    }

    @AfterEach
    public void tearDown() {
        frame.cleanUp();
    }

    // CP1: DNI existente, cliente sin seguros (Luis, 33333333A)
    @Test
    public void testClienteExistenteSinSeguros() {
        frame.textBox("txtDNICliente").enterText("33333333A");
        frame.button("btnBuscar").click();

        frame.textBox("txtNombreCliente").requireText("Luis");
        frame.list("listSeguros").requireItemCount(0);
    }

    // CP2: DNI existente, cliente con un seguro (Ana, 22222222A)
    @Test
    public void testClienteExistenteConUnSeguro() {
        frame.textBox("txtDNICliente").enterText("22222222A");
        frame.button("btnBuscar").click();

        frame.textBox("txtNombreCliente").requireText("Ana");
        frame.list("listSeguros").requireItemCount(1);
        assertArrayEquals(
            new String[]{"2222AAA TERCEROS_LUNAS"},
            frame.list("listSeguros").contents());
    }

    // CP3: DNI existente, cliente con varios seguros (Juan, 11111111A)
    @Test
    public void testClienteExistenteConVariosSeguros() {
        frame.textBox("txtDNICliente").enterText("11111111A");
        frame.button("btnBuscar").click();

        frame.textBox("txtNombreCliente").requireText("Juan");
        frame.list("listSeguros").requireItemCount(3);
        assertArrayEquals(
            new String[]{"2222AAA TERCEROS_LUNAS"},
            frame.list("listSeguros").contents());

    }

    // CP4: DNI no existente
    @Test
    public void testClienteNoExistente() {
        frame.textBox("txtDNICliente").enterText("00000000Z");
        frame.button("btnBuscar").click();

        frame.textBox("txtNombreCliente").requireText("Error en BBDD");
        frame.textBox("txtTotalCliente").requireText("");
        frame.list("listSeguros").requireItemCount(0);
    }
    //CP5: Error en la DB
    @Test
    public void testErrorConexionBaseDatos() throws Exception {
        // Cerramos la conexión para forzar el fallo en la siguiente consulta
        H2ServerConnectionManager.connection.close();
        H2ServerConnectionManager.connection = null;
 
        // Introducimos un DNI cualquiera y pulsamos Buscar
        frame.textBox("txtDNICliente").enterText("11111111A");
        frame.button("btnBuscar").click();
 
        // La vista debe mostrar el mensaje de error y limpiar los campos
        frame.textBox("txtNombreCliente").requireText("Error en BBDD");
        frame.textBox("txtTotalCliente").requireText("");
        frame.list("listSeguros").requireItemCount(0);
    }
}