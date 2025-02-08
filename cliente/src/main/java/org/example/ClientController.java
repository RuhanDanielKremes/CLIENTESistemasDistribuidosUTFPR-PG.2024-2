package org.example;

import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.SwingUtilities;

import org.example.controller.LogController;
import org.example.model.Json;
import org.example.model.JsonResponse;
import org.example.model.User;
import org.example.view.Init;

import java.io.*; 

import com.google.gson.Gson;


public class ClientController extends Thread{
    public static void main(String[] args) throws IOException {

        SwingUtilities.invokeLater(() -> new Init());

        try (FileWriter writer = new FileWriter("log.txt")) {
            writer.write("");
        }  

        LogController logController = new LogController();
        boolean sair = false;
        List<Socket> sockets = new ArrayList<Socket>();
        OperationController operationController = new OperationController();
        int operacao = 0;
        int socketNumber = -1;
        String token = "";
        String inputString = "";
        Scanner scanner = new Scanner(System.in);

        ClearConsole.clearConsole();
        while(sair == false){
            while (true) {
                System.out.println("[1] Cadastrar socket\n[2] Utilizar socket\n[0] Sair");
                inputString = scanner.nextLine().trim();
                if (inputString.isEmpty()) {
                    System.out.println("Opção inválida");
                    continue;
                }
                try {
                    operacao = Integer.parseInt(inputString);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Opção inválida");
                }
            }
            switch (operacao) {
                case 1: 
                    ClearConsole.clearConsole();
                    Socket newSocket = operationController.cadastrarSocket(scanner); 
                    if (newSocket != null) {
                        logController.writeSimpleLog("CLIENT: HOST", "Socket cadastrado", true);
                        sockets.add(newSocket);
                    }
                    break;
                case 2:  
                    logController.writeSimpleLog("CLIENT: HOST", "Listando sockets disponiveis", true);
                    ClearConsole.clearConsole();
                    int i = 0;
                    boolean isnull = false;
                    for (Socket socket : sockets) {
                        if (socket != null) {
                            ClearConsole.clearConsole();
                            System.out.println("[" + i + "] " + socket);
                            logController.writeSimpleLog("CLIENT: HOST", "[" + i + "] " + socket, true);
                            i++;
                        }else{
                            for (int j = 0; j < sockets.size(); j++) {
                                if (sockets.get(j) == null) {
                                    logController.writeSimpleLog("CLIENT: HOST", "Removendo socket " + j + "estado null", true);
                                    sockets.remove(j);
                                }
                            }
                        }
                    }
                    if (sockets.size() == 0) {
                        isnull = true;
                        logController.writeSimpleLog("CLIENT: HOST", "Nenhum socket cadastrado", true);
                    }
                    if (!isnull) {
                        logController.writeSimpleLog("CLIENT: HOST", "Solicitando escolha de socket", true);
                        while (true) {
                            System.out.println("Digite o número do socket que deseja utilizar");
                            inputString = scanner.nextLine().trim();
                            if (inputString.isEmpty()) {
                                System.out.println("Opção inválida");
                                continue;
                            }
                            try {
                                socketNumber = Integer.parseInt(inputString);
                                if (socketNumber < 0 && socketNumber > sockets.size()){
                                    ClearConsole.clearConsole();
                                    System.out.println("Opção inválida");
                                }else{
                                    break;
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Opção inválida");
                            }
                        }
                        logController.writeSimpleLog("CLIENT: HOST", "Socket escolhido: " + socketNumber, true);
                        if(socketNumber >= 0 && socketNumber < sockets.size()){
                            User user = new User();
                            logController.writeSimpleLog("CLIENT: HOST", "Conectando ao socket", true);
                            BufferedReader reader = new BufferedReader(new InputStreamReader(sockets.get(socketNumber).getInputStream()));
                            PrintWriter writer = new PrintWriter(sockets.get(socketNumber).getOutputStream(), true);
                            Gson gson = new Gson();
                            boolean sair2 = false;
                            // testserver(reader, writer);
                            ClearConsole.clearConsole();
                            while (sair2 == false) {
                                logController.writeSimpleLog("CLIENT: HOST", "Solicitando operação", true);
                                int operation;
                                while (true) {
                                    if (user.getToken() == null || user.getToken().isEmpty()) {
                                        System.out.println("[1] Cadastrar usuário\n[2] Login\n[0] Sair");
                                    }else{
                                        System.out.println("[1] Logout\n[2] Opções do Usuario\n[3] Opções das Categorias\n[4] Opções dos avisos\n[0] Sair");
                                    }
                                    inputString = scanner.nextLine().trim();
                                    if (inputString.isEmpty()) {
                                        System.out.println("Opção inválida");
                                        continue;
                                    }
                                    try {
                                        operation = Integer.parseInt(inputString);
                                        break;
                                    } catch (NumberFormatException e) {
                                        ClearConsole.clearConsole();
                                        System.out.println("Opção inválida");
                                    }
                                }
                                String operationString = "";
                                switch (operation) {
                                    case 1: operationString = user.getToken() != null && !user.getToken().isEmpty() ? "logout" : "cadastrarUsuario";
                                        break;
                                    case 2: 
                                        if (user.getToken() == null || user.getToken().isEmpty()) {
                                            operationString = "login";
                                        }else{
                                            while (true) {
                                                ClearConsole.clearConsole();
                                                if (user.getToken().equals("2524198")) {
                                                    System.out.println("[1] Listar Informações do Usuario\n[2] Listar todos os Usuarios\n[2] Editar Informações do Usuario\n[4] Excluir seu Usuario\n[0] Sair");
                                                    
                                                }else{
                                                    System.out.println("[1] Listar Informações do Usuario\n[2] Editar suas Informações\n[3] Excluir seu Usuario\n[0] Sair");
                                                }
                                                inputString = scanner.nextLine().trim();
                                                if (inputString.isEmpty()) {
                                                    System.out.println("Opção inválida");
                                                    continue;
                                                }
                                                try {
                                                    operation = Integer.parseInt(inputString);
                                                    break;
                                                } catch (NumberFormatException e) {
                                                    ClearConsole.clearConsole();
                                                    System.out.println("Opção inválida");
                                                }
                                            }
                                            switch (operation) {
                                                case 1:
                                                    operationString = "localizarUsuario";
                                                    break;
                                                case 2:
                                                    if (user.getToken().equals("2524198")) {
                                                        operationString = "listarUsuarios";

                                                    }else{
                                                        operationString = "editarUsuario";
                                                    }
                                                    break;
                                                case 3:
                                                    if (user.getToken().equals("2524198")) {
                                                        operationString = "editarUsuario";
                                                    }else{
                                                        operationString = "excluirUsuario";
                                                    }
                                                    break;
                                                case 4:
                                                    if (user.getToken().equals("2524198")) {
                                                        operationString = "excluirUsuario";
                                                    }else{
                                                        ClearConsole.clearConsole();
                                                        System.out.println("Opção inválida");
                                                    }
                                                    break;
                                                case 0:
                                                    operation = -1;
                                                    ClearConsole.clearConsole();
                                                    break;
                                                default:
                                                    ClearConsole.clearConsole();
                                                    System.out.println("Opção inválida");
                                                    break;
                                            }
                                        }
                                        break;
                                    case 3: 
                                        ClearConsole.clearConsole();
                                        if (user.getToken() != null && !user.getToken().isEmpty()) {
                                            while (true) {
                                                if (user.getToken().equals("2524198")) {
                                                    System.out.println("[1] Listar categorias\n[2] Localizar categoria\n[3] Listar categorias cadastradas\n[4] Cadastrar na categoria\n[5] Descadastrar na categoria\n"+
                                                                        "[6] Criar categoria\n[7] Editar Categoria\n[8] Excluir Categoria\n[0] Sair");
                                                }else{
                                                    System.out.println("[1] Listar categorias\n[2] Localizar categoria\n[3] Listar suas categorias\n[4] Cadastrarse na categoria\n[5] Descadastrarse na categoria\n[0] Sair");
                                                }
                                                inputString = scanner.nextLine().trim();
                                                if (inputString.isEmpty()) {
                                                    System.out.println("Opção inválida");
                                                    continue;
                                                }
                                                try {
                                                    operation = Integer.parseInt(inputString);
                                                    break;
                                                } catch (NumberFormatException e) {
                                                    ClearConsole.clearConsole();
                                                    System.out.println("Opção inválida");
                                                }
                                            }
                                            switch (operation) {
                                                case 1:
                                                    operationString = "listarCategorias";
                                                    break;
                                                case 2:
                                                    operationString = "localizarCategoria";
                                                    break;
                                                case 3:
                                                    operationString = "listarUsuarioCategorias";
                                                    break;
                                                case 4:
                                                    operationString = "cadastrarUsuarioCategoria";
                                                    break;
                                                case 5:
                                                    operationString = "descadastrarUsuarioCategoria";
                                                    break;
                                                case 6:
                                                    if (user.getToken().equals("2524198")) {
                                                        operationString = "criarCategoria";
                                                    }else{
                                                        ClearConsole.clearConsole();
                                                        System.out.println("Opção inválida");
                                                    }
                                                    break;
                                                case 7:
                                                    if (user.getToken().equals("2524198")) {
                                                        operationString = "editarCategoria";
                                                    }else{
                                                        ClearConsole.clearConsole();
                                                        System.out.println("Opção inválida");
                                                    }
                                                    break;
                                                case 8:
                                                    if (user.getToken().equals("2524198")) {
                                                        operationString = "excluirCategoria";
                                                    }else{
                                                        ClearConsole.clearConsole();
                                                        System.out.println("Opção inválida");
                                                    }
                                                    break;
                                                case 0:
                                                    operation = -1;
                                                    ClearConsole.clearConsole();
                                                    break;
                                                default:
                                                    break;
                                            }
                                        }
                                        break;
                                    case 4: 
                                        ClearConsole.clearConsole();
                                        if(user.getToken() != null && !user.getToken().isEmpty()){
                                            while (true) {
                                                if (user.getToken().equals("2524198")) {
                                                    System.out.println("[1] Listar Avisos\n[2] Localizar Aviso\n[3] Listar Avisos cadastrados\n[4] Cadastrar Aviso\n[5] Editar Aviso\n[6] Excluir Aviso\n[0] Sair");
                                                } else {
                                                    System.out.println("[1] Listar seus Avisos\n[0] Sair");
                                                }
                                                inputString = scanner.nextLine().trim();
                                                if (inputString.isEmpty()) {
                                                    System.out.println("Opção inválida");
                                                    continue;
                                                }
                                                try {
                                                    operation = Integer.parseInt(inputString);
                                                    break;
                                                } catch (NumberFormatException e) {
                                                    ClearConsole.clearConsole();
                                                    System.out.println("Opção inválida");
                                                }
                                            }
                                            switch (operation) {
                                                case 1:
                                                    if (user.getToken().equals("2524198")) {
                                                        operationString = "listarAvisos";
                                                    }else{
                                                        operationString = "listarUsuarioAvisos";
                                                    }
                                                    break;
                                                case 2:
                                                    if (user.getToken().equals("2524198")) {
                                                        operationString = "localizarAviso";
                                                    } else {
                                                        ClearConsole.clearConsole();
                                                        System.out.println("Opção inválida");
                                                    }
                                                    break;
                                                case 3:
                                                    if (user.getToken().equals("2524198")) {
                                                        operationString = "listarUsuarioAvisos";
                                                    } else {
                                                        ClearConsole.clearConsole();
                                                        System.out.println("Opção inválida");
                                                    }
                                                    break;
                                                case 4:
                                                    if (user.getToken().equals("2524198")) {
                                                        operationString = "criarAviso";
                                                    } else {
                                                        ClearConsole.clearConsole();
                                                        System.out.println("Opção inválida");
                                                    }
                                                    break;
                                                case 5:
                                                    if (user.getToken().equals("2524198")) {
                                                        operationString = "editarAviso";
                                                    } else {
                                                        ClearConsole.clearConsole();
                                                        System.out.println("Opção inválida");
                                                    }
                                                    break;
                                                case 6:
                                                    System.out.println(user.getToken());
                                                    if (user.getToken().equals("2524198")) {
                                                        operationString = "excluirAviso";
                                                        System.out.println("true");
                                                    } else {
                                                        ClearConsole.clearConsole();
                                                        System.out.println("Opção inválida");
                                                        System.out.println("false");
                                                    }
                                                    System.out.println("operationString: " + operationString);
                                                    break;
                                                case 0:
                                                    operation = -1;
                                                    ClearConsole.clearConsole();
                                                    break;
                                                default:
                                                    ClearConsole.clearConsole();
                                                    System.out.println("Opção inválida");
                                                    break;
                                            }
                                        }
                                        break;
                                    case 0: operationString = "Sair";
                                        break;
                                    default: 
                                        ClearConsole.clearConsole();
                                        System.out.println("Opção inválida");
                                        break;
                                }
                                logController.writeSimpleLog("CLIENT HOST", "operation:" + operation, true);
                                logController.writeSimpleLog("CLIENT: HOST", "Operação escolhida: " + operationString, true);
                                if (operation != 0) {
                                    logController.writeSimpleLog("CLIENT: HOST", "Operação escolhida válida: " + operationString, true);
                                    Json json = null;
                                    if (operationString == "logout") {
                                        if(token != null && !token.isEmpty()){
                                            user.setToken(token);
                                            json = operationController.sitchOperation(operationString, user);
                                        }else{
                                            ClearConsole.clearConsole();
                                            System.out.println("Token não encontrado");
                                        }
                                    }else{
                                        json = operationController.sitchOperation(operationString, user);
                                    }
                                    // String jsonString = gson.toJson(json);
                                    // String jsonString = json.toJson(false);
                                    try (FileWriter filewriter = new FileWriter("output.json", true)) {
                                        String jsonString = json.toString();
                                        filewriter.write(jsonString);
                                        if (jsonString == null || jsonString.isEmpty() || jsonString.equals("{}") || jsonString.equals("null")) {
                                            logController.writeLogJson(operationString, inputString, jsonString);
                                            System.out.println("JSON nulo");
                                        }else{
                                            logController.writeSimpleLog("CLIENT: HOST", "enviando JSON: " + jsonString, true);
                                            writer.println(jsonString);
                                            logController.writeSimpleLog("CLIENT: HOST", "JSON enviado", true);
                                            ClearConsole.clearConsole();
                                            System.out.println("JSON enviado: " + jsonString);
                                            logController.writeSimpleLog("SERVER: RESPONSE", "recebida resposta do servidor", true);
                                            String serverResponse = reader.readLine();
                                            // System.out.println("serverResponse" + serverResponse);
                                            JsonResponse jsonResponse = gson.fromJson(serverResponse, JsonResponse.class);
                                            if (jsonResponse.getStatus() == 200) {
                                                if (jsonResponse.getOperacao().equals("login")) {
                                                    user.setToken(jsonResponse.getToken());
                                                    logController.writeSimpleLog("CLIENT: HOST", "Token recebido: " + jsonResponse.getToken(), true);
                                                }
                                            }
                                            logController.writeLogJson("SERVER: RESPONSE", "lendo resposta do servidor", jsonString);
                                            System.out.println("JSON recebido: " + jsonResponse.toString());
                                            if(jsonResponse.getOperacao().equals("logout")){
                                                logController.writeSimpleLog("CLIENT: HOST", "Desconectando", true);
                                                sair2 = true;
                                            }
                                        }
                                    } catch (IOException e) {
                                       e.printStackTrace();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }else{
                                    if (operation == 0) {
                                        logController.writeSimpleLog("CLIENT: HOST", "Saindo", true);
                                        while (true) {
                                            System.out.println("Deseja desconectar do socket? [S/N]");
                                            inputString = scanner.nextLine().trim().toUpperCase();
                                            if (inputString.isEmpty()) {
                                                System.out.println("Opção inválida");
                                                continue;
                                            }
                                            if (inputString.equals("S")) {
                                                logController.writeSimpleLog("CLIENT: HOST", "Desconectando do socket", true);
                                                sockets.get(socketNumber).close();
                                                sair2 = true;
                                                logController.writeSimpleLog("CLIENT: HOST", "Socket removido da lista", true);
                                                sockets.remove(socketNumber);
                                                break;
                                            }else{
                                                if (inputString.equals("N")) {
                                                    logController.writeSimpleLog("CLIENT: HOST", "Permanecendo conectado", true);
                                                    sair2 = true;
                                                    break;
                                                }else{
                                                    System.out.println("Opção inválida");
                                                }
                                            }
                                        }
                                        sair2 = true;
                                    }
                                }
                            }
                        }
                    }else{
                        ClearConsole.clearConsole();
                        System.out.println("Nenhum socket cadastrado");
                    }
                    break;
                case 0: ClearConsole.clearConsole();
                    sair = true;
                    break;
                default:
                    ClearConsole.clearConsole();
                    System.out.println("Opção inválida");
                    break;
            }
        }
        if(socketNumber >= 0 && socketNumber < sockets.size()){
            sockets.get(socketNumber).close();
        }
        scanner.close();
    }

    public static void testserver(BufferedReader reader, PrintWriter writer) throws IOException {
        String json2 = "{\"operacao\": \"cadastrarUsuario\", \"ra\": \"1234567\", \"senha\": \"essaehasenha\", \"nome\": \"FULANO DA SILVA\"}";
        String json1 = "{\"operacao\": \"login\", \"ra\": \"1234567\", \"senha\": \"essaehasenha\"}";
        // String json3 = "{\"operacao\": \"listarUsuarios\", \"token\":\"1234567\"}";
        String json3 = "{\"operacao\": \"listarUsuarios\", \"token\":\"2524198\"}";
        String json4 = "{\"operacao\": \"localizarUsuario\", \"token\":\"1234567\", \"ra\": \"1234567\"}";
        String json6 = "{\"operacao\": \"editarUsuario\", \"token\":\"1234567\", \"usuario\": {\"ra\": \"1234567\", \"senha\": \"essaehasenha\", \"nome\": \"SILVA FULANO\"}}";

        // CRUD Categoria
        String salvarCategoria = "{ \"operacao\": \"salvarCategoria\", \"token\": \"2524198\", \"categoria\": { \"id\": 0, \"nome\": \"NOME DA CATEGORIA\" } }";
        String listarCategorias = "{ \"operacao\": \"listarCategorias\", \"token\": \"2524198\" }";
        String localizarCategoria = "{ \"operacao\": \"localizarCategoria\", \"token\": \"2524198\", \"id\": 1 }";

        String salvarAviso = "{ \"operacao\": \"salvarAviso\", \"token\": \"2524198\", \"aviso\": { \"id\": 0, \"categoria\": 1, \"titulo\": \"Título breve\", \"descricao\": \"...\" } }";
        String listarAvisos = "{ \"operacao\": \"listarAvisos\", \"token\": \"2524198\", \"categoria\": 1 }";
        String localizarAviso = "{ \"operacao\": \"localizarAviso\", \"token\": \"2524198\", \"id\": 1 }";

        String json15 = "{\"operacao\": \"cadastrarUsuarioCategoria\", \"token\":\"1234567\", \"ra\": \"1234567\", \"categoria\": 1}";
        String json16 = "{\"operacao\": \"listarUsuarioCategorias\", \"token\":\"1234567\", \"ra\": \"1234567\"}";
        String json18 = "{\"operacao\": \"listarUsuarioAvisos\", \"token\":\"1234567\", \"ra\": \"1234567\"}";
        String json17 = "{\"operacao\": \"descadastrarUsuarioCategoria\", \"token\":\"1234567\", \"ra\": \"1234567\", \"categoria\": 1}";

        String excluirAviso = "{ \"operacao\": \"excluirAviso\", \"token\": \"2524198\", \"id\": 1 }";

        String excluirCategoria = "{ \"operacao\": \"excluirCategoria\", \"token\": \"2524198\", \"id\": 1 }";

        String json19 = "{\"operacao\": \"logout\", \"token\":\"1234567\"}";
        String json5 = "{\"operacao\": \"excluirUsuario\", \"token\":\"1234567\", \"ra\": \"1234567\"}";
        // String[] jsonArray = {json1, json2, json3, json4, json5, json6, json7, json8, json9, json10, json11, json12, json13, json14, json15, json16, json17, json18, json19};
        // String[] jsonArray = {salvarCategoria, listarCategorias, localizarCategoria, excluirCategoria, salvarAviso, listarAvisos, localizarAviso, excluirAviso};
        String[] jsonArray = { json2, json1, json3, json4, json6, salvarCategoria, listarCategorias, localizarCategoria,
                salvarAviso, listarAvisos, localizarAviso, json15, json16, json18, json17, excluirAviso,
                excluirCategoria, json19, json5 };

        for (String json : jsonArray) {
            writer.println(json);
            String serverResponse = reader.readLine();
            System.out.println("JSON enviado: " + json);
            System.out.println("JSON recebido: " + serverResponse);
        }

        // writer.println(json1);
        // System.out.println(reader.readLine());

        System.in.read();
    }
}