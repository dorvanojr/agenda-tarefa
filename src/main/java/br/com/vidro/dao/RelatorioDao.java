package br.com.vidro.dao;


import java.io.IOException;


import java.util.Date;
import java.util.List;

import br.com.vidro.controller.dto.OrcamentoDto;
import br.com.vidro.entity.Empresa;
import br.com.vidro.entity.FinalizarPagamento;
import br.com.vidro.entity.Vendas;
import net.sf.jasperreports.engine.JasperPrint;



public interface RelatorioDao {


	public JasperPrint gerar(int id, Date dtinicial ,  Date dtfinal) throws IOException;
	public JasperPrint imprimirRecibo(Vendas vendas, Empresa empresa, List<String> listasAString) throws Exception;
	public JasperPrint imprimirOrcamento(List<OrcamentoDto> listas) throws Exception;
}
