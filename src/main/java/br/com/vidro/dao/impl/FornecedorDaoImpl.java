package br.com.vidro.dao.impl;


import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.mapping.Map;

import br.com.vidro.dao.FornecedorDao;
import br.com.vidro.entity.Cliente;
import br.com.vidro.entity.Endereco;
import br.com.vidro.entity.Fornecedor;
import br.com.vidro.entity.Telefone;


public class FornecedorDaoImpl  implements FornecedorDao, Serializable {

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
	public void saveFornecedor(Fornecedor fornecedor) {
		beginTransaction();	
		em.persist(fornecedor);
		commit();
			
	}
	
	@Transactional
	  public void update(Fornecedor fornecedor) {
		try{
			 beginTransaction();
			 em.merge(fornecedor); 
			 commit();
		}catch(Exception ex){
			System.out.println("erro" + ex.getMessage());
			ex.getStackTrace();
		}
	}
	
	@Transactional
	  public List<Fornecedor> listAll() {  
		  List<Fornecedor> resultado = null;  

	        try {  
	            Query consulta = em.createNamedQuery("Fornecedor.findAll");  
	         
	            resultado = consulta.getResultList();
	                   
	        } catch (Exception e) {  
	            System.out.println("ocorreu o erro: " + e.getMessage());  
	        } 
	        
			return resultado;
	    
	    
	 }
	@Transactional
    public List<Fornecedor> listLoginNome(String nome, int id) {
	   StringBuilder hql = new StringBuilder();
        hql.append(" SELECT cli FROM Fornecedor cli ");
        hql.append(" INNER JOIN FETCH  cli.empresa em ");
        hql.append(" where ");
        hql.append(" em.idEmpresa = :id");
        hql.append(" and cli.nomeFantasia = :nome");
        Query q = em.createQuery(hql.toString());
       // q.setParameter("status", 1);  
        q.setParameter("id", id);  
        q.setParameter("nome", nome);  
        List<Fornecedor> singleResult = (List<Fornecedor>) q.getResultList();
		return singleResult;  
    }
	
	@Transactional
	  public List<Fornecedor> listLoginAll(int id) {
		   StringBuilder hql = new StringBuilder();
	        hql.append(" SELECT cli FROM Fornecedor cli ");
	        hql.append(" INNER JOIN FETCH  cli.empresa em ");
	        hql.append(" where ");
	        hql.append(" em.idEmpresa = :id");
	        Query q = em.createQuery(hql.toString());
	    //    q.setParameter("status", 0);  
	        q.setParameter("id", id);  
	        List<Fornecedor> singleResult = (List<Fornecedor>) q.getResultList();
			return singleResult;   
	    }
	
	@Transactional
	 public List<Fornecedor> list(String nome) {  
		  List<Fornecedor> resultado = null;  

	        try {  
	            Query consulta = em.createNamedQuery("Fornecedor.findList");  
	  
	            consulta.setParameter("nome",nome);  
	             
	            resultado = consulta.getResultList();
	                   
	        } catch (Exception e) {  
	            System.out.println("ocorreu o erro: " + e.getMessage());  
	        } 
	        
			return resultado;
	    
	    
	 }
	
	
    
	
	  @Transactional
	  public void remove(Fornecedor fornecedor) {
		    beginTransaction();   
	        Fornecedor f = em.getReference(Fornecedor.class, fornecedor.getIdFornecedor());
	        em.remove(f);
	        commit();
	       
	    }
	
	 
	 public EntityManager getEm() {
			return em;
		}

		public void setEm(EntityManager em) {
			this.em = em;
		}

	

}
