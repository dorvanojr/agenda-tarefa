package br.com.vidro.dao;


import java.io.IOException;
import java.util.List;

import net.sf.jasperreports.engine.JasperPrint;
import br.com.vidro.entity.Authority;
import br.com.vidro.entity.Cliente;
import br.com.vidro.entity.Email;
import br.com.vidro.entity.Empresa;
import br.com.vidro.entity.Endereco;
import br.com.vidro.entity.Telefone;
import br.com.vidro.entity.User;
import br.com.vidro.entity.Userauth;


public interface UsuarioDao {

	public void save(Cliente cliente);
	public void saveEndereco(Endereco endereco);
	public void saveTelefone(Telefone telefone);
	Userauth retornaUsuario(String login);
	
	public void saveLogin(User user);
	public void saveEmail(Email email);
	public List<Cliente> list(String nome);
	public List<Cliente> listAll();
	public void remove(Cliente cliente);
	public void update(Cliente cliente);
	public List<User> consultaLogin(String login, String senha);
	public List<Cliente> listUsuario(String nome); 
	Empresa retornaEmpresaId(int id);
	List<User>  retornaEmpresa(String login);
	
}
