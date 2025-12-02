package model;

import java.time.LocalDate;

public class Agenda {
	private Integer id;
	private String tipo; //entrada ou saída
	private Integer dia;
	private Double valor;
    private Integer repetirMeses;
    private Integer usuarioId;
    private Integer categoriaId;
    private String categoriaNome; // <-- ADICIONADO PARA EXIBIR NA VIEW
    private LocalDate data; // data agendada
    
	
	// construtores 
	
	public Agenda() {
		
	}


	public Agenda(Integer id, String tipo, Integer dia, LocalDate data, Double valor, Integer repetirMeses,
		Integer usuarioId, Integer categoriaId, String categoriaNome) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.dia = dia;
		this.data = data;
		this.valor = valor;
		this.repetirMeses = repetirMeses;
		this.usuarioId = usuarioId;
		this.categoriaId = categoriaId;
		this.categoriaNome = categoriaNome;
	}

	//getters  e setters

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public Integer getDia() {
		return dia;
	}


	public void setDia(Integer dia) {
		this.dia = dia;
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


	public Integer getRepetirMeses() {
		return repetirMeses;
	}


	public void setRepetirMeses(Integer repetirMeses) {
		this.repetirMeses = repetirMeses;
	}


	public Integer getUsuarioId() {
		return usuarioId;
	}


	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}


	public Integer getCategoriaId() {
		return categoriaId;
	}


	public void setCategoriaId(Integer categoriaId) {
		this.categoriaId = categoriaId;
	}


	public String getCategoriaNome() {
		return categoriaNome;
	}


	public void setCategoriaNome(String categoriaNome) {
		this.categoriaNome = categoriaNome;
	}

	
}
