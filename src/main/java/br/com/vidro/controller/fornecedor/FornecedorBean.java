package br.com.vidro.controller.fornecedor;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.primefaces.event.RowEditEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.vidro.controller.dto.GenericDto;
import br.com.vidro.dao.ClienteDao;
import br.com.vidro.dao.FornecedorDao;
import br.com.vidro.dao.UsuarioDao;
import br.com.vidro.dao.impl.FornecedorDaoImpl;
import br.com.vidro.dao.impl.UsuarioDaoImpl;
import br.com.vidro.entity.Cliente;
import br.com.vidro.entity.Documentos;
import br.com.vidro.entity.Email;
import br.com.vidro.entity.Empresa;
import br.com.vidro.entity.Endereco;
import br.com.vidro.entity.Fornecedor;
import br.com.vidro.entity.Telefone;
import br.com.vidro.entity.User;
import br.com.vidro.entity.Userauth;

@ConversationScoped
@Named
public class FornecedorBean  implements Serializable  {


	private static final long serialVersionUID = 1L;
	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Inject
	private Conversation conversation;
	@Inject
	private UsuarioDaoImpl usuarioDaoImpl;
	@Inject 
	private FornecedorDaoImpl DAO;
	@Inject 
	private Cliente cliente;
	@Inject 
	private Telefone telefone;
	@Inject 
	private Endereco endereco;
	@Inject 
	private Fornecedor fornecedor;
	@Inject 
	private Fornecedor fornecedorAlterar;
	@Inject
	private Documentos documentos;
	@Inject
	private Email email;
	private String parametro;
	
	private List<Fornecedor> fornecedores;
	private List<Fornecedor> listas;
	
	private static GenericDto instance;

	public static GenericDto getInstance() {
	      if(instance == null) {
	         instance = new GenericDto();
	      }
	      return instance;
	 }
	  @PostConstruct
			public void init() {
				log.info("Inicializando a conversacao no Bean "
						+ this.getClass().getCanonicalName());
				beginConversation();
			}
		 
		    private void beginConversation() {
				if (conversation.isTransient()) {
					conversation.begin();
					log.info("ConversaÃ§Ã£o iniciada - ID:" + conversation.getId());
				}
			}
		    @PreDestroy
		    public void endConversation() {
				if (!conversation.isTransient()) {
					System.out.println("Conversação encerrada, id: " + conversation.getId());
					conversation.end();
				}
			}

	@Transactional
	public void save() {
		FacesContext context = FacesContext.getCurrentInstance();
		try{
		beginConversation();
		Empresa empresa = new  Empresa();
		   UsuarioDao uDao = getUsuarioDaoImpl();
		   List<User> users = uDao.retornaEmpresa(getInstance().getLogin());
		   for(User user : users){
			   empresa = user.getEmpresa();
			    
		   }
        System.out.println("usuarioNOme " + empresa.getIdEmpresa());
		
		FornecedorDao dao = getDAO();
		cliente.setFornecedor(fornecedor);
		cliente.setEmpresa(empresa);
		telefone.setFornecedor(fornecedor);
		telefone.setEmpresa(empresa);
		endereco.setFornecedor(fornecedor);
		endereco.setEmpresa(empresa);
		email.setFornecedor(fornecedor);
		email.setEmpresa(empresa);
		documentos.setFornecedor(fornecedor);
		documentos.setEmpresa(empresa);
		fornecedor.setEmpresa(empresa);
		fornecedor.addTelefones(telefone);
		fornecedor.addEnderecos(endereco);
		fornecedor.addEmails(email);
		fornecedor.addClientes(cliente);
		fornecedor.addDocumentos(documentos);
	
		dao.saveFornecedor(fornecedor);

		context.addMessage(null, new FacesMessage("Resultado",
				"Cadastrado com sucesso!!! " + ""));

	} catch (Exception ex) {

		context.addMessage(null, new FacesMessage("Resultado",
				"Erro ao Cadastrar!!! " + ex.getMessage()));
		     ex.fillInStackTrace();
	 }finally{
			endConversation();
			
	 }	
	}
	
	public void onRowEdit(RowEditEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();

		try {

			FornecedorDao dao = getDAO();

			Fornecedor fornecedor = (Fornecedor) event.getObject();

			dao.update(fornecedor);

			context.addMessage(null, new FacesMessage("Resultado",
					"Alterado com sucesso!!! " + ""));

		} catch (Exception ex) {

			context.addMessage(null, new FacesMessage("Resultado",
					"Alterado com sucesso!!! " + ""));
		}

	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edit Cancelled",
				((Fornecedor) event.getObject()).getNomeFantasia());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	
	
	@Transactional
	public String listFornecedor() {
		
		UsuarioDao uDao = getUsuarioDaoImpl();
		Userauth auth = uDao.retornaUsuario(getInstance().getLogin());
        System.out.println("usuarioNOme " + auth.getUser().getUsername());
        Empresa empresa = new Empresa();
		   List<User> users = uDao.retornaEmpresa(getInstance().getLogin());
		   for(User user : users){
			   empresa = user.getEmpresa();
			    
		   }
		   FornecedorDao dao = getDAO();
		fornecedores = dao.listLoginNome(fornecedor.getNomeFantasia(), empresa.getIdEmpresa());
          
	//	fornecedores = dao.list(fornecedor.getNomeFantasia());
            
		
		return "";

	}
	
	@Transactional
	public List<Fornecedor> listFornecedorAll() {
		try {
             System.out.println("usuario 2" + getInstance().getLogin());
            FornecedorDao dao = getDAO();
            UsuarioDao uDao = getUsuarioDaoImpl();
			 Empresa empresa = new Empresa();
			   List<User> users = uDao.retornaEmpresa(getInstance().getLogin());
			   for(User user : users){
				   empresa = user.getEmpresa();
				    
			   }

		        System.out.println("usuarioNOmem 2" + empresa.getIdEmpresa());
			
				listas = dao.listLoginAll(empresa.getIdEmpresa());

            
		} catch (Exception ex) {

		}
		return listas;

	}
	
	 @Transactional
	 public void excluir(){

		 FornecedorDao dao = getDAO();
			
		  for(Fornecedor fornecedor: fornecedores){
		  dao.remove(fornecedor);
		  }
		 fornecedores = dao.list(cliente.getNome());
		 fornecedores = dao.listAll();
		  
	    }
	 
		public void update() {
			FacesContext context = FacesContext.getCurrentInstance();
			try {			  			
				  
				   
				FornecedorDao dao = getDAO();
				   
				   
				   dao.update(fornecedorAlterar);	

				context.addMessage(null, new FacesMessage("Resultado",
						"update com sucesso!!! " + ""));

			} catch (Exception ex) {

				context.addMessage(null, new FacesMessage("Resultado",
						"Erro ao Cadastrar!!! " + ex.getMessage()));
				ex.fillInStackTrace();
			}
		}
	
	   public void cleanForm(){
	    	 fornecedor.setIdFornecedor(0);
	    	 cliente.setNome("");
	    	 documentos.setCpfcnpj("");
	    	 cliente.setObservacao("");
	    	 cliente.setNomeFantasia("");
	    	 endereco.setEndereco("");
	    	 endereco.setBairro("");
	    	 endereco.setCidade("");
	    	 endereco.setCep("");
	    	 telefone.setFax("");
	    	 telefone.setTelefone("");
	    	 telefone.setResidencial("");
	    	 telefone.setWhatapps("");
	    	 telefone.setCelular("");
	    	 cliente.setCfdf("");
	    	 endereco.setUf("");
	    	 documentos.setCnpj("");
	    	 documentos.setCpf("");
	    	 documentos.setEmissor("");
	    	 documentos.setCpfcnpj("");
	    	 documentos.setNim("");
	    	 documentos.setNle("");
	    	 documentos.setRg("");
	    	 fornecedor.setNomeFantasia("");
	    	 fornecedor.setRepresentante("");
	     }

	public List<Fornecedor> getFornecedores() {
		
		fornecedores = getDAO().list(getParametro());
		
		return fornecedores;
	}


	public void setFornecedores(List<Fornecedor> fornecedores) {
		this.fornecedores = fornecedores;
	}


	public List<Fornecedor> getListas() {
		return listas;
	}


	public void setListas(List<Fornecedor> listas) {
		this.listas = listas;
	}


	public FornecedorDaoImpl getDAO() {
		return DAO;
	}


	public void setDAO(FornecedorDaoImpl dao) {
		this.DAO = dao;
	}


	public Cliente getCliente() {
		return cliente;
	}


	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}


	public Telefone getTelefone() {
		return telefone;
	}


	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}


	public Endereco getEndereco() {
		return endereco;
	}


	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}


	public Fornecedor getFornecedor() {
		return fornecedor;
	}


	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Documentos getDocumentos() {
		return documentos;
	}

	public void setDocumentos(Documentos documentos) {
		this.documentos = documentos;
	}

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public String getParametro() {
		return parametro;
	}

	public void setParametro(String parametro) {
		this.parametro = parametro;
	}

	public Fornecedor getFornecedorAlterar() {
		return fornecedorAlterar;
	}

	public void setFornecedorAlterar(Fornecedor fornecedorAlterar) {
		this.fornecedorAlterar = fornecedorAlterar;
	}

	public UsuarioDaoImpl getUsuarioDaoImpl() {
		return usuarioDaoImpl;
	}

	public void setUsuarioDaoImpl(UsuarioDaoImpl usuarioDaoImpl) {
		this.usuarioDaoImpl = usuarioDaoImpl;
	}
	

	
}
