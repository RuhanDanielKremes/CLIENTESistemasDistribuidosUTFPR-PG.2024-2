package org.example.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
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
import org.example.model.JsonResponseCategoryResponse;
import org.example.model.User;
import org.example.model.Avisos;
import org.example.model.Category;

import com.google.gson.Gson;

public class CategoryView extends JFrame{
    
    public CategoryView(Socket socket, BufferedReader in, PrintWriter out, User user) {
        setTitle("Categories");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(Box.createVerticalGlue());
        JButton CriarCategoria = new JButton("Criar Categoria");
        CriarCategoria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                new CategoryCrud(socket, in, out, user, "createCategory");
                dispose();
            }
        });
        CriarCategoria.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton listarCategoria = new JButton("Listar Categorias");
        listarCategoria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                OperationController operationController = new OperationController();
                try {
                    Json json = operationController.listarCategorias(user);
                    System.out.println("Client: " + json.toString());
                    out.println(json.toString());
                    out.flush();
                    String response = in.readLine();
                    System.out.println("Server: " + response);
                    Gson gson = new Gson();
                    JsonResponse jsonResponse = gson.fromJson(response, JsonResponse.class);
                    List<Category> categories = new ArrayList<>();
                    for (int i = 0; i < jsonResponse.getCategorias().size(); i++) {
                        categories.add(jsonResponse.getCategorias().get(i));
                    }
                    new WarningListView(socket, in, out, user, null, categories);
                    dispose();
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, "Erro ao listar categorias" + e1.getMessage());
                }
            }
        });
        listarCategoria.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton localizarCategoria = new JButton("Localizar Categoria");
        localizarCategoria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                new WarningCRUD(socket, in, out, user, "findCategory");
                dispose();
            }
        });
        localizarCategoria.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton excluirCategoria = new JButton("Excluir Categoria");
        excluirCategoria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                new WarningCRUD(socket, in, out, user,"deleteCategory");
                dispose();
            }
        });
        excluirCategoria.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton editarCategoria = new JButton("Editar Categoria");
        editarCategoria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                new WarningCRUD(socket, in, out, user, "editCategory");
                dispose();
            }
        });
        editarCategoria.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton listSubscription = new JButton("Listar Inscrições");
        listSubscription.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OperationController operationController = new OperationController();
                try {
                    Json json = operationController.listarUsuarioCategorias(user);
                    System.out.println("Client: " + json.toString());
                    out.println(json.toString());
                    out.flush();
                    String response = in.readLine();
                    System.out.println("Server: " + response);
                    Gson gson = new Gson();
                    JsonResponseCategoryResponse jsonResponseCategoryResponse = gson.fromJson(response,
                            JsonResponseCategoryResponse.class);
                    List<Avisos> warnings = new ArrayList<>();
                    List<Category> categories = new ArrayList<>();
                    try {
                        for (int i = 0; i < jsonResponseCategoryResponse.getCategorias().size(); i++) {
                            json = operationController.listarAvisos(user,
                                    jsonResponseCategoryResponse.getCategorias().get(i));
                            System.out.println("Client: " + json.toString());
                            out.println(json.toString());
                            out.flush();
                            response = in.readLine();
                            System.out.println("Server: " + response);
                            JsonResponse jsonResponse = gson.fromJson(response, JsonResponse.class);
                            for (int j = 0; j < jsonResponse.getAvisos().size(); j++) {
                                warnings.add(jsonResponse.getAvisos().get(j));
                                categories.add(jsonResponse.getAvisos().get(j).getCategory());
                            }
                        }
                        new WarningListView(socket, in, out, user, warnings, categories);
                        dispose();
                    } catch (Exception e2) {
                        JOptionPane.showMessageDialog(null, "Erro ao listar avisos" + e2.getMessage());
                    }
                    JOptionPane.showMessageDialog(null, "Avisos listados com sucesso");
                    dispose();
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, "Erro ao listar categorias" + e1.getMessage());
                }
                ;
            }
        });
        listSubscription.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton subscribe = new JButton("Inscrever-se");
        subscribe.setAlignmentX(Component.CENTER_ALIGNMENT);
        subscribe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new WarningCRUD(socket, in, out, user, "subscribe");
                dispose();
            }
        });
        JButton unsubscribe = new JButton("Desinscrever-se");
        unsubscribe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new WarningCRUD(socket, in, out, user, "unsubscribe");
                dispose();
            }
        });
        unsubscribe.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton back = new JButton("Voltar");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Warning(socket, in, out, user);
                dispose();
            }
        });
        back.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(CriarCategoria);
        panel.add(listarCategoria);
        panel.add(localizarCategoria);
        panel.add(editarCategoria);
        panel.add(excluirCategoria);
        panel.add(listSubscription);
        panel.add(subscribe);
        panel.add(unsubscribe);
        panel.add(back);

        panel.add(Box.createVerticalGlue());

        add(panel);

        setVisible(true);
    }
}
