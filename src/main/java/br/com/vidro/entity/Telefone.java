package br.com.vidro.entity;

import java.io.Serializable;

import javax.persistence.*;


/**
 * 
 */
@Entity
@Table(name = "telefone")
public class Telefone implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idTelefone")
	private int idTelefone;
	@Column(name = "residencial")
	private String residencial;
	@Column(name = "whatapps")
	private String whatapps;
	@Column(name = "celular")
	private String celular;
	@Column(name = "fax")
	private String fax;
	@Column(name = "telefone")
	private String telefone;
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


	public int getIdTelefone() {
		return idTelefone;
	}

	public void setIdTelefone(int idTelefone) {
		this.idTelefone = idTelefone;
	}

	public String getResidencial() {
		return residencial;
	}

	public void setResidencial(String residencial) {
		this.residencial = residencial;
	}

	public String getWhatapps() {
		return whatapps;
	}

	public void setWhatapps(String whatapps) {
		this.whatapps = whatapps;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
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

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	
	


}
