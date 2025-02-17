package org.example.view;

import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.*;

import org.example.OperationController;
import org.example.model.Avisos;
import org.example.model.Category;
import org.example.model.Json;
import org.example.model.JsonResponse;
import org.example.model.JsonResponseCategoryResponse;
import org.example.model.User;

import com.google.gson.Gson;

public class Login extends BaseFrame {

    private boolean raLenght;
    private boolean raTypeFormat;
    private boolean passwordLenght;
    private boolean passwordTypeFormat;

    public Login(Socket socket, BufferedReader in, PrintWriter out) {
        super("Login"); // Chama o construtor da classe BaseFrame

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createVerticalGlue());

        JPanel raLine = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel passwordLine = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel ra = new JLabel("RA");
        JLabel password = new JLabel("Senha");
        JTextField raField = new JTextField(7);
        JPasswordField passwordField = new JPasswordField(20);
        JLabel raStatusLabelLenght = new JLabel("❌ RA precisa ter 7 caracteres");
        JLabel raStatusLabelTypeFormat = new JLabel("❌ RA precisa ser apenas números");
        JLabel passwordStatusLabel = new JLabel("❌ Senha precisa ter entre 8 e 20 caracteres");
        JLabel passwordStatusLabelTypeFormat = new JLabel("❌ Senha precisa ter apenas letras");

        raLenght = false;
        raTypeFormat = false;
        passwordLenght = false;
        passwordTypeFormat = false;

        raField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (raField.getText().length() == 7) {
                    raLenght = true;
                    raStatusLabelLenght.setText("✔️");
                } else {
                    raLenght = false;
                    raStatusLabelLenght.setText("❌ RA precisa ter 7 caracteres");
                }
                if (raField.getText().matches("[0-9]+")) {
                    raTypeFormat = true;
                    raStatusLabelTypeFormat.setText("✔️");
                } else {
                    raTypeFormat = false;
                    raStatusLabelTypeFormat.setText("❌ RA precisa ser apenas números");
                }
            }
        });

        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (passwordField.getPassword().length >= 8 && passwordField.getPassword().length <= 20) {
                    passwordLenght = true;
                    passwordStatusLabel.setText("✔️");
                } else {
                    passwordLenght = false;
                    passwordStatusLabel.setText("❌ Senha precisa ter entre 8 e 20 caracteres");
                }
                if (new String(passwordField.getPassword()).matches("[a-zA-Z]+")) {
                    passwordTypeFormat = true;
                    passwordStatusLabelTypeFormat.setText("✔️");
                } else {
                    passwordTypeFormat = false;
                    passwordStatusLabelTypeFormat.setText("❌ Senha precisa ter apenas letras");
                }
            }
        });

        raLine.add(ra);
        raLine.add(raField);
        raLine.add(raStatusLabelLenght);
        raLine.add(raStatusLabelTypeFormat);
        passwordLine.add(password);
        passwordLine.add(passwordField);
        passwordLine.add(passwordStatusLabel);
        passwordLine.add(passwordStatusLabelTypeFormat);

        JButton send = new JButton("Enviar");
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = new User();
                if (!raLenght || !raTypeFormat || !passwordLenght || !passwordTypeFormat) {
                    JOptionPane.showMessageDialog(null, "RA ou Senha inválidos");
                    return;
                }
                user.setRa(raField.getText());
                user.setPassword(new String(passwordField.getPassword()));

                try {
                    Json json = new OperationController().login(user);
                    System.out.println("Client:" + json.toString());
                    out.println(json.toString());
                    String response = in.readLine();
                    System.out.println("Server:" + response);
                    Gson gson = new Gson();
                    JsonResponse jsonResp = gson.fromJson(response, JsonResponse.class);
                    if (jsonResp.getStatus() == 200) {
                        if (jsonResp.getToken() != null) {
                            User userResp = new User();
                            userResp.setToken(jsonResp.getToken());
                            getWarning(socket, in, out, userResp);
                        } else {
                            JOptionPane.showMessageDialog(null, "Token não recebido");
                        }
                    } else {
                        if (jsonResp.getMensagem() != null) {
                            JOptionPane.showMessageDialog(null, jsonResp.getMensagem());
                        }
                    }
                } catch (Exception e1) {
                    // e1.printStackTrace();
                    System.out.println("Erro: " + e1.getMessage());
                }
            }
        });
        send.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton back = new JButton("Voltar");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Method(socket, in, out);
                dispose();
            }
        });
        back.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(raLine);
        panel.add(passwordLine);
        panel.add(send);
        panel.add(back);
        panel.add(Box.createVerticalGlue());

        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }
    
    private void getWarning(Socket socket, BufferedReader in, PrintWriter out, User user) {
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

}