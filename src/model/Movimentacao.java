package model;

import java.sql.Date;

public class Movimentacao {
	private String tipo; //entrada ou saída
	private Date data;
	private Double valor;
	private String categoria;
	
	
	public Movimentacao() {
		
	}
	
	public Movimentacao(String tipo, Date data, Double valor, String categoria) {
		this.tipo = tipo;
		this.data = data;
		this.valor = valor;
		this.categoria = categoria;
	}
	
	
	// getters e setters
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date date) {
		this.data = date;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
}
