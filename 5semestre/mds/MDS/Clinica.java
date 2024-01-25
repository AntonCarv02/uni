package org.example.model;

public class Clinica
{
    String nome;
   
    boolean aprovado;

    public Clinica(String nome, boolean aprovado)
    {
        this.nome = nome;
        this.aprovado = aprovado;
    }
    
    public String getNome(){
        return nome;
    }


    public void setNome(String nome)
    {
	this.nome = nome;
    }

    public Boolean getAprovado(){
        return aprovado;
    }

    public void setAprovado(Boolean aprovado){
        this.aprovado = aprovado;
    }


	
   
}
