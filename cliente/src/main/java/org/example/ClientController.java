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
                            ClearConsole.clearConsole();
                            while (sair2 == false) {
                                logController.writeSimpleLog("CLIENT: HOST", "Solicitando operação", true);
                                int operation;
                                while (true) {
                                    System.out.println("[1] Cadastrar usuário\n[2] Login\n[3] Logout\n[4] Listar Categorias\n[0] Sair");
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
                                    case 1: operationString = "cadastrarUsuario";
                                        break;
                                    case 2: operationString = "login";
                                        break;
                                    case 3: operationString = "logout";
                                        break;
                                    case 4: operationString = "listarCategorias";
                                        break;
                                    case 0: operationString = "Sair";
                                        break;
                                    default: 
                                        ClearConsole.clearConsole();
                                        System.out.println("Opção inválida");
                                        break;
                                }
                                if (operation >= 1 && operation <= 4) {
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
                                    String jsonString = gson.toJson(json);
                                    try (FileWriter filewriter = new FileWriter("output.json", true)) {
                                        filewriter.write(jsonString);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
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
                                            token = jsonResponse.getToken();
                                            user.setToken(token);
                                            logController.writeSimpleLog("CLIENT: HOST", "Token recebido: " + token, true);
                                        }
                                        logController.writeLogJson("SERVER: RESPONSE", "lendo resposta do servidor", jsonString);
                                        System.out.println("JSON recebido: " + jsonResponse.toString());
                                        if(jsonResponse.getOperacao().equals("logout")){
                                            logController.writeSimpleLog("CLIENT: HOST", "Desconectando", true);
                                            sair2 = true;
                                        }
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
}