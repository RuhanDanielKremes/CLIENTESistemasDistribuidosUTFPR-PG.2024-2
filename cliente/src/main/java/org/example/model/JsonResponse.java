package org.example.model;

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

    public JsonResponse(){
        this.status = 0;
        this.token = "";
        this.mensagem = "";
        this.operacao = "";
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
            default:
                return  "{status=" + '\'' + status + '\'' +
                        ", operacao=" + '\'' + operacao + '\'' +
                        ", mensagem=" + '\'' + mensagem + '\'' +
                        '}';
        }
    }
}