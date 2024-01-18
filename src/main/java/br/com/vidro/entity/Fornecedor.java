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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="fornecedor")
@NamedQueries({  
	@NamedQuery(name="Fornecedor.findList", query="SELECT f FROM Fornecedor f where f.nomeFantasia = :nome"),
    @NamedQuery(name = "Fornecedor.findAll", query = "SELECT f FROM Fornecedor f")}) 
public class Fornecedor implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idFornecedor")
	private int idFornecedor;
	@Column(name = "representante")
	private String representante;
	@Column(name = "nomeFantasia")
	private String nomeFantasia;
	
	@OneToMany(mappedBy="fornecedor", cascade = CascadeType.ALL)
    private List<Email> email;
	
	@OneToMany(mappedBy="fornecedor", cascade = CascadeType.ALL)
    private List<Endereco> endereco;
	
	@OneToMany(mappedBy="fornecedor", cascade = CascadeType.ALL)
	private List<Telefone> telefone;

	@OneToMany(mappedBy="fornecedor", cascade = CascadeType.ALL)
	private List<Cliente> cliente;
	
	@OneToMany(mappedBy="fornecedor", cascade = CascadeType.ALL)
    private List<Documentos> documentos;
	
	@JoinColumn(name = "idEmpresa", referencedColumnName = "idEmpresa", insertable = true, updatable = true, nullable =  true)   
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private Empresa empresa;
	
	
	
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
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
	public List<Cliente> getCliente() {
		return cliente;
	}
	public void setCliente(List<Cliente> cliente) {
		this.cliente = cliente;
	}
	
	public int getIdFornecedor() {
		return idFornecedor;
	}
	public void setIdFornecedor(int idFornecedor) {
		this.idFornecedor = idFornecedor;
	}
	
	public String getNomeFantasia() {
		return nomeFantasia;
	}
	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}
	public String getRepresentante() {
		return representante;
	}
	public void setRepresentante(String representante) {
		this.representante = representante;
	}

	public List<Email> getEmail() {
		return email;
	}
	public void setEmail(List<Email> email) {
		this.email = email;
	}
	public List<Documentos> getDocumentos() {
		return documentos;
	}
	public void setDocumentos(List<Documentos> documentos) {
		this.documentos = documentos;
	}
	public void addEmails(Email a){
		if(this.email==null){
		     this.email = new ArrayList<Email>();
		}
		this.email.add(a);
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
	
	public void addClientes(Cliente a){
		if(this.cliente==null){
		     this.cliente = new ArrayList<Cliente>();
		}
		this.cliente.add(a);
	}

}
