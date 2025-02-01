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

    public JsonResponse(){
        this.status = 0;
        this.token = "";
        this.mensagem = "";
        this.operacao = "";
        this.categorias = null;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setMessage(String message) {
        this.mensagem = message;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public int getStatus() {
        return status;
    }

    public String getToken() {
        return token;
    }

    public String getMessage() {
        return mensagem;
    }

    public String getOperacao() {
        return operacao;
    }
    
    @Override
    public String toString() {
        switch (this.operacao) {
            case "cadastrarUsuario":
                return "{status=" + '\'' + status + '\'' + 
                        ", operacao=" + '\'' + operacao + '\'' +
                        ", mensagem=" + '\'' + mensagem + '\'' +
                        '}';
            case "login":
                if (this.status == 200) {
                    return "{status=" + '\'' + status +
                            ", token=" + '\'' + token + '\'' +
                            '}';
                } else {
                    return "{status=" + '\'' + status + '\'' +
                            ", operacao=" + '\'' + operacao + '\'' +
                            ", mensagem='" + '\'' + mensagem + '\'' +
                            '}';
                }
            case "logout":
                if (this.status == 200) {
                    return "{status=" + '\'' + status + '\'' + '}';
                }else{
                    return "{status=" + '\'' + status + '\'' +
                            ", operacao=" + '\'' + operacao + '\'' +
                            ", mensagem=" + '\'' + mensagem + '\'' +
                            '}';
                }
            case "listarCategorias":
                String returnToString;
                returnToString = "{status=" + '\'' + status + '\'' +
                        ", operacao=" + '\'' + operacao + '\''
                        + ", categorias=[";
                if (categorias == null) {
                    returnToString += "null]}";
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
            default:
                return  "{status=" + '\'' + status + '\'' +
                        ", operacao=" + '\'' + operacao + '\'' +
                        ", mensagem=" + '\'' + mensagem + '\'' +
                        '}';
        }
    }
}