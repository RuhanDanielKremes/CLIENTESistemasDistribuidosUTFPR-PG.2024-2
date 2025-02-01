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

    public JsonResponse() {
        this.status = 0;
        this.token = "";
        this.mensagem = "";
        this.operacao = "";
        this.categorias = null;
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

    @Override
    public String toString() {
        switch (this.operacao) {
            case "login":
                return "{status=" + status +
                        ", token='" + token + '\'' +
                        ", operacao='" + operacao + '\'' +
                        ", mensagem='" + mensagem + '\'' +
                        '}';
            case "listarCategorias":
                StringBuilder returnToString = new StringBuilder();
                returnToString.append("{status=").append(status)
                        .append(", operacao='").append(operacao)
                        .append("', categorias=[");
                if (categorias != null) {
                    for (int i = 0; i < categorias.size(); i++) {
                        returnToString.append(categorias.get(i).toString());
                        if (i != categorias.size() - 1) {
                            returnToString.append(", ");
                        }
                    }
                }
                returnToString.append("]}");
                return returnToString.toString();
            default:
                return "{status=" + status +
                        ", operacao='" + operacao + '\'' +
                        ", mensagem='" + mensagem + '\'' +
                        '}';
        }
    }
}