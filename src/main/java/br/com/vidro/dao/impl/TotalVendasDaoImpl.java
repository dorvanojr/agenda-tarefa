package br.com.vidro.dao.impl;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import br.com.vidro.dao.TotalVendasDao;
import br.com.vidro.entity.TotalVendas;
import br.com.vidro.entity.Vendas;


public class TotalVendasDaoImpl implements TotalVendasDao, Serializable {

	private static final long serialVersionUID = 1L;
	@Inject private EntityManager em;
	

	public void beginTransaction() {

		em.getTransaction().begin();
	}

	public void commit() {
		em.getTransaction().commit();
	}

	public void rollback() {
		em.getTransaction().rollback();
	}

	public void closeTransaction() {
		em.close();
	}

	public void commitAndCloseTransaction() {
		commit();
		closeTransaction();
	}

	public void flush() {
		em.flush();
	}


	@Transactional
	public void save(TotalVendas a) {
		try{
			beginTransaction();	
			em.persist(a);
			flush();
	        em.clear();
			commit();
			
		}catch(Exception ex){
			System.out.println("erro" + ex.getMessage());
			ex.getStackTrace();
		}
	
			
	}

	@Transactional
	public void save(Vendas a) {
		try{
			beginTransaction();	
			em.persist(a);
			flush();
	        em.clear();
			commit();
			
		}catch(Exception ex){
			System.out.println("erro" + ex.getMessage());
			ex.getStackTrace();
		}
	
			
	}
	
}
