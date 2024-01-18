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
@Table(name="documentos")
public class Documentos implements Serializable {

  
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDocumentos")
	private int idDocumentos;
	
	@Column(name = "rg")
	private String rg;
	
	@Column(name = "cpf")
	private String cpf;
	
	@Column(name = "cnpj")
	private String cnpj;
	
	@Column(name = "nle")
	private String nle;
	
	@Column(name = "nim")
	private String nim;
	
	@Column(name = "emissor")
	private String emissor;
	
	@Column(name = "cpfcnpj")
	private String cpfcnpj;
	
	@Column(name = "carteiraTrabalho")
	private String carteiraTrabalho;
	
	@JoinColumn(name = "idFornecedor", nullable = true)
    @ManyToOne
	private Fornecedor fornecedor;
	
	@JoinColumn(name = "idCliente", nullable = true)
    @ManyToOne
	private Cliente cliente;
	
	
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

	
	public int getIdDocumentos() {
		return idDocumentos;
	}

	public void setIdDocumentos(int idDocumentos) {
		this.idDocumentos = idDocumentos;
	}

	
	public String getCpfcnpj() {
		return cpfcnpj;
	}

	public void setCpfcnpj(String cpfcnpj) {
		this.cpfcnpj = cpfcnpj;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getNle() {
		return nle;
	}

	public void setNle(String nle) {
		this.nle = nle;
	}

	public String getNim() {
		return nim;
	}

	public void setNim(String nim) {
		this.nim = nim;
	}

	public String getEmissor() {
		return emissor;
	}

	public void setEmissor(String emissor) {
		this.emissor = emissor;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public String getCarteiraTrabalho() {
		return carteiraTrabalho;
	}
	public void setCarteiraTrabalho(String carteiraTrabalho) {
		this.carteiraTrabalho = carteiraTrabalho;
	}
	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	
	
	
	
}
