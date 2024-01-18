package br.com.vidro.dao.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.spi.SessionFactoryImplementor;












import br.com.vidro.controller.dto.OrcamentoDto;
import br.com.vidro.dao.RelatorioDao;
import br.com.vidro.entity.Empresa;
import br.com.vidro.entity.FinalizarPagamento;
import br.com.vidro.entity.Vendas;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class RelatorioDaoImpl implements RelatorioDao, Serializable{

		private static final long serialVersionUID = 1L;

		@Inject
		private EntityManager em;
		
		
		//atalhos
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
	  	 public JasperPrint gerar(int id, Date dtinicial ,  Date dtfinal) throws IOException{
		 JasperPrint rel = null;
	        try {
	        	
	            HashMap<String, Object> parametros = new HashMap<String, Object>();
	            String arquivoJasper = "VendasController.jasper";
		         //    parametros.put(JRJpaQueryExecuterFactory.PARAMETER_JPA_ENTITY_MANAGER, manager);
	            JasperReport relatoriosJasper = (JasperReport) JRLoader.loadObject(getReportStream(arquivoJasper));
	             
	             Session session = em.unwrap(Session.class);
	             
	             SessionFactoryImplementor sfi = (SessionFactoryImplementor) session.getSessionFactory();
	             
	             ConnectionProvider cp = sfi.getConnectionProvider();
	             
	             Connection conn = cp.getConnection();
	             String inicio = new SimpleDateFormat("yyyy-mm-dd").format(dtinicial);
	             
	             SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	             String dateinicial=sdf.format(dtinicial);
	             
	             SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	             String datefinal=sdf1.format(dtfinal);

	             parametros.put("id", id);
	             parametros.put("dataInicial",  dateinicial);
	             parametros.put("dataFinal", datefinal);
	             rel = JasperFillManager.fillReport(relatoriosJasper, parametros, conn); 
	          //   byte[] bytes = JasperExportManager.exportReportToPdf(rel);                 
		     //    writeBytesAsAttachedTextFile(bytes, "Vendas.pdf"); 
	            // JasperExportManager.exportReportToPdfFile(rel,
                 //        "report.pdf");
	             JasperViewer.viewReport(rel, false);
	        } catch (JRException | SQLException e) {
	        	System.out.print(e.getMessage());
	            e.getStackTrace();
	        } catch (Exception e) {
				// TODO Auto-generated catch block
	        	System.out.print(e.getMessage());
				e.printStackTrace();
			}
	
	        return rel;   

	  }
		
		
		@Transactional
	  	 public JasperPrint imprimirRecibo(Vendas vendas, Empresa empresa, List<String> listasAString) throws IOException{
		 JasperPrint rel = null;
	        try {
	        	
	            HashMap<String, Object> parametros = new HashMap<String, Object>();
	            String arquivoJasper = "reciboCupon.jasper";
	         
		         //    parametros.put(JRJpaQueryExecuterFactory.PARAMETER_JPA_ENTITY_MANAGER, manager);
	            JasperReport relatoriosJasper = (JasperReport) JRLoader.loadObject(getReportStream(arquivoJasper));
	             
	             Session session = em.unwrap(Session.class);
	             
	             SessionFactoryImplementor sfi = (SessionFactoryImplementor) session.getSessionFactory();
	             
	             ConnectionProvider cp = sfi.getConnectionProvider();
	             
	             Connection conn = cp.getConnection();
	             
	             String subreportdir = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Relatorio") + "\\";          
	            //String arquvio =  subreportdir.replaceAll("/", "/");
	            
	             parametros.put("SUBREPORT_DIR", subreportdir);
	             parametros.put("id", vendas.getTotalVendas().getIdTotalVendas());
	             parametros.put("valorTotalVendas", vendas.getTotalVendas().getValorTotalVendas());
	             parametros.put("fun", vendas.getTotalVendas().getFuncionario().getCliente().getNome());
	             parametros.put("ltda", empresa.getCliente().get(0).getNomeFantasia());
	             parametros.put("end", empresa.getEndereco().get(0).getEndereco());
	             parametros.put("bairro", empresa.getEndereco().get(0).getBairro());
	             parametros.put("est", empresa.getEndereco().get(0).getCidade());
	             parametros.put("nu", empresa.getEndereco().get(0).getCep());
	             parametros.put("uf", empresa.getEndereco().get(0).getUf());
	             parametros.put("listas", listasAString);
	             parametros.put("REPORT_CONNECTION", conn);
	             rel = JasperFillManager.fillReport(relatoriosJasper, parametros, conn); 
	            // byte[] bytes = JasperExportManager.exportReportToPdf(rel);                 
		        // writeBytesAsAttachedTextFile(bytes, "Vendas.pdf"); 
	            // JasperExportManager.exportReportToPdfFile(rel,
                //        "report.pdf");
	             JasperViewer.viewReport(rel, false);
	        } catch (JRException | SQLException e) {
	        	System.out.print(e.getMessage());
	            e.getStackTrace();
	        } catch (Exception e) {
	        	System.out.print(e.getMessage());
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
	        return rel;   

	  }
		
		
		 public JasperPrint imprimirOrcamento(List<OrcamentoDto> listas) throws Exception {
		        JasperPrint rel = null;
		        try {
		        	 String arquivoJasper = "modeloOrcamento.jasper";
			         //    parametros.put(JRJpaQueryExecuterFactory.PARAMETER_JPA_ENTITY_MANAGER, manager);
		            JasperReport relatoriosJasper = (JasperReport) JRLoader.loadObject(getReportStream(arquivoJasper));
		         
		            JRDataSource jrds = new JRBeanCollectionDataSource(listas);
		           // JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listas);
		            HashMap parametros = new HashMap();
		        
		            File arquivo = new File("");
		       
		            rel = JasperFillManager.fillReport(relatoriosJasper, parametros, jrds);
		            
		            JasperViewer.viewReport(rel, false);
		            //rel.
		        }catch(Exception exception){
		        	
		        }
				return rel;
		 }
		        
		
		private static InputStream getReportStream(String nomeRelatorio) {
			FacesContext context = FacesContext.getCurrentInstance();
			return context.getExternalContext().getResourceAsStream("/Relatorio/" + nomeRelatorio);


		}



	  	 
	  	@Transactional
		protected void writeBytesAsAttachedTextFile(byte[] bytes, String fileName) throws Exception  
		   {  
		      if (bytes == null)  
		         throw new Exception("Array de bytes nulo.");  
		  
		      if (fileName == null)  
		         throw new Exception("Nome do arquivo é nulo.");  
		        
		      FacesContext facesContext = FacesContext.getCurrentInstance();  
		      HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();  
		      response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");  
		      response.setContentLength(bytes.length);  
		      ServletOutputStream ouputStream = response.getOutputStream();  
		      ouputStream.write(bytes, 0, bytes.length);  
		      facesContext.responseComplete();        
		   }

	  	 public EntityManager getEm() {
				return em;
			}

			public void setEm(EntityManager em) {
				this.em = em;
			}

		

	

}
