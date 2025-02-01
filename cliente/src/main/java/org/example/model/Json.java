package org.example.model;

import java.util.List;

import com.google.gson.annotations.Expose;

public class Json {
    @Expose
    private int id;
    @Expose
    private String operacao;
    @Expose
    private String ra;
    @Expose
    private String senha;
    @Expose
    private String nome;
    @Expose
    private String token;
    @Expose
    private List<Category> categorias;

    // Getters e setters
    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public List<Category> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Category> categorias) {
        this.categorias = categorias;
    }

    @Override
    public String toString() {
        switch (this.operacao) {
            case "cadastrarUsuario":
                return "{operacao=" + '\'' + operacao + '\'' + 
                        ", ra=" + '\'' + ra + '\'' +
                        ", senha=" + '\'' + senha + '\'' +
                        ", nome=" + '\'' + nome + '\'' +
                        '}';
            case "login":
                return "{operacao=" + '\'' + operacao + '\'' + 
                        ", ra=" + '\'' + ra + '\'' +
                        ", senha=" + '\'' + senha + '\'' +
                        '}';
            case "logout":
                return "{operacao=" + '\'' + operacao + '\'' + 
                        ", token=" + '\'' + token + '\'' +
                        '}';
            case "listarCategorias":
                return "{operacao=" + '\'' + operacao + '\'' + 
                        ", token=" + '\'' + token + '\'' +
                        '}';
            default:
                return "operacao inválida";
        }
    }
}