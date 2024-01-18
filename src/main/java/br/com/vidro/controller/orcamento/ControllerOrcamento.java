package br.com.vidro.controller.orcamento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.vidro.controller.dto.OrcamentoDto;
import br.com.vidro.dao.ProdutoDao;
import br.com.vidro.dao.RelatorioDao;
import br.com.vidro.dao.impl.ProdutoDaoImpl;
import br.com.vidro.dao.impl.RelatorioDaoImpl;
import br.com.vidro.entity.Produto;

@ConversationScoped
@Named
public class ControllerOrcamento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Logger log = LoggerFactory.getLogger(this.getClass());
 
	
	@Inject
	private RelatorioDaoImpl reldao;
	@Inject
	private Conversation conversation;
	@Inject
	private Produto produto;
	@Inject
	private ProdutoDaoImpl daoImpl;
	private int idProduto;
	private int qtdProduto;
	private double valorUnitario;
	private double valorTotalVendas;
	private String nomeProdutos;
	private List<OrcamentoDto> listasOrcamentos;
	private static OrcamentoDto orcamentoDto;
	
	private List<Produto> listasProdutos;
	


	public static OrcamentoDto getOrcamentoDto() {
	      if(orcamentoDto == null) {
	    	  orcamentoDto = new OrcamentoDto();
	      }
	      return orcamentoDto;
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
			System.out.println("Conversação encerrada, id: "
					+ conversation.getId());
			conversation.end();
		}
	}
	
	public void getListaProdutosValorUnitario() {
		 beginConversation();
		if(listasProdutos == null){
			listasProdutos	= new ArrayList<Produto>();	
		}
	     ProdutoDao dao = getDaoImpl();
	     listasProdutos = dao.listID(getIdProduto());
	     for(Produto prd: listasProdutos){
	    	 setValorUnitario(prd.getPrecoVenda());
	    	 getOrcamentoDto().setValorUnitario(getValorUnitario());
	    	 getOrcamentoDto().setNomeProdutos(prd.getNomeProduto());
	     }
		
		endConversation();
	}
	
	public void getQuantidadesProdutos() {
		 beginConversation();
		 double valor = getOrcamentoDto().getValorUnitario() * getQtdProduto();
         double resultado = valor + getValorTotalVendas() ;
	     setValorTotalVendas(resultado) ;
	   
	 
		
		
	}
	
	
	public void addOrcamentos(){
		beginConversation();
		if(listasOrcamentos == null){
			listasOrcamentos = new ArrayList<OrcamentoDto>();
		}
		//OrcamentoDto.quantidade = getQtdProduto();
		//orcamentoDto = new OrcamentoDto();
		orcamentoDto.setQuantidade(getQtdProduto());
		System.out.println("Produto" + produto.getEstoqueAtual());
		listasOrcamentos.add(orcamentoDto);
		getQuantidadesProdutos();
	}
	
	
	public void gerarOrcamentoRelatorio(){
		try {
		    RelatorioDao dao = getReldao();
			
			dao.imprimirOrcamento(listasOrcamentos);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	

	public int getIdProduto() {
		return idProduto;
	}

	public double getValorTotalVendas() {
		return valorTotalVendas;
	}

	public void setValorTotalVendas(double valorTotalVendas) {
		this.valorTotalVendas = valorTotalVendas;
	}

	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public int getQtdProduto() {
		return qtdProduto;
	}

	public void setQtdProduto(int qtdProduto) {
		this.qtdProduto = qtdProduto;
	}

	public double getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public ProdutoDaoImpl getDaoImpl() {
		return daoImpl;
	}

	public void setDaoImpl(ProdutoDaoImpl daoImpl) {
		this.daoImpl = daoImpl;
	}

	public List<Produto> getListasProdutos() {
		return listasProdutos;
	}

	public void setListasProdutos(List<Produto> listasProdutos) {
		this.listasProdutos = listasProdutos;
	}

	public List<OrcamentoDto> getListasOrcamentos() {
		return listasOrcamentos;
	}

	public void setListasOrcamentos(List<OrcamentoDto> listasOrcamentos) {
		this.listasOrcamentos = listasOrcamentos;
	}


	public void setOrcamentoDto(OrcamentoDto orcamentoDto) {
		this.orcamentoDto = orcamentoDto;
	}

	public String getNomeProdutos() {
		return nomeProdutos;
	}

	public void setNomeProdutos(String nomeProdutos) {
		this.nomeProdutos = nomeProdutos;
	}

	public RelatorioDaoImpl getReldao() {
		return reldao;
	}

	public void setReldao(RelatorioDaoImpl reldao) {
		this.reldao = reldao;
	}



}
