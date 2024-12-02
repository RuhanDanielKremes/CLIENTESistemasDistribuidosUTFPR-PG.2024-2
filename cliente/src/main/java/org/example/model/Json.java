package org.example.model;

public class Json {
    private String operacao;
    private String ra;
    private String senha;
    private String nome;
    private String token;

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
            default:
                return "operacao inválida";
        }
    }
}