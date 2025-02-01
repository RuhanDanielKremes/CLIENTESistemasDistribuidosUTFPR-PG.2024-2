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
    private List<Warnings> avisos;

    public JsonResponse() {
        this.status = 0;
        this.token = "";
        this.mensagem = "";
        this.operacao = "";
        this.categorias = null;
        this.avisos = null;
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

    public List<Warnings> getAvisos() {
        return avisos;
    }

    public void setAvisos(List<Warnings> avisos) {
        this.avisos = avisos;
    }

    @Override
    public String toString() {
        switch (this.operacao) {
            case "cadastrarUsuario":
                return "{status=" + status + 
                        ", operacao=" + '\'' + operacao + '\'' +
                        ", mensagem=" + '\'' + mensagem + '\'' +
                        '}';
            case "login":
                if (this.status == 200) {
                    return "{status=" + status +
                            ", token=" + '\'' + token + '\'' +
                            '}';
                } else {
                    return "{status=" + status +
                            ", operacao=" + '\'' + operacao + '\'' +
                            ", mensagem='" + '\'' + mensagem + '\'' +
                            '}';
                }
            case "logout":
                if (this.status == 200) {
                    return "{status=" + status + '}';
                }else{
                    return "{status=" + status +
                            ", operacao=" + '\'' + operacao + '\'' +
                            ", mensagem=" + '\'' + mensagem + '\'' +
                            '}';
                }
            case "listarCategorias":
                if (status != 200) {
                return "{status=" + status + 
                        ", operacao=" + '\'' + operacao + '\'' +
                        ", mensagem=" + '\'' + mensagem + '\'' +
                        '}';
                }
                String returnToString;
                returnToString = "{status=" + status + 
                        ", operacao=" + '\'' + operacao + '\''
                        + ", categorias=[";
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
            if (status != 200) {
                return "{status=" + status + 
                        ", operacao=" + '\'' + operacao + '\'' +
                        ", mensagem=" + '\'' + mensagem + '\'' +
                        '}';
                }
                return "{status=" + status + 
                        ", operacao=" + '\'' + operacao + '\'' +
                        ", categorias=" + categorias.toString() +
                        '}';
            case "listarAvisos":
                if (status != 200) {
                return "{status=" + status + 
                        ", operacao=" + '\'' + operacao + '\'' +
                        ", mensagem=" + '\'' + mensagem + '\'' +
                        '}';
                }
                String returnToStringWarnings;
                returnToStringWarnings = "{status=" + status + 
                        ", operacao=" + '\'' + operacao + '\''
                        + ", avisos=[";
                if (avisos == null) {
                    returnToStringWarnings += "]}";
                    return returnToStringWarnings;
                }
                if (avisos.isEmpty()) {
                    returnToStringWarnings += "]}";
                    return returnToStringWarnings;
                }
                for (Warnings warning : this.avisos) {
                    if (warning.equals(this.avisos.get(this.avisos.size() - 1))) {
                        returnToStringWarnings += warning.toString() + "]}";
                    } else {
                        returnToStringWarnings += warning.toString() + ", ";
                    }
                }
                return returnToStringWarnings;
            case "cadastrarUsuarioCategoria":
                if (status != 200) {
                return "{status=" + status + 
                        ", operacao=" + '\'' + operacao + '\'' +
                        ", mensagem=" + '\'' + mensagem + '\'' +
                        '}';
                }
                return "{status=" + status + 
                        ", operacao=" + '\'' + operacao + '\'' +
                        ", mensagem=" + '\'' + mensagem + '\'' +
                        '}';
            case "descadastrarUsuarioCategoria":
                if (status != 200) {
                return "{status=" + status + 
                        ", operacao=" + '\'' + operacao + '\'' +
                        ", mensagem=" + '\'' + mensagem + '\'' +
                        '}';
                }
                return "{status=" + status + 
                        ", operacao=" + '\'' + operacao + '\'' +
                        ", mensagem=" + '\'' + mensagem + '\'' +
                        '}';
            default:
                if (status != 200) {
                return "{status=" + status + 
                        ", operacao=" + '\'' + operacao + '\'' +
                        ", mensagem=" + '\'' + mensagem + '\'' +
                        '}';
                }
                return  "{status=" + status + 
                        ", operacao=" + '\'' + operacao + '\'' +
                        ", mensagem=" + '\'' + mensagem + '\'' +
                        '}';
        }
    }
}