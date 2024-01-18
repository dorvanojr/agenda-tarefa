package br.com.vidro.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name="finalizarpagamento")
@NamedQuery(name="FinalizarPagamento.findAll", query="SELECT f FROM FinalizarPagamento f  ")
public class FinalizarPagamento implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idFinalizarPagamento")
    private int idFinalizarPagamento;
	
	@JoinColumn(name = "idFormaPagamento", nullable = true)
    @ManyToOne
	private FormaPagamento formaPagamento;
	
	@JoinColumn(name = "idTipoPagamento", nullable = true)
    @ManyToOne
	private TipoPagamento tipoPagamento;
	
	@Column(name = "valorParcelas")
	private double valorParcelas;
	
	@Column(name = "qtdParcelas")
	private int qtdParcelas;
	
	@Column(name = "valorPago")
	private double valorPago;
	
	@JoinColumn(name = "idTotalVendas", nullable = true)
	@ManyToOne
	private TotalVendas totalVendas;

	public int getIdFinalizarPagamento() {
		return idFinalizarPagamento;
	}

	public void setIdFinalizarPagamento(int idFinalizarPagamento) {
		this.idFinalizarPagamento = idFinalizarPagamento;
	}

	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public TipoPagamento getTipoPagamento() {
		return tipoPagamento;
	}

	public void setTipoPagamento(TipoPagamento tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}

	public double getValorParcelas() {
		return valorParcelas;
	}

	public void setValorParcelas(double valorParcelas) {
		this.valorParcelas = valorParcelas;
	}

	public int getQtdParcelas() {
		return qtdParcelas;
	}

	public void setQtdParcelas(int qtdParcelas) {
		this.qtdParcelas = qtdParcelas;
	}

	public TotalVendas getTotalVendas() {
		return totalVendas;
	}

	public void setTotalVendas(TotalVendas totalVendas) {
		this.totalVendas = totalVendas;
	}

	public double getValorPago() {
		return valorPago;
	}

	public void setValorPago(double valorPago) {
		this.valorPago = valorPago;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((formaPagamento == null) ? 0 : formaPagamento.hashCode());
		result = prime * result + idFinalizarPagamento;
		result = prime * result + qtdParcelas;
		result = prime * result
				+ ((tipoPagamento == null) ? 0 : tipoPagamento.hashCode());
		long temp;
		temp = Double.doubleToLongBits(valorPago);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(valorParcelas);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FinalizarPagamento other = (FinalizarPagamento) obj;
		if (formaPagamento == null) {
			if (other.formaPagamento != null)
				return false;
		} else if (!formaPagamento.equals(other.formaPagamento))
			return false;
		if (idFinalizarPagamento != other.idFinalizarPagamento)
			return false;
		if (qtdParcelas != other.qtdParcelas)
			return false;
		if (tipoPagamento == null) {
			if (other.tipoPagamento != null)
				return false;
		} else if (!tipoPagamento.equals(other.tipoPagamento))
			return false;
		if (Double.doubleToLongBits(valorPago) != Double
				.doubleToLongBits(other.valorPago))
			return false;
		if (Double.doubleToLongBits(valorParcelas) != Double
				.doubleToLongBits(other.valorParcelas))
			return false;
		return true;
	} 

	
	
}
