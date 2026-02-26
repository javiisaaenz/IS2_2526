package es.unican.is2;

/**
 * Lógica de negocio para gestionar los
 * seguros de la empresa de seguros
 */
public class GestionSeguros implements IGestionSeguros, IInfoSeguros, IGestionClientes{
    
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
    public Seguro nuevoSeguro(Seguro s, String dni) throws OperacionNoValida, DataAccessException {
        return null;
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
    public Seguro bajaSeguro(String matricula, String dni) throws OperacionNoValida, DataAccessException {
        return null;
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
    public Seguro anhadeConductorAdicional(String matricula, String conductor) throws DataAccessException {
        return null;
    }

    /**
	 * Persiste un nuevo cliente
	 * @param c Cliente que desea persistir
	 * @return El cliente persitido
	 * 		   null si no se persiste porque ya existe
	  * @throws DataAccessException si se produce un error 
	 * en el acceso a la base de datos
	 */
    public Cliente nuevoCliente(Cliente c) throws DataAccessException {
        return null;
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
    public Cliente bajaCliente(String dni) throws OperacionNoValida, DataAccessException {
        return null;
    }

	/**
	 * Retorna el cliente cuyo dni se indica
	 * @param dni DNI del cliente buscado
	 * @return El cliente cuyo dni coincide
	 * 		   null en caso de que no exista
	 * @throws DataAccessException si se produce un error 
	 * en el acceso a la base de datos
	 */
	public Cliente cliente(String dni) throws DataAccessException {
		return null;
	}
	
	/**
	 * Retorna el seguro cuya matricula se indica
	 * @param matricula Identificador del seguro
	 * @return El seguro indicado
	 * 	       null si no existe
	* @throws DataAccessException si se produce un error 
	 * en el acceso a la base de datos
	 */
	public Seguro seguro(String matricula) throws DataAccessException {
		return null;
	}



	

 
}
