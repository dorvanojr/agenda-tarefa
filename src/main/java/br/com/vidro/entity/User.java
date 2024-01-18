package br.com.vidro.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * @author Felipe
 * 
 */
@Entity
@Table(name = "user")
@NamedQueries({  
	@NamedQuery(name="User.findList", query="SELECT u FROM User u  where u.username = :login")
 })
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "username", length = 40)
	private String username;
	@Column(name = "password", length = 40)
	private String password;
	@Column(name = "enable")
	private int enable;
	
	@OneToMany(mappedBy="user", cascade = CascadeType.ALL)
    private List<Userauth> userauth;
	
	
	@ManyToMany
	@JoinTable(name = "userauth", joinColumns = @JoinColumn(name = "USER_Username"), inverseJoinColumns = @JoinColumn(name = "AUTH_authority"))
	private List<Authority> authorities;


	@JoinColumn(name = "idCliente",  nullable = false)
    @ManyToOne
	private Cliente cliente;
	

	@JoinColumn(name = "idEmpresa", referencedColumnName = "idEmpresa", insertable = true, updatable = true, nullable =  true)   
	@ManyToOne(cascade = CascadeType.MERGE)
	private Empresa empresa;
	
	@JoinColumn(name = "idFuncionario", referencedColumnName = "idFuncionario", insertable = true, updatable = true, nullable =  true)   
	@ManyToOne(cascade = CascadeType.MERGE)
	private Funcionario funcionario;
	
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getEnable() {
		return enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
	}

	public List<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Userauth> getUserauth() {
		return userauth;
	}

	public void setUserAuth(List<Userauth> userAuth) {
		this.userauth = userAuth;
	}
	
	
	
	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public void setUserauth(List<Userauth> userauth) {
		this.userauth = userauth;
	}

	public void addUserAlth(Userauth a){
		if(this.userauth==null){
		     this.userauth = new ArrayList<Userauth>();
		}
		this.userauth.add(a);
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	
	
	
}