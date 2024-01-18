package br.com.vidro.dao;

import java.util.List;

import br.com.vidro.entity.Cliente;
import br.com.vidro.entity.Funcionario;

public interface FuncionarioDao {

	public void save(Funcionario funcionario);
	public List<Funcionario> list(String nome);
	public List<Funcionario> listLoginAll(int id);
	public List<Funcionario> listLoginNome(String nome, int idEmpresa);
	public List<Funcionario> listAll();
	public void remove(Funcionario funcionario);
	public void update(Funcionario funcionario);
	public List<Funcionario> listId(int id);
	
}
