package org.example.model;

import java.util.List;

public class JsonResponseWarning {
    private int status;
    private String token;
    private String mensagem;
    private String operacao;
    private List<User> usuarios;
    private List<Category> categorias;
    private Avisos aviso;
    private User usuario;

    public JsonResponseWarning(){
        this.status = 0;
        this.token = null;
        this.mensagem = null;
        this.operacao = null;
        this.usuarios = null;
        this.categorias = null;
        this.aviso = null;
        this.usuario = null;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setMessage(String mensagem) {
        this.mensagem = mensagem;
    }

    public void setOperation(String operacao) {
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

    public String getOperation() {
        return operacao;
    }

    public void setUsers(List<User> user) {
        this.usuarios = user;
    }

    public List<User> getUsers() {
        return usuarios;
    }

    public void setCategory(List<Category> category) {
        this.categorias = category;
    }

    public List<Category> getCategory() {
        return categorias;
    }

    public void setWarning(Avisos warning) {
        this.aviso = warning;
    }

    public Avisos getWarning() {
        return aviso;
    }

    public void setUser(User user) {
        this.usuario = user;
    }

    public User getUser() {
        return usuario;
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
                        + ", aviso=" + aviso.toString() + "}";
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
            case "listarUsuarios":
                if (status != 201) {
                    return "(status=" + status +
                            ", operacao=" + '\'' + operacao + '\'' +
                            ", mensagem=" + '\'' + mensagem + '\'' +
                            '}';
                }
                return "{status=" + status +
                        ", operacao=" + '\'' + operacao + '\'' +
                        ", usuarios=" + usuarios.toString() +
                        '}';
            case "localizarUsuario":
                if (status != 201) {
                    return "{status=" + status +
                            ", operacao=" + '\'' + operacao + '\'' +
                            ", mensagem=" + '\'' + mensagem + '\'' +
                            '}';
                }
                return "{status=" + status +
                        ", operacao=" + '\'' + operacao + '\'' +
                        ", usuario=" + usuario.toString() +
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