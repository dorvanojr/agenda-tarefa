package br.com.vidro.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="totalvendas")
public class TotalVendas implements Serializable {

  
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTotalVendas")
	private int idTotalVendas;

    @Column(name = "totalVendas")
	private double valorTotalVendas;
    
    @OneToMany(mappedBy = "totalVendas",  cascade = CascadeType.ALL)
    private List<Vendas> vendas;
    
	@OneToMany(mappedBy="totalVendas", cascade = CascadeType.ALL)
    private List<FinalizarPagamento> finalizarPagamentos;

	@JoinColumn(name = "idFuncionario", referencedColumnName = "idFuncionario", insertable = true, updatable = true, nullable =  true)   
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private Funcionario funcionario;
	
    
	public List<Vendas> getVendas() {
		return vendas;
	}

	public void setVendas(List<Vendas> vendas) {
		this.vendas = vendas;
	}

	public int getIdTotalVendas() {
		return idTotalVendas;
	}

	public void setIdTotalVendas(int idTotalVendas) {
		this.idTotalVendas = idTotalVendas;
	}

	public double getValorTotalVendas() {
		return valorTotalVendas;
	}

	public void setValorTotalVendas(double valorTotalVendas) {
		this.valorTotalVendas = valorTotalVendas;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	
	public void addVendas(Vendas  a){
		if(this.vendas==null){
		     this.vendas = new ArrayList<Vendas>();
		}
		this.vendas.add(a);
	}
    
	public List<FinalizarPagamento> getFinalizarPagamentos() {
		return finalizarPagamentos;
	}

	public void setFinalizarPagamentos(List<FinalizarPagamento> finalizarPagamentos) {
		this.finalizarPagamentos = finalizarPagamentos;
	}

	
	public void addFinalizarVendas(FinalizarPagamento a){
		if(this.finalizarPagamentos==null){
		     this.finalizarPagamentos = new ArrayList<FinalizarPagamento>();
		}
		this.finalizarPagamentos.add(a);
	}
	
	
}	
	
	
	 