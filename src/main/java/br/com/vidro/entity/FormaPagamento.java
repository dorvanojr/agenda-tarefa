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
@Table(name="formapagamento")
@NamedQuery(name="FormaPagamento.findAll", query="SELECT f FROM FormaPagamento f  ")
public class FormaPagamento implements EntidadeImutavel, Serializable {

		private static final long serialVersionUID = 1L;
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "idFormaPagamento")
		private int idFormaPagamento;
		
		@Column(name = "formaPagamento")
		private String formaPagamento;
		
		@OneToMany(mappedBy="formaPagamento", cascade = CascadeType.ALL)
	    private List<FinalizarPagamento> finalizarPagamentos;

		public int getIdFormaPagamento() {
			return idFormaPagamento;
		}

		public void setIdFormaPagamento(int idFormaPagamento) {
			this.idFormaPagamento = idFormaPagamento;
		}

		public String getFormaPagamento() {
			return formaPagamento;
		}

		public void setFormaPagamento(String formaPagamento) {
			this.formaPagamento = formaPagamento;
		}
		
		

		public List<FinalizarPagamento> getFinalizarPagamentos() {
			return finalizarPagamentos;
		}

		public void setFinalizarPagamentos(List<FinalizarPagamento> finalizarPagamentos) {
			this.finalizarPagamentos = finalizarPagamentos;
		}

		@Override
		public Serializable getId() {
			// TODO Auto-generated method stub
			return getIdFormaPagamento();
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
