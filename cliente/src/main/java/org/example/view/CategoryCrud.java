package org.example.view;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.example.OperationController;
import org.example.model.Json;
import org.example.model.JsonResponse;
import org.example.model.User;

import com.google.gson.Gson;

public class CategoryCrud extends JFrame {
    
    public CategoryCrud(Socket socket, BufferedReader in, PrintWriter out, User user, String operacao) {
        setTitle("Category CRUD");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(Box.createVerticalGlue());

        JLabel catLabel = new JLabel("Categoria");
        catLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel idCatLabel = new JLabel("Id");
        JTextField idCat = new JTextField(3);
        idCat.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel nameCatLabel = new JLabel("Nome");
        JTextField nameCat = new JTextField(10);
        nameCat.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel AvisoLabel = new JLabel("Aviso");
        AvisoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel idLabel = new JLabel("ID");
        JTextField id = new JTextField(3);
        id.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel tituloLabel = new JLabel("Título");
        JTextField titulo = new JTextField(10);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel descricaoLabel = new JLabel("Descrição");
        JTextField descricao = new JTextField(10);
        descricao.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton createCat = new JButton("Criar Categoria");
        createCat.addActionListener(_ -> {
            if (nameCat.getText().equals("")) {
                new JOptionPane("Nome da categoria não pode ser vazio");
            } else {
                try {
                    Json json = new OperationController().criarCategoria(user, nameCat.getText());
                    System.out.println("Client: " + json.toString());
                    out.println(json.toString());
                    out.flush();
                    String response = in.readLine();
                    System.out.println("Server: " + response);
                    JsonResponse jsonResponse = new Gson().fromJson(response, JsonResponse.class);
                    JOptionPane.showMessageDialog(null, jsonResponse.getMensagem());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao criar categoria\n" + ex.getMessage());
                }
            }
        });
        createCat.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton editCat = new JButton("Editar Categoria");
        editCat.addActionListener(_ -> {
            if (nameCat.getText().equals("") && idCat.getText().equals("")) {
                new JOptionPane("Nome da categoria não pode ser vazio");
            } else {
                try {
                    Json json = new OperationController().editarCategoria(user, Integer.parseInt(idCat.getText()), nameCat.getText());
                    System.out.println("Client: " + json.toString());
                    out.println(json.toString());
                    out.flush();
                    String response = in.readLine();
                    System.out.println("Server: " + response);
                    JsonResponse jsonResponse = new Gson().fromJson(response, JsonResponse.class);
                    JOptionPane.showMessageDialog(null, jsonResponse.getMensagem());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao editar categoria\n" + ex.getMessage());
                }
            }
        });
        editCat.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton create = new JButton("Criar");
        create.addActionListener(_ -> {
            if (idCat.getText().equals("") && titulo.getText().equals("") || descricao.getText().equals("")) {
                new JOptionPane("Título, descrição e id da categoria não podem ser vazios");
            } else {
                try {
                    Json json = new OperationController().criarAviso(user, titulo.getText(), descricao.getText(), Integer.parseInt(id.getText()));
                    System.out.println("Client: " + json.toString());
                    out.println(json.toString());
                    out.flush();
                    String response = in.readLine();
                    System.out.println("Server: " + response);
                    JsonResponse jsonResponse = new Gson().fromJson(response, JsonResponse.class);
                    JOptionPane.showMessageDialog(null, jsonResponse.getMensagem());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao criar aviso\n" + ex.getMessage());
                }
            }
        });
        create.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton edit = new JButton("Editar");
        edit.addActionListener(_ -> {
            if (id.getText().equals("") && idCat.getText().equals("") && titulo.getText().equals("") || descricao.getText().equals("")) {
                new JOptionPane("Título, descrição, id do aviso e id da categoria não podem ser vazios");
            } else {
                try {
                    Json json = new OperationController().editarAviso(user, Integer.parseInt(id.getText()), titulo.getText(), descricao.getText(), Integer.parseInt(idCat.getText()));
                    System.out.println("Client: " + json.toString());
                    out.println(json.toString());
                    out.flush();
                    String response = in.readLine();
                    System.out.println("Server: " + response);
                    JsonResponse jsonResponse = new Gson().fromJson(response, JsonResponse.class);
                    JOptionPane.showMessageDialog(null, jsonResponse.getMensagem());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao editar aviso\n" + ex.getMessage());
                }
            }
        });
        edit.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton back = new JButton("Voltar");
        back.addActionListener(_ -> {
            new Option(socket, in, out, user);
            dispose();
        });
        back.setAlignmentX(Component.CENTER_ALIGNMENT);

        switch (operacao) {
            case "createCategory":
                panel.add(catLabel);
                panel.add(nameCatLabel);
                panel.add(nameCat);
                panel.add(createCat);
                break;
            case "editCategory":
                panel.add(catLabel);
                panel.add(idCatLabel);
                panel.add(idCat);
                panel.add(nameCatLabel);
                panel.add(nameCat);
                panel.add(editCat);
                break;
            case "createWarning":
                panel.add(catLabel);
                panel.add(idCatLabel);
                panel.add(idCat);
                panel.add(AvisoLabel);
                panel.add(tituloLabel);
                panel.add(titulo);
                panel.add(descricaoLabel);
                panel.add(descricao);
                panel.add(create);
                break;
            case "editWarning":
                panel.add(catLabel);
                panel.add(idCatLabel);
                panel.add(idCat);
                panel.add(AvisoLabel);
                panel.add(idLabel);
                panel.add(id);
                panel.add(tituloLabel);
                panel.add(titulo);
                panel.add(descricaoLabel);
                panel.add(descricao);
                panel.add(edit);
                break;
            default:
                break;
        }

        panel.add(back);

        add(panel);

        setVisible(true);

    }

}
