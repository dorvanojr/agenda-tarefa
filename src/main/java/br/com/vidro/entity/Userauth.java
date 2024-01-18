package br.com.vidro.entity;

import java.io.Serializable;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="userauth")
public class Userauth implements Serializable {

  
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_Username", referencedColumnName = "username", nullable = true)
	private User user;
	
	@Column(name = "AUTH_authority", nullable = true)
	private String nameAuthority;
	
	
	@OneToMany(mappedBy="userauth", cascade = CascadeType.ALL)
	private List<Vendas> vendas;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getNameAuthority() {
		return nameAuthority;
	}

	public void setNameAuthority(String nameAuthority) {
		this.nameAuthority = nameAuthority;
	}

	public List<Vendas> getVendas() {
		return vendas;
	}

	public void setVendas(List<Vendas> vendas) {
		this.vendas = vendas;
	}

	
	
	
	
	


	
	
}
