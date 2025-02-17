package org.example.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import org.example.model.Category;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.example.OperationController;
import org.example.model.Json;
import org.example.model.JsonResponse;
import org.example.model.User;

import com.google.gson.Gson;

public class Warning extends JFrame{
    
    public Warning(Socket socket, BufferedReader in, PrintWriter out, User user){
        setTitle("Warnings");
        setSize(400,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(Box.createVerticalGlue());

        JButton listAllWarnings = new JButton("Listar Todos os Avisos");
        listAllWarnings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                OperationController operationController = new OperationController();
                try {
                    Json json = operationController.listarAvisos(user);
                    System.out.println("Client: " + json.toString());
                    out.println(json.toString());
                    out.flush();
                    String response = in.readLine();
                    System.out.println("Server: " + response);
                    Gson gson = new Gson();
                    JsonResponse jsonResponse = gson.fromJson(response, JsonResponse.class);
                    List<Category> categories = new ArrayList<>();
                    for (int i = 0; i < jsonResponse.getAvisos().size(); i++) {
                        if (categories.size() == 0) {
                            categories.add(jsonResponse.getAvisos().get(i).getCategory());
                        }
                        for (int j = 0; j < categories.size(); j++) {
                            if (categories.get(j).getId() != jsonResponse.getAvisos().get(i).getCategory().getId()) {
                                categories.add(jsonResponse.getAvisos().get(i).getCategory());
                                break;
                            }
                        }
                    }
                    new WarningListView(socket, in, out, user, jsonResponse.getAvisos(), categories);
                    dispose();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        listAllWarnings.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton findWarning = new JButton("Procurar Aviso");
        findWarning.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                new WarningCRUD(socket, in, out, user, "findWarning");
                dispose();
            }
        }); 
        findWarning.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton createWarning = new JButton("Criar Aviso");
        createWarning.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                new CategoryCrud(socket, in, out, user, "createWarning");
                dispose();
            }
        });
        createWarning.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton deleteWarning = new JButton("Deletar Aviso");
        deleteWarning.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                new WarningCRUD(socket, in, out, user, "deleteWarning");
                dispose();
            }
        });
        deleteWarning.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton editWarning = new JButton("Editar Aviso");
        editWarning.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                new CategoryCrud(socket, in, out, user, "editWarning");
                dispose();
            }
        });
        editWarning.setAlignmentX(Component.CENTER_ALIGNMENT);


        JButton back = new JButton("Voltar");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                new Option(socket, in, out, user);
                dispose();
            }
        });
        back.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(listAllWarnings);
        panel.add(findWarning);
        panel.add(createWarning);
        panel.add(deleteWarning);
        panel.add(editWarning);
        panel.add(back);
        
        panel.add(Box.createVerticalGlue());

        add(panel);
        
        setVisible(true);
    }
}
