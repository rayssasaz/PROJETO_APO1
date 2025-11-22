package model;

import java.time.LocalDate;


public class Entrada {
	private Double valor;
	private LocalDate data;
	private String categoria;	// três categorias inseridas no banco
	
	//construtores
	
	public Entrada() {
		
	}

	public Entrada(Double valor, LocalDate data, String categoria) {
		this.valor = valor;
		this.data = data;
		this.categoria = categoria;
	}
	
	//getters e setters
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	
	
}
