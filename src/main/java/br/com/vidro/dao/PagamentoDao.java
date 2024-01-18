package br.com.vidro.dao;

import java.util.List;

import br.com.vidro.entity.FormaPagamento;
import br.com.vidro.entity.TipoPagamento;



public interface PagamentoDao {

	public List<FormaPagamento> findAllForma();
	public List<TipoPagamento> findAllTipo();
	
}
