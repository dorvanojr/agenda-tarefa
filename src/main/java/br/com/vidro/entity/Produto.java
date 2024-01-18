package br.com.vidro.entity;

import java.io.Serializable;





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
@Table(name = "produto")
@NamedQueries({  
	@NamedQuery(name="Produto.findList", query="SELECT p FROM Produto p where p.nomeProduto = :nome"),
	@NamedQuery(name="Produto.findBarra", query="SELECT p FROM Produto p where p.codigoBarra = :barra"),
	@NamedQuery(name="Produto.findID", query="SELECT p FROM Produto p where p.idProduto = :id"),
    @NamedQuery(name = "Produto.findAll", query = "SELECT p FROM Produto p")}) 
public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idProduto")
	private int idProduto;
	private String codigoBarra;
	private String nomeProduto;
	private double precoCusto;
	private double precoVenda;
	private String marca;
	private String garantia;
	private String lucro;
	private int estoqueAtual;
	private int estoqueMinimo;
	private int estoqueMaximo;
	private String categoria;
	private String dataCadastro;
	private String nomeFornecedor;
	private String descricaoProduto;
	private double valorCusto;
	private String margemAvista;
	private double valorAvista;
	private String margemPrazo;
	private double valorPrazo;
	private String margemAtacado;
	private double valorAtacado;
	private Date dtValidade;
	
	@JoinColumn(name = "idCliente",  insertable = true, updatable = true, nullable =  true)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Cliente cliente;
	
	@JoinColumn(name = "idEmpresa", referencedColumnName = "idEmpresa", insertable = true, updatable = true, nullable =  true)   
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private Empresa empresa;
	
	@OneToMany(mappedBy = "produto",  cascade = CascadeType.ALL)
    private List<Vendas> vendas;
    

	public String getNomeFornecedor() {
		return nomeFornecedor;
	}

	public void setNomeFornecedor(String nomeFornecedor) {
		this.nomeFornecedor = nomeFornecedor;
	}

	public int getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}

	public String getCodigoBarra() {
		return codigoBarra;
	}

	public void setCodigoBarra(String codigoBarra) {
		this.codigoBarra = codigoBarra;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public double getPrecoCusto() {
		return precoCusto;
	}

	public void setPrecoCusto(double precoCusto) {
		this.precoCusto = precoCusto;
	}

	public double getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(double precoVenda) {
		this.precoVenda = precoVenda;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getGarantia() {
		return garantia;
	}

	public void setGarantia(String garantia) {
		this.garantia = garantia;
	}

	public String getLucro() {
		return lucro;
	}

	public void setLucro(String lucro) {
		this.lucro = lucro;
	}

	public int getEstoqueAtual() {
		return estoqueAtual;
	}

	public void setEstoqueAtual(int estoqueAtual) {
		this.estoqueAtual = estoqueAtual;
	}

	public int getEstoqueMinimo() {
		return estoqueMinimo;
	}

	public void setEstoqueMinimo(int estoqueMinimo) {
		this.estoqueMinimo = estoqueMinimo;
	}

	public int getEstoqueMaximo() {
		return estoqueMaximo;
	}

	public void setEstoqueMaximo(int estoqueMaximo) {
		this.estoqueMaximo = estoqueMaximo;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(String dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getDescricaoProduto() {
		return descricaoProduto;
	}

	public void setDescricaoProduto(String descricaoProduto) {
		this.descricaoProduto = descricaoProduto;
	}

	public double getValorCusto() {
		return valorCusto;
	}

	public void setValorCusto(double valorCusto) {
		this.valorCusto = valorCusto;
	}

	public String getMargemAvista() {
		return margemAvista;
	}

	public void setMargemAvista(String margemAvista) {
		this.margemAvista = margemAvista;
	}

	public double getValorAvista() {
		return valorAvista;
	}

	public void setValorAvista(double valorAvista) {
		this.valorAvista = valorAvista;
	}

	public String getMargemPrazo() {
		return margemPrazo;
	}

	public void setMargemPrazo(String margemPrazo) {
		this.margemPrazo = margemPrazo;
	}

	public double getValorPrazo() {
		return valorPrazo;
	}

	public void setValorPrazo(double valorPrazo) {
		this.valorPrazo = valorPrazo;
	}

	public String getMargemAtacado() {
		return margemAtacado;
	}

	public void setMargemAtacado(String margemAtacado) {
		this.margemAtacado = margemAtacado;
	}

	public double getValorAtacado() {
		return valorAtacado;
	}

	public void setValorAtacado(double valorAtacado) {
		this.valorAtacado = valorAtacado;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public List<Vendas> getVendas() {
		return vendas;
	}

	public void setVendas(List<Vendas> vendas) {
		this.vendas = vendas;
	}

	public Date getDtValidade() {
		return dtValidade;
	}

	public void setDtValidade(Date dtValidade) {
		this.dtValidade = dtValidade;
	}

	

}
