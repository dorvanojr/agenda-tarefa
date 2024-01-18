package br.com.vidro.dao.impl;


import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.transaction.Transactional;

import br.com.vidro.dao.ClienteDao;
import br.com.vidro.dao.PagamentoDao;
import br.com.vidro.entity.Cliente;
import br.com.vidro.entity.Endereco;
import br.com.vidro.entity.Telefone;
import br.com.vidro.entity.Userauth;
import br.com.vidro.entity.FormaPagamento;
import br.com.vidro.entity.TipoPagamento;


public class PagamentoDaoImpl  implements PagamentoDao, Serializable {

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

	@Override
	public List<FormaPagamento> findAllForma() {
		  List<FormaPagamento> resultado = null;  

	        try {  
	            Query consulta = em.createNamedQuery("FormaPagamento.findAll");  
	            resultado = consulta.getResultList();
	                   
	        } catch (Exception e) {  
	            System.out.println("ocorreu o erro: " + e.getMessage());  
	        } 
	        
			return resultado;
	    
	}

	@Override
	public List<TipoPagamento> findAllTipo() {
		  List<TipoPagamento> resultado = null;  

	        try {  
	            Query consulta = em.createNamedQuery("TipoPagamento.findAll");  
	            resultado = consulta.getResultList();
	                   
	        } catch (Exception e) {  
	            System.out.println("ocorreu o erro: " + e.getMessage());  
	        } 
	        
			return resultado;
	    
	}

	
	 
	 public EntityManager getEm() {
			return em;
		}

		public void setEm(EntityManager em) {
			this.em = em;
		}

		
		

	

}
