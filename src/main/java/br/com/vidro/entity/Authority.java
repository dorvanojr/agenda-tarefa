/**
 * 
 */
package br.com.vidro.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Felipe
 * 
 */
@Entity
@Table(name = "authority")
@NamedQuery(name = "Authority.findId", query = "SELECT a FROM Authority a WHERE a.name = :id")
public class Authority implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String name;

	
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
}