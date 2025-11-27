package model;

import java.time.LocalDate;

public class Saida {
	private Double valor;
	private LocalDate data;
	private Integer categoria;	// três categorias inseridas no banco
	

	public Saida() {
		
	}
	
	public Saida(Double valor, Integer categoria) {
		this.valor = valor;
		this.categoria = categoria;
	}

	public Saida(double valor, LocalDate data, Integer categoria) {
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
	public Integer getCategoria() {
		return categoria;
	}
	public void setCategoria(Integer categoria) {
		this.categoria = categoria;
	}
}
