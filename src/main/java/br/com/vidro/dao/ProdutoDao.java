package br.com.vidro.dao;


import java.util.List;

import br.com.vidro.entity.Cliente;
import br.com.vidro.entity.Produto;


public interface ProdutoDao {

	public void save(Produto produto);
	public List<Produto> list(String nome);
	public List<Produto> listAll();
	public List<Produto> listID(int id);
	public void update(Produto produto);
	public List<Produto> listProdutoLoginAll(int id);
	public List<Produto> listProdutLoginNome(String nome, int id);
	 public List<Produto> listProdutLoginBarras(String nome, int id);
	public List<Produto> listBarra(String barra); 
	  public void updateProduto(Produto produto);
}
