package br.com.vidro.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "vendas")
public class Vendas  implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idVendas")
	private int idVendas;

	@Column(name = "quantidade")
	private int quantidade;

	@Column(name = "valorTotal")
	private double valorTotal;

	@JoinColumn(name = "idTotalVendas", nullable = true)
	@ManyToOne
	private TotalVendas totalVendas;
	
    @JoinColumn(name = "id_user_Auth", referencedColumnName = "id", insertable = true, updatable = true, nullable =  true)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private Userauth userauth;
	

	@JoinColumn(name = "idProduto", referencedColumnName = "idProduto", insertable = true, updatable = true, nullable =  true)
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private Produto produto;
	
	@JoinColumn(name = "idEmpresa", referencedColumnName = "idEmpresa", insertable = true, updatable = true, nullable =  true)   
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private Empresa empresa;
	


	
	@Column(name = "dataVenda")
	private Date dataVenda;
		

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public int getIdVendas() {
		return idVendas;
	}

	public void setIdVendas(int idVendas) {
		this.idVendas = idVendas;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public TotalVendas getTotalVendas() {
		return totalVendas;
	}

	public void setTotalVendas(TotalVendas totalVendas) {
		this.totalVendas = totalVendas;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Userauth getUserauth() {
		return userauth;
	}

	public void setUserauth(Userauth userauth) {
		this.userauth = userauth;
	}

	public Date getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(Date dataVenda) {
		this.dataVenda = dataVenda;
	}



	
}
