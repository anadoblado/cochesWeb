package model.controladores;

import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import model.Concesionario;
import model.Controlador;



public class ConcesionarioControlador extends Controlador {

	private static ConcesionarioControlador controlador = null;

	public ConcesionarioControlador () {
		super(Concesionario.class, "VentaDeCoches");
	}
	
	/**
	 * 
	 * @return
	 */
	public static ConcesionarioControlador getControlador () {
		if (controlador == null) {
			controlador = new ConcesionarioControlador();
		}
		return controlador;
	}

	/**
	 *  
	 */
	public Concesionario find (int id) {
		return (Concesionario) super.find(id);
	}

	
	/**
	 * 
	 * @return
	 */
	public Concesionario findFirst () {
		try {
			EntityManager em = getEntityManagerFactory().createEntityManager();
			Query q = em.createQuery("SELECT c FROM Concesionario c order by c.id", Concesionario.class);
			Concesionario resultado = (Concesionario) q.setMaxResults(1).getSingleResult();
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
	public Concesionario findLast () {
		try {
			EntityManager em = getEntityManagerFactory().createEntityManager();
			Query q = em.createQuery("SELECT c FROM Concesionario c order by c.id desc", Concesionario.class);
			Concesionario resultado = (Concesionario) q.setMaxResults(1).getSingleResult();
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
	public Concesionario findNext (Concesionario c) {
		try {
			EntityManager em = getEntityManagerFactory().createEntityManager();
			Query q = em.createQuery("SELECT c FROM Concesionario c where c.id > :idActual order by c.id", Concesionario.class);
			q.setParameter("idActual", c.getId());
			Concesionario resultado = (Concesionario) q.setMaxResults(1).getSingleResult();
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
	public Concesionario findPrevious (Concesionario c) {
		try {
			EntityManager em = getEntityManagerFactory().createEntityManager();
			Query q = em.createQuery("SELECT c FROM Concesionario c where c.id < :idActual order by c.id desc", Concesionario.class);
			q.setParameter("idActual", c.getId());
			Concesionario resultado = (Concesionario) q.setMaxResults(1).getSingleResult();
			em.close();
			return resultado;
		}
		catch (NoResultException nrEx) {
			return null;
		}
	}

	
	
	
	public List<Concesionario> findAll () {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		Query q = em.createQuery("SELECT c FROM Concesionario c", Concesionario.class);
		List<Concesionario> resultado = (List<Concesionario>) q.getResultList();
		em.close();
		return resultado;
	}
	

	
	public static String toString (Concesionario concesionario) {
		return "Id: " + concesionario.getId() + " - Nombre: " + concesionario.getNombre() +
				" - CIF: " + concesionario.getCif() + " - Localidad: " + concesionario.getLocalidad(); 
	}

	
	
}
