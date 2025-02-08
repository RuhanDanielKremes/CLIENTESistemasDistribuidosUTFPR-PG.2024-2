package org.example.model;

import java.util.List;
import com.google.gson.annotations.Expose;

public class JsonResponse {
    @Expose
    private int status;
    @Expose
    private String token;
    @Expose
    private String mensagem;
    @Expose
    private String operacao;
    @Expose
    private List<Category> categorias;
    @Expose
    private List<Avisos> avisos;
    @Expose
    private List<User> usuarios;

    public JsonResponse() {
        this.status = 0;
        this.token = "";
        this.mensagem = "";
        this.operacao = "";
        this.categorias = null;
        this.avisos = null;
        this.usuarios = null;
    }

    // Getters e Setters
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public List<Category> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Category> categorias) {
        this.categorias = categorias;
    }

    public List<Avisos> getAvisos() {
        return avisos;
    }

    public void setAvisos(List<Avisos> avisos) {
        this.avisos = avisos;
    }

    public List<User> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<User> usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public String toString() {
        switch (this.operacao) {
            case "cadastrarUsuario":
                return "{\"status\"=" + status + 
                        ", \"operacao=\"" + '\'' + operacao + '\'' +
                        ", \"mensagem\"=" + '\'' + mensagem + '\'' +
                        '}';
            case "login":
                if (this.status == 201) {
                    return "{\"status\"=" + status +
                            ", token=" + '\'' + token + '\'' +
                            '}';
                } else {
                    return "{\"status\"=" + status +
                            ", \"operacao=\"" + '\'' + operacao + '\'' +
                            ", \"mensagem\"='" + '\'' + mensagem + '\'' +
                            '}';
                }
            case "logout":
                if (this.status == 200) {
                    return "{\"status\"=" + status + '}';
                }else{
                    return "{\"status\"=" + status +
                            ", \"operacao=\"" + '\'' + operacao + '\'' +
                            ", \"mensagem\"=" + '\'' + mensagem + '\'' +
                            '}';
                }
            case "listarCategorias":
                if (status != 201) {
                return "{\"status\"=" + status + 
                        ", \"operacao=\"" + '\'' + operacao + '\'' +
                        ", \"mensagem\"=" + '\'' + mensagem + '\'' +
                        '}';
                }
                String returnToString;
                returnToString = "{\"status\"=" + status + 
                        ", \"operacao=\"" + '\'' + operacao + '\''
                        + ", \"categorias\"=[";
                if (categorias == null) {
                    returnToString += "]}";
                    return returnToString;
                }
                if (categorias.isEmpty()) {
                    returnToString += "]}";
                    return returnToString;
                }
                for (Category category : this.categorias) {
                    if (category.equals(this.categorias.get(this.categorias.size() - 1))) {
                        returnToString += category.toString() + "]}";
                    } else {
                        returnToString += category.toString() + ", ";
                    }
                }
                return returnToString;
            case "localizarCategoria":
            if (status != 201) {
                return "{\"status\"=" + status + 
                        ", \"operacao=\"" + '\'' + operacao + '\'' +
                        ", \"mensagem\"=" + '\'' + mensagem + '\'' +
                        '}';
                }
                return "{\"status\"=" + status + 
                        ", \"operacao=\"" + '\'' + operacao + '\'' +
                        ", \"categorias\"=" + categorias.toString() +
                        '}';
            case "listarAvisos":
                if (status != 201) {
                return "{\"status\"=" + status + 
                        ", \"operacao=\"" + '\'' + operacao + '\'' +
                        ", \"mensagem\"=" + '\'' + mensagem + '\'' +
                        '}';
                }
                String returnToStringWarnings;
                returnToStringWarnings = "{\"status\"=" + status + 
                        ", \"operacao=\"" + '\'' + operacao + '\''
                        + ", \"avisos\"=[";
                if (avisos == null) {
                    returnToStringWarnings += "]}";
                    return returnToStringWarnings;
                }
                if (avisos.isEmpty()) {
                    returnToStringWarnings += "]}";
                    return returnToStringWarnings;
                }
                for (Avisos warning : this.avisos) {
                    if (warning.equals(this.avisos.get(this.avisos.size() - 1))) {
                        returnToStringWarnings += warning.toString() + "]}";
                    } else {
                        returnToStringWarnings += warning.toString() + ", ";
                    }
                }
                return returnToStringWarnings;
            case "cadastrarUsuarioCategoria":
                if (status != 201) {
                return "{\"status\"=" + status + 
                        ", \"operacao=\"" + '\'' + operacao + '\'' +
                        ", \"mensagem\"=" + '\'' + mensagem + '\'' +
                        '}';
                }
                return "{\"status\"=" + status + 
                        ", \"operacao=\"" + '\'' + operacao + '\'' +
                        ", \"mensagem\"=" + '\'' + mensagem + '\'' +
                        '}';
            case "descadastrarUsuarioCategoria":
                if (status != 201) {
                return "{\"status\"=" + status + 
                        ", \"operacao=\"" + '\'' + operacao + '\'' +
                        ", \"mensagem\"=" + '\'' + mensagem + '\'' +
                        '}';
                }
                return "{\"status\"=" + status + 
                        ", \"operacao=\"" + '\'' + operacao + '\'' +
                        ", \"mensagem\"=" + '\'' + mensagem + '\'' +
                        '}';
            case "listarUsuarioCategorias":
                if (status != 201) {
                return "{\"status\"=" + status + 
                        ", \"operacao=\"" + '\'' + operacao + '\'' +
                        ", \"mensagem\"=" + '\'' + mensagem + '\'' +
                        '}';
                }
                String returnToStringCategories;
                returnToStringCategories = "{\"status\"=" + status + 
                        ", \"operacao=\"" + '\'' + operacao + '\''
                        + ", \"categorias\"=[";
                if (categorias == null) {
                    returnToStringCategories += "]}";
                    return returnToStringCategories;
                }
                if (categorias.isEmpty()) {
                    returnToStringCategories += "]}";
                    return returnToStringCategories;
                }
                for (Category category : this.categorias) {
                    if (category.equals(this.categorias.get(this.categorias.size() - 1))) {
                        returnToStringCategories += category.toString() + "]}";
                    } else {
                        returnToStringCategories += category.toString() + ", ";
                    }
                }
                return returnToStringCategories;
            case "listarUsuarios":
                if (status != 201) {
                return "{\"status\"=" + status + 
                        ", \"operacao=\"" + '\'' + operacao + '\'' +
                        ", \"mensagem\"=" + '\'' + mensagem + '\'' +
                        '}';
                }
                String returnToStringUsers;
                returnToStringUsers = "{\"status\"=" + status + 
                        ", \"operacao=\"" + '\'' + operacao + '\''
                        + ", \"usuarios\"=[";
                if (usuarios == null) {
                    returnToStringUsers += "]}";
                    return returnToStringUsers;
                }
                if (usuarios.isEmpty()) {
                    returnToStringUsers += "]}";
                    return returnToStringUsers;
                }
                for (User user : this.usuarios) {
                    if (user.equals(this.usuarios.get(this.usuarios.size() - 1))) {
                        returnToStringUsers += user.toString() + "]}";
                    } else {
                        returnToStringUsers += user.toString() + ", ";
                    }
                }
                return returnToStringUsers;
            case "localizarUsuario":
                if (status != 201) {
                return "{\"status\"=" + status + 
                        ", \"operacao=\"" + '\'' + operacao + '\'' +
                        ", \"mensagem\"=" + '\'' + mensagem + '\'' +
                        '}';
                }
                return "{\"status\"=" + status + 
                        ", \"operacao=\"" + '\'' + operacao + '\'' +
                        ", \"usuarios\"=" + usuarios.toString() +
                        '}';
            case "excluirUsuario":
                if (status != 201) {
                return "{\"status\"=" + status + 
                        ", \"operacao=\"" + '\'' + operacao + '\'' +
                        ", \"mensagem\"=" + '\'' + mensagem + '\'' +
                        '}';
                }
                return "{\"status\"=" + status + 
                        ", \"operacao=\"" + '\'' + operacao + '\'' +
                        ", \"mensagem\"=" + '\'' + mensagem + '\'' +
                        '}';
            case "editarUsuario":
                if (status != 201) {
                return "{\"status\"=" + status + 
                        ", \"operacao=\"" + '\'' + operacao + '\'' +
                        ", \"mensagem\"=" + '\'' + mensagem + '\'' +
                        '}';
                }
                return "{\"status\"=" + status + 
                        ", \"operacao=\"" + '\'' + operacao + '\'' +
                        ", \"mensagem\"=" + '\'' + mensagem + '\'' +
                        '}';
            case "criarCategoria":
                if (status != 201) {
                return "{\"status\"=" + status + 
                        ", \"operacao=\"" + '\'' + operacao + '\'' +
                        ", \"mensagem\"=" + '\'' + mensagem + '\'' +
                        '}';
                }
                return "{\"status\"=" + status + 
                        ", \"operacao=\"" + '\'' + operacao + '\'' +
                        ", \"mensagem\"=" + '\'' + mensagem + '\'' +
                        '}';
            case "editarCategoria":
                if (status != 201) {
                return "{\"status\"=" + status + 
                        ", \"operacao=\"" + '\'' + operacao + '\'' +
                        ", \"mensagem\"=" + '\'' + mensagem + '\'' +
                        '}';
                }
                return "{\"status\"=" + status + 
                        ", \"operacao=\"" + '\'' + operacao + '\'' +
                        ", \"mensagem\"=" + '\'' + mensagem + '\'' +
                        '}';
            case "excluirCategoria":
                if (status != 201) {
                return "{\"status\"=" + status + 
                        ", \"operacao=\"" + '\'' + operacao + '\'' +
                        ", \"mensagem\"=" + '\'' + mensagem + '\'' +
                        '}';
                }
                return "{\"status\"=" + status + 
                        ", \"operacao=\"" + '\'' + operacao + '\'' +
                        ", \"mensagem\"=" + '\'' + mensagem + '\'' +
                        '}';
            case "criarAviso":
                if (status != 201) {
                return "{\"status\"=" + status + 
                        ", \"operacao=\"" + '\'' + operacao + '\'' +
                        ", \"mensagem\"=" + '\'' + mensagem + '\'' +
                        '}';
                }
                return "{\"status\"=" + status + 
                        ", \"operacao=\"" + '\'' + operacao + '\'' +
                        ", \"mensagem\"=" + '\'' + mensagem + '\'' +
                        '}';
            case "editarAviso":
                if (status != 201) {
                return "{\"status\"=" + status + 
                        ", \"operacao=\"" + '\'' + operacao + '\'' +
                        ", \"mensagem\"=" + '\'' + mensagem + '\'' +
                        '}';
                }
                return "{\"status\"=" + status + 
                        ", \"operacao=\"" + '\'' + operacao + '\'' +
                        ", \"mensagem\"=" + '\'' + mensagem + '\'' +
                        '}';
            case "localizarAviso":
                if (status != 201) {
                return "{\"status\"=" + status + 
                        ", \"operacao=\"" + '\'' + operacao + '\'' +
                        ", \"mensagem\"=" + '\'' + mensagem + '\'' +
                        '}';
                }
                return "{\"status\"=" + status + 
                        ", \"operacao=\"" + '\'' + operacao + '\'' +
                        ", \"avisos\"=" + avisos.toString() +
                        '}';
            case "excluirAviso":
                if (status != 201) {
                return "{\"status\"=" + status + 
                        ", \"operacao=\"" + '\'' + operacao + '\'' +
                        ", \"mensagem\"=" + '\'' + mensagem + '\'' +
                        '}';
                }
                return "{\"status\"=" + status + 
                        ", \"operacao=\"" + '\'' + operacao + '\'' +
                        ", \"mensagem\"=" + '\'' + mensagem + '\'' +
                        '}';
            default:
                if (status != 201) {
                return "{\"status\"=" + status + 
                        ", \"operacao=\"" + '\'' + operacao + '\'' +
                        ", \"mensagem\"=" + '\'' + mensagem + '\'' +
                        '}';
                }
                return  "{\"status\"=" + status + 
                        ", \"operacao=\"" + '\'' + operacao + '\'' +
                        ", \"mensagem\"=" + '\'' + mensagem + '\'' +
                        '}';
        }
    }
}