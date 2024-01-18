package br.com.vidro.controller.produto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lowagie.text.pdf.PRAcroForm;

import br.com.vidro.controller.dto.GenericDto;
import br.com.vidro.controller.fornecedor.FornecedorBean;
import br.com.vidro.dao.CategoriaDao;
import br.com.vidro.dao.ClienteDao;
import br.com.vidro.dao.FornecedorDao;
import br.com.vidro.dao.GrupoDao;
import br.com.vidro.dao.ProdutoDao;
import br.com.vidro.dao.UsuarioDao;
import br.com.vidro.dao.impl.CategoriaDaoImpl;
import br.com.vidro.dao.impl.ClienteDaoImpl;
import br.com.vidro.dao.impl.GrupoDaoImpl;
import br.com.vidro.dao.impl.ProdutoDaoImpl;
import br.com.vidro.dao.impl.UsuarioDaoImpl;
import br.com.vidro.entity.Categoria;
import br.com.vidro.entity.Cliente;
import br.com.vidro.entity.Empresa;
import br.com.vidro.entity.Fornecedor;
import br.com.vidro.entity.Grupo;
import br.com.vidro.entity.Produto;
import br.com.vidro.entity.User;


@ConversationScoped
@Named
public class ProdutoBean implements Serializable {


	private static final long serialVersionUID = 1L;
	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Inject
	private Conversation conversation;
	@Inject Fornecedor fornecedor;
	@Inject private UsuarioDaoImpl usuarioDaoImpl;
	@Inject Produto produto;
	@Inject Produto produto1;
	@Inject Categoria categoria;
	@Inject Grupo grupo;
	@Inject FornecedorBean fornecedorBean;
	@Inject 
	private Cliente cliente;
	@Inject private ProdutoDaoImpl DAO;
	@Inject private CategoriaDaoImpl categoriaDAO;
	@Inject private GrupoDaoImpl grupoDAO;
	private String ProdutoF;
	List<Produto> produtos; 
	List<Produto> produtosAll; 
	@Inject
	private ClienteDaoImpl dao;
	private DataModel listaProdutos;
	private Part part;
	private Part part2;
	private String NomeImg; 
	private String NomeImg2;
	private Date dtValidade;
	private boolean lista = true;
	
	private static GenericDto instance;

	public static GenericDto getInstance() {
	      if(instance == null) {
	         instance = new GenericDto();
	      }
	      return instance;
	 }

	public Part getPart2() {
		return part2;
	}

	public void setPart2(Part part2) {
		this.part2 = part2;
	}

	public String getNomeImg2() {
		return NomeImg2;
	}

	public void setNomeImg2(String nomeImg2) {
		NomeImg2 = nomeImg2;
	}

	private String parametro;
	private String statusMessage;
	private byte[] image;
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
		 

	

	
	
	
	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}

	
	
	@Transactional
	public List<Produto> listProdutoAllOrcamento() {
			
			 Empresa empresa = new  Empresa();
			   UsuarioDao uDao = getUsuarioDaoImpl();
			   List<User> users = uDao.retornaEmpresa(getInstance().getLogin());
			   for(User user : users){
				   empresa = user.getEmpresa();
				    
			   }

            ProdutoDao dao = getDAO();
			produtosAll = dao.listProdutoLoginAll(empresa.getIdEmpresa());
			
		return produtosAll;

	}
	
	@Transactional
	public List<Produto> listProdutoAll() {
		if(isLista()){
		try {
			
			 Empresa empresa = new  Empresa();
			   UsuarioDao uDao = getUsuarioDaoImpl();
			   List<User> users = uDao.retornaEmpresa(getInstance().getLogin());
			   for(User user : users){
				   empresa = user.getEmpresa();
				    
			   }

            ProdutoDao dao = getDAO();
			produtosAll = dao.listProdutoLoginAll(empresa.getIdEmpresa());
			
			String resultadoNome = "" ;
			if(produtosAll.size() > 0){
              for(int i= 0; i < produtosAll.size(); i++){  
			      Date date = new Date();
			
 				 if(date.after(produtosAll.get(i).getDtValidade())){ 
 					 DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
               	     String dateF = dateFormat.format(produtosAll.get(i).getDtValidade());
					 resultadoNome += "#Produto " + produtosAll.get(i).getNomeProduto()  
							                     + " esta com a validade vencida data: " + dateF 
							                     + ".---------------------------------------------------------------------------------------------------------------------------------------------------"
					                             + "----------------------------------------------------------------------------------";
			     }
                if((date).before(produtosAll.get(i).getDtValidade())){
                	
                 	 DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                	 String s = df.format(date);
                	     		   	
                     DateFormat dV = new SimpleDateFormat("dd/MM/yyyy");
                	 String dataV = dV.format(produtosAll.get(i).getDtValidade());
                	          		   
                	 long dias = calcular (s, dataV); 
               	          		    
                	  if(dias <= 20 ){
                		     DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        	 String dateF = dateFormat.format(produtosAll.get(i).getDtValidade());
                	      resultadoNome += "#Produto " + produtosAll.get(i).getNomeProduto() 
                	    		                      + " esta com a validade vencendo data: " + dateF 
                	    		                      + " faltam " + dias +" dias para vencer este produto. --------------------------------------------------------------------------------------------"
                	    		                      + "-------------------------------------------------------------------------- "; 		    
                	  }
                 } 	  
				   if(i==produtosAll.size()-1){
			        	if(resultadoNome != ""){
					      throw new IllegalArgumentException(resultadoNome);
			        	}
					   
			        }
           
		     }
		   }  
		
	      } catch (Exception ex) {
			RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção", ex.getMessage()));

		  }   
		
		}
		return produtosAll;

	}
	
	public long calcular (String inicio, String fim) throws ParseException  {
	   	 DateFormat df = new SimpleDateFormat ("dd/MM/yyyy");
	     Date dtInicial = df.parse (inicio);
	     Date dtFinal = df.parse (fim);
	     return (dtFinal.getTime() - dtInicial.getTime() + 3600000L) / 86400000L;
	}
	
	public void onRowEdit(RowEditEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();

		try {

			ProdutoDao dao = getDAO();

			Produto produto1 = (Produto) event.getObject();

			dao.update(produto1);

			context.addMessage(null, new FacesMessage("Resultado",
					"Alterado com sucesso!!! " + ""));

		} catch (Exception ex) {

			context.addMessage(null, new FacesMessage("Resultado",
					"Alterado com sucesso!!! " + ""));
		}

	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edit Cancelled",
				((Cliente) event.getObject()).getNome());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	
	

	@Transactional
	public String listProduto() {
		try {
			 setLista(false);
			 Empresa empresa = new  Empresa();
			   UsuarioDao uDao = getUsuarioDaoImpl();
			   List<User> users = uDao.retornaEmpresa(getInstance().getLogin());
			   for(User user : users){
				   empresa = user.getEmpresa();
				    
			   }
		   
            ProdutoDao dao = getDAO();
			produtos = dao. listProdutLoginNome(parametro, empresa.getIdEmpresa());
			for (Produto f : produtos) {
			   getListarProdutos(produtos, f);
			
			}
            
		} catch (Exception ex) {

		}
		return "";

	}
	
	


	@Transactional
	public void save() {

	FacesContext context = FacesContext.getCurrentInstance();
	try{
		beginConversation();
	 
	 String obrigatorio = "";
		
	 if(produto.getDescricaoProduto() == null || produto.getDescricaoProduto().equals("")){
		 
		    obrigatorio += "Campo Descrição do Produto e obrigatorio!!!";   
		 
	 }if(produto.getCodigoBarra() == null || produto.getCodigoBarra().equals("")){
			 
			 obrigatorio += "Campo Codigo de Barra e obrigatorio!!!";   
	 
	 }if(produto.getEstoqueAtual() == 0){
		 
		    obrigatorio += "Campo Quantidade do Estoque e obrigatorio!!!";   
 
     }if(produto.getPrecoVenda() == 0){
		 
		    obrigatorio += "Campo Preço de Venda e obrigatorio!!!";   
 
     }if(produto.getPrecoCusto() == 0){
		 
		    obrigatorio += "Campo Preço de Custo e obrigatorio!!!";   
 
     }else{		 
		  ProdutoDao dao = getDAO();
		  Empresa empresa = new  Empresa();
		  UsuarioDao uDao = getUsuarioDaoImpl();
		   List<User> users = uDao.retornaEmpresa(getInstance().getLogin());
		   for(User user : users){
			   empresa = user.getEmpresa();
			    
		   }
         System.out.println("usuarioNOme " + empresa.getIdEmpresa());
          cliente.setEmpresa(empresa);                   
          produto.setEmpresa(empresa);
          cliente.setStatus(3);
	      produto.setCliente(cliente);
	      produto.setNomeFornecedor(ProdutoF);
	      produto.setDtValidade(dtValidade);
		  dao.save(produto);
		  
		  context.addMessage(null, new FacesMessage("Resultado",
					"Cadastrado com sucesso!!! " + ""));
	       endConversation();
	  }
	   if(obrigatorio != null){
	      throw new IllegalArgumentException(obrigatorio); 
	   }
		
	} catch (Exception ex) {

		context.addMessage(null, new FacesMessage("Resultado",
				"Erro ao Cadastrar!!! " + ex.getMessage()));
		     ex.fillInStackTrace();
	 }
   }
	
	@Transactional
	public void update() {

	FacesContext context = FacesContext.getCurrentInstance();
	try{
		beginConversation();
		ProdutoDao dao = getDAO();
		dao.update(produto1);
	
       endConversation();
	} catch (Exception ex) {

	//	context.addMessage(null, new FacesMessage("Resultado",
			//	"Erro ao Alterado!!! " + ex.fillInStackTrace()));
		     ex.fillInStackTrace();
	 }finally{
		 //funcionando gato
		 context.addMessage(null, new FacesMessage("Resultado",
					"Alterado com sucesso!!! " + ""));
	 }
   }
	
	@Transactional
	public void saveGrupo() {

	FacesContext context = FacesContext.getCurrentInstance();
	try{
		beginConversation();
	
		GrupoDao dao = getGrupoDAO();
		 Empresa empresa = new  Empresa();
		   UsuarioDao uDao = getUsuarioDaoImpl();
		   List<User> users = uDao.retornaEmpresa(getInstance().getLogin());
		   for(User user : users){
			   empresa = user.getEmpresa();
			    
		   }
         System.out.println("usuarioNOme " + empresa.getIdEmpresa());
         grupo.setEmpresa(empresa);
		dao.save(grupo);
		
		
		context.addMessage(null, new FacesMessage("Resultado",
				"Cadastrado com sucesso!!! " + ""));
       endConversation();
	} catch (Exception ex) {

		context.addMessage(null, new FacesMessage("Resultado",
				"Erro ao Cadastrar!!! " + ex.fillInStackTrace()));
		     ex.fillInStackTrace();
	 }
   }
	
	
	@Transactional
	public void saveCategoria() {

	FacesContext context = FacesContext.getCurrentInstance();
	try{
		beginConversation();
	
		CategoriaDao dao = getCategoriaDAO();
		Empresa empresa = new  Empresa();
		UsuarioDao uDao = getUsuarioDaoImpl();
		List<User> users = uDao.retornaEmpresa(getInstance().getLogin());
		for(User user : users){
			   empresa = user.getEmpresa();
			    
		   }
         System.out.println("usuarioNOme " + empresa.getIdEmpresa());
        categoria.setEmpresa(empresa);
		dao.save(categoria);
		
		
		context.addMessage(null, new FacesMessage("Resultado",
				"Cadastrado com sucesso!!! " + ""));
       endConversation();
	} catch (Exception ex) {

		context.addMessage(null, new FacesMessage("Resultado",
				"Erro ao Cadastrar!!! " + ex.fillInStackTrace()));
		     ex.fillInStackTrace();
	 }
   }
	
	public String getProdutoF() {
		return ProdutoF;
	}




	public void setProdutoF(String produtoF) {
		ProdutoF = produtoF;
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

	
	public String uploadFile2() throws IOException {
		beginConversation();
		ServletContext sContext = (ServletContext) FacesContext
				.getCurrentInstance().getExternalContext().getContext();
		// Extract file name from content-disposition header of file part
		String fileName = getFileName2(part2);
		System.out.println("***** fileName: " + fileName);
        
		String basePath = "C:" + File.separator + "anexos" + File.separator;
		String arquivo = sContext.getRealPath("/temp") + File.separator
				+ fileName;
		File outputFilePath = new File(arquivo);

		// Copy uploaded file to destination path
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			inputStream = part2.getInputStream();
			outputStream = new FileOutputStream(outputFilePath);

			int read = 0;
			final byte[] bytes = new byte[1024];
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}

			File fileQuadrado = new File(arquivo);
			   System.out.println("***** fileName: " + arquivo);
			byte[] bFileQuadrado = new byte[(int) fileQuadrado.length()];
			
            setNomeImg2(fileName);
            System.out.println("***** fileName: " + fileName);
            try {
				// Quadrado
				FileInputStream fileInputStreamQuadrado = new FileInputStream(
						fileQuadrado);
				fileInputStreamQuadrado.read(bFileQuadrado);
				fileInputStreamQuadrado.close();
				
				cliente.setImagem2(bFileQuadrado);
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
		
		private String getFileName2(Part part) {
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

	public Fornecedor getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
	
	public FornecedorBean getFornecedorBean() {
		return fornecedorBean;
	}
	public void setFornecedorBean(FornecedorBean fornecedorBean) {
		this.fornecedorBean = fornecedorBean;
	}
	

	public List<Produto> getProdutosAll() {	
		return produtosAll;
	}




	public void setProdutosAll(List<Produto> produtosAll) {
		this.produtosAll = produtosAll;
	}




	public List<Produto> getProdutos() {
		return produtos;
	}




	public String getParametro() {
		return parametro;
	}




	public void setParametro(String parametro) {
		this.parametro = parametro;
	}




	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public Part getPart() {
		 
		return part;
	}

	public void setPart(Part part) {
		this.part = part;
	}

	public String getNomeImg() {
		return NomeImg;
	}

	public void setNomeImg(String nomeImg) {
		NomeImg = nomeImg;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public ClienteDaoImpl getDao() {
		return dao;
	}

	public void setDao(ClienteDaoImpl dao) {
		this.dao = dao;
	}

	public UsuarioDaoImpl getUsuarioDaoImpl() {
		return usuarioDaoImpl;
	}

	public void setUsuarioDaoImpl(UsuarioDaoImpl usuarioDaoImpl) {
		this.usuarioDaoImpl = usuarioDaoImpl;
	}

	public Produto getProduto1() {
		return produto1;
	}

	public void setProduto1(Produto produto1) {
		this.produto1 = produto1;
	}

    
    @Transactional
  	public String updateProduto() {
        produto1 = (Produto)(listaProdutos.getRowData());
  	    return "/br/com/vidro/controller/produto/updateProduto.jsf?faces-redirect=true";
  		
  	}

    public DataModel getListarProdutos(List<Produto> produtos, Produto produto) {
    	ServletContext sContext = (ServletContext) FacesContext
				.getCurrentInstance().getExternalContext().getContext();
    	ProdutoDao dao = getDAO();
		produtos  = dao.list(produto.getNomeProduto());
		  
		File folder = new File(sContext.getRealPath("/temp"));
		if (!folder.exists())
			folder.mkdirs();

		for (Produto f : produtos) {
			produto1 = f;
			setNomeImg(String.valueOf(f.getCliente().getIdCliente()));
			setNomeImg2(String.valueOf(f.getIdProduto()));
			String nomeArquivo = f.getCliente().getIdCliente()	 + ".jpg";
			String arquivo = sContext.getRealPath("/temp") + File.separator
					+ nomeArquivo;
			
			String nomeArquivo1 = f.getIdProduto()	 + ".jpg";
			String arquivo1 = sContext.getRealPath("/temp") + File.separator
					+ nomeArquivo1;

			criaArquivo(f.getCliente().getImagem(), arquivo);
			criaArquivo(f.getCliente().getImagem2(), arquivo1);
		}
        listaProdutos = new ListDataModel(produtos);

        return listaProdutos;
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

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public CategoriaDaoImpl getCategoriaDAO() {
		return categoriaDAO;
	}

	public void setCategoriaDAO(CategoriaDaoImpl categoriaDAO) {
		this.categoriaDAO = categoriaDAO;
	}

	public GrupoDaoImpl getGrupoDAO() {
		return grupoDAO;
	}

	public void setGrupoDAO(GrupoDaoImpl grupoDAO) {
		this.grupoDAO = grupoDAO;
	}

	public Date getDtValidade() {
		return dtValidade;
	}

	public void setDtValidade(Date dtValidade) {
		this.dtValidade = dtValidade;
	}

	public boolean isLista() {
		return lista;
	}

	public void setLista(boolean lista) {
		this.lista = lista;
	}



}
