package br.com.vidro.controller.venda;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.vidro.controller.dto.GenericDto;
import br.com.vidro.controller.dto.VendasDto;
import br.com.vidro.dao.FuncionarioDao;
import br.com.vidro.dao.PagamentoDao;
import br.com.vidro.dao.ProdutoDao;
import br.com.vidro.dao.RelatorioDao;
import br.com.vidro.dao.TotalVendasDao;
import br.com.vidro.dao.UserDao;
import br.com.vidro.dao.UsuarioDao;
import br.com.vidro.dao.impl.FuncionarioDaoImpl;
import br.com.vidro.dao.impl.PagamentoDaoImpl;
import br.com.vidro.dao.impl.ProdutoDaoImpl;
import br.com.vidro.dao.impl.RelatorioDaoImpl;
import br.com.vidro.dao.impl.TotalVendasDaoImpl;
import br.com.vidro.dao.impl.UserDaoImpl;
import br.com.vidro.dao.impl.UsuarioDaoImpl;
import br.com.vidro.entity.Empresa;
import br.com.vidro.entity.FinalizarPagamento;
import br.com.vidro.entity.Funcionario;
import br.com.vidro.entity.Produto;
import br.com.vidro.entity.TipoPagamento;
import br.com.vidro.entity.TotalVendas;
import br.com.vidro.entity.User;
import br.com.vidro.entity.Userauth;
import br.com.vidro.entity.Vendas;
import br.com.vidro.entity.FormaPagamento;

@ConversationScoped
@Named
public class VendaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Inject
	private UsuarioDaoImpl usuarioDaoImpl;
	@Inject
	private Conversation conversation;
	@Inject
	private TotalVendas totalVendas;
	@Inject
	private Vendas vendas;
	@Inject
	private Vendas vendas1;
	@Inject
	private Produto produto;
	@Inject
	private Produto produto1;
	@Inject
	private FormaPagamento formaPagamento;
	@Inject
	private TipoPagamento tipoPagamento;
	@Inject
	private Funcionario funcionario;
	@Inject
	private ProdutoDaoImpl DAO;
	@Inject
	private TotalVendasDaoImpl daoImpl;
	@Inject
	private PagamentoDaoImpl pagamentoDaoImpl;
	@Inject
	private FuncionarioDaoImpl FuncionarioDAO;
	@Inject
    private FinalizarPagamento finalizarPagamento;
	@Inject
	private UserDaoImpl userDAO;
	@Inject
	private RelatorioDaoImpl reldao;
	private List<FormaPagamento> listasFormaPagamentos;
	private List<TipoPagamento> listasTipoPagamentos;
	private List<FormaPagamento> selectedFormaPagamentos;
	private List<TipoPagamento>  selectedTipoPagamentos;
	private List<Produto> listas;
	private List<Vendas> listasVendas;
	private List<Produto> listasDeProdutos;
	private String parametros;
	private String parametrosProdutos;
	private static GenericDto instance;
	private int quantidade;
	private double valorTotal;
	private VendasDto vendasDto;
	private List<VendasDto> listVendas;
	private List<Vendas> listVendas1;
	private List<FinalizarPagamento> listFinalizarPagamento;
	private DataModel<VendasDto> listaClientesData;
	private DataModel<Vendas> listaProdutosData;
	private int idFuncionario;
	private int idProduto;
	private double valorFaltando;
	private double valorParcelasVendas;
	private double valor;
    private int qtdParcelas;
    boolean resultadoparcelas = true;
    private double valorPagoCliente;
    private double valorTroco;
    private double valorTotalTrouco = valorTotal;
    
	public static GenericDto getInstance() {
		if (instance == null) {
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
			System.out.println("Conversação encerrada, id: "
					+ conversation.getId());
			conversation.end();
		}
	}
	
	
	public void addTiposPagamentos(){
		PagamentoDao dao = getPagamentoDaoImpl();
		listasTipoPagamentos = dao.findAllTipo();
	}
	
	public void error() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Contact admin."));
    }
	
	public void adicionarPagamentos(){
		FacesContext context = FacesContext.getCurrentInstance();
		beginConversation();
		try {
			if(listFinalizarPagamento == null){
				listFinalizarPagamento = new ArrayList<FinalizarPagamento>();
			}
			
			finalizarPagamento = new FinalizarPagamento();
			
			if(selectedFormaPagamentos.size() == 0 && selectedTipoPagamentos.size() == 0){
				 throw new IllegalArgumentException("E Necessario que adcione uma Forma e um Tipo de Pagamento!!!");

			}
			
			if(selectedFormaPagamentos.size() == 1){
			     for (int i = 0; i < selectedFormaPagamentos.size(); ++i) {
				   System.out.println("pagamneto" +selectedFormaPagamentos.get(i).getFormaPagamento());
				   getFinalizarPagamento().setFormaPagamento(selectedFormaPagamentos.get(i));
			   }
			}else if(selectedFormaPagamentos.size() == 0){
				 throw new IllegalArgumentException("E Necessario que adcione uma forma de pagamento !!!");

			}else if(selectedFormaPagamentos.size() > 1){
				 throw new IllegalArgumentException("So pode ser adcionado uma forma de pagamento por vez !!!");

			}
			
			if(selectedTipoPagamentos.size() == 1){
			     for (int i = 0; i < selectedTipoPagamentos.size(); ++i) {
				   System.out.println("pagamneto" +selectedTipoPagamentos.get(i).getTipoPagamento());
				   getFinalizarPagamento().setTipoPagamento(selectedTipoPagamentos.get(i));
			   }
			}else if(selectedTipoPagamentos.size() == 0){
				 throw new IllegalArgumentException("E Necessario que adcione um tipo de pagamento !!!");

			}else if(selectedTipoPagamentos.size() > 1){
				 throw new IllegalArgumentException("So pode ser adcionado um tipo de pagamento por vez !!!");

			}
			getFinalizarPagamento().setValorParcelas(valorParcelasVendas);
			getFinalizarPagamento().setQtdParcelas(getQtdParcelas());
			getFinalizarPagamento().setValorPago(getValorTotal());
		    
			
			    listFinalizarPagamento.add(getFinalizarPagamento());
			
		   
		} catch (Exception ex) {
			
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Error !!! " + ex.getMessage()));
			ex.fillInStackTrace();
		} finally {
			setValorTotal(0);
			selectedTipoPagamentos = new ArrayList<TipoPagamento>();
		    selectedFormaPagamentos = new ArrayList<FormaPagamento>();
		}

			
			
		
	}
	
	
    public TipoPagamento getTipoPagamentosHabilita(){ 
      TipoPagamento pagamento = null;
           for (int i = 0; i < selectedTipoPagamentos.size(); ++i) {
		   System.out.println("pagamneto" +selectedTipoPagamentos.get(i).getTipoPagamento());
		   pagamento = selectedTipoPagamentos.get(i);
	     } 
 
           return pagamento;
    }
    
    public boolean habilitaParcelados(){
    	
    	TipoPagamento pagamento = getTipoPagamentosHabilita();
    	if(pagamento.getIdtipoPagamento() == 2){
    		resultadoparcelas = false;
    	}else{
    		resultadoparcelas = true;
    	}
    	
    	
    	return resultadoparcelas;
    }
    		
    		
    
	public void getAddValorParcelas(){
		double valor = getValorTotal() / getQtdParcelas();
		setValorParcelasVendas(valor);
	}
	
	
	public void getAddValorPago(){
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			if(listFinalizarPagamento == null){
				listFinalizarPagamento = new ArrayList<FinalizarPagamento>();
			}
			if(listFinalizarPagamento.size() > 0){
			
		      for(FinalizarPagamento pagamento : listFinalizarPagamento){
		    	 setValor(pagamento.getValorPago() + getValorTotal());
		    	 if(getValor() >= vendas.getTotalVendas().getValorTotalVendas()){
		    		  setValorFaltando(vendas.getTotalVendas().getValorTotalVendas() - pagamento.getValorPago());
		    		  setValorTotal(getValorFaltando());
		    		  throw new IllegalArgumentException("Valor esta sendo maior que valor total da venda!!!");   
		    	 }else{
		    		 setValorFaltando(pagamento.getValorPago() - vendas.getTotalVendas().getValorTotalVendas());
		    		 setValorTotal(getValorFaltando()); 
		    	 }
		       }
			}
		   if(getValorTotal() > vendas.getTotalVendas().getValorTotalVendas()){
			   setValorTotal(vendas.getTotalVendas().getValorTotalVendas());
			   throw new IllegalArgumentException("Valor escrito e maior que o Valor Total !!!");
		   }else{
			setValorFaltando(vendas.getTotalVendas().getValorTotalVendas() -  getValorTotal());
			System.out.println("total" + vendas.getTotalVendas().getValorTotalVendas());
		   }
		   
		   setValorTotalTrouco(getValorTotal());
		}catch(Exception ex) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Error !!! " + ex.getMessage()));
			ex.fillInStackTrace();
		}
		
	}
	
	public void addFormasPagamentos(){
		PagamentoDao dao = getPagamentoDaoImpl();
		listasFormaPagamentos = dao.findAllForma();
	}
  
	public void getListaProdutos1() {
		FacesContext context = FacesContext.getCurrentInstance();
		try{
		beginConversation();	
		ServletContext sContext = (ServletContext) FacesContext
				.getCurrentInstance().getExternalContext().getContext();
		listas = new ArrayList<Produto>();
    	ProdutoDao dao = getDAO();
    	  Empresa empresa = new  Empresa();
		   UsuarioDao uDao = getUsuarioDaoImpl();
		   List<User> users = uDao.retornaEmpresa(getInstance().getLogin());
		   for(User user : users){
			   empresa = user.getEmpresa();  
		   }
		   
		   File folder = new File(sContext.getRealPath("/temp"));
			if (!folder.exists())
				folder.mkdirs();
			
    	listas = dao.listProdutLoginBarras(getParametros(), empresa.getIdEmpresa());
	       for(Produto produto : listas){
		        
	    	   setProduto(produto);
			   System.out.print("teste" + getProduto().getPrecoVenda());
				String nomeArquivo = produto.getCliente().getIdCliente() + ".jpg";
				String arquivo = sContext.getRealPath("/temp") + File.separator
						+ nomeArquivo;

				criaArquivo(produto.getCliente().getImagem(), arquivo);
				
				if(getProduto().getEstoqueAtual() <= 20){
					throw new IllegalArgumentException("Produto acabando no estoque, por favor refaça a reposição do estoque!!!");   
				}if(getProduto().getEstoqueAtual() == 0){
					throw new IllegalArgumentException("Produto acabou no estoque, por favor refaça a reposição do estoque!!!");   
			    }
			}
	       
	     
	    
	   setQuantidade(0);
	   setValorTotal(0);
	   
		}catch(Exception ex){
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Error !!! " + ex.getMessage()));
		}
	}
	
	
	
	public void addTroco(){
		setValorTroco(getValorPagoCliente() - getValorTotalTrouco());
	}
	
	public void getListaProdutos2() {
		beginConversation();	
		listasDeProdutos = new ArrayList<Produto>();
    	ProdutoDao dao = getDAO();
    	  Empresa empresa = new  Empresa();
		   UsuarioDao uDao = getUsuarioDaoImpl();
		   List<User> users = uDao.retornaEmpresa(getInstance().getLogin());
		   for(User user : users){
			   empresa = user.getEmpresa();  
		   }
    	  listasDeProdutos = dao.listProdutoLoginAll(empresa.getIdEmpresa());
	       for(Produto produto : listasDeProdutos){
		        
	    	    setProduto1(produto);
	    	    
	    	  
	     }
	   setQuantidade(0);
	   setValorTotal(0);
	}
	
	public void getListaProdutos3() {
		beginConversation();	
		ServletContext sContext = (ServletContext) FacesContext
				.getCurrentInstance().getExternalContext().getContext();
		listas = new ArrayList<Produto>();
    	ProdutoDao dao = getDAO();
  
		   File folder = new File(sContext.getRealPath("/temp"));
			if (!folder.exists())
				folder.mkdirs();
			
    	listas = dao.listID(getIdProduto());
	       for(Produto produto : listas){
		        
	    	   setProduto(produto);
			   System.out.print("teste" + getProduto().getPrecoVenda());
			   parametros = produto.getCodigoBarra();
				String nomeArquivo = produto.getCliente().getIdCliente() + ".jpg";
				String arquivo = sContext.getRealPath("/temp") + File.separator
						+ nomeArquivo;

				criaArquivo(produto.getCliente().getImagem(), arquivo);
				
				
			}
	   setQuantidade(0);
	   setValorTotal(0);
	}
	
	public void salvarProduto(){
     	System.out.println("id" + getIdProduto());
	}
	
	private void criaArquivo(byte[] bytes, String arquivo) {
		FileOutputStream fos;

		try {
			fos = new FileOutputStream(arquivo);
			fos.write(bytes);

			fos.flush();
			fos.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}


	public void getTotal() {
		if (listVendas == null) {
			listVendas = new ArrayList<VendasDto>();
		}
		double total = ((double) getQuantidade() * getProduto().getPrecoVenda());

		setValorTotal(total);

		int TotalVendas = (int) getTotalVendas().getValorTotalVendas();
		double total1 = TotalVendas + total;

		TotalVendas = (int) total1;

		getTotalVendas().setValorTotalVendas((double) TotalVendas);

		vendasDto = new VendasDto();
		int seq = vendasDto.getSeq() + 1;
		seq = seq++;
		vendasDto.setSeq(seq);
		vendasDto.setProduto(getProduto());
		vendasDto.setValorTotal(getValorTotal());
		vendasDto.setQtd((int) getQuantidade());
		listVendas.add(vendasDto);
		// endConversation();
	}


	public void finaliCompras() {
		
        listasVendas = new ArrayList<Vendas>();
		Empresa empresa = new Empresa();
		UsuarioDao uDao = getUsuarioDaoImpl();
		List<User> users = uDao.retornaEmpresa(getInstance().getLogin());
		for (User user : users) {
			empresa = user.getEmpresa();

		}
		FuncionarioDao Funciodao = getFuncionarioDAO();
		Funcionario funcionario = new Funcionario();
		List<Funcionario> funcionarios = Funciodao.listId(getIdFuncionario());
		for (Funcionario func : funcionarios) {
			funcionario = func;
		}
		Userauth auth = uDao.retornaUsuario(getInstance().getLogin());
		
		for (Iterator<VendasDto> it = listVendas.iterator(); it.hasNext();) {
			VendasDto a = it.next();
			
			

			getVendas().setProduto(a.getProduto());
			getVendas().setValorTotal(a.getValorTotal());
			getVendas().setQuantidade(a.getQtd());
			totalVendas.setFuncionario(funcionario);
			getVendas().setTotalVendas(totalVendas);
			getVendas().setEmpresa(empresa);
			getVendas().setUserauth(auth);
			getVendas().setDataVenda(new Date());
			listasVendas.add(getVendas());
			
			setVendas(new Vendas());
			   listaProdutosData = new ListDataModel<Vendas>(listasVendas); 
		}
		
	

	}
	
	  public String prepararFinalicaoVendas(){
		  FacesContext context = FacesContext.getCurrentInstance();
		  try{
		   addFormasPagamentos();
		   addTiposPagamentos();
		   finaliCompras();
	          vendas = (Vendas)(listaProdutosData.getRowData());
	        
	          setValorTotal(vendas.getTotalVendas().getValorTotalVendas());
	          setValorTotalTrouco(getValorTotal());
	        return "/br/com/vidro/controller/venda/finalizarCompra.jsf?faces-redirect=true";
	      }catch(Exception ex){
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Error !!! Para que seja liberado o finalizar da venda  e necessario  que faça a inicialização da venda !!!"));
		    return null;
	      }
	    }
	  
	  
	@SuppressWarnings("finally")
	public String fecharVendas(){
			FacesContext context = FacesContext.getCurrentInstance();
			
			try {
				Empresa empresa = new Empresa();
				UsuarioDao uDao = getUsuarioDaoImpl();
				
				  TotalVendasDao vendasDao = getDaoImpl();
				  totalVendas.setValorTotalVendas(getVendas().getTotalVendas().getValorTotalVendas());
				  for(FinalizarPagamento pagamento: listFinalizarPagamento){
	            	     pagamento.setTotalVendas(totalVendas);
	            	     totalVendas.addFinalizarVendas(pagamento);
	            	 
	             }
				  if(totalVendas.getFuncionario().getIdFuncionario() == 0){
	                   UserDao dao = getUserDAO();
		 			    List<User> users = dao.list(getInstance().getLogin());
		 			
		 			     for(User u : users){
		 			    	 
		 				   totalVendas.setFuncionario(u.getFuncionario());
		 				   getVendas1().setTotalVendas(totalVendas);
		 			     }
		                }
		              
				  vendasDao.save(totalVendas);  
				for (Iterator<Vendas> it =listasVendas.iterator(); it.hasNext();) {
					Vendas a = it.next();
					 beginConversation();
	             
	                 vendasDao.save(a);
	                 setVendas1(a);
	               
				 }
				
				List<User> users = uDao.retornaEmpresa(getInstance().getLogin());
				for (User user : users) {
					empresa = user.getEmpresa();

				}
				List<String> listasAString = new ArrayList<String>();  
				listasAString.add("as");
				gerar(getVendas1(), empresa, listasAString);
				for (Iterator<Vendas> it =listasVendas.iterator(); it.hasNext();) {
					Vendas a = it.next();
				   ProdutoDao dao = getDAO();
	               int qtdProduto = a.getProduto().getEstoqueAtual() - getQuantidade();
	               a.getProduto().setEstoqueAtual(qtdProduto);
	               dao.update(a.getProduto());
				}
				
				
			}catch(Exception ex){
				//context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Error !!! " + ex.getMessage()));
				ex.fillInStackTrace();
				
			}finally{
				
				
				//temporario depois deve ser corrigido
				context.addMessage(null, new FacesMessage("Resultado",
						"Venda Finalizada Com Sucesso!!! " + ""));
				endConversation();
				return "/imagem.jsf?faces-redirect=true";
			}
	  }
	
	@Transactional
	public void gerar(Vendas vendas, Empresa empresa, List<String> listasAString) throws IOException {
        try{
        
		    RelatorioDao dao = getReldao();
			dao.imprimirRecibo(vendas, empresa,  listasAString);
			
        }catch(Exception ex){
        	System.out.println("Erro" + ex.getMessage());
        	
        }

	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}

	public TotalVendas getTotalVendas() {
		return totalVendas;
	}

	public void setTotalVendas(TotalVendas totalVendas) {
		this.totalVendas = totalVendas;
	}

	public Vendas getVendas() {
		return vendas;
	}

	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public ProdutoDaoImpl getDAO() {
		return DAO;
	}

	public void setDAO(ProdutoDaoImpl dAO) {
		DAO = dAO;
	}

	public List<Produto> getListas() {

		return listas;
	}

	public void setListas(List<Produto> listas) {
		this.listas = listas;
	}

	public String getParametros() {
		return parametros;
	}

	public void setParametros(String parametros) {
		this.parametros = parametros;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = (int) quantidade;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public List<VendasDto> getListVendas() {
		return listVendas;
	}

	public void setListVendas(List<VendasDto> listVendas) {
		this.listVendas = listVendas;
	}

	public VendasDto getVendasDto() {
		return vendasDto;
	}

	public void setVendasDto(VendasDto vendasDto) {
		this.vendasDto = vendasDto;
	}

	public DataModel<VendasDto> getListaClientesData() {
		return listaClientesData;
	}

	public void setListaClientesData(DataModel<VendasDto> listaClientesData) {
		this.listaClientesData = listaClientesData;
	}

	public TotalVendasDaoImpl getDaoImpl() {
		return daoImpl;
	}

	public void setDaoImpl(TotalVendasDaoImpl daoImpl) {
		this.daoImpl = daoImpl;
	}

	public UsuarioDaoImpl getUsuarioDaoImpl() {
		return usuarioDaoImpl;
	}

	public void setUsuarioDaoImpl(UsuarioDaoImpl usuarioDaoImpl) {
		this.usuarioDaoImpl = usuarioDaoImpl;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public FuncionarioDaoImpl getFuncionarioDAO() {
		return FuncionarioDAO;
	}

	public void setFuncionarioDAO(FuncionarioDaoImpl funcionarioDAO) {
		FuncionarioDAO = funcionarioDAO;
	}

	public int getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(int idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public String getParametrosProdutos() {
		return parametrosProdutos;
	}

	public void setParametrosProdutos(String parametrosProdutos) {
		this.parametrosProdutos = parametrosProdutos;
	}

	public List<Produto> getListasDeProdutos() {
		return listasDeProdutos;
	}

	public void setListasDeProdutos(List<Produto> listasDeProdutos) {
		this.listasDeProdutos = listasDeProdutos;
	}

	public Produto getProduto1() {
		return produto1;
	}

	public void setProduto1(Produto produto1) {
		this.produto1 = produto1;
	}

	public int getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}

	public DataModel<Vendas> getListaProdutosData() {
		return listaProdutosData;
	}

	public void setListaProdutosData(DataModel<Vendas> listaProdutosData) {
		this.listaProdutosData = listaProdutosData;
	}

	public PagamentoDaoImpl getPagamentoDaoImpl() {
		return pagamentoDaoImpl;
	}

	public void setPagamentoDaoImpl(PagamentoDaoImpl pagamentoDaoImpl) {
		this.pagamentoDaoImpl = pagamentoDaoImpl;
	}

	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public TipoPagamento getTipoPagamento() {
		return tipoPagamento;
	}

	public void setTipoPagamento(TipoPagamento tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}

	public List<FormaPagamento> getListasFormaPagamentos() {
		return listasFormaPagamentos;
	}

	public void setListasFormaPagamentos(List<FormaPagamento> listasFormaPagamentos) {
		this.listasFormaPagamentos = listasFormaPagamentos;
	}

	public List<TipoPagamento> getListasTipoPagamentos() {
		return listasTipoPagamentos;
	}

	public void setListasTipoPagamentos(List<TipoPagamento> listasTipoPagamentos) {
		this.listasTipoPagamentos = listasTipoPagamentos;
	}

	public List<Vendas> getListasVendas() {
		return listasVendas;
	}

	public void setListasVendas(List<Vendas> listasVendas) {
		this.listasVendas = listasVendas;
	}

	public List<FormaPagamento> getSelectedFormaPagamentos() {
		return selectedFormaPagamentos;
	}

	public void setSelectedFormaPagamentos(
			List<FormaPagamento> selectedFormaPagamentos) {
		this.selectedFormaPagamentos = selectedFormaPagamentos;
	}

	public List<TipoPagamento> getSelectedTipoPagamentos() {
		return selectedTipoPagamentos;
	}

	public void setSelectedTipoPagamentos(List<TipoPagamento> selectedTipoPagamentos) {
		this.selectedTipoPagamentos = selectedTipoPagamentos;
	}

	public FinalizarPagamento getFinalizarPagamento() {
		return finalizarPagamento;
	}

	public void setFinalizarPagamento(FinalizarPagamento finalizarPagamento) {
		this.finalizarPagamento = finalizarPagamento;
	}

	public List<FinalizarPagamento> getListFinalizarPagamento() {
		return listFinalizarPagamento;
	}

	public void setListFinalizarPagamento(
			List<FinalizarPagamento> listFinalizarPagamento) {
		this.listFinalizarPagamento = listFinalizarPagamento;
	}

	public double getValorFaltando() {
		return valorFaltando;
	}

	public void setValorFaltando(double valorFaltando) {
		this.valorFaltando = valorFaltando;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public double getValorParcelasVendas() {
		return valorParcelasVendas;
	}

	public void setValorParcelasVendas(double valorParcelasVendas) {
		this.valorParcelasVendas = valorParcelasVendas;
	}

	public int getQtdParcelas() {
		return qtdParcelas;
	}

	public void setQtdParcelas(int qtdParcelas) {
		this.qtdParcelas = qtdParcelas;
	}

	public boolean isResultadoparcelas() {
		return resultadoparcelas;
	}

	public void setResultadoparcelas(boolean resultadoparcelas) {
		this.resultadoparcelas = resultadoparcelas;
	}

	public double getValorPagoCliente() {
		return valorPagoCliente;
	}

	public void setValorPagoCliente(double valorPagoCliente) {
		this.valorPagoCliente = valorPagoCliente;
	}

	public double getValorTroco() {
		return valorTroco;
	}

	public void setValorTroco(double valorTroco) {
		this.valorTroco = valorTroco;
	}

	public double getValorTotalTrouco() {
		return valorTotalTrouco;
	}

	public void setValorTotalTrouco(double valorTotalTrouco) {
		this.valorTotalTrouco = valorTotalTrouco;
	}

	public UserDaoImpl getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDaoImpl userDAO) {
		this.userDAO = userDAO;
	}

	public RelatorioDaoImpl getReldao() {
		return reldao;
	}

	public void setReldao(RelatorioDaoImpl reldao) {
		this.reldao = reldao;
	}

	public List<Vendas> getListVendas1() {
		return listVendas1;
	}

	public void setListVendas1(List<Vendas> listVendas1) {
		this.listVendas1 = listVendas1;
	}

	public Vendas getVendas1() {
		return vendas1;
	}

	public void setVendas1(Vendas vendas1) {
		this.vendas1 = vendas1;
	}
	
	
	

}
