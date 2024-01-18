package br.com.vidro.dao;


import java.io.IOException;
import java.util.List;

import net.sf.jasperreports.engine.JasperPrint;
import br.com.vidro.entity.Categoria;
import br.com.vidro.entity.Cliente;
import br.com.vidro.entity.Endereco;
import br.com.vidro.entity.Telefone;


public interface CategoriaDao {

	public void save(Categoria categoria);

	
}
