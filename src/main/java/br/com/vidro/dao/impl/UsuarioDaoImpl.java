package br.com.vidro.dao.impl;


import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.transaction.Transactional;

import br.com.vidro.dao.UsuarioDao;
import br.com.vidro.entity.Authority;
import br.com.vidro.entity.Cliente;
import br.com.vidro.entity.Email;
import br.com.vidro.entity.Empresa;
import br.com.vidro.entity.Endereco;
import br.com.vidro.entity.Telefone;
import br.com.vidro.entity.User;
import br.com.vidro.entity.Userauth;

public class UsuarioDaoImpl  implements UsuarioDao, Serializable {

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
	public void saveTelefone(Telefone telefone) {
		beginTransaction();
		em.persist(telefone);
		commit();	
	}
	
	@Transactional
	public void saveEndereco(Endereco endereco) {
		beginTransaction();	
		em.persist(endereco);
		commit();
		
	}
	
	@Transactional
	public void save(Cliente cliente) {
		beginTransaction();	
		em.persist(cliente);
		commit();
			
	}
	
	
	
	
	@Transactional
	public void saveEmail(Email email) {
		beginTransaction();	
		em.persist(email);
		commit();
			
	}
	
	@Transactional
	public void saveLogin(User user) {
		beginTransaction();	
		em.persist(user);
		commit();
			
	}

	
	
	
	@Transactional
	  public void remove(Cliente cliente) {
		    beginTransaction();   
	        Cliente c = em.getReference(Cliente.class, cliente.getIdCliente());
	        em.remove(c);
	        commit();
	       
	    }
	

	@Transactional
	  public void update(Cliente cliente) {
		    beginTransaction();
		    em.merge(cliente); 
		    commit();
			rollback();
	       
	    }
	  
	
	
	
	@Transactional
	  public List<Cliente> listAll() {  
		  List<Cliente> resultado = null;  

	        try {  
	            Query consulta = em.createNamedQuery("Cliente.findList");  
	  
	            resultado = consulta.getResultList();
	                   
	        } catch (Exception e) {  
	            System.out.println("ocorreu o erro: " + e.getMessage());  
	        } 
	        
			return resultado;
	    	    
	 }
	@Transactional
	 public List<Cliente> list(String nome) {  
		  List<Cliente> resultado = null;  

	        try {  
	            Query consulta = em.createNamedQuery("Cliente.findAll");  
	  
	            consulta.setParameter("nome",nome);  
	            consulta.setParameter("status",0);  
	             
	            resultado = consulta.getResultList();
	                   
	        } catch (Exception e) {  
	            System.out.println("ocorreu o erro: " + e.getMessage());  
	        } 
	        
			return resultado;
	    
	    
	 }
	 @Transactional
	   public Userauth retornaUsuario(String login) {
		   StringBuilder hql = new StringBuilder();
	        hql.append(" SELECT ua FROM Userauth ua ");
	        hql.append(" INNER JOIN FETCH ua.user u ");
	        hql.append(" where u.username = :login");
	        Query q = em.createQuery(hql.toString());
	        q.setParameter("login", login);
	        return (Userauth) q.getSingleResult();
	    }
	   @Transactional
	   public Empresa retornaEmpresaId(int id) {
		   StringBuilder hql = new StringBuilder();
	        hql.append(" SELECT em FROM Empresa em ");
	        hql.append(" where em.idEmpresa = :id");
	        Query q = em.createQuery(hql.toString());
	        q.setParameter("id", id);
	        return (Empresa) q.getSingleResult();
	    }
	   
	   
	   @Transactional
	   public List<User> retornaEmpresa(String login) {
		   List<User> resultado = null;  

	        try {  
	            Query consulta = em.createNamedQuery("User.findList");  
	            consulta.setParameter("login",login);  
	            resultado = consulta.getResultList();
	                   
	        } catch (Exception e) {  
	            System.out.println("ocorreu o erro: " + e.getMessage());  
	        } 
	        
			return resultado;
	    }
	
	public List<User> consultaLogin(String login, String senha){
		
		  List<User> result = null;  
		
		  
		  try {  
	            Query consulta = em.createNamedQuery("Login.findLogin");  
	  
	            consulta.setParameter("login",login);  
	            consulta.setParameter("senha",senha);  
	             
	            result = consulta.getResultList();
	            System.out.println("ocorreu o erro: " + result.toString());      
	        } catch (Exception e) {  
	            System.out.println("ocorreu o erro: " + e.getMessage());  
	        } 
		
		return result;
		
		
		
		
	}
	 
	 public EntityManager getEm() {
			return em;
		}

		public void setEm(EntityManager em) {
			this.em = em;
		}

		@Override
		public List<Cliente> listUsuario(String nome) {
			// TODO Auto-generated method stub
			return null;
		}

	

}
