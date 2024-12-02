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
            default:
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
                if (console == 10) {
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
                    }
                }else{
                    if (console == 1) {
                        String host = "";
                        System.out.print("\033[H\033[2J"); // Código ANSI para limpar tela
                        System.out.flush();
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
                            System.out.print("\033[H\033[2J"); // Código ANSI para limpar tela
                            System.out.flush();
                            System.out.println("Conexão estabelecida");
                            return testSocket(host, port);
                        }else{
                            logController.writeSimpleLog("CLIENT: HOST", "Host ou porta inválidos", true);
                            System.out.println("Host ou porta inválidos");
                            console = 10;
                        }
                    }else{
                        if (console == 2) {
                            Socket socket = testSocket("localhost", 22222);
                            if (socket != null) {
                                logController.writeSimpleLog("CLIENT: HOST", "Conexão estabelecida", true);
                                System.out.print("\033[H\033[2J"); // Código ANSI para limpar tela
                                System.out.flush();
                                System.out.println("Conexão estabelecida");
                                return socket;
                            }else{
                                logController.writeSimpleLog("CLIENT: HOST", "Host ou porta inválidos", true);
                                System.out.println("Host ou porta inválidos");
                                console = 10;
                            }
                        }
                    }
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
            System.out.print("\033[H\033[2J"); // Código ANSI para limpar tela
            System.out.flush();
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
            System.out.print("\033[H\033[2J"); // Código ANSI para limpar tela
            System.out.flush();
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

    public void sair(){}

}