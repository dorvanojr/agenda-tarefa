package br.com.vidro.dao;


import java.io.IOException;
import java.util.List;

import net.sf.jasperreports.engine.JasperPrint;
import br.com.vidro.entity.Authority;
import br.com.vidro.entity.Cliente;
import br.com.vidro.entity.Email;
import br.com.vidro.entity.Endereco;
import br.com.vidro.entity.Telefone;
import br.com.vidro.entity.User;


public interface UserDao {

	
	public List<User> list(String nome);
	
}
