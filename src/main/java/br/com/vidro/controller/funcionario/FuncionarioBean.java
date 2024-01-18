package br.com.vidro.controller.funcionario;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;
import javax.transaction.Transactional;

import org.primefaces.event.RowEditEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.vidro.controller.dto.GenericDto;
import br.com.vidro.dao.FuncionarioDao;
import br.com.vidro.dao.RelatorioDao;
import br.com.vidro.dao.UsuarioDao;
import br.com.vidro.dao.impl.FuncionarioDaoImpl;
import br.com.vidro.dao.impl.RelatorioDaoImpl;
import br.com.vidro.dao.impl.UsuarioDaoImpl;
import br.com.vidro.entity.Cliente;
import br.com.vidro.entity.Documentos;
import br.com.vidro.entity.Email;
import br.com.vidro.entity.Empresa;
import br.com.vidro.entity.Endereco;
import br.com.vidro.entity.Funcionario;
import br.com.vidro.entity.Telefone;
import br.com.vidro.entity.User;
import br.com.vidro.entity.Userauth;

@ConversationScoped
@Named
public class FuncionarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Inject
	private Conversation conversation;
	@Inject
	private FuncionarioDaoImpl dao;
	@Inject
	private UsuarioDaoImpl usuarioDaoImpl;
	@Inject
	private RelatorioDaoImpl reldao;
	@Inject
	private Funcionario funcionario;
	@Inject
	private Cliente cliente;
	@Inject
	private Cliente cliente1;
	@Inject
	private Email email;
	@Inject
	private Telefone telefone;
	@Inject
	private Endereco endereco;
	@Inject
	private Documentos documentos;
	private List<Funcionario> clientes;
	private List<Funcionario> listas;
	private Integer idCliente;
	private String parametros;
	private DataModel<Funcionario> listaClientesData;
	private String NomeImg;
	private Part part;
	private String statusMessage;
	

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
	
	 
	    public DataModel<Funcionario> getListaClientesData() {
	    	FuncionarioDao dao =  getDAO();
	    	clientes = dao.list(parametros);
			listaClientesData = new ListDataModel<Funcionario>();
			return listaClientesData;
		}

		public void setListaClientesData(DataModel<Funcionario> listaClientesData) {
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
	
	
	public void setListas(List<Funcionario> listas) {
		this.listas = listas;
   }

	public List<Funcionario> getClientes() {
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
			   FuncionarioDao dao = getDAO();
			   cliente.setStatus(4);
			   cliente.setFornecedor(null);
			   email.setFuncionario(funcionario);
			   funcionario.setCliente(cliente);
			   endereco.setFuncionario(funcionario);
			   telefone.setFuncionario(funcionario);
			   documentos.setFuncionario(funcionario);
			   funcionario.setEmpresa(empresa);
			   funcionario.addDocumentos(documentos);
			   funcionario.addEnderecos(endereco);
			   funcionario.addTelefones(telefone);
			   funcionario.addEmail(email);
			   dao.save(funcionario);
			

			context.addMessage(null, new FacesMessage("Resultado",
					"Cadastrado com sucesso!!! " + ""));

		} catch (Exception ex) {

			context.addMessage(null, new FacesMessage("Resultado",
					"Erro ao Cadastrar!!! " + ex.getMessage()));
			ex.fillInStackTrace();
		}finally{
			endConversation();
		//	clientes = dao.listId(cliente.getIdCliente());
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
			   
			   FuncionarioDao dao = getDAO();
			   
			   
			  // dao.update(cliente1);	

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
	   FuncionarioDao dao = getDAO();
		UsuarioDao uDao = getUsuarioDaoImpl();
		Userauth auth = uDao.retornaUsuario(getInstance().getLogin());
        System.out.println("usuarioNOme " + auth.getUser().getUsername());
        Empresa empresa = new Empresa();
		   List<User> users = uDao.retornaEmpresa(getInstance().getLogin());
		   for(User user : users){
			   empresa = user.getEmpresa();
			    
		   }
		clientes = dao.listLoginNome(parametros, empresa.getIdEmpresa());
		listaClientesData = new ListDataModel<Funcionario>(clientes);
		parametros = "";
		return "";
	}
   @Transactional
	public void onRowEdit(RowEditEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();

		try {

			FuncionarioDao dao = getDAO();
			
		

			Cliente cliente1 = (Cliente) event.getObject();

			//dao.update(cliente1);

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
	public List<Funcionario> listClienteAll() {
		try {
			System.out.println(cliente.getNome());
			FuncionarioDao dao = getDAO();
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

	//	for (Funcionario funcionario : clientes) {
			//System.out.print("listar" + funcionario.getIdFuncionario());
			//dao.gerar(cliente.getIdCliente());
		//}

	}

	@Transactional
	public void excluir() {

		FuncionarioDao dao = getDAO();

		for (Funcionario fun : clientes) {
			dao.remove(fun);
		}
		clientes = dao.list(funcionario.getCliente().getNome());

	}

	public String uploadFile() throws IOException {
		beginConversation();
		ServletContext sContext = (ServletContext) FacesContext
				.getCurrentInstance().getExternalContext().getContext();
		// Extract file name from content-disposition header of file part
		String fileName = getFileName(part);
		System.out.println("***** fileName: " + fileName);
        
		String basePath = "C:" + File.separator + "anexos" + File.separator;
		String arquivo = sContext.getRealPath("/temp") + File.separator
				+ fileName;
		File outputFilePath = new File(arquivo);

		// Copy uploaded file to destination path
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			inputStream = part.getInputStream();
			outputStream = new FileOutputStream(outputFilePath);

			int read = 0;
			final byte[] bytes = new byte[1024];
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}

			File fileQuadrado = new File(arquivo);
			   System.out.println("***** fileName: " + arquivo);
			byte[] bFileQuadrado = new byte[(int) fileQuadrado.length()];
			
            setNomeImg(fileName);
            System.out.println("***** fileName: " + fileName);
            try {
				// Quadrado
				FileInputStream fileInputStreamQuadrado = new FileInputStream(
						fileQuadrado);
				fileInputStreamQuadrado.read(bFileQuadrado);
				fileInputStreamQuadrado.close();
				
				cliente.setImagem(bFileQuadrado);
			} catch (Exception ex) {

				ex.getStackTrace();
			}
			statusMessage = "File upload successfull !!";
		} catch (IOException e) {
			e.printStackTrace();
			statusMessage = "File upload failed !!";
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
		}
		return null; // return to same page
	}
	
	
	private String getFileName(Part part) {
		final String partHeader = part.getHeader("content-disposition");
		System.out.println("***** partHeader: " + partHeader);
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim()
						.replace("\"", "");
			}
		}
		return null;
	}

	@Transactional
	public String prepareUpdate() {
		FuncionarioDao dao = getDAO();
		for (Funcionario funcionario : clientes) {
			
			clientes = dao.list(funcionario.getCliente().getNome());
		}
		return "alteracao-autor";
	}

	public RelatorioDaoImpl getRelDAO() {
		return reldao;
	}

	public List<Funcionario> getListas() {
		return listas;
	}
	
	

	public void setClientes(List<Funcionario> clientes) {
		this.clientes = clientes;
	}

	public FuncionarioDaoImpl getDAO() {
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

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public String getNomeImg() {
		return NomeImg;
	}

	public void setNomeImg(String nomeImg) {
		NomeImg = nomeImg;
	}

	
	public Part getPart() {
		return part;
	}

	public void setPart(Part part) {
		this.part = part;
	}

	public FuncionarioDaoImpl getDao() {
		return dao;
	}

	public void setDao(FuncionarioDaoImpl dao) {
		this.dao = dao;
	}
	
	
}
