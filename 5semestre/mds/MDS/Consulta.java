package org.example.model;


public class Consulta {
	
	String nome_cliente;
	String nome_prof;
	String data;
	boolean aprovado; 
	public Consulta(String nome_cliente, String nome_prof, String data, boolean aprovado) {
		this.nome_cliente = nome_cliente;
		this.nome_prof = nome_prof;
		this.data = data;
		this.aprovado = aprovado;
	}
	
	public boolean getAprovado() {
		return aprovado;
	}
	public void setAprovado(boolean aprovado) {
		this.aprovado = aprovado;
	}
	public String getNome_cliente() {
		return nome_cliente;
	}
	public void setNome_cliente(String nome_cliente) {
		this.nome_cliente = nome_cliente;
	}
	public String getNome_prof() {
		return nome_prof;
	}
	public void setNome_prof(String nome_prof) {
		this.nome_prof = nome_prof;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
}

