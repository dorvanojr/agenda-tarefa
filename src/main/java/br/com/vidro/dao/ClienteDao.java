package br.com.vidro.dao;


import java.io.IOException;
import java.util.List;

import net.sf.jasperreports.engine.JasperPrint;
import br.com.vidro.entity.Cliente;
import br.com.vidro.entity.Endereco;
import br.com.vidro.entity.Telefone;


public interface ClienteDao {

	public void save(Cliente cliente);
	public List<Cliente> list(String nome);
	public List<Cliente> listLoginAll(int id);
	public List<Cliente> listLoginNome(String nome, int idEmpresa);
	public List<Cliente> listLoginNomeEmpresa(String nome);
	public List<Cliente> listAll();
	public void remove(Cliente cliente);
	public void update(Cliente cliente);
	public List<Cliente> listId(int id);
	
}
