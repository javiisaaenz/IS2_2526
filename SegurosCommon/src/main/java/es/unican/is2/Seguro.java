package es.unican.is2;

import java.time.LocalDate;

/**
 * Clase que representa un seguro de coche.
 */
public class Seguro {
	
	private long id;

    private String matricula;

	private int potencia;

    private Cobertura cobertura;
    
    private LocalDate fechaInicio;

	private String conductorAdicional;

	/**
	 * Retorna el identificador del seguro
	 */
	public long getId() {
		return id;
	}

	/**
	 *  Asigna el valor del identificador del seguro
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Retorna la matricula del coche 
	 * asociado al seguro
	 */
	public String getMatricula() {
		return matricula;
	}

	/**
	 *  Asigna el valor de la matrícula del seguro
	 */
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	/**
	 * Retorna la fecha de contratacion del seguro
	 */
	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * Asigna la fecha de inicio del seguro
	 * @param fechaInicio La fecha de inicio del seguro
	 */
	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * Retorna el tipo de cobertura del seguro
	 */
	public Cobertura getCobertura() {
		return cobertura;
	}

	/**
	 * Asigna el tipo de cobertura del seguro
	 * @param cobertura El tipo de cobertura del seguro
	 */	
	public void setCobertura(Cobertura cobertura) {
		this.cobertura = cobertura;		
}

	/**
     * Retorna la potencia del coche asociado 
     * al seguro. 
     */
    public int getPotencia() {
        return potencia;
    }

	/**
	 *  Asigna el valor del identificador del seguro
	 */
	public void setPotencia(int potencia) {
		this.potencia = potencia;
	}

	/**
	 * Retorna el conductor adicional del seguro, si lo hay
	 * @return El conductor adicional si lo hay
	 * 		null en caso contrario
	 */
	public String getConductorAdicional() {
		return conductorAdicional;
	}

	/**
	 * Asigna el conductor adicional del seguro
	 * @param conductorAdicional
	 */
	public void setConductorAdicional(String conductorAdicional) {
		this.conductorAdicional = conductorAdicional;
	}
    
    /**
     * Retorna el precio del seguro. 
	 * El precio se calcula a partir de la cobertura, la potencia del coche y el tiempo que lleva contratado el seguro
	 * @return El precio del seguro
	 *         0 si el seguro todavía no está en vigor (no se ha alcanzado su fecha de inicio)
     */
	public double precio(Cliente c) {
		if (fechaInicio == null || fechaInicio.isAfter(LocalDate.now())) {
       		return 0.0;
   	 	}
    
    	double precio = 0.0;
		//precio segun la cobertura
    	switch (cobertura) {
      	  	case TERCEROS:
        	    precio = 400.0;
        	    break;
      	  	case TERCEROS_LUNAS:
        	    precio = 600.0;
            	break;
        	case TODO_RIESGO:
            	precio = 1000.0;
            	break;
   		 }
		//aumento segun la potencia del coche
		double mult = 1.0;
		if (90 <= potencia && potencia <= 110) {
    		mult = 1.05;
		}
		else if (potencia > 110) {
			mult = 1.20;
		}
		//descuento segun si la oferta está activa
		int añosVigor = LocalDate.now().getYear() - fechaInicio.getYear();
		if (añosVigor < 1) {
			mult -= 0.2;
		}
		double precioTotal = precio * mult;	
		if (c.getMinusvalia()) {
			return (precioTotal)* 0.75;
		}
		return precioTotal;
	}

	
	
}
