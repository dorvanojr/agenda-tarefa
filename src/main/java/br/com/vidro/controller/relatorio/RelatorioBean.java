package br.com.vidro.controller.relatorio;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.vidro.controller.dto.GenericDto;
import br.com.vidro.dao.RelatorioDao;
import br.com.vidro.dao.UsuarioDao;
import br.com.vidro.dao.impl.ClienteDaoImpl;
import br.com.vidro.dao.impl.RelatorioDaoImpl;
import br.com.vidro.dao.impl.UsuarioDaoImpl;
import br.com.vidro.entity.Cliente;
import br.com.vidro.entity.Documentos;
import br.com.vidro.entity.Empresa;
import br.com.vidro.entity.Endereco;
import br.com.vidro.entity.Produto;
import br.com.vidro.entity.Telefone;
import br.com.vidro.entity.User;

@ConversationScoped
@Named
public class RelatorioBean implements Serializable {

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
	private List<Cliente> listas;
	private List<Produto> selectProdutos;
	
	
	private Date dtinicial;
	private Date dtfinal;

	
	
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
	public void gerar() throws IOException {
        try{
		RelatorioDao dao = getRelDAO();
		Empresa empresa = new  Empresa();
		   UsuarioDao uDao = getUsuarioDaoImpl();
		   List<User> users = uDao.retornaEmpresa(getInstance().getLogin());
		   for(User user : users){
			   empresa = user.getEmpresa();
			    
		   }
			dao.gerar(empresa.getIdEmpresa(), getDtinicial(), getDtfinal());
        }catch(Exception ex){
        	System.out.println("Erro" + ex.getMessage());
        	
        }

	}

	
	public RelatorioDaoImpl getRelDAO() {
		return reldao;
	}

	public List<Cliente> getListas() {
		return listas;
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

	public Date getDtinicial() {
		return dtinicial;
	}

	public void setDtinicial(Date dtinicial) {
		this.dtinicial = dtinicial;
	}

	public Date getDtfinal() {
		return dtfinal;
	}

	public void setDtfinal(Date dtfinal) {
		this.dtfinal = dtfinal;
	}

	public List<Produto> getSelectProdutos() {
		return selectProdutos;
	}

	public void setSelectProdutos(List<Produto> selectProdutos) {
		this.selectProdutos = selectProdutos;
	}
	
	

	
}
