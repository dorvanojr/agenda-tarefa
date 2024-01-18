package br.com.vidro.entity;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;


import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 */
@Entity
@Table(name="cliente")
@NamedQueries({  
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c WHERE c.nome = :nome and c.status = :status"),
    @NamedQuery(name = "Cliente.findList", query = "SELECT c FROM Cliente c WHERE c.status = :status"),
    @NamedQuery(name="Cliente.findListUser", query="SELECT u.username, c.nome, c.imagem FROM User u , Cliente c  where   u.username = :login"),
    @NamedQuery(name = "Cliente.findId", query = "SELECT c FROM Cliente c WHERE c.idCliente = :id and c.status = :status")})  
public class Cliente implements Serializable {

  
	private static final long serialVersionUID = 1L;
	
	 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCliente")
	private int idCliente;
	@Column(name = "nome")
	private String nome;
	@Column(name = "imagem")
    private byte[] imagem;
	@Column(name = "imagem2")
    private byte[] imagem2;
	@Column(name = "status")
    private int status;
	@Column(name = "nomeFantasia")
    private String nomeFantasia;
	@Column(name = "observacao")
    private String observacao;
	@Column(name = "cfdf")
    private String cfdf;
	@Column(name = "dataNasc")
    private Date dataNasc;
	@Column(name = "estadoCivil")
    private String estadoCivil;
	
    @OneToMany(mappedBy = "cliente",  cascade = CascadeType.ALL)
    private List<User> user;
    
	@OneToMany(mappedBy="cliente", cascade = CascadeType.ALL)
    private List<Documentos> documentos;
    
	@OneToMany(mappedBy="cliente", cascade = CascadeType.ALL)
    private List<Email> email;
    
	@OneToMany(mappedBy="cliente", cascade = CascadeType.ALL)
    private List<Endereco> endereco;
   
	@OneToMany(mappedBy="cliente", cascade = CascadeType.ALL)
    private List<Telefone> telefone;
	
	@OneToMany(mappedBy="cliente", cascade = CascadeType.ALL)
    private List<Funcionario> funcionario;
	
	@OneToMany(mappedBy="cliente", cascade = CascadeType.ALL)
    private List<Produto> produto;
    
    @JoinColumn(name = "idFornecedor", nullable =  true)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Fornecedor fornecedor;
    
    @JoinColumn(name = "idEmpresa", referencedColumnName = "idEmpresa", insertable = true, updatable = true, nullable =  true)   
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private Empresa empresa;
	
    
	public Fornecedor getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
	public void setUser(List<User> user) {
		this.user = user;
	}
	public byte[] getImagem() {
		return imagem;
	}
	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}

	public List<User> getUser() {
		return user;
	}
	
	public Collection<Email> getEmail() {
		return email;
	}
	public void setEmail(List<Email> email) {
		this.email = email;
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
	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getNomeFantasia() {
		return nomeFantasia;
	}
	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public String getCfdf() {
		return cfdf;
	}
	public void setCfdf(String cfdf) {
		this.cfdf = cfdf;
	}
	
	public List<Funcionario> getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(List<Funcionario> funcionario) {
		this.funcionario = funcionario;
	}
	public byte[] getImagem2() {
		return imagem2;
	}
	public void setImagem2(byte[] imagem2) {
		this.imagem2 = imagem2;
	}
	public List<Documentos> getDocumentos() {
		return documentos;
	}
	public void setDocumentos(List<Documentos> documentos) {
		this.documentos = documentos;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public Date getDataNasc() {
		return dataNasc;
	}
	public void setDataNasc(Date dataNasc) {
		this.dataNasc = dataNasc;
	}
	public String getEstadoCivil() {
		return estadoCivil;
	}
	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}
	
	public List<Produto> getProduto() {
		return produto;
	}
	public void setProduto(List<Produto> produto) {
		this.produto = produto;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
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
	
	public void addUser(User a){
		if(this.user==null){
		     this.user = new ArrayList<User>();
		}
		this.user.add(a);
	}
	
	public void addProduto(Produto  a){
		if(this.produto==null){
		     this.produto = new ArrayList<Produto>();
		}
		this.produto.add(a);
	}
}
