package model;

import java.time.LocalDate;

public class Agenda {
	private String tipo; //entrada ou saída
	private LocalDate data; // data agendada
	private Double valor;
	private String categoria;
	private String status;
	
	// construtores 
	
	public Agenda() {
		
	}

	public Agenda(String tipo, LocalDate data, Double valor, String categoria, String status) {
		this.tipo = tipo;
		this.data = data;
		this.valor = valor;
		this.categoria = categoria;
		this.status = status;
	}
	
	//getters  e setters
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
	
	
}
