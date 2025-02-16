package org.example.view;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.example.OperationController;
import org.example.model.Json;
import org.example.model.JsonResponse;
import org.example.model.User;

import com.google.gson.Gson;


public class UserView extends JFrame {
    
    public UserView(Socket socket, BufferedReader in, PrintWriter out, User user) {
        {
            setTitle("User");
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            panel.add(Box.createVerticalGlue());

            JButton listUserInformationButton = new JButton("Listar Informações do Usuário");
            listUserInformationButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            JButton listAllUsersInformationButton = new JButton("Listar Todos os Usuários");
            listAllUsersInformationButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            JButton deleteUser = new JButton("Deletar Usuário");
            deleteUser.setAlignmentX(Component.CENTER_ALIGNMENT);
            deleteUser.addActionListener(_ -> {
                new UserDeleteView(socket, in, out, user);
                dispose();
            });
            JButton editarUser = new JButton("Editar Usuário");
            editarUser.setAlignmentX(Component.CENTER_ALIGNMENT);
            editarUser.addActionListener(_ -> {
                new UserEditView(socket, in, out, user);
                dispose();
            });
            JButton back = new JButton("Voltar");
            back.setAlignmentX(Component.CENTER_ALIGNMENT);
            back.addActionListener(_ -> {
                new Option(socket, in, out, user);
                dispose();
            });
            listUserInformationButton.addActionListener(_ -> {
                OperationController operationController = new OperationController();
                try {
                    Json json = operationController.localizarUsuario(user);
                    System.out.println("Client: " + json.toString());
                    out.println(json.toString());
                    out.flush();
                    String response = in.readLine();
                    System.out.println("Client: " + response);
                    Gson gson = new Gson();
                    JsonResponse jsonResponse = gson.fromJson(response, JsonResponse.class);
                    if (jsonResponse.getStatus() == 201) {
                        List<User> users = jsonResponse.getUsuarios();
                        new UserListView(socket, in, out, user, users);
                        dispose();
                    }else{
                        JOptionPane.showMessageDialog(null, jsonResponse.getMensagem());
                    }
                } catch (IOException ex) {
                    // ex.printStackTrace();
                    System.out.println("Erro ao listar usuários" + ex.getMessage());
                }
            });
            listAllUsersInformationButton.addActionListener(_ -> {
                OperationController operationController = new OperationController();
                try {
                    Json json = operationController.listarUsuarios(user);
                    System.out.println("Client: " + json.toString());
                    out.println(json.toString());
                    out.flush();
                    String response = in.readLine();
                    System.out.println("Client: " + response);
                    Gson gson = new Gson();
                    JsonResponse jsonResponse = gson.fromJson(response, JsonResponse.class);
                    if (jsonResponse.getStatus() == 201) {
                        List<User> users = jsonResponse.getUsuarios();
                        new UserListView(socket, in, out, user, users);
                        dispose();
                    }else{
                        JOptionPane.showMessageDialog(null, jsonResponse.getMensagem());
                    }
                } catch (IOException ex) {
                    // ex.printStackTrace();
                    System.out.println("Erro ao listar usuários" + ex.getMessage());
                }
            });
            panel.add(listUserInformationButton);
            panel.add(listAllUsersInformationButton);
            panel.add(deleteUser);
            panel.add(editarUser);
            panel.add(back);

            panel.add(Box.createVerticalGlue());
            
            add(panel);
            
            setVisible(true);
        }
    }

}
