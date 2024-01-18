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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="funcionario")
@NamedQuery(name = "Funcionario.findId", query = "SELECT f FROM Funcionario f WHERE f.idFuncionario = :id")
public class Funcionario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idFuncionario")
	private int idFuncionario;
	
	@Column(name = "cargo")
	private String cargo;
	
	@Column(name = "dataEntrada")
	private Date dataEntrada;
	
	@Column(name = "grauInstrucao")
	private String grauInstrucao;
	
	@JoinColumn(name = "idEmpresa", referencedColumnName = "idEmpresa", insertable = true, updatable = true, nullable =  true)   
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private Empresa empresa;
	
    @JoinColumn(name = "idCliente", referencedColumnName = "idCliente", insertable = true, updatable = true, nullable =  true)   
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Cliente cliente;

	@OneToMany(mappedBy="funcionario", cascade = CascadeType.ALL)
    private List<Email> Email;
	
	@OneToMany(mappedBy="funcionario", cascade = CascadeType.ALL)
    private List<Endereco> endereco;
	
	@OneToMany(mappedBy="funcionario", cascade = CascadeType.ALL)
    private List<Telefone> telefone;
	
	@OneToMany(mappedBy="funcionario", cascade = CascadeType.ALL)
    private List<Documentos> documentos;

	public int getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(int idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public Date getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(Date dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public String getGrauInstrucao() {
		return grauInstrucao;
	}

	public void setGrauInstrucao(String grauInstrucao) {
		this.grauInstrucao = grauInstrucao;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	

	public List<Email> getEmail() {
		return Email;
	}

	public void setEmail(List<Email> email) {
		Email = email;
	}

	public List<Endereco> getEndereco() {
		return endereco;
	}

	public void setEndereco(List<Endereco> endereco) {
		this.endereco = endereco;
	}

	public List<Telefone> getTelefone() {
		return telefone;
	}

	public void setTelefone(List<Telefone> telefone) {
		this.telefone = telefone;
	}

	public List<Documentos> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<Documentos> documentos) {
		this.documentos = documentos;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public void addEnderecos(Endereco a){
		if(this.endereco==null){
		     this.endereco = new ArrayList<Endereco>();
		}
		this.endereco.add(a);
	}
	
	public void addTelefones(Telefone a){
		if(this.telefone==null){
		     this.telefone = new ArrayList<Telefone>();
		}
		this.telefone.add(a);
	}
	
	public void addDocumentos(Documentos a){
		if(this.documentos==null){
		     this.documentos = new ArrayList<Documentos>();
		}
		this.documentos.add(a);
	}
	
	
	public void addEmail(Email a){
		if(this.Email==null){
		     this.Email = new ArrayList<Email>();
		}
		this.Email.add(a);
	}
}
