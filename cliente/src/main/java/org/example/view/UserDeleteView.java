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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.example.OperationController;
import org.example.model.Json;
import org.example.model.JsonResponse;
import org.example.model.User;

import com.google.gson.Gson;

public class UserDeleteView extends JFrame {
    
    public UserDeleteView(Socket socket, BufferedReader in, PrintWriter out, User user) {
        JFrame frame = new JFrame("User");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(Box.createVerticalGlue());

        JTextField raField = new JTextField(7);
        JButton deleteUserButton = new JButton("Deletar Usuário");
        deleteUserButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton back = new JButton("Voltar");
        back.setAlignmentX(Component.CENTER_ALIGNMENT);
        back.addActionListener(_ -> {
            new Option(socket, in, out, user);
            frame.dispose();
        });
        deleteUserButton.addActionListener(e -> {
            OperationController operationController = new OperationController();
            try {
                Json json = operationController.excluirUsuario(user.getToken(), raField.getText());
                System.out.println("Client: " + json.toString());
                out.println(json.toString());
                out.flush();
                String response = in.readLine();
                System.out.println("Client: " + response);
                Gson gson = new Gson();
                JsonResponse jsonResponse = gson.fromJson(response, JsonResponse.class);
                JOptionPane.showMessageDialog(null, jsonResponse.getMensagem());
            } catch (IOException e1) {
                // e1.printStackTrace();
            }
        });

        panel.add(deleteUserButton);
        panel.add(raField);
        panel.add(back);

        add(panel);
        frame.add(panel);
        frame.setVisible(true);
    }
        
}
