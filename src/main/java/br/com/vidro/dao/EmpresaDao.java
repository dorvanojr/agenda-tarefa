package br.com.vidro.dao;

import java.util.List;

import br.com.vidro.entity.Cliente;
import br.com.vidro.entity.Empresa;



public interface EmpresaDao {

	public void save(Empresa empresa);
	public List<Empresa> list(String nome);
	public List<Empresa> listLoginAll(String login);
	public List<Empresa> listLoginNome(String nome, String login);
	public List<Cliente> listAll();
	public void remove(Empresa empresa);
	public void update(Empresa empresa);
	public List<Empresa> listId(int id);
	public List<Empresa> listAllEmpresa();
	
}
