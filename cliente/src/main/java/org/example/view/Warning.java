package org.example.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.server.Operation;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.example.OperationController;
import org.example.model.Json;
import org.example.model.JsonResponse;
import org.example.model.User;
import org.example.model.Warnings;
import org.example.model.Avisos;
import org.example.model.Category;

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
                    List<Avisos> warnings = jsonResponse.getAvisos();
                    try {
                        json = operationController.listarCategorias(user);
                        System.out.println("Client: " + json.toString());
                        out.println(json.toString());
                        out.flush();
                        String responseCategory = in.readLine();
                        System.out.println("Server: " + responseCategory);
                        JsonResponse jsonResponseCategory = gson.fromJson(responseCategory, JsonResponse.class);
                        new WarningListView(socket, in, out, user, jsonResponse.getAvisos(), jsonResponse.getCategorias());
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                // new WarningListView();
                dispose();
            }
        });
        JButton findWarning = new JButton("Procurar Aviso");
        JButton createWarning = new JButton("Criar Aviso");
        JButton deleteWarning = new JButton("Deletar Aviso");
        JButton editWarning = new JButton("Editar Aviso");


        JButton back = new JButton("Voltar");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                new Option(socket, in, out, user);
                dispose();
            }
        });
        back.setAlignmentX(Component.CENTER_ALIGNMENT);


        panel.add(back);
        
        panel.add(Box.createVerticalGlue());

        add(panel);
        
        setVisible(true);
    }
}
