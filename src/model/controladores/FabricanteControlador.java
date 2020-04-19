package model.controladores;

import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import model.Fabricante;


public class FabricanteControlador extends model.Controlador {

	private static FabricanteControlador controlador = null;

	public FabricanteControlador () {
		super(Fabricante.class, "VentaDeCoches");
	}
	
	/**
	 * 
	 * @return
	 */
	public static FabricanteControlador getControlador () {
		if (controlador == null) {
			controlador = new FabricanteControlador();
		}
		return controlador;
	}

	/**
	 *  
	 */
	public Fabricante find (int id) {
		return (Fabricante) super.find(id);
	}

	
	/**
	 * 
	 * @return
	 */
	public Fabricante findFirst () {
		try {
			EntityManager em = getEntityManagerFactory().createEntityManager();
			Query q = em.createQuery("SELECT c FROM Fabricante c order by c.id", Fabricante.class);
			Fabricante resultado = (Fabricante) q.setMaxResults(1).getSingleResult();
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
	public Fabricante findLast () {
		try {
			EntityManager em = getEntityManagerFactory().createEntityManager();
			Query q = em.createQuery("SELECT c FROM Fabricante c order by c.id desc", Fabricante.class);
			Fabricante resultado = (Fabricante) q.setMaxResults(1).getSingleResult();
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
	public Fabricante findNext (Fabricante c) {
		try {
			EntityManager em = getEntityManagerFactory().createEntityManager();
			Query q = em.createQuery("SELECT c FROM Fabricante c where c.id > :idActual order by c.id", Fabricante.class);
			q.setParameter("idActual", c.getId());
			Fabricante resultado = (Fabricante) q.setMaxResults(1).getSingleResult();
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
	public Fabricante findPrevious (Fabricante c) {
		try {
			EntityManager em = getEntityManagerFactory().createEntityManager();
			Query q = em.createQuery("SELECT c FROM Fabricante c where c.id < :idActual order by c.id desc", Fabricante.class);
			q.setParameter("idActual", c.getId());
			Fabricante resultado = (Fabricante) q.setMaxResults(1).getSingleResult();
			em.close();
			return resultado;
		}
		catch (NoResultException nrEx) {
			return null;
		}
	}

	
	
	/**
	 * 
	 * @param coche
	 * @return
	 */
	public boolean exists(Fabricante fab) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		
		boolean ok = true;
		try {
			Query q = em.createNativeQuery("SELECT * FROM tutorialjavacoches.coche where id = ?", Fabricante.class);
			q.setParameter(1, fab.getId());
			fab = (Fabricante) q.getSingleResult(); 
		}
		catch (NoResultException ex) {
			ok = false;
		}
		em.close();
		return ok;
	}
	
	
	
	public List<Fabricante> findAll () {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		Query q = em.createQuery("SELECT c FROM Fabricante c", Fabricante.class);
		List<Fabricante> resultado = (List<Fabricante>) q.getResultList();
		em.close();
		return resultado;
	}
	

	
	public static String toString (Fabricante fabricante) {
		return fabricante.getNombre() + " " + fabricante.getCif(); 
	}

	

}
