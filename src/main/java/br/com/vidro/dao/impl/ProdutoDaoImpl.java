package br.com.vidro.dao.impl;


import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import br.com.vidro.dao.ProdutoDao;
import br.com.vidro.entity.Cliente;
import br.com.vidro.entity.Endereco;
import br.com.vidro.entity.Fornecedor;
import br.com.vidro.entity.Produto;
import br.com.vidro.entity.Telefone;


public class ProdutoDaoImpl  implements ProdutoDao, Serializable {

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
	public void save(Produto produto) {
		beginTransaction();	
		em.persist(produto);
		commit();
			
	}
	
	@Transactional
	  public void update(Produto produto) {
		    beginTransaction();
		    em.merge(produto); 
		    commit();
			rollback();
	       
	    }
	
	
	  public void updateProduto(Produto produto) {
		    em.merge(produto); 
		    commit();
			rollback();
	       
	    }
	
	
	@Transactional
	  public List<Produto> listAll() {  
		  List<Produto> resultado = null;  

	        try {  
	            Query consulta = em.createNamedQuery("Produto.findAll");  
	            resultado = consulta.getResultList();
	                   
	        } catch (Exception e) {  
	            System.out.println("ocorreu o erro: " + e.getMessage());  
	        } 
	        
			return resultado;
	    
	    
	 }
	@Transactional
	 public List<Produto> list(String nome) {  
		  List<Produto> resultado = null;  
		  
	        try {  
	            Query consulta = em.createNamedQuery("Produto.findList");  
	  
	            consulta.setParameter("nome",nome);  
	             
	            resultado = consulta.getResultList();
	                   
	        } catch (Exception e) {  
	            System.out.println("ocorreu o erro: " + e.getMessage());  
	        } 
	        
			return resultado;
	    
	    
	 }
	
	@Transactional
	 public List<Produto> listID(int id){  
		  List<Produto> resultado = null; 
	        try {  
	            Query consulta = em.createNamedQuery("Produto.findID");  
	  
	            consulta.setParameter("id",id);  
	             
	            resultado = consulta.getResultList();
	                   
	        } catch (Exception e) {  
	            System.out.println("ocorreu o erro: " + e.getMessage());  
	        } 
	        
			return resultado;
	    
	    
	 }
	
	
	@Transactional
	 public List<Produto> listBarra(String barra) {  
		  List<Produto> resultado = null;  
	        try {  
	            Query consulta = em.createNamedQuery("Produto.findBarra");  
	  
	            consulta.setParameter("barra", barra);  
	             
	            resultado = consulta.getResultList();
	                   
	        } catch (Exception e) {  
	            System.out.println("ocorreu o erro: " + e.getMessage());  
	        } 
	        
			return resultado;
	    
	    
	 }
	
	@Transactional
	  public List<Produto> listProdutoLoginAll(int id) {
		   StringBuilder hql = new StringBuilder();
	        hql.append(" SELECT prod FROM Produto prod ");
	        hql.append(" INNER JOIN FETCH  prod.cliente cli ");
	        hql.append(" INNER JOIN FETCH  prod.empresa em ");
	        hql.append(" where cli.status = :status");
	        hql.append(" and em.id = :id");
	        Query q = em.createQuery(hql.toString());
	        q.setParameter("status", 3);  
	        q.setParameter("id", id);  
	        List<Produto> singleResult = (List<Produto>) q.getResultList();
			return singleResult;   
	    }
	
	@Transactional
    public List<Produto> listProdutLoginNome(String nome, int id) {
	   StringBuilder hql = new StringBuilder();
        hql.append(" SELECT prod FROM Produto prod ");
        hql.append(" INNER JOIN FETCH  prod.empresa em ");
        hql.append(" INNER JOIN FETCH  prod.cliente cli ");
        hql.append(" where cli.status = :status");
        hql.append(" and em.idEmpresa = :id");
        hql.append(" and prod.nomeProduto = :nome");
        Query q = em.createQuery(hql.toString());
        q.setParameter("status", 3);  
        q.setParameter("id", id);  
        q.setParameter("nome", nome);  
        List<Produto> singleResult = (List<Produto>) q.getResultList();
		return singleResult;  
    }

	 
	@Transactional
    public List<Produto> listProdutLoginBarras(String nome, int id) {
	   StringBuilder hql = new StringBuilder();
        hql.append(" SELECT prod FROM Produto prod ");
        hql.append(" INNER JOIN FETCH  prod.empresa em ");
        hql.append(" INNER JOIN FETCH  prod.cliente cli ");
        hql.append(" where cli.status = :status");
        hql.append(" and em.idEmpresa = :id");
        hql.append(" and prod.codigoBarra = :nome");
        Query q = em.createQuery(hql.toString());
        q.setParameter("status", 3);  
        q.setParameter("id", id);  
        q.setParameter("nome", nome);  
        List<Produto> singleResult = (List<Produto>) q.getResultList();
		return singleResult;  
    }
	
	
	 public EntityManager getEm() {
			return em;
		}

		public void setEm(EntityManager em) {
			this.em = em;
		}

	

}
