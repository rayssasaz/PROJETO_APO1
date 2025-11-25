package model;

public class CategoriaEntrada {
	private String nome;
	private Integer cod;
	
	
	public CategoriaEntrada() {
		
	}

	public CategoriaEntrada(String nome, Integer cod) {
		this.nome = nome;
		this.cod = cod;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getCod() {
		return cod;
	}
	public void setCod(Integer cod) {
		this.cod = cod;
	}
}
