package org.example;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import org.example.controller.LogController;
import org.example.model.Json;
import org.example.model.User;

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
            default:
                logController.writeSimpleLog("CLIENT: SEARCH OPERATION", "Operation not implementated", false);
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

    public Json cadastrarUsuario() throws IOException{
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
            }else{
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

    public Json localizarCategoria(User user) throws IOException{
        logController.writeSimpleLog("CLIENT: OPERATION", "Localizando categoria", true);
        Json json = new Json();
        while (true) {
            System.out.print("Digite o ID da categoria: ");
            String id = System.console().readLine();
            if (id.matches("^[0-9]+$")) {
                json.setId(Integer.parseInt(id));
                break;
            }else{
                logController.writeSimpleLog("CLIENT: OPERATION", "ID inválido", true);
                System.out.println("ID inválido");
            }
        }
        json.setOperacao("localizarCategoria");
        json.setToken(user.getToken());
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

    public Json cadastrarUsuarioCategoria(User user) throws IOException{
        logController.writeSimpleLog("CLIENT: OPERATION", "Cadastrando categoria do usuário", true);
        Json json = new Json();
        while (true) {
            System.out.print("Digite o ID da categoria: ");
            String id = System.console().readLine();
            if (id.matches("^[0-9]+$")) {
                json.setId(Integer.parseInt(id));
                break;
            }else{
                logController.writeSimpleLog("CLIENT: OPERATION", "ID inválido", true);
                System.out.println("ID inválido");
            }
        }
        json.setOperacao("cadastrarUsuarioCategoria");
        json.setToken(user.getToken());
        logController.writeSimpleLog("CLIENT: OPERATION", "Cadastrar categoria do usuario pronto para ser enviado", true);
        return json;
    }

    public Json descadastrarUsuarioCategoria(User user) throws IOException{
        logController.writeSimpleLog("CLIENT: OPERATION", "Descadastrando categoria do usuário", true);
        Json json = new Json();
        while (true) {
            System.out.print("Digite o ID da categoria: ");
            String id = System.console().readLine();
            if (id.matches("^[0-9]+$")) {
                json.setId(Integer.parseInt(id));
                break;
            }else{
                logController.writeSimpleLog("CLIENT: OPERATION", "ID inválido", true);
                System.out.println("ID inválido");
            }
        }
        json.setOperacao("descadastrarUsuarioCategoria");
        json.setToken(user.getToken());
        logController.writeSimpleLog("CLIENT: OPERATION", "Descadastrar categoria do usuario pronto para ser enviado", true);
        return json;
    }

    public Json listarAvisos(User user) throws IOException{
        logController.writeSimpleLog("CLIENT: OPERATION", "Listando avisos", true);
        Json json = new Json();
        json.setOperacao("listarAvisos");
        json.setToken(user.getToken());
        logController.writeSimpleLog("CLIENT: OPERATION", "Listar avisos pronto para ser enviado", true);
        return json;
    }

    public void sair(){}

}