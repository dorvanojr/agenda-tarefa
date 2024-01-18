package br.com.vidro.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="email")
public class Email implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEmail")
	private int idEmail;
	
	@Column(name = "gtalk")
	private String gtalk;
	
	@Column(name = "homepage")
	private String homepage;
	
	@Column(name = "email")
	private String email;
	@JoinColumn(name = "idCliente", nullable = true)
    @ManyToOne
	private Cliente cliente;

	@JoinColumn(name = "idFornecedor", nullable = true)
    @ManyToOne
	private Fornecedor fornecedor;
	
	@JoinColumn(name = "idEmpresa",  nullable = true)
    @ManyToOne
	private Empresa empresa;
	
	@JoinColumn(name = "idFuncionario",  nullable = true)
    @ManyToOne
	private Funcionario funcionario;
	
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	
	public int getIdEmail() {
		return idEmail;
	}
	public void setIdEmail(int idEmail) {
		this.idEmail = idEmail;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Fornecedor getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
	public String getGtalk() {
		return gtalk;
	}
	public void setGtalk(String gtalk) {
		this.gtalk = gtalk;
	}
	public String getHomepage() {
		return homepage;
	}
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}
	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	
	
	
	

}
