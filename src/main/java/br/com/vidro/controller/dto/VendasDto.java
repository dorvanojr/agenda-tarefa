package br.com.vidro.controller.dto;

import javax.inject.Inject;

import br.com.vidro.entity.Produto;

public class VendasDto {

	
	private int seq;
	@Inject
    private Produto produto;
    private double valorTotal;
    private int qtd;
    
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	public double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
	public int getQtd() {
		return qtd;
	}
	public void setQtd(int qtd) {
		this.qtd = qtd;
	}
    
    
    
    

}
