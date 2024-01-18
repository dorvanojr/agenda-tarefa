package br.com.vidro.dao.impl;


import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.transaction.Transactional;

import br.com.vidro.dao.CategoriaDao;
import br.com.vidro.dao.ClienteDao;
import br.com.vidro.entity.Categoria;
import br.com.vidro.entity.Cliente;
import br.com.vidro.entity.Endereco;
import br.com.vidro.entity.Telefone;
import br.com.vidro.entity.Userauth;


public class CategoriaDaoImpl  implements CategoriaDao, Serializable {

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
	public void save(Categoria categoria) {
		try{
			beginTransaction();	
			em.persist(categoria);
			commit();
			
		}catch(Exception ex){
			System.out.println("erro" + ex.getMessage());
			ex.getStackTrace();
		}
	
			
	}
	
	
	 public EntityManager getEm() {
			return em;
		}

		public void setEm(EntityManager em) {
			this.em = em;
		}

		

	

}
