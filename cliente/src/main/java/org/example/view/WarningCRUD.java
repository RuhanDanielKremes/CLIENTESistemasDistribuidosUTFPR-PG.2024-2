package org.example.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import org.example.model.Category;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.example.OperationController;
import org.example.model.Avisos;
import org.example.model.Json;
import org.example.model.JsonResponse;
import org.example.model.JsonResponseCategoryResponse2;
import org.example.model.JsonResponseWarning;
import org.example.model.User;

import com.google.gson.Gson;

public class WarningCRUD extends JFrame{

    public WarningCRUD(Socket socket, BufferedReader in, PrintWriter out, User user, String operacao) {
        setTitle("Warnings");
        setSize(400,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    
    
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(Box.createVerticalGlue());

        JLabel idLabel = new JLabel("ID");
        JTextField id = new JTextField(7);
        id.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton subscribe = new JButton("Inscrever-se");
        subscribe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    Json json = new OperationController().cadastrarUsuarioCategoria(user, Integer.parseInt((id.getText())));
                    System.out.println("Client: " + json.toString());
                    out.println(json.toString());
                    out.flush();
                    String response = in.readLine();
                    System.out.println("Server: " + response);
                    JsonResponse jsonResponse = new Gson().fromJson(response, JsonResponse.class);
                    JOptionPane.showMessageDialog(null, jsonResponse.getMensagem());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao se inscrever\n" + ex.getMessage());
                }
            }
        });
        subscribe.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton unsubscribe = new JButton("Desinscrever-se");
        unsubscribe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    Json json = new OperationController().descadastrarUsuarioCategoria(user, Integer.parseInt((id.getText())));
                    System.out.println("Client: " + json.toString());
                    out.println(json.toString());
                    out.flush();
                    String response = in.readLine();
                    System.out.println("Server: " + response);
                    JsonResponse jsonResponse = new Gson().fromJson(response, JsonResponse.class);
                    JOptionPane.showMessageDialog(null, jsonResponse.getMensagem());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao se desinscrever\n" + ex.getMessage());
                }
            }
        });
        unsubscribe.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton delete = new JButton("Deletar");
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    Json json = new OperationController().excluirAviso(user, Integer.parseInt((id.getText())));
                    System.out.println("Client: " + json.toString());
                    out.println(json.toString());
                    out.flush();
                    String response = in.readLine();
                    System.out.println("Server: " + response);
                    JsonResponse jsonResponse = new Gson().fromJson(response, JsonResponse.class);
                    JOptionPane.showMessageDialog(null, jsonResponse.getMensagem());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao deletar aviso\n" + ex.getMessage());
                }
            }
        });
        delete.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton findCategory = new JButton("Localizar Categoria");
        findCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    Json json = new OperationController().localizarCategoria(user, Integer.parseInt((id.getText())));
                    System.out.println("Client: " + json.toString());
                    out.println(json.toString());
                    out.flush();
                    String response = in.readLine();
                    System.out.println("Server: " + response);
                    JsonResponseCategoryResponse2 jsonResponse = new Gson().fromJson(response, JsonResponseCategoryResponse2.class);
                    // JOptionPane.showMessageDialog(null, jsonResponse.getMensagem());
                    List<Category> categorias = new ArrayList<>();
                    categorias.add(jsonResponse.getCategory());
                    new WarningListView(socket, in, out, user, null, categorias);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao localizar categoria\n" + ex.getMessage());
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, "Erro ao localizar categoria\n" + e1.getMessage());
                }
            }
        });
        findCategory.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton deleteCategory = new JButton("Deletar Categoria");
        deleteCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    Json json = new OperationController().excluirCategoria(user, Integer.parseInt((id.getText())));
                    System.out.println("Client: " + json.toString());
                    out.println(json.toString());
                    out.flush();
                    String response = in.readLine();
                    System.out.println("Server: " + response);
                    JsonResponse jsonResponse = new Gson().fromJson(response, JsonResponse.class);
                    JOptionPane.showMessageDialog(null, jsonResponse.getMensagem());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao deletar categoria\n" + ex.getMessage());
                }
            }
        });
        deleteCategory.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton findWarning = new JButton("Procurar Aviso");
        findWarning.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    Json json = new OperationController().localizarAviso(user, Integer.parseInt((id.getText())));
                    System.out.println("Client: " + json.toString());
                    out.println(json.toString());
                    out.flush();
                    String response = in.readLine();
                    System.out.println("Server: " + response);
                    JsonResponseWarning jsonResponse = new Gson().fromJson(response, JsonResponseWarning.class);
                    List<Avisos> avisos = new ArrayList<>();
                    avisos.add(jsonResponse.getWarning());
                    new WarningListView(socket, in, out, user, avisos, null);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao localizar aviso\n" + ex.getMessage());
                }
            }
        });
        findWarning.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton deleteWarning = new JButton("Deletar Aviso");
        deleteWarning.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    Json json = new OperationController().excluirAviso(user, Integer.parseInt((id.getText())));
                    System.out.println("Client: " + json.toString());
                    out.println(json.toString());
                    out.flush();
                    String response = in.readLine();
                    System.out.println("Server: " + response);
                    JsonResponse jsonResponse = new Gson().fromJson(response, JsonResponse.class);
                    JOptionPane.showMessageDialog(null, jsonResponse.getMensagem());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao deletar aviso\n" + ex.getMessage());
                }
            }
        });
        deleteWarning.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton back = new JButton("Voltar");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                new Option(socket, in, out, user);
                dispose();
            }
        });
        back.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(idLabel);
        panel.add(id);
        switch (operacao) {
            case "subscribe":
                panel.add(subscribe);            
                break;
            case "unsubscribe":
                panel.add(unsubscribe);
                break;
            case "delete":
                panel.add(delete);
                break;
            case "findCategory":
                panel.add(findCategory);
                break;
            case "deleteCategory":
                panel.add(deleteCategory);
                break;
            case "findWarning":
                panel.add(findWarning);
                break;
            case "deleteWarning":
                panel.add(deleteWarning);
                break;
            default:
                break;
        }
        panel.add(back);

        add(panel);

        setVisible(true);
    }

}
