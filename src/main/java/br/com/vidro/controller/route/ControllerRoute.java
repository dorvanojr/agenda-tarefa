package br.com.vidro.controller.route;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ConversationScoped
@Named
public class ControllerRoute implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Inject
	private Conversation conversation;

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
			System.out.println("Conversação encerrada, id: "
					+ conversation.getId());
			conversation.end();
		}
	}

	private String usuario;
	private String senha;

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getMessage() {

		if (usuario.equals("admin") && senha.equals("admin")) {

			return "imagem";

		} else {

			return "invalid";
		}
	}

	@Transactional
	public String voltar() {
        
		return "br/com/vidro/controller/login/login";

	}

	@Transactional
	public String consultarCliente() {
		beginConversation();
		return "br/com/vidro/controller/cliente/consultaCliente.jsf?faces-redirect=true";
	}

	@Transactional
	public String cunsultarProduto() {
		beginConversation();
		return "br/com/vidro/controller/produto/consultaProduto.jsf?faces-redirect=true";
	}
	
	@Transactional
	public String cunsultarEmpresa() {
		beginConversation();
		return "br/com/vidro/controller/empresa/consultaEmpresa.jsf?faces-redirect=true";
	}

	@Transactional
	public String cunsultarFornecedor() {
		beginConversation();
		return "br/com/vidro/controller/fornecedor/consultaFornecedor.jsf?faces-redirect=true";
	}

	@Transactional
	public String cunsultarFuncionario() {
		beginConversation();
		return "br/com/vidro/controller/funcionario/consultaFuncionario.jsf?faces-redirect=true";
	}

	
	public String controllerVendas() {
		beginConversation();
		return "br/com/vidro/controller/venda/controllerVenda.jsf?faces-redirect=true";
	}
	
	@Transactional
	public String finalizarVendas() {
		beginConversation();
		return "/br/com/vidro/controller/venda/finalizarCompra.jsf?faces-redirect=true";
	}
	
	@Transactional
	public String gerarOrcamento() {
		beginConversation();
		return "/br/com/vidro/controller/orcamento/gerarOrcamento.jsf?faces-redirect=true";
	}
	
	public String cadastroProduto() {
		beginConversation();
	    return "/br/com/vidro/controller/produto/cadastroProduto.jsf?faces-redirect=true";
		
	}
	
	public String cadastroFuncionario() {
		beginConversation();
	    return "/br/com/vidro/controller/funcionario/cadastroFuncionario.jsf?faces-redirect=true";
		
	}
	
	public String cadastroEmpresa() {
		beginConversation();
	    return "/br/com/vidro/controller/empresa/cadastroEmpresa.jsf?faces-redirect=true";
		
	}
	
	
	@Transactional
	public String criarUsuario() {
		endConversation();
	    return "/br/com/vidro/controller/usuario/usuario.jsf?faces-redirect=true";
		
	}
	
	@Transactional
	public String criarFuncionario() {
		endConversation();
	    return "/br/com/vidro/controller/funcionario/consultaFuncionario.jsf?faces-redirect=true";
		
	}

	@Transactional
	public String voltarUsuario() {
	beginConversation();
	    return "../usuario.jsf?faces-redirect=true";
		
	}
	
	 @Transactional
	 public String cadastroUsuarioPorEmpresa(){
	 beginConversation();
	  return "/br/com/vidro/controller/usuario/cadastroUsuarioPorEmpresa.jsf?faces-redirect=true";
	   		
	 }   
	
    @Transactional
	public String consultarUsuario() {
    	beginConversation();
	    return "/br/com/vidro/controller/usuario/cadastroUsuario.jsf?faces-redirect=true";
		
	}   
 
    public String EmailPage() {
    	beginConversation();
	    return "br/com/vidro/controller/email/email.jsf?faces-redirect=true";
		
	}
    
    @Transactional
	public String consultaProduto() {
		endConversation();
	    return "/br/com/vidro/controller/produto/consultaProduto.jsf?faces-redirect=true";
		
	}

}
