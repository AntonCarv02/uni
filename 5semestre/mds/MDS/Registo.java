package org.example.model;

import java.util.ArrayList;

public class Registo {
	
	ArrayList<String> lista_profissionais = new ArrayList<String>();
	ArrayList<String> lista_especialidade = new ArrayList<String>();
	ArrayList<String> lista_ato = new ArrayList<String>();
	ArrayList<Boolean> lista_disponibilidade = new ArrayList<Boolean>();
	ArrayList<Integer> lista_preco = new ArrayList<Integer>();
	ArrayList<Boolean> lista_clinica = new ArrayList<Boolean>();
	ArrayList<String> lista_seguro = new ArrayList<String>();
	
	
	public void setListaProfissionais(ArrayList<String> lista_profissionais) {
		this.lista_profissionais = lista_profissionais;
	}
	public void setListaEspecialidade(ArrayList<String> lista_especialidade) {
		this.lista_especialidade = lista_especialidade;
	}
	public ArrayList<String> getListaAto() {
		return lista_ato;
	}
	public void setListaAto(ArrayList<String> lista_ato) {
		this.lista_ato = lista_ato;
	}
	public void setListaDisponibilidade(ArrayList<Boolean> lista_disponibilidade) {
		this.lista_disponibilidade = lista_disponibilidade;
	}
	public void setListaPreco(ArrayList<Integer> lista_preco) {
		this.lista_preco = lista_preco;
	}
	public void setListaClinica(ArrayList<Boolean> lista_clinica) {
		this.lista_clinica = lista_clinica;
	}
	public void setListaSeguro(ArrayList<String> lista_seguro) {
		this.lista_seguro = lista_seguro;
	}
	public ArrayList<String> getListaSeguro() {
		return lista_seguro;
	}
}

