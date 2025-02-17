package org.example.model;

import java.util.List;

import com.google.gson.annotations.Expose;

public class Json {
    @Expose(serialize = false)
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
    private Category categorias;
    @Expose
    private List<Warnings> avisos;
    @Expose
    private User usuario;

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

    public Category getCategorias() {
        return categorias;
    }

    public void setCategorias(Category categorias) {
        this.categorias = categorias;
    }

    public List<Warnings> getAvisos() {
        return avisos;
    }

    public void setAvisos(List<Warnings> avisos) {
        this.avisos = avisos;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        switch (this.operacao) {
            case "cadastrarUsuario":
                return "{\"operacao\":\"" + operacao + "\"" + 
                        ", \"ra\":\"" + ra + "\"" +
                        ", \"senha\":\"" + senha + "\"" +
                        ", \"nome\":\"" + nome + "\"" +
                        '}';
            case "login":
                return "{\"operacao\":\"" + operacao + "\"" + 
                        ", \"ra\":\"" + ra + "\"" +
                        ", \"senha\":\"" + senha + "\"" +
                        '}';
            case "logout":
                return "{\"operacao\":\"" + operacao + "\"" + 
                        ", \"token\":\"" + token + "\"" +
                        '}';
            case "listarCategorias":
                return "{\"operacao\":\"" + operacao + "\"" + 
                        ", \"token\":\"" + token + "\"" +
                        '}';
            case "localizarCategoria":
                return "{\"operacao\":\"" + operacao + "\"" + 
                        ", \"token\":\"" + token + "\"" +
                        ", \"id\":" + id +
                        '}';
            case "listarUsuarioCategorias":
                return "{\"operacao\":\"" + operacao + "\"" + 
                        ", \"token\":\"" + token + "\"" +
                        ", \"ra\":\"" + ra + "\""+
                        '}';
            case "cadastrarUsuarioCategoria":
                return "{\"operacao\":\"" + operacao + "\"" + 
                        ", \"token\":\"" + token + "\"" +
                        ", \"ra\":\"" + ra + "\"" +
                        ", \"categoria\":" + id +
                        '}';
            case "descadastrarUsuarioCategoria":
                return "{\"operacao\":\"" + operacao + "\"" + 
                        ", \"token\":\"" + token + "\"" +
                        ", \"ra\" : \"" + ra + "\"" +
                        ", \"categoria\":" + id +
                        '}';
            case "listarAvisos":
                return "{\"operacao\":\"" + operacao + "\"" + 
                        ", \"token\":\"" + token + "\"" +
                        ", \"categoria\":" + id +
                        '}';
            case "localizarUsuario":
                return "{\"operacao\":\"" + operacao + "\"" + 
                        ", \"token\":\"" + token + "\"" +
                        ", \"ra\":\"" + ra + "\"" + '}';
            case "listarUsuarios":
                return "{\"operacao\":\"" + operacao + "\"" + 
                        ", \"token\":\"" + token + "\"" + '}';
            case "excluirUsuario":
                return "{\"operacao\":\"" + operacao + "\"" + 
                        ", \"token\":\"" + token + "\"" +
                        ", \"ra\":\"" + ra + "\"" + '}';
            case "editarUsuario":
                if (usuario == null) {   
                    return "{\"operacao\":\"" + operacao + "\"" + 
                    ", \"token\":\"" + token + "\"" +
                    ", \"usuario\":{" +
                    "\"ra\":\"" + ra + "\"" +
                    ", \"senha\":\"" + senha + "\"" +
                    ", \"nome\":\"" + nome + "\"" + "}}";
                } else {
                    return "{\"operacao\":\"" + operacao + "\"" + 
                    ", \"token\":\"" + token + "\"" +
                    ", \"usuario\":" + usuario.toString() + "}";
                }
            case "salvarCategoria":
                return "{\"operacao\":\"" + operacao + "\"" + 
                        ", \"token\":\"" + token + "\"" +
                        ", \"categoria\": "+
                        "{ \"id\":" + categorias.getId() +
                        ", \"nome\":\"" + categorias.getName() + "\"}}";
            case "excluirCategoria":
                return "{\"operacao\":\"" + operacao + "\"" + 
                        ", \"token\":\"" + token + "\"" +
                        ", \"id\":" + id + '}';
            case "salvarAviso":
                return "{\"operacao\":\"" + operacao + "\"" + 
                        ", \"token\":\"" + token + "\"" +
                        ", \"aviso\": " + 
                        "{ \"id\":" + avisos.get(0).getId() + 
                        ", \"titulo\":\"" + avisos.get(0).getTitle() + "\"" + 
                        ", \"descricao\":\"" + avisos.get(0).getDescription() + "\"" + 
                        ", \"categoria\":\"" + avisos.get(0).getCategory() + "\"}}";
            case "localizarAviso":
                return "{\"operacao\":\"" + operacao + "\"" + 
                        ", \"token\":\"" + token + "\"" +
                        ", \"id\":" + id + '}';
            case "excluirAviso":
                return "{\"operacao\":\"" + operacao + "\"" + 
                        ", \"token\":\"" + token + "\"" +
                        ", \"id\":" + id + '}';
            default:
                return "operacao inválida";
        }
    }
}