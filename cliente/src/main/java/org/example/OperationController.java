package org.example;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.example.controller.LogController;
import org.example.model.Json;
import org.example.model.User;
import org.example.model.Category;
import org.example.model.Warnings;

public class OperationController {
    
    LogController logController = new LogController();

    public Json sitchOperation(String operation, User user) throws IOException{
        switch (operation) {
            case "cadastrarUsuario":
                return cadastrarUsuario();
            case "login":
                return login();
            case "logout":
                return logout(user);
            case "listarCategorias":
                return listarCategorias(user);
            case "localizarCategoria":
                return localizarCategoria(user);
            case "listarUsuarioCategorias":
                return listarUsuarioCategorias(user);
            case "cadastrarUsuarioCategoria":
                return cadastrarUsuarioCategoria(user);
            case "descadastrarUsuarioCategoria":
                return descadastrarUsuarioCategoria(user);
            case "listarAvisos":
                return listarAvisos(user);
            case "listarUsuarios":
                return listarUsuarios(user);
            case "localizarUsuario":
                return localizarUsuario(user);
            case "excluirUsuario":
                return excluirUsuario(user);
            case "editarUsuario":
                return editarUsuario(user);
            case "criarCategoria":
                return criarCategoria(user);
            case "editarCategoria":
                return editarCategoria(user);
            case "excluirCategoria":
                return excluirCategoria(user);
            case "criarAviso":
                return criarAviso(user);
            case "editarAviso":
                return editarAviso(user);
            case "localizarAviso":
                return localizarAvisos(user);
            case "excluirAviso":
                return excluirAviso(user);
            default:
                logController.writeSimpleLog("CLIENT: SEARCH OPERATION", "Operation not implementated", false);
                System.out.println(operation);
                return null;
        }
    }

    public Socket cadastrarSocket(Scanner scanner) throws IOException{
        logController.writeSimpleLog("CLIENT: HOST", "Solicitando dados para conexão", true);
        int console = 10;
        String inputString = "";
        try {
            scanner = new Scanner(System.in);
            while (console != 0) {
                switch (console) {
                    case 10:
                    while (true) {
                        System.out.println("[0] para sair\n[1] Cadastar novo host\n[2] Cadastar host padrão");
                        inputString = scanner.nextLine().trim();
                        if (inputString.isEmpty()) {
                            System.out.println("Opção inválida");
                            continue;
                        }
                        try {
                            console = Integer.parseInt(inputString);
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Opção inválida");
                        }
                        break;
                    }
                    case 0:
                        ClearConsole.clearConsole();
                        break;
                    case 1:
                        String host = "";
                        ClearConsole.clearConsole();// Código ANSI para limpar tela
                        
                        System.out.println("Digite o host");
                        host = System.console().readLine();
                        int port = 0;
                        while (port == 0) {
                            System.out.println("Digite a porta");
                            try {
                                port = Integer.parseInt(System.console().readLine());
                            } catch (Exception e) {
                                System.out.println("Porta inválida");
                            }
                        }
                        Socket socket = testSocket(host, port);
                        if (socket != null) {
                            logController.writeSimpleLog("CLIENT: HOST", "Conexão estabelecida", true);
                            ClearConsole.clearConsole();// Código ANSI para limpar tela
                            
                            System.out.println("Conexão estabelecida");
                            return testSocket(host, port);
                        }else{
                            logController.writeSimpleLog("CLIENT: HOST", "Host ou porta inválidos", true);
                            System.out.println("Host ou porta inválidos");
                            console = 10;
                        }
                        break;
                    case 2:
                        socket = testSocket("localhost", 22222);
                        if (socket != null) {
                            logController.writeSimpleLog("CLIENT: HOST", "Conexão estabelecida", true);
                            ClearConsole.clearConsole();
                            System.out.println("Conexão estabelecida");
                            return socket;
                        }else{
                            logController.writeSimpleLog("CLIENT: HOST", "Host ou porta inválidos", true);
                            System.out.println("Host ou porta inválidos");
                            console = 10;
                        }
                        break;
                    default:
                        ClearConsole.clearConsole();
                        System.out.println("Opção inválida");
                        console = 10;
                        break;
                }
            }
        } catch (Exception e) {
            logController.writeSimpleLog("CLIENT: HOST", "Erro com o scanner", true);
            System.out.println("Erro com o scanner");
        }
        return null;
    }

    public Socket testSocket (String HostName, int port) throws IOException{
        logController.writeSimpleLog("CLIENT: HOST", "Attempting Connection", true);
        try {
            Socket socket = new Socket(HostName, port);
            logController.writeSimpleLog("CLIENT: HOST", "Connected to server", true);
            return socket;
        } catch (Exception e) {
            logController.writeSimpleLog("CLIENT: HOST", "Connection failed", true);
            return null;
        }
    }

    public Json cadastrarUsuario() throws IOException {
        logController.writeSimpleLog("CLIENT: OPERATION", "Cadastrando usuário", true);
        User user = new User();
        String nome;
        do {
            ClearConsole.clearConsole();// Código ANSI para limpar tela

            System.out.print("Digite o nome do usuário: ");
            nome = System.console().readLine();
            nome = nome.toUpperCase();
            if (nome.length() < 50 && nome.matches("^[A-Z]+$")) {
                user.setName(nome);
            } else {
                logController.writeSimpleLog("CLIENT: OPERATION", "Nome inválido", true);
                System.out.println("Nome inválido");
            }
        } while (user.getName() == "");
        String ra;
        do {
            System.out.print("Digite o RA do usuário: ");
            ra = System.console().readLine();
            if (ra.length() != 7) {
                logController.writeSimpleLog("CLIENT: OPERATION", "RA inválido", true);
                System.out.println("RA inválido");
            } else {
                user.setRa(ra);
            }
        } while (user.getRa() == "");
        String senha;
        do {
            System.out.print("Digite a senha do usuário: ");
            senha = System.console().readLine();
            if (senha.length() < 8 || senha.length() > 20) {
                logController.writeSimpleLog("CLIENT: OPERATION", "Senha inválida", true);
                System.out.println("Senha inválida");
            } else {
                user.setPassword(senha);
            }
        } while (user.getPassword() == "");
        Json json = new Json();
        json.setOperacao("cadastrarUsuario");
        json.setNome(user.getName());
        json.setRa(user.getRa());
        json.setSenha(user.getPassword());
        logController.writeSimpleLog("CLIENT: OPERATION", "Cadastro do usuario pronto para ser enviado", true);
        return json;
    }
    
    public Json cadastrarUsuario(User user) throws IOException{
        logController.writeSimpleLog("CLIENT: OPERATION", "Cadastrando usuário", true);
        Json json = new Json();
        json.setOperacao("cadastrarUsuario");
        json.setNome(user.getName());
        json.setRa(user.getRa());
        json.setSenha(user.getPassword());
        logController.writeSimpleLog("CLIENT: OPERATION", "Cadastro do usuario pronto para ser enviado", true);
        return json;
    }

    public Json login() throws IOException{
        logController.writeSimpleLog("CLIENT: OPERATION", "Realizando login", true);
        User user = new User();
        String ra;
        do {
            ClearConsole.clearConsole();
            
            System.out.print("Digite o RA do usuário: ");
            ra = System.console().readLine();
            if (ra.length() != 7) {
                logController.writeSimpleLog("CLIENT: OPERATION", "RA inválido", true);
                System.out.println("RA inválido");
            }else{
                user.setRa(ra);
            }
        } while (user.getRa() == "");
        String senha;
        do {
            System.out.print("Digite a senha do usuário: ");
            senha = System.console().readLine();
            if (senha.length() < 8 || senha.length() > 20) {
                logController.writeSimpleLog("CLIENT: OPERATION", "Senha inválida", true);
                System.out.println("Senha inválida");
            }else{
                user.setPassword(senha);
            }
        } while (user.getPassword() == "");
        Json json = new Json();
        json.setOperacao("login");
        json.setRa(user.getRa());
        json.setSenha(user.getPassword());
        logController.writeSimpleLog("CLIENT: OPERATION", "Login de usuario pronto para ser enviado", true);
        return json;
    }

    public Json login(User user) throws IOException{
        logController.writeSimpleLog("CLIENT: OPERATION", "Realizando login", true);
        Json json = new Json();
        json.setOperacao("login");
        json.setRa(user.getRa());
        json.setSenha(user.getPassword());
        logController.writeSimpleLog("CLIENT: OPERATION", "Login de usuario pronto para ser enviado", true);
        return json;
    }

    public Json logout(User user) throws IOException{
        logController.writeSimpleLog("CLIENT: OPERATION", "Realizando logout", true);
        Json json = new Json();
        json.setOperacao("logout");
        json.setToken(user.getToken());
        logController.writeSimpleLog("CLIENT: OPERATION", "Logout do usuario pronto para ser enviado", true);
        return json;
    }

    public Json listarCategorias(User user) throws IOException{
        logController.writeSimpleLog("CLIENT: OPERATION", "Listando categorias", true);
        Json json = new Json();
        json.setOperacao("listarCategorias");
        json.setToken(user.getToken());
        logController.writeSimpleLog("CLIENT: OPERATION", "Listar categorias pronto para ser enviado", true);
        return json;
    }

    public Json localizarCategoria(User user) throws IOException {
        logController.writeSimpleLog("CLIENT: OPERATION", "Localizando categoria", true);
        Json json = new Json();
        while (true) {
            System.out.print("Digite o ID da categoria: ");
            String id = System.console().readLine();
            if (id.matches("^[0-9]+$")) {
                json.setId(Integer.parseInt(id));
                break;
            } else {
                logController.writeSimpleLog("CLIENT: OPERATION", "ID inválido", true);
                System.out.println("ID inválido");
            }
        }
        json.setOperacao("localizarCategoria");
        json.setToken(user.getToken());
        logController.writeSimpleLog("CLIENT: OPERATION", "Localizar categoria pronto para ser enviado", true);
        return json;
    }
    
    public Json localizarCategoria(User user, int id) throws IOException {
        logController.writeSimpleLog("CLIENT: OPERATION", "Localizando categoria", true);
        Json json = new Json();
        json.setOperacao("localizarCategoria");
        json.setToken(user.getToken());
        json.setId(id);
        logController.writeSimpleLog("CLIENT: OPERATION", "Localizar categoria pronto para ser enviado", true);
        return json;
    }

    public Json listarUsuarioCategorias(User user) throws IOException{
        logController.writeSimpleLog("CLIENT: OPERATION", "Listando categorias do usuário", true);
        Json json = new Json();
        json.setOperacao("listarUsuarioCategorias");
        json.setToken(user.getToken());
        logController.writeSimpleLog("CLIENT: OPERATION", "Listar categorias do usuario pronto para ser enviado", true);
        return json;
    }

    public Json cadastrarUsuarioCategoria(User user) throws IOException {
        logController.writeSimpleLog("CLIENT: OPERATION", "Cadastrando categoria do usuário", true);
        Json json = new Json();
        while (true) {
            System.out.print("Digite o ID da categoria: ");
            String id = System.console().readLine();
            if (id.matches("^[0-9]+$")) {
                json.setId(Integer.parseInt(id));
                break;
            } else {
                logController.writeSimpleLog("CLIENT: OPERATION", "ID inválido", true);
                System.out.println("ID inválido");
            }
        }
        json.setOperacao("cadastrarUsuarioCategoria");
        json.setToken(user.getToken());
        logController.writeSimpleLog("CLIENT: OPERATION", "Cadastrar categoria do usuario pronto para ser enviado",
                true);
        return json;
    }
    
    public Json cadastrarUsuarioCategoria(User user, int id) throws IOException{
        logController.writeSimpleLog("CLIENT: OPERATION", "Cadastrando categoria do usuário", true);
        Json json = new Json();
        json.setOperacao("cadastrarUsuarioCategoria");
        json.setToken(user.getToken());
        json.setId(id);
        json.setRa(user.getToken());
        logController.writeSimpleLog("CLIENT: OPERATION", "Cadastrar categoria do usuario pronto para ser enviado", true);
        return json;
    }

    public Json descadastrarUsuarioCategoria(User user) throws IOException {
        logController.writeSimpleLog("CLIENT: OPERATION", "Descadastrando categoria do usuário", true);
        Json json = new Json();
        while (true) {
            System.out.print("Digite o ID da categoria: ");
            String id = System.console().readLine();
            if (id.matches("^[0-9]+$")) {
                json.setId(Integer.parseInt(id));
                break;
            } else {
                logController.writeSimpleLog("CLIENT: OPERATION", "ID inválido", true);
                System.out.println("ID inválido");
            }
        }
        json.setOperacao("descadastrarUsuarioCategoria");
        json.setToken(user.getToken());
        logController.writeSimpleLog("CLIENT: OPERATION", "Descadastrar categoria do usuario pronto para ser enviado",
                true);
        return json;
    }
    
    public Json descadastrarUsuarioCategoria(User user, int id) throws IOException{
        logController.writeSimpleLog("CLIENT: OPERATION", "Descadastrando categoria do usuário", true);
        Json json = new Json();
        json.setOperacao("descadastrarUsuarioCategoria");
        json.setToken(user.getToken());
        json.setId(id);
        json.setRa(user.getToken());
        logController.writeSimpleLog("CLIENT: OPERATION", "Descadastrar categoria do usuario pronto para ser enviado", true);
        return json;
    }

    public Json listarAvisos(User user) throws IOException {
        logController.writeSimpleLog("CLIENT: OPERATION", "Listando avisos", true);
        Json json = new Json();
        json.setOperacao("listarAvisos");
        json.setToken(user.getToken());
        json.setRa(user.getToken());
        logController.writeSimpleLog("CLIENT: OPERATION", "Listar avisos pronto para ser enviado", true);
        return json;
    }
    
    public Json listarAvisos(User user, int id) throws IOException {
        logController.writeSimpleLog("CLIENT: OPERATION", "Listando avisos", true);
        Json json = new Json();
        json.setOperacao("listarAvisos");
        json.setToken(user.getToken());
        json.setId(id);
        json.setRa(user.getToken());
        logController.writeSimpleLog("CLIENT: OPERATION", "Listar avisos pronto para ser enviado", true);
        return json;
    }

    public Json localizarAvisos(User user) throws IOException {
        logController.writeSimpleLog("CLIENT: OPERATION", "Localizando Avisos", true);
        Json json = new Json();
        json.setOperacao("localizarAviso");
        json.setToken(user.getToken());
        do {
            System.out.print("Digite o ID do aviso: ");
            String id = System.console().readLine();
            if (id.matches("^[0-9]+$")) {
                json.setId(Integer.parseInt(id));
                break;
            } else {
                logController.writeSimpleLog("CLIENT: OPERATION", "ID inválido", true);
                System.out.println("ID inválido");
            }
        } while (true);
        logController.writeSimpleLog("CLIENT: OPERATION", "Localizar avisos pronto para ser enviado", true);
        return json;
    }
    
    public Json localizarAviso(User user, int id) throws IOException {
        logController.writeSimpleLog("CLIENT: OPERATION", "Localizando Avisos", true);
        Json json = new Json();
        json.setOperacao("localizarAviso");
        json.setToken(user.getToken());
        json.setId(id);
        logController.writeSimpleLog("CLIENT: OPERATION", "Localizar avisos pronto para ser enviado", true);
        return json;
    }

    public Json listarUsuarios(User user) throws IOException{
        logController.writeSimpleLog("CLIENT: OPERATION", "Listando Usuarios", true);
        Json json = new Json();
        json.setOperacao("listarUsuarios");
        json.setToken(user.getToken());
        logController.writeSimpleLog("CLIENT: OPERATION", "Listar usuarios pronto para ser enviado", true);
        return json;
    }

    public Json localizarUsuario(User user) throws IOException{
        logController.writeSimpleLog("CLIENT: OPERATION", "Localizando Usuarios", true);
        Json json = new Json();
        json.setOperacao("localizarUsuario");
        json.setToken(user.getToken());
        json.setRa(user.getToken());
        logController.writeSimpleLog("CLIENT: OPERATION", "Localizar usuario pronto para ser enviado", true);
        return json;
    }

    public Json excluirUsuario(User user) throws IOException {
        logController.writeSimpleLog("CLIENT: OPERATION", "Excluindo Usuarios");
        Json json = new Json();
        json.setOperacao("excluirUsuario");
        json.setToken(user.getToken());
        if (user.getToken().equals("2524198") || user.getToken().equals("2509849")) {
            int opcao;
            while (true) {
                System.out.println("Deseja deletar o seu usuario[1] ou de outra pessoa[2]");
                String opcaoString = System.console().readLine();
                if (opcaoString.matches("^[1-2]$")) {
                    opcao = Integer.parseInt(opcaoString);
                    if (opcao == 1) {
                        json.setRa(user.getRa());
                    } else {
                        String ra;
                        do {
                            System.out.print("Digite o RA do usuário: ");
                            ra = System.console().readLine();
                            if (ra.length() != 7) {
                                logController.writeSimpleLog("CLIENT: OPERATION", "RA inválido", true);
                                System.out.println("RA inválido");
                            } else {
                                json.setRa(ra);
                            }
                        } while (json.getRa() == "");
                    }
                    break;
                } else {
                    logController.writeSimpleLog("CLIENT: OPERATION", "Opção inválida", true);
                    System.out.println("Opção inválida");
                }
            }
        } else {
            json.setRa(user.getRa());
        }
        logController.writeSimpleLog("CLIENT: OPERATION", "Excluir usuario pronto para ser enviado", true);
        return json;
    }

    public Json excluirUsuario(String token, String ra) throws IOException{
        logController.writeSimpleLog("CLIENT: OPERATION", "Excluindo Usuarios", true);
        Json json = new Json();
        json.setOperacao("excluirUsuario");
        json.setToken(token);
        json.setRa(ra);
        logController.writeSimpleLog("CLIENT: OPERATION", "Excluir usuario pronto para ser enviado", true);
        return json;
    }

    public Json editarUsuario(User user) throws IOException{
        logController.writeSimpleLog("CLIENT: OPERATION", "Editando Usuarios", true);
        Json json = new Json();
        json.setOperacao("editarUsuario");
        json.setToken(user.getToken());
        String nome = "";
        user.setName(nome);
        do {
            ClearConsole.clearConsole();
            System.out.print("Digite o nome do usuário: ");
            nome = System.console().readLine();
            nome = nome.toUpperCase();
            if (nome.length() < 50 && nome.matches("^[A-Z]+$")) {
                user.setName(nome);
            }else{
                logController.writeSimpleLog("CLIENT: OPERATION", "Nome inválido", true);
                System.out.println("Nome inválido");
            }
        } while (user.getName() == "");
        String ra = "";
        user.setRa(ra);
        do {
            System.out.print("Digite o RA do usuário: ");
            ra = System.console().readLine();
            if (ra.length() != 7) {
                logController.writeSimpleLog("CLIENT: OPERATION", "RA inválido", true);
                System.out.println("RA inválido");
            }else{
                user.setRa(ra);
            }
        } while (user.getRa() == "");
        String senha = "";
        user.setPassword(senha);
        do {
            System.out.print("Digite a senha do usuário: ");
            senha = System.console().readLine();
            if (senha.length() < 8 || senha.length() > 20) {
                logController.writeSimpleLog("CLIENT: OPERATION", "Senha inválida", true);
                System.out.println("Senha inválida");
            }else{
                user.setPassword(senha);
            }
        } while (user.getPassword() == "");
        json.setNome(user.getName());
        json.setRa(user.getRa());
        json.setSenha(user.getPassword());
        logController.writeSimpleLog("CLIENT: OPERATION", "Editar usuario pronto para ser enviado", true);
        return json;
    }

    public Json editUser(User user) {
        Json json = new Json();
        json.setOperacao("editarUsuario");
        json.setToken(user.getToken());
        user.setToken(null);
        json.setUsuario(user);
        return json;        
    }

    public Json criarCategoria(User user) throws IOException {
        logController.writeSimpleLog("CLIENT: OPERATION", "Salvando Categoria", true);
        Json json = new Json();
        json.setOperacao("salvarCategoria");
        json.setToken(user.getToken());
        Category categoria = new Category();
        ClearConsole.clearConsole();
        do {
            System.out.print("Digite o nome da categoria: ");
            categoria.setName(System.console().readLine());
            if (categoria.getName().length() < 50 && categoria.getName().matches("^[A-Z]+$")) {
                break;
            } else {
                logController.writeSimpleLog("CLIENT: OPERATION", "Nome inválido", true);
                ClearConsole.clearConsole();
                System.out.println("Nome inválido");
            }

        } while (true);
        categoria.setId(0);
        json.setCategorias(categoria);
        logController.writeSimpleLog("CLIENT: OPERATION", "Salvar categoria pronto para ser enviado", true);
        return json;
    }

    public Json criarCategoria(User user, String name) throws IOException {
        logController.writeSimpleLog("CLIENT: OPERATION", "Salvando Categoria", true);
        Json json = new Json();
        json.setOperacao("salvarCategoria");
        json.setToken(user.getToken());
        Category categoria = new Category();
        categoria.setName(name);
        categoria.setId(0);
        json.setCategorias(categoria);
        logController.writeSimpleLog("CLIENT: OPERATION", "Salvar categoria pronto para ser enviado", true);
        return json;
    }

    public Json editarCategoria(User user) throws IOException {
        logController.writeSimpleLog("CLIENT: OPERATION", "Salvando Categoria", true);
        Json json = new Json();
        json.setOperacao("salvarCategoria");
        json.setToken(user.getToken());
        Category categoria = new Category();
        do {
            ClearConsole.clearConsole();
            System.out.print("Digite o ID da categoria: ");
            String idString = System.console().readLine();
            if (idString.matches("^[0-9]+$")) {
                categoria.setId(Integer.parseInt(idString));
                break;
            } else {
                logController.writeSimpleLog("CLIENT: OPERATION", "ID inválido", true);
                System.out.println("ID inválido");
            }
        } while (true);
        do {
            System.out.print("Digite o nome da categoria: ");
            categoria.setName(System.console().readLine());
            if (categoria.getName().length() < 50 && categoria.getName().matches("^[A-Z]+$")) {
                logController.writeSimpleLog("CLIENT: OPERATION", "Nome válido", true);
                break;
            } else {
                logController.writeSimpleLog("CLIENT: OPERATION", "Nome inválido", true);
                System.out.println("Nome inválido");
            }
        } while (true);
        json.setCategorias(categoria);
        logController.writeSimpleLog("CLIENT: OPERATION", "Salvar categoria pronto para ser enviado", true);
        return json;
    }
    
    public Json editarCategoria(User user, int id, String name) throws IOException {
        logController.writeSimpleLog("CLIENT: OPERATION", "Salvando Categoria", true);
        Json json = new Json();
        json.setOperacao("salvarCategoria");
        json.setToken(user.getToken());
        Category categoria = new Category();
        categoria.setId(id);
        categoria.setName(name);
        json.setCategorias(categoria);
        logController.writeSimpleLog("CLIENT: OPERATION", "Salvar categoria pronto para ser enviado", true);
        return json;
    }

    public Json excluirCategoria(User user) throws IOException {
        logController.writeSimpleLog("CLIENT: OPERATION", "Excluindo Categoria", true);
        Json json = new Json();
        json.setOperacao("excluirCategoria");
        json.setToken(user.getToken());
        while (true) {
            System.out.print("Digite o ID da categoria: ");
            String id = System.console().readLine();
            if (id.matches("^[0-9]+$")) {
                json.setId(Integer.parseInt(id));
                break;
            } else {
                logController.writeSimpleLog("CLIENT: OPERATION", "ID inválido", true);
                System.out.println("ID inválido");
            }
        }
        logController.writeSimpleLog("CLIENT: OPERATION", "Excluir categoria pronto para ser enviado", true);
        return json;
    }
    
    public Json excluirCategoria(User user, int id) throws IOException {
        logController.writeSimpleLog("CLIENT: OPERATION", "Excluindo Categoria", true);
        Json json = new Json();
        json.setOperacao("excluirCategoria");
        json.setToken(user.getToken());
        json.setRa(user.getToken());
        json.setId(id);
        logController.writeSimpleLog("CLIENT: OPERATION", "Excluir categoria pronto para ser enviado", true);
        return json;
    }

    public Json criarAviso(User user) throws IOException {
        logController.writeSimpleLog("CLIENT: OPERATION", "Salvando Aviso", true);
        Json json = new Json();
        json.setOperacao("salvarAviso");
        json.setToken(user.getToken());
        Warnings warning = new Warnings();
        do {
            ClearConsole.clearConsole();
            System.out.print("Digite o título do aviso: ");
            warning.setTitle(System.console().readLine());
            if (warning.getTitle().length() < 50 && warning.getTitle().matches("^[a-zA-Z ]+$")) {
                break;
            } else {
                logController.writeSimpleLog("CLIENT: OPERATION", "Título inválido", true);
                System.out.println("Título inválido");
            }
        } while (true);
        do {
            System.out.print("Digite a descrição do aviso: ");
            warning.setDescription(System.console().readLine());
            if (warning.getDescription().length() < 50 && warning.getDescription().matches("^[a-zA-Z ]+$")) {
                break;
            } else {
                logController.writeSimpleLog("CLIENT: OPERATION", "Descrição inválida", true);
                System.out.println("Descrição inválida");
            }
        } while (true);
        do {
            System.out.print("Digite o ID da categoria: ");
            String id = System.console().readLine();
            if (id.matches("^[0-9]+$")) {
                warning.setCategory(Integer.parseInt(id));
                break;
            } else {
                logController.writeSimpleLog("CLIENT: OPERATION", "ID inválido", true);
                System.out.println("ID inválido");
            }
        } while (true);
        warning.setId(0);
        List<Warnings> warnings = new ArrayList<>();
        warnings.add(warning);
        json.setAvisos(warnings);
        logController.writeSimpleLog("CLIENT: OPERATION", "Salvar aviso pronto para ser enviado", true);
        return json;
    }
    
    public Json criarAviso(User user, String title, String description, int category) throws IOException {
        logController.writeSimpleLog("CLIENT: OPERATION", "Salvando Aviso", true);
        Json json = new Json();
        json.setOperacao("salvarAviso");
        json.setToken(user.getToken());
        Warnings warning = new Warnings();
        warning.setTitle(title);
        warning.setDescription(description);
        warning.setCategory(category);
        warning.setId(0);
        List<Warnings> warnings = new ArrayList<>();
        warnings.add(warning);
        json.setAvisos(warnings);
        logController.writeSimpleLog("CLIENT: OPERATION", "Salvar aviso pronto para ser enviado", true);
        return json;
    }

    public Json editarAviso(User user) throws IOException {
        logController.writeSimpleLog("CLIENT: OPERATION", "Editando Aviso", true);
        Json json = new Json();
        json.setOperacao("salvarAviso");
        json.setToken(user.getToken());
        Warnings warning = new Warnings();
        do {
            ClearConsole.clearConsole();
            System.out.print("Digite o ID do aviso: ");
            String idString = System.console().readLine();
            if (idString.matches("^[0-9]+$")) {
                warning.setId(Integer.parseInt(idString));
                break;
            } else {
                logController.writeSimpleLog("CLIENT: OPERATION", "ID inválido", true);
                System.out.println("ID inválido");
            }
        } while (true);
        do {
            System.out.print("Digite o título do aviso: ");
            warning.setTitle(System.console().readLine());
            if (warning.getTitle().length() < 50 && warning.getTitle().matches("^[a-zA-Z ]+$")) {
                break;
            } else {
                logController.writeSimpleLog("CLIENT: OPERATION", "Título inválido", true);
                System.out.println("Título inválido");
            }
        } while (true);
        do {
            System.out.print("Digite a descrição do aviso: ");
            warning.setDescription(System.console().readLine());
            if (warning.getDescription().length() < 50 && warning.getDescription().matches("^[a-zA-Z ]+$")) {
                break;
            } else {
                logController.writeSimpleLog("CLIENT: OPERATION", "Descrição inválida", true);
                System.out.println("Descrição inválida");
            }
        } while (true);
        do {
            System.out.print("Digite o ID da categoria: ");
            String id = System.console().readLine();
            if (id.matches("^[0-9]+$")) {
                warning.setCategory(Integer.parseInt(id));
                break;
            } else {
                logController.writeSimpleLog("CLIENT: OPERATION", "ID inválido", true);
                System.out.println("ID inválido");
            }
        } while (true);
        List<Warnings> warnings = new ArrayList<>();
        warnings.add(warning);
        json.setAvisos(warnings);
        logController.writeSimpleLog("CLIENT: OPERATION", "Editar aviso pronto para ser enviado", true);
        return json;
    }

    public Json editarAviso(User user, int id, String title, String description, int category) throws IOException {
        logController.writeSimpleLog("CLIENT: OPERATION", "Editando Aviso", true);
        Json json = new Json();
        json.setOperacao("salvarAviso");
        json.setToken(user.getToken());
        Warnings warning = new Warnings();
        warning.setId(id);
        warning.setTitle(title);
        warning.setDescription(description);
        warning.setCategory(category);
        List<Warnings> warnings = new ArrayList<>();
        warnings.add(warning);
        json.setAvisos(warnings);
        logController.writeSimpleLog("CLIENT: OPERATION", "Editar aviso pronto para ser enviado", true);
        return json;
    }

    public Json excluirAviso(User user) throws IOException {
        logController.writeSimpleLog("CLIENT: OPERATION", "Excluindo Aviso", true);
        Json json = new Json();
        json.setOperacao("excluirAviso");
        json.setToken(user.getToken());
        while (true) {
            System.out.print("Digite o ID do aviso: ");
            String id = System.console().readLine();
            if (id.matches("^[0-9]+$")) {
                json.setId(Integer.parseInt(id));
                break;
            } else {
                logController.writeSimpleLog("CLIENT: OPERATION", "ID inválido", true);
                System.out.println("ID inválido");
            }
        }
        logController.writeSimpleLog("CLIENT: OPERATION", "Excluir aviso pronto para ser enviado", true);
        return json;
    }
    
    public Json excluirAviso(User user, int id) throws IOException {
        logController.writeSimpleLog("CLIENT: OPERATION", "Excluindo Aviso", true);
        Json json = new Json();
        json.setOperacao("excluirAviso");
        json.setToken(user.getToken());
        json.setRa(user.getToken());
        json.setId(id);
        logController.writeSimpleLog("CLIENT: OPERATION", "Excluir aviso pronto para ser enviado", true);
        return json;
    }
    

    public void sair(){}

}