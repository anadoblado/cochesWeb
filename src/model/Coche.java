package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the coche database table.
 * 
 */
@Entity
@NamedQuery(name="Coche.findAll", query="SELECT c FROM Coche c")
public class Coche extends Entidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String bastidor;

	private String color;

	private String modelo;

	//bi-directional many-to-one association to Fabricante
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idfabricante")
	private Fabricante fabricante;

	//bi-directional many-to-one association to Venta
	@OneToMany(mappedBy="coche")
	private List<Venta> ventas;

	public Coche() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBastidor() {
		return this.bastidor;
	}

	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}

	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getModelo() {
		return this.modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public Fabricante getFabricante() {
		return this.fabricante;
	}

	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}

	public List<Venta> getVentas() {
		return this.ventas;
	}

	public void setVentas(List<Venta> ventas) {
		this.ventas = ventas;
	}

	public Venta addVenta(Venta venta) {
		getVentas().add(venta);
		venta.setCoche(this);

		return venta;
	}

	public Venta removeVenta(Venta venta) {
		getVentas().remove(venta);
		venta.setCoche(null);

		return venta;
	}

	@Override
	public String toString() {
		return modelo + ", " + color;
	}
	
	public boolean equals(Object obj) {
		Coche c = (Coche) obj;
		if (this.id == c.id) {
			return true;
		} 
		
		return false;
		
	}

	
}