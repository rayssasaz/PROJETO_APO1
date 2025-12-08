package model;

import java.time.LocalDate;

public class Notificacao {
	private String tipo;
	private LocalDate data;
	private String legenda;
	
	
	
	public Notificacao() {
		
	}


	public Notificacao(String tipo, LocalDate data, String legenda) {
		this.tipo = tipo;
		this.data = data;
		this.legenda = legenda;
	}


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


	public String getLegenda() {
		return legenda;
	}


	public void setLegenda(String legenda) {
		this.legenda = legenda;
	}
	
	
}
