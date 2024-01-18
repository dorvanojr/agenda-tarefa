package br.com.vidro.dao.impl;


import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import br.com.vidro.dao.EmpresaDao;
import br.com.vidro.entity.Cliente;
import br.com.vidro.entity.Empresa;

public class EmpresaDaoImpl  implements EmpresaDao, Serializable {

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
	public void save(Empresa empresa) {
		try{
			beginTransaction();	
			em.persist(empresa);
			commit();
			
		}catch(Exception ex){
			System.out.println("erro" + ex.getMessage());
			ex.getStackTrace();
		}
	
			
	}
	
	@Transactional
	  public void remove(Empresa empresa) {
		    beginTransaction();   
	        Empresa c = em.getReference(Empresa.class, empresa.getIdEmpresa());
	        em.remove(c);
	        commit();
	       
	    }
	

	@Transactional
	  public void update(Empresa empresa) {
		try{
			 beginTransaction();
			 em.merge(empresa); 
			 commit();
		}catch(Exception ex){
			System.out.println("erro" + ex.getMessage());
			ex.getStackTrace();
		}
		   
			
	       
	    }
	  
	@Transactional
	  public List<Empresa> listAllEmpresa() {  
		  List<Empresa> resultado = null;  

	        try {  
	            Query consulta = em.createNamedQuery("Empresa.findAll");  
	            resultado = consulta.getResultList();
	                   
	        } catch (Exception e) {  
	            System.out.println("ocorreu o erro: " + e.getMessage());  
	        } 
	        
			return resultado;
	    
	    
	 }
	
	
	@Transactional
	  public List<Cliente> listAll() {  
		      StringBuilder hql = new StringBuilder();
	        hql.append(" SELECT cli FROM Cliente cli ");
	        hql.append(" INNER JOIN FETCH  cli.empresa em ");
	        hql.append(" where cli.status = :status ");
	        hql.append(" and cli.nome is null");
	        Query q = em.createQuery(hql.toString());
	        q.setParameter("status", 1);  

	        List<Cliente> singleResult = (List<Cliente>) q.getResultList();
			return singleResult;  
	    
	    
	 }
	
	    public List<Empresa> listLoginNome(String nome, String login) {
		   StringBuilder hql = new StringBuilder();
	        hql.append(" SELECT cli FROM Cliente cli ");
	        hql.append(" INNER JOIN FETCH  cli.userauth asth ");
	        hql.append(" INNER JOIN FETCH  asth.user use ");
	        hql.append(" where cli.status = :status");
	        hql.append(" and use.username = :login");
	        hql.append(" and cli.nome = :nome");
	        Query q = em.createQuery(hql.toString());
	        q.setParameter("status", 1);  
	        q.setParameter("login", login);  
	        q.setParameter("nome", nome);  
	        List<Empresa> singleResult = (List<Empresa>) q.getResultList();
			return singleResult;  
	    }
	
	
	  public List<Empresa> listLoginAll(String login) {
		   StringBuilder hql = new StringBuilder();
	        hql.append(" SELECT cli FROM Cliente cli ");
	        hql.append(" INNER JOIN FETCH  cli.userauth asth ");
	        hql.append(" INNER JOIN FETCH  asth.user use ");
	        hql.append(" where cli.status = :status");
	        hql.append(" and use.username = :login");
	        Query q = em.createQuery(hql.toString());
	        q.setParameter("status", 1);  
	        q.setParameter("login", login);  
	        List<Empresa> singleResult = (List<Empresa>) q.getResultList();
			return singleResult;   
	    }
	
	@Transactional
	 public List<Empresa> list(String nome) {  
		  List<Empresa> resultado = null;  

	        try {  
	            Query consulta = em.createNamedQuery("Cliente.findList");  
	  
	            consulta.setParameter("nome",nome);  
	           
	             
	            resultado = consulta.getResultList();
	                   
	        } catch (Exception e) {  
	            System.out.println("ocorreu o erro: " + e.getMessage());  
	        } 
	        
			return resultado;
	    
	    
	 }
	 
	 
		@Transactional
		 public List<Empresa> listId(int id) {  
			  List<Empresa> resultado = null;  

		        try {  
		            Query consulta = em.createNamedQuery("Cliente.findId");  
		  
		            consulta.setParameter("id",id);  
		            consulta.setParameter("status",1);  
		             
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
