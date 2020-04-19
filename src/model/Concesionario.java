package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the concesionario database table.
 * 
 */
@Entity
@NamedQuery(name="Concesionario.findAll", query="SELECT c FROM Concesionario c")
public class Concesionario extends Entidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String cif;

	private String localidad;

	private String nombre;

	//bi-directional many-to-one association to Venta
	@OneToMany(mappedBy="concesionario")
	private List<Venta> ventas;

	public Concesionario() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCif() {
		return this.cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getLocalidad() {
		return this.localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Venta> getVentas() {
		return this.ventas;
	}

	public void setVentas(List<Venta> ventas) {
		this.ventas = ventas;
	}

	public Venta addVenta(Venta venta) {
		getVentas().add(venta);
		venta.setConcesionario(this);

		return venta;
	}

	public Venta removeVenta(Venta venta) {
		getVentas().remove(venta);
		venta.setConcesionario(null);

		return venta;
	}

	@Override
	public String toString() {
		return nombre;
	}
	
	public boolean equals(Object obj) {
		Concesionario c = (Concesionario) obj;
		if (this.id == c.id) {
			return true;
		} 
		
		return false;
		
	}
	
	

}