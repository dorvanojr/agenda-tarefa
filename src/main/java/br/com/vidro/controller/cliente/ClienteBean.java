package br.com.vidro.controller.cliente;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.primefaces.event.RowEditEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;

import br.com.vidro.controller.dto.GenericDto;
import br.com.vidro.controller.dto.OrcamentoDto;
import br.com.vidro.dao.ClienteDao;
import br.com.vidro.dao.RelatorioDao;
import br.com.vidro.dao.UsuarioDao;
import br.com.vidro.dao.impl.ClienteDaoImpl;
import br.com.vidro.dao.impl.RelatorioDaoImpl;
import br.com.vidro.dao.impl.UsuarioDaoImpl;
import br.com.vidro.entity.Cliente;
import br.com.vidro.entity.Documentos;
import br.com.vidro.entity.Empresa;
import br.com.vidro.entity.Endereco;
import br.com.vidro.entity.Telefone;
import br.com.vidro.entity.User;
import br.com.vidro.entity.Userauth;

@ConversationScoped
@Named
public class ClienteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Inject
	private Conversation conversation;
	@Inject
	private ClienteDaoImpl dao;
	@Inject
	private UsuarioDaoImpl usuarioDaoImpl;
	@Inject
	private RelatorioDaoImpl reldao;
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
	private List<Cliente> clientes;
	private List<Cliente> listas;
	private Integer idCliente;
	private String parametros;
	private DataModel<Cliente> listaClientesData;
	
	
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
	
		
		
	 
	    public DataModel<Cliente> getListaClientesData() {
	    	ClienteDao dao = getDAO();
	    	clientes = dao.list(parametros);
			listaClientesData = new ListDataModel<Cliente>();
			return listaClientesData;
		}

		public void setListaClientesData(DataModel<Cliente> listaClientesData) {
			this.listaClientesData = listaClientesData;
		}

     public void cleanForm(){
    	 cliente.setIdCliente(0);
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
    	
     }


	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public void setListas(List<Cliente> listas) {
		this.listas = listas;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}
	
	public void save() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			   beginConversation();
			   System.out.println("usuario login" + GenericDto.login + " Singleton " +getInstance().getLogin());
			   
			   Empresa empresa = new  Empresa();
			   UsuarioDao uDao = getUsuarioDaoImpl();
			   List<User> users = uDao.retornaEmpresa(getInstance().getLogin());
			   for(User user : users){
				   empresa = user.getEmpresa();
				    
			   }
               System.out.println("usuarioNOme " + empresa.getIdEmpresa());
			   ClienteDao dao = getDAO();
			   cliente.setStatus(2);
			   cliente.setFornecedor(null);
			   cliente.setEmpresa(empresa);
			   endereco.setCliente(cliente);
			   endereco.setEmpresa(empresa);
			   telefone.setCliente(cliente);
			   telefone.setEmpresa(empresa);
			   documentos.setCliente(cliente);
			   documentos.setEmpresa(empresa);
			   cliente.addDocumentos(documentos);
			   cliente.addEnderecos(endereco);
			   cliente.addTelefones(telefone);
			
			   dao.save(cliente);
			

			context.addMessage(null, new FacesMessage("Resultado",
					"Cadastrado com sucesso!!! " + ""));

		} catch (Exception ex) {

			context.addMessage(null, new FacesMessage("Resultado",
					"Erro ao Cadastrar!!! " + ex.getMessage()));
			ex.fillInStackTrace();
		}finally{
			endConversation();
			clientes = dao.listId(cliente.getIdCliente());
		}
	}
	
	public Cliente selectCliente(Cliente cliente1){
		
		return this.cliente1 = cliente1;
	}
	
	
	
	@Transactional
	public void update() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			  
		
			   System.out.println("teste" +  cliente1);
			   
			   ClienteDao dao = getDAO();
			   
			   
			   dao.update(cliente1);	

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
		ClienteDao dao = getDAO();
		UsuarioDao uDao = getUsuarioDaoImpl();
		Userauth auth = uDao.retornaUsuario(getInstance().getLogin());
        System.out.println("usuarioNOme " + auth.getUser().getUsername());
        Empresa empresa = new Empresa();
		   List<User> users = uDao.retornaEmpresa(getInstance().getLogin());
		   for(User user : users){
			   empresa = user.getEmpresa();
			    
		   }
		clientes = dao.listLoginNome(parametros, empresa.getIdEmpresa());
		listaClientesData = new ListDataModel<Cliente>(clientes);
		parametros = "";
		return "";
	}
   @Transactional
	public void onRowEdit(RowEditEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();

		try {

			ClienteDao dao = getDAO();
			
		

			Cliente cliente1 = (Cliente) event.getObject();

			dao.update(cliente1);

			context.addMessage(null, new FacesMessage("Resultado",
					"Alterado com sucesso!!! " + ""));

		} catch (Exception ex) {

			context.addMessage(null, new FacesMessage("Resultado",
					"Alterado com sucesso!!! " + ""));
		}

	}


   @Transactional
	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edit Cancelled",
				((Cliente) event.getObject()).getNome());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	

	@Transactional
	public List<Cliente> listClienteAll() {
		try {
			System.out.println(cliente.getNome());
			ClienteDao dao = getDAO();
			 UsuarioDao uDao = getUsuarioDaoImpl();
			 Empresa empresa = new Empresa();
			   List<User> users = uDao.retornaEmpresa(getInstance().getLogin());
			   for(User user : users){
				   empresa = user.getEmpresa();
				    
			   }
			listas = dao.listLoginAll(empresa.getIdEmpresa());

		} catch (Exception ex) {

		}
		return listas;

	}

	@Transactional
	public void gerar() throws IOException {

		RelatorioDao dao = getRelDAO();

		for (Cliente cliente : clientes) {
			System.out.print("listar" + cliente.getIdCliente());
			//dao.gerar(cliente.getIdCliente());
		}

	}

	@Transactional
	public void excluir() {

		ClienteDao dao = getDAO();

		for (Cliente cliente : clientes) {
			dao.remove(cliente);
		}
		clientes = dao.list(cliente.getNome());

	}


	@Transactional
	public String prepareUpdate() {
		ClienteDao dao = getDAO();
		for (Cliente cliente : clientes) {
			clientes = dao.list(cliente.getNome());
		}
		return "alteracao-autor";
	}

	public RelatorioDaoImpl getRelDAO() {
		return reldao;
	}

	public List<Cliente> getListas() {
		return listas;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public ClienteDaoImpl getDAO() {
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


	public Cliente getCliente1() {
		return cliente1;
	}


	public void setCliente1(Cliente cliente1) {
		this.cliente1 = cliente1;
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

	
}
