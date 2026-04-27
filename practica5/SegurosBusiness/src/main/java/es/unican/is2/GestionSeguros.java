package es.unican.is2;

/**
 * Lógica de negocio para gestionar los
 * seguros de la empresa de seguros
 */
public class GestionSeguros implements IGestionSeguros, IInfoSeguros, IGestionClientes{

	IClientesDAO daoClientes;
	ISegurosDAO  daoSeguros;
    
	public GestionSeguros(IClientesDAO daoClientes, ISegurosDAO daoSeguros) {
		this.daoClientes = daoClientes;
		this.daoSeguros = daoSeguros;
	}

    /**
	 * Agrega un nuevo seguro al cliente cuyo dni se indica.
	 * @param s Seguro que desea agregar
	 * @param dni DNI del cliente
	 * @return El seguro agregado
	 * 		   null si no se agrega porque no se encuentra el cliente
	 * @throws OperacionNoValida si el seguro ya existe
	 * @throws DataAccessException si se produce un error 
	 * en el acceso a la base de datos
	 */
	@Override
    public Seguro nuevoSeguro(Seguro s, String dni) throws OperacionNoValida, DataAccessException {
		Cliente c = daoClientes.cliente(dni);
		if (c == null) {
			return null;
		}
		Seguro seguroEncontrado = c.buscaSeguro(s.getMatricula());
		if (seguroEncontrado != null) {
			throw new OperacionNoValida("Seguro ya existente");
		}
		daoSeguros.creaSeguro(s);
		c.anhadeSeguro(s);
	
        return s;
    }

    /**
	 * Elimina el seguro cuya matricula se indica y 
	 * que pertenece al cliente cuyo dni se indica
	 * @param matricula Identificador del seguro a eliminar
	 * @param dni DNI del propietario del seguro
	 * @return El seguro eliminado
	 *         null si el seguro o el cliente no existen
	 * @throws OperacionNoValida si el seguro no pertenece al dni indicado
	 * @throws DataAccessException si se produce un error 
	 * en el acceso a la base de datos
	 */
	@Override
	public Seguro bajaSeguro(String matricula, String dni) throws OperacionNoValida, DataAccessException {
		Cliente c = daoClientes.cliente(dni);
		if (c == null) {
			return null;
		}
		Seguro seguro = daoSeguros.seguroPorMatricula(matricula);
		if (seguro == null) {
			return null;
		}
		if (c.buscaSeguro(matricula) == null) {
			throw new OperacionNoValida("El seguro a eliminar no corresponde con el cliente indicado");
		}

		daoSeguros.eliminaSeguro(seguro.getId());
		c.eliminaSeguro(seguro);

        return seguro;
    }

    /**
	 * Agrega o modifica el conductor adicional al seguro cuya matricula se indica
	 * @param matricula Identificador del seguro
	 * @param conductor Nombre del conductor adicional a agregar
	 * @return El seguro modificado
	 *         null si el seguro no existe
	 * @throws DataAccessException si se produce un error 
	 * en el acceso a la base de datos
	 */
	@Override
	public Seguro anhadeConductorAdicional(String matricula, String conductor) throws DataAccessException {
		Seguro seguro = daoSeguros.seguroPorMatricula(matricula);
		if (seguro == null) {
			return null;
		}
		seguro.setConductorAdicional(conductor);
		daoSeguros.actualizaSeguro(seguro);

        return seguro;
    }

    /**
	 * Persiste un nuevo cliente
	 * @param c Cliente que desea persistir
	 * @return El cliente persitido
	 * 		   null si no se persiste porque ya existe
	  * @throws DataAccessException si se produce un error 
	 * en el acceso a la base de datos
	 */
	@Override
	public Cliente nuevoCliente(Cliente c) throws DataAccessException {
		if (daoClientes.cliente(c.getDni()) != null) {
			return null;
		}
		daoClientes.creaCliente(c);

		return c;
    }
	
	/**
	 * Elimina el cliente cuyo dni se indica
	 * @param dni DNI del cliente que se quiere eliminar
	 * @return El cliente eliminado
	 * 		   null si no se elimina porque no se encuentra 
	 * @throws OperacionNoValida si el cliente existe 
	 *         pero tiene seguros a su nombre
	 * @throws DataAccessException si se produce un error 
	 * en el acceso a la base de datos
	 */
	@Override
	public Cliente bajaCliente(String dni) throws OperacionNoValida, DataAccessException {
		Cliente c = daoClientes.cliente(dni);
		if (c == null) {
			return null;
		}
		if (!c.getSeguros().isEmpty()) {
			throw new OperacionNoValida("No es posible eliminar un cliente con seguros asociados");
		}
		daoClientes.eliminaCliente(dni);
        return c;
    }

	/**
	 * Retorna el cliente cuyo dni se indica
	 * @param dni DNI del cliente buscado
	 * @return El cliente cuyo dni coincide
	 * 		   null en caso de que no exista
	 * @throws DataAccessException si se produce un error 
	 * en el acceso a la base de datos
	 */
	@Override
	public Cliente cliente(String dni) throws DataAccessException {
		return daoClientes.cliente(dni);
	}
	
	/**
	 * Retorna el seguro cuya matricula se indica
	 * @param matricula Identificador del seguro
	 * @return El seguro indicado
	 * 	       null si no existe
	* @throws DataAccessException si se produce un error 
	 * en el acceso a la base de datos
	 */
	@Override
	public Seguro seguro(String matricula) throws DataAccessException {
		return daoSeguros.seguroPorMatricula(matricula);
	}



	

 
}
