package org.example.model;

public class User
{
    String nome;
    String dados_medicos;
    String dados_pessoais;
    String role;
    boolean aprovado;

    public User(String nome, String dados_medicos, String dados_pessoais, String role, boolean aprovado)
    {
        this.nome = nome;
	    this.dados_medicos = dados_medicos;
        this.dados_pessoais = dados_pessoais;
        this.role = role;
        this.aprovado = aprovado;
    }
    
    public String getNome(){
        return nome;
    }

    public String getDadosMedicos()
    {
        return dados_medicos;
    }
	
    public String getDadosPessoais()
    {
        return dados_pessoais;
    }

    public String getRole()
    {
        return role;
    }

    public void setNome(String nome)
    {
	this.nome = nome;
    }
	
    public void setDadosMedicos(String dados_medicos)
    {
	this.dados_medicos = dados_medicos;
    }

    public void setDadosPessoais(String dados_pessoais)
    {
	this.dados_pessoais = dados_pessoais;
    }
   
    public void setRole(String role) 
    {
	this.role = role;
    }

    public Boolean getAprovado(){
        return aprovado;
    }

    public void setAprovado(boolean aprovado){
        this.aprovado = aprovado;
    }
}
