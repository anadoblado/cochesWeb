package model;


import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;



public class Controlador {

	private static EntityManagerFactory entityManagerFactory;

	private String nombreEntidadControlada = "";
	private Class entidadControlada = null;

	/**
	 * 
	 * @param nombreEntidad
	 */
	public Controlador(Class entidadControlada, String unidadPersistencia) {
		if (entityManagerFactory == null) {
			entityManagerFactory = Persistence.createEntityManagerFactory(unidadPersistencia);
		}
		this.entidadControlada = entidadControlada;
		this.nombreEntidadControlada = this.entidadControlada.getName();
	}

	/**
	 * 
	 * @return
	 */
	public static EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Entidad find(int id) {
		try {
			EntityManager em = getEntityManagerFactory().createEntityManager();
			Entidad entidad = (Entidad) em.find(this.entidadControlada, id);
			em.close();
			return entidad;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 
	 */
	public void deleteAll() {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		em.getTransaction().begin();
		em.createQuery("DELETE FROM " + this.nombreEntidadControlada + " o").executeUpdate();
		em.getTransaction().commit();
		em.close();
	}

	/**
	 * 
	 */
	public void persist(Entidad e) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		em.getTransaction().begin();
		em.persist(e);
		em.getTransaction().commit();
		em.close();
	}

	/**
	 * 
	 */
	public void merge(Entidad e) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		em.getTransaction().begin();
		em.merge(e);
		em.getTransaction().commit();
		em.close();
	}

	/**
	 * 
	 * @param e
	 */
	public void save(Entidad e) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		if (e.getId() != 0) {
			merge(e);
		}
		else {
			persist(e);
		}
	}
	
	/**
	 * 
	 */
	public void remove(Entidad e) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		Entidad actual = null;
		em.getTransaction().begin();
		if (!em.contains(e)) {
			actual = em.merge(e);
		}
		em.remove(actual);
		em.getTransaction().commit();
		em.close();
	}

	
	/**
	 * 
	 * @return
	 */
	public Entidad findFirst () {
		try {
			EntityManager em = getEntityManagerFactory().createEntityManager();
			Query q = em.createQuery("SELECT e FROM " + this.nombreEntidadControlada + " e order by e.id", Entidad.class);
			Entidad resultado = (Entidad) q.setMaxResults(1).getSingleResult();
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
	public Entidad findLast () {
		try {
			EntityManager em = getEntityManagerFactory().createEntityManager();
			Query q = em.createQuery("SELECT e FROM " + this.nombreEntidadControlada + " e order by e.id desc", Entidad.class);
			Entidad resultado = (Entidad) q.setMaxResults(1).getSingleResult();
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
	public Entidad findNext (Entidad e) {
		try {
			EntityManager em = getEntityManagerFactory().createEntityManager();
			Query q = em.createQuery("SELECT e FROM " + this.nombreEntidadControlada + " e where e.id > :idActual order by e.id", Entidad.class);
			q.setParameter("idActual", e.getId());
			Entidad resultado = (Entidad) q.setMaxResults(1).getSingleResult();
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
	public Entidad findPrevious (Entidad e) {
		try {
			EntityManager em = getEntityManagerFactory().createEntityManager();
			Query q = em.createQuery("SELECT e FROM " + this.nombreEntidadControlada + " e where e.id < :idActual order by e.id desc", Entidad.class);
			q.setParameter("idActual", e.getId());
			Entidad resultado = (Entidad) q.setMaxResults(1).getSingleResult();
			em.close();
			return resultado;
		}
		catch (NoResultException nrEx) {
			return null;
		}
	}
	
}
