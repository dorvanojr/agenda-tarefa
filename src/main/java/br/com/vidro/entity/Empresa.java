package br.com.vidro.entity;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;


import java.util.List;

import javax.persistence.*;

/**
 *
 */
@Entity
@Table(name="empresa")
@NamedQueries({  
    @NamedQuery(name = "Empresa.findList", query = "SELECT e FROM Empresa e"),
    @NamedQuery(name = "Empresa.findAll", query = "SELECT e FROM Empresa e, Cliente a where a.status = 1  "),
    @NamedQuery(name = "Empresa.findId", query = "SELECT e FROM Empresa e WHERE e.idEmpresa = :id")})  
public class Empresa implements Serializable {

  
	private static final long serialVersionUID = 1L;
	
	 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEmpresa")
	private int idEmpresa;
	@Column(name = "ramoAtividade")
	private String ramoAtividade;
	@Column(name = "razaoSocial")
	private String razaoSocial;
    @OneToMany(mappedBy = "empresa",  cascade = CascadeType.ALL)
    private List<User> user;
    
	@OneToMany(mappedBy="empresa", cascade = CascadeType.ALL)
    private List<Documentos> documentos;
    
	@OneToMany(mappedBy="empresa", cascade = CascadeType.ALL)
    private List<Email> email;
    
	@OneToMany(mappedBy="empresa", cascade = CascadeType.ALL)
    private List<Endereco> endereco;
   
	@OneToMany(mappedBy="empresa", cascade = CascadeType.ALL)
    private List<Telefone> telefone;
    
	@OneToMany(mappedBy="empresa", cascade = CascadeType.ALL)
    private List<Fornecedor> fornecedor;
	
	@OneToMany(mappedBy="empresa", cascade = CascadeType.ALL)
	private List<Cliente> cliente;
	
	@OneToMany(mappedBy="empresa", cascade = CascadeType.ALL)
	private List<Categoria> categoria;
	
	@OneToMany(mappedBy="empresa", cascade = CascadeType.ALL)
	private List<Grupo> grupo;
	
	@OneToMany(mappedBy="empresa", cascade = CascadeType.ALL)
    private List<Funcionario> funcionario;

	public int getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(int idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	public String getRamoAtividade() {
		return ramoAtividade;
	}
	public void setRamoAtividade(String ramoAtividade) {
		this.ramoAtividade = ramoAtividade;
	}
	public List<Fornecedor> getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(List<Fornecedor> fornecedor) {
		this.fornecedor = fornecedor;
	}
	public void setUser(List<User> user) {
		this.user = user;
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
	
	public List<Documentos> getDocumentos() {
		return documentos;
	}
	public void setDocumentos(List<Documentos> documentos) {
		this.documentos = documentos;
	}
	
	
	
	public List<Funcionario> getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(List<Funcionario> funcionario) {
		this.funcionario = funcionario;
	}
	public String getRazaoSocial() {
		return razaoSocial;
	}
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	public List<Cliente> getCliente() {
		return cliente;
	}
	
	public void setCliente(List<Cliente> cliente) {
		this.cliente = cliente;
	}

	public List<Categoria> getCategoria() {
		return categoria;
	}
	public void setCategoria(List<Categoria> categoria) {
		this.categoria = categoria;
	}
	public List<Grupo> getGrupo() {
		return grupo;
	}
	public void setGrupo(List<Grupo> grupo) {
		this.grupo = grupo;
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
	
	public void addCliente(Cliente a){
		if(this.cliente==null){
		     this.cliente = new ArrayList<Cliente>();
		}
		this.cliente.add(a);
	}
	public void addCategoria(Categoria a){
		if(this.categoria ==null){
		     this.categoria  = new ArrayList<Categoria>();
		}
		this.categoria.add(a);
	}
	public void addGrupo(Grupo a){
		if(this.grupo==null){
		     this.grupo = new ArrayList<Grupo>();
		}
		this.grupo.add(a);
	}

}
