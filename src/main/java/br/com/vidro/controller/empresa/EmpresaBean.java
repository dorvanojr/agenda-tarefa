package br.com.vidro.controller.empresa;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.New;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.transaction.Transactional;

import org.primefaces.event.RowEditEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.vidro.controller.dto.GenericDto;
import br.com.vidro.dao.ClienteDao;
import br.com.vidro.dao.EmpresaDao;
import br.com.vidro.dao.RelatorioDao;
import br.com.vidro.dao.UserDao;
import br.com.vidro.dao.UsuarioDao;
import br.com.vidro.dao.impl.ClienteDaoImpl;
import br.com.vidro.dao.impl.EmpresaDaoImpl;
import br.com.vidro.dao.impl.RelatorioDaoImpl;
import br.com.vidro.dao.impl.UsuarioDaoImpl;
import br.com.vidro.entity.Cliente;
import br.com.vidro.entity.Documentos;
import br.com.vidro.entity.Email;
import br.com.vidro.entity.Empresa;
import br.com.vidro.entity.Endereco;
import br.com.vidro.entity.Telefone;
import br.com.vidro.entity.User;
import br.com.vidro.entity.Userauth;

@ConversationScoped
@Named
public class EmpresaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Inject
	private Conversation conversation;
	@Inject
	private EmpresaDaoImpl dao;
	@Inject
	private ClienteDaoImpl clienteDaoImpl;
	@Inject
	private UsuarioDaoImpl usuarioDaoImpl;
	@Inject
	private RelatorioDaoImpl reldao;
	@Inject
	private Empresa empresa;
	@Inject
	private Cliente cliente;
	@Inject
	private Cliente cliente1;

	@Inject
	private Telefone telefone;
	@Inject
	private Endereco endereco;
	@Inject
	private Documentos documentos;
	@Inject
	private Email email;
	private List<Empresa> clientes;
	private List<Cliente> clientesEmpresas;
	private List<Empresa> listas;
	private Integer idCliente;
	private String parametros;
	private DataModel<Empresa> listaClientesData;
	private DataModel<Cliente> listaClientesEmpresaData;
	private int IdEmpresa;
	
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
	
	 
	    public DataModel<Empresa> getListaClientesData() {
	    	EmpresaDao dao = getDAO();
	    	//clientes = dao.list(parametros);
			listaClientesData = new ListDataModel<Empresa>();
			return listaClientesData;
		}

		public void setListaClientesData(DataModel<Empresa> listaClientesData) {
			this.listaClientesData = listaClientesData;
		}

     public void cleanForm(){
    	 empresa.setIdEmpresa(0);
    	 empresa.setRamoAtividade("");
    	 empresa.setRazaoSocial("");
    	 documentos.setCnpj("");
    	 cliente.setNomeFantasia("");
    	 email.setEmail("");
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
    	
     }


	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public void setListas(List<Empresa> listas) {
		this.listas = listas;
	}

	public List<Empresa> getClientes() {
		return clientes;
	}
	
	public void save() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			   beginConversation();
			   System.out.println("usuario login" + GenericDto.login + " Singleton " +getInstance().getLogin());
			   
			   
			  
			   EmpresaDao dao = getDAO();
			    
               cliente.setStatus(1);
			   cliente.setFornecedor(null);
			   cliente.setEmpresa(empresa);
			   endereco.setEmpresa(empresa);
			   telefone.setEmpresa(empresa);
			   documentos.setEmpresa(empresa);
			   email.setEmpresa(empresa);
			   empresa.addCliente(cliente);
			   empresa.addDocumentos(documentos);
			   empresa.addEnderecos(endereco);
			   empresa.addTelefones(telefone);
			   empresa.addEmails(email);
			   dao.save(empresa);
			

			context.addMessage(null, new FacesMessage("Resultado",
					"Cadastrado com sucesso!!! " + ""));

		} catch (Exception ex) {

			context.addMessage(null, new FacesMessage("Resultado",
					"Erro ao Cadastrar!!! " + ex.getMessage()));
			ex.fillInStackTrace();
		}finally{
			endConversation();
			//clientes = dao.listId(cliente.getIdCliente());
		}
	}
	
	
	
	
	public Cliente selectCliente(Cliente cliente){
		
		return this.cliente = cliente;
	}
	
	@Transactional
	public void update(Empresa empresa) {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			  
		
			   System.out.println("teste" +  cliente);
			   
			   EmpresaDao dao = getDAO();
			   
			   
			   dao.update(empresa);	

			context.addMessage(null, new FacesMessage("Resultado",
					"update com sucesso!!! " + ""));

		} catch (Exception ex) {

			context.addMessage(null, new FacesMessage("Resultado",
					"Erro ao Cadastrar!!! " + ex.getMessage()));
			ex.fillInStackTrace();
		}
	}
	
	@Transactional
	public void updatePerfilGerente(Empresa empresa) {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			  
		
			   System.out.println("teste" +  cliente);
			   
			   EmpresaDao dao = getDAO();
			   
			   
			   dao.update(empresa);	

			context.addMessage(null, new FacesMessage("Resultado",
					"update com sucesso!!! " + ""));

		} catch (Exception ex) {

			context.addMessage(null, new FacesMessage("Resultado",
					"Erro ao Cadastrar!!! " + ex.getMessage()));
			ex.fillInStackTrace();
		}
	}

   @Transactional
	public String acao() {
	     ClienteDao dao = getClienteDaoImpl();
		clientesEmpresas = dao.listLoginNomeEmpresa(parametros);
		listaClientesEmpresaData = new ListDataModel<Cliente>(clientesEmpresas);
		parametros = "";
		return "";
	}
   @Transactional
	public void onRowEdit(RowEditEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();

		try {

			EmpresaDao dao = getDAO();
			
		

			Empresa cliente1 = (Empresa) event.getObject();

			dao.update(cliente1);

			context.addMessage(null, new FacesMessage("Resultado",
					"Alterado com sucesso!!! " + ""));

		} catch (Exception ex) {

			context.addMessage(null, new FacesMessage("Resultado",
					"Alterado com sucesso!!! " + ""));
		}

	}
   
   
	public List<Empresa> listEmpresa() {

		try {


		
		EmpresaDao dao = getDAO();
			listas = dao.listId(cliente.getEmpresa().getIdEmpresa());
		

		} catch (Exception ex) {

		}

		return listas;

	}


   @Transactional
	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edit Cancelled",
				((Cliente) event.getObject()).getNome());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	

	@Transactional
	public List<Cliente> listEmpresaAll() {
		try {
			EmpresaDao dao = getDAO();
			  clientesEmpresas = dao.listAll();
			 // System.out.println(clientesEmpresas);

		} catch (Exception ex) {

		}
		return clientesEmpresas;

	}
	
	@Transactional
	public List<Empresa> listEmpresaAll1() {
		try {
			EmpresaDao dao = getDAO();
			  clientes = dao.listAllEmpresa();
			 // System.out.println(clientesEmpresas);

		} catch (Exception ex) {

		}
		return clientes;

	}
	

	@Transactional
	public void gerar() throws IOException {

		RelatorioDao dao = getRelDAO();

		for (Empresa empresa : clientes) {
			System.out.print("listar" + empresa.getIdEmpresa());
			//dao.gerar(empresa.getIdEmpresa());
		}

	}

	@Transactional
	public void excluir() {

		EmpresaDao dao = getDAO();

		for (Empresa empresa : clientes) {
		    dao.remove(empresa);
		}
		//clientes = dao.list(empresa.getRazaoSocial());

	}


	
	


	@Transactional
	public String prepareUpdate() {
		EmpresaDao dao = getDAO();
		for (Empresa empresa : clientes) {
			//clientes = dao.list(cliente.getNome());
		}
		return "alteracao-autor";
	}

	public RelatorioDaoImpl getRelDAO() {
		return reldao;
	}

	public List<Empresa> getListas() {
		return listas;
	}

	public void setClientes(List<Empresa> clientes) {
		this.clientes = clientes;
	}

	public EmpresaDaoImpl getDAO() {
		return dao;
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

	public String getParametros() {
		return parametros;
	}

	public void setParametros(String parametros) {
		this.parametros = parametros;
	}

	public Documentos getDocumentos() {
		return documentos;
	}


	public void setDocumentos(Documentos documentos) {
		this.documentos = documentos;
	}


	public UsuarioDaoImpl getUsuarioDaoImpl() {
		return usuarioDaoImpl;
	}


	public void setUsuarioDaoImpl(UsuarioDaoImpl usuarioDao) {
		this.usuarioDaoImpl = usuarioDaoImpl;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public ClienteDaoImpl getClienteDaoImpl() {
		return clienteDaoImpl;
	}

	public void setClienteDaoImpl(ClienteDaoImpl clienteDaoImpl) {
		this.clienteDaoImpl = clienteDaoImpl;
	}

	public List<Cliente> getClientesEmpresas() {
		return clientesEmpresas;
	}

	public void setClientesEmpresas(List<Cliente> clientesEmpresas) {
		
		this.clientesEmpresas = clientesEmpresas;
	}

	public Cliente getCliente1() {
		return cliente1;
	}

	public void setCliente1(Cliente cliente1) {
		this.cliente1 = cliente1;
	}

	public DataModel<Cliente> getListaClientesEmpresaData() {
		//clientes = dao.list(parametros);
	//	listaClientesEmpresaData = new ListDataModel<Cliente>();
		return listaClientesEmpresaData;
	}

	public void setListaClientesEmpresaData(
			DataModel<Cliente> listaClientesEmpresaData) {
		this.listaClientesEmpresaData = listaClientesEmpresaData;
	}

	public int getIdEmpresa() {
		return IdEmpresa;
	}

	public void setIdEmpresa(int idEmpresa) {
		IdEmpresa = idEmpresa;
	}
	
	

	
}
