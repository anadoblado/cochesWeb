package model.controladores;

import java.util.List;



import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import model.Cliente;
import model.Controlador;



public class ClienteControlador extends Controlador {

	private static ClienteControlador controlador = null;

	public ClienteControlador () {
		super(Cliente.class, "VentaDeCoches");
	}
	
	/**
	 * 
	 * @return
	 */
	public static ClienteControlador getControlador () {
		if (controlador == null) {
			controlador = new ClienteControlador();
		}
		return controlador;
	}

	/**
	 *  
	 */
	public Cliente find (int id) {
		return (Cliente) super.find(id);
	}

	
	/**
	 * 
	 * @return
	 */
	public Cliente findFirst () {
		try {
			EntityManager em = getEntityManagerFactory().createEntityManager();
			Query q = em.createQuery("SELECT c FROM Cliente c order by c.id", Cliente.class);
			Cliente resultado = (Cliente) q.setMaxResults(1).getSingleResult();
			em.close();
			return resultado;
		}
		catch (NoResultException nrEx) {
			return null;
		}
	}

	
	
	
	/**
	 * 
	 * @return
	 */
	public Cliente findLast () {
		try {
			EntityManager em = getEntityManagerFactory().createEntityManager();
			Query q = em.createQuery("SELECT c FROM Cliente c order by c.id desc", Cliente.class);
			Cliente resultado = (Cliente) q.setMaxResults(1).getSingleResult();
			em.close();
			return resultado;
		}
		catch (NoResultException nrEx) {
			return null;
		}
	}

	
	
	
	/**
	 * 
	 * @return
	 */
	public Cliente findNext (Cliente c) {
		try {
			EntityManager em = getEntityManagerFactory().createEntityManager();
			Query q = em.createQuery("SELECT c FROM Cliente c where c.id > :idActual order by c.id", Cliente.class);
			q.setParameter("idActual", c.getId());
			Cliente resultado = (Cliente) q.setMaxResults(1).getSingleResult();
			em.close();
			return resultado;
		}
		catch (NoResultException nrEx) {
			return null;
		}
	}

	
	
	
	/**
	 * 
	 * @return
	 */
	public Cliente findPrevious (Cliente c) {
		try {
			EntityManager em = getEntityManagerFactory().createEntityManager();
			Query q = em.createQuery("SELECT c FROM Cliente c where c.id < :idActual order by c.id desc", Cliente.class);
			q.setParameter("idActual", c.getId());
			Cliente resultado = (Cliente) q.setMaxResults(1).getSingleResult();
			em.close();
			return resultado;
		}
		catch (NoResultException nrEx) {
			return null;
		}
	}

	
	
	
	public List<Cliente> findAll () {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		Query q = em.createQuery("SELECT c FROM Cliente c", Cliente.class);
		List<Cliente> resultado = (List<Cliente>) q.getResultList();
		em.close();
		return resultado;
	}
	
	/**
	 * M�todo que limita a 5 los resultados de una lista
	 * @param limit
	 * @param offset
	 * @return
	 */
	public List<Cliente> findAllLimited (int limit, int offset) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		Query q = em.createQuery("SELECT c FROM Cliente c", Cliente.class);
		q.setMaxResults(limit);
		q.setFirstResult(offset);
		List<Cliente> resultado = (List<Cliente>) q.getResultList();
		em.close();
		return resultado;
	}

	/**
	 * M�todo que saca un entero con el valor de la cantidad de registros
	 * @return
	 */
	public int numRegistros() {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		Query q = em.createNativeQuery("SELECT count(*) FROM Cliente");
		Long cantidad = (Long) q.getSingleResult();
		em.close();
		return cantidad.intValue();
		
	}
	
	public static String toString (Cliente cliente) {
		return "Id: " + cliente.getId() + " - Nombre: " + cliente.getNombre() + 
				" - Apellidos: " + cliente.getApellidos() + " - fecha de nacimiento: " + cliente.getFechaNac() +
				" - Dni: " + cliente.getDniNie() + " - Localidad: " + cliente.getLocalidad() + 
				"- Activo: " + cliente.getActivo(); 
	}

	
	
}
