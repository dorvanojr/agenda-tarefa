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
import br.com.vidro.dao.FuncionarioDao;
import br.com.vidro.entity.Cliente;
import br.com.vidro.entity.Endereco;
import br.com.vidro.entity.Funcionario;
import br.com.vidro.entity.Telefone;
import br.com.vidro.entity.Userauth;


public class FuncionarioDaoImpl  implements FuncionarioDao, Serializable {

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
	public void save(Funcionario funcionario) {
		try{
			beginTransaction();	
			em.persist(funcionario);
			commit();
			
		}catch(Exception ex){
			System.out.println("erro" + ex.getMessage());
			ex.getStackTrace();
		}
	
			
	}
	
	@Transactional
	  public void remove(Funcionario funcionario) {
		    beginTransaction();   
	        Funcionario c = em.getReference(Funcionario.class, funcionario.getIdFuncionario());
	        em.remove(c);
	        commit();
	       
	    }
	

	@Transactional
	  public void update(Funcionario funcionario) {
		try{
			 beginTransaction();
			 em.merge(funcionario); 
			 commit();
		}catch(Exception ex){
			System.out.println("erro" + ex.getMessage());
			ex.getStackTrace();
		}
		   
			
	       
	    }
	  
	@Transactional
	  public List<Funcionario> listAll() {  
		  List<Funcionario> resultado = null;  

	        try {  
	            Query consulta = em.createNamedQuery("Cliente.findList");  
	            consulta.setParameter("status",1); 
	            resultado = consulta.getResultList();
	                   
	        } catch (Exception e) {  
	            System.out.println("ocorreu o erro: " + e.getMessage());  
	        } 
	        
			return resultado;
	    
	    
	 }
	@Transactional
    public List<Funcionario> listLoginNome(String nome, int id) {
	   StringBuilder hql = new StringBuilder();
        hql.append(" SELECT f FROM Funcionario f ");
        hql.append(" INNER JOIN FETCH  f.empresa em ");
        hql.append(" INNER JOIN FETCH  f.cliente cli ");
        hql.append(" where cli.status = :status");
        hql.append(" and em.idEmpresa = :id");
        hql.append(" and cli.nome = :nome");
        Query q = em.createQuery(hql.toString());
        q.setParameter("status", 4);  
        q.setParameter("id", id);  
        q.setParameter("nome", nome);  
        List<Funcionario> singleResult = (List<Funcionario>) q.getResultList();
		return singleResult;  
    }

	
	@Transactional
	  public List<Funcionario> listLoginAll(int id) {
		   StringBuilder hql = new StringBuilder();
	        hql.append(" SELECT f FROM Funcionario f ");
	        hql.append(" INNER JOIN FETCH  f.empresa em ");
	        hql.append(" INNER JOIN FETCH  f.cliente cli ");
	        hql.append(" where cli.status = :status");
	        hql.append(" and em.id = :id");
	        Query q = em.createQuery(hql.toString());
	        q.setParameter("status", 4);  
	        q.setParameter("id", id);  
	        List<Funcionario> singleResult = (List<Funcionario>) q.getResultList();
			return singleResult;   
	    }
	
	
	@Transactional
	 public List<Funcionario> list(String nome) {  
		  List<Funcionario> resultado = null;  

	        try {  
	            Query consulta = em.createNamedQuery("Cliente.findAll");  
	  
	            consulta.setParameter("nome",nome);  
	            consulta.setParameter("status",1);  
	             
	            resultado = consulta.getResultList();
	                   
	        } catch (Exception e) {  
	            System.out.println("ocorreu o erro: " + e.getMessage());  
	        } 
	        
			return resultado;
	    
	    
	 }
	 
	 
		@Transactional
		 public List<Funcionario> listId(int id) {  
			  List<Funcionario> resultado = null;  

		        try {  
		            Query consulta = em.createNamedQuery("Funcionario.findId");  
		  
		            consulta.setParameter("id",id);  
		             
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
