package br.com.vidro.dao;


import java.io.IOException;
import java.util.List;

import net.sf.jasperreports.engine.JasperPrint;
import br.com.vidro.entity.Cliente;
import br.com.vidro.entity.Endereco;
import br.com.vidro.entity.Fornecedor;
import br.com.vidro.entity.Telefone;


public interface FornecedorDao {

	
	public void saveFornecedor(Fornecedor fornecedor);
	public List<Fornecedor> list(String nome);
	public List<Fornecedor> listLoginNome(String nome, int idEmpresa);
	public List<Fornecedor> listLoginAll(int id);
	public List<Fornecedor> listAll();
	public void remove(Fornecedor fornecedor);
	public void update(Fornecedor fornecedor);
	
}
