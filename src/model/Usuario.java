package model;

public class Usuario {
	private String nome;
	private String telefone;
	private String email;
	private String cpf;
	private String senha;
	private Integer codUsuario;
	
	public Usuario() {		//construtor vazio: nao recebe parametros de inicialização no new Usuario()
		
	}
	
	public Usuario(String nome, String telefone, String email, String cpf, String senha, Integer codUsuario) {
		this.nome = nome;
		this.telefone = telefone;
		this.email = email;
		this.cpf = cpf;
		this.senha = senha;
		this.codUsuario = codUsuario;
	}
	
	// getters e setters
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Integer getCodUsuario() {
		return codUsuario;
	}
	public void setCodUsuario(Integer codUsuario) {
		this.codUsuario = codUsuario;
	}
	
}
