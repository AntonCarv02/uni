package org.example.model;


public class Profissional {
    String nome;
    String dados_medicos;
    String dados_pessoais;
    Boolean aprovado;
    String especialidade;
    float preco;
    String data;
    String seguro;
    
    public Profissional(String nome, String dados_medicos, String dados_pessoais, Boolean aprovado,
            String especialidade, float preco, String data, String seguro) {
        this.nome = nome;
        this.dados_medicos = dados_medicos;
        this.dados_pessoais = dados_pessoais;
        this.aprovado = aprovado;
        this.especialidade = especialidade;
        this.preco = preco;
        this.data = data;
        this.seguro = seguro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDados_medicos() {
        return dados_medicos;
    }

    public void setDados_medicos(String dados_medicos) {
        this.dados_medicos = dados_medicos;
    }

    public String getDados_pessoais() {
        return dados_pessoais;
    }

    public void setDados_pessoais(String dados_pessoais) {
        this.dados_pessoais = dados_pessoais;
    }

   

    public Boolean getAprovado() {
        return aprovado;
    }

    public void setAprovado(Boolean aprovado) {
        this.aprovado = aprovado;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSeguro() {
        return seguro;
    }

    public void setSeguro(String seguro) {
        this.seguro = seguro;
    }

    
     
}
