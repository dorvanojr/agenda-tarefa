package br.com.vidro.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.vidro.util.EntidadeImutavel;


@Entity
@Table(name="tipopagamento")
@NamedQuery(name="TipoPagamento.findAll", query="SELECT f FROM TipoPagamento f  ")
public class TipoPagamento  implements EntidadeImutavel, Serializable {

		private static final long serialVersionUID = 1L;
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "idtipoPagamento")
		private int idtipoPagamento;
		
		@Column(name = "tipoPagamento")
		private String tipoPagamento;
		
		@OneToMany(mappedBy="tipoPagamento", cascade = CascadeType.ALL)
	    private List<FinalizarPagamento> finalizarPagamentos;

		public int getIdtipoPagamento() {
			return idtipoPagamento;
		}

		public void setIdtipoPagamento(int idtipoPagamento) {
			this.idtipoPagamento = idtipoPagamento;
		}

		public String gettipoPagamento() {
			return tipoPagamento;
		}

		public void settipoPagamento(String tipoPagamento) {
			this.tipoPagamento = tipoPagamento;
		}
		
		

		public String getTipoPagamento() {
			return tipoPagamento;
		}

		public void setTipoPagamento(String tipoPagamento) {
			this.tipoPagamento = tipoPagamento;
		}

		public List<FinalizarPagamento> getFinalizarPagamentos() {
			return finalizarPagamentos;
		}

		public void setFinalizarPagamentos(List<FinalizarPagamento> finalizarPagamentos) {
			this.finalizarPagamentos = finalizarPagamentos;
		}

		@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idtipoPagamento;
		result = prime * result
				+ ((tipoPagamento == null) ? 0 : tipoPagamento.hashCode());
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
		TipoPagamento other = (TipoPagamento) obj;
		if (idtipoPagamento != other.idtipoPagamento)
			return false;
		if (tipoPagamento == null) {
			if (other.tipoPagamento != null)
				return false;
		} else if (!tipoPagamento.equals(other.tipoPagamento))
			return false;
		return true;
	}

	@Override
	public Serializable getId() {
		// TODO Auto-generated method stub
		return getIdtipoPagamento();
	}

	@Override
	public Boolean getExcluido() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setExcluido(Boolean attr) {
		// TODO Auto-generated method stub
		
	}

		
	
}
