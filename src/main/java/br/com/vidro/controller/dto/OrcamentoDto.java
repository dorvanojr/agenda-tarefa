package br.com.vidro.controller.dto;

public class OrcamentoDto {
	
	public double valorUnitario;
	public int quantidade;
	public String nomeProdutos;
	
	
	public double getValorUnitario() {
		return valorUnitario;
	}
	public void setValorUnitario(double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public String getNomeProdutos() {
		return nomeProdutos;
	}
	public void setNomeProdutos(String nomeProdutos) {
		this.nomeProdutos = nomeProdutos;
	}
	

}
