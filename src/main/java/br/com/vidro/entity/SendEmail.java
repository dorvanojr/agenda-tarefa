package br.com.vidro.entity;

import java.io.Serializable;

public class SendEmail implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	
	private String email;
	private String assunto;
	private String Caixatexto;
	
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAssunto() {
		return assunto;
	}
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	public String getCaixatexto() {
		return Caixatexto;
	}
	public void setCaixatexto(String caixatexto) {
		Caixatexto = caixatexto;
	}
	
	
	
	

}
