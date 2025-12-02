package model;

import java.util.ArrayList;


public class Conta {
	private Double saldo;
	private Integer codigo;
	private ArrayList<Movimentacao> movimentacoes = new ArrayList<Movimentacao>(); // agregação
	
	
	public Conta() {
		
	}

	public Conta(Double saldo, Integer codigo) {
		this.saldo = saldo;
		this.codigo = codigo;
	}

	// adiciona as movimentacoes
	public void inserirMovimentacao(Movimentacao movimentacoes) {
		this.movimentacoes.add(movimentacoes);
	}

	// getters e setters
	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	
	
	
	
	
}
