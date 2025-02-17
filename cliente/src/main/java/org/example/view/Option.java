package org.example.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

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

public class Option extends JFrame {

    public Option(Socket socket, BufferedReader in, PrintWriter out, User user) {
        {
            setTitle("Options");
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            panel.add(Box.createVerticalGlue());

            JButton userButton = new JButton("Usuários");
            userButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new UserView(socket, in, out, user);
                    dispose();
                }
            });
            userButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            JButton logout = new JButton("Logout");
            logout.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        Json json = new OperationController().logout(user);
                        System.out.println("Client: " + json.toString());
                        out.println(json.toString());
                        out.flush();
                        String response = in.readLine();
                        System.out.println("Server: " + response);
                        JsonResponse jsonResponse = new Gson().fromJson(response, JsonResponse.class);
                        JOptionPane.showMessageDialog(null, jsonResponse.getMensagem());
                        user.setToken(null);
                        if (jsonResponse.getStatus() == 200) {
                            new Login(socket, in, out);
                        }
                    } catch (Exception e10) {
                        e10.printStackTrace();
                    }
                    // new Method();
                    dispose();
                }
            });
            logout.setAlignmentX(Component.CENTER_ALIGNMENT);

            JButton category = new JButton("Categorias");
            category.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO
                    new CategoryView(socket, in, out, user);
                    dispose();
                }
            });
            category.setAlignmentX(Component.CENTER_ALIGNMENT);

            JButton warning = new JButton("Avisos");
            warning.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO
                    new Warning(socket, in, out, user);
                    dispose();
                }
            });
            warning.setAlignmentX(Component.CENTER_ALIGNMENT);

            panel.add(logout);
            panel.add(userButton);
            panel.add(category);
            panel.add(warning);

            panel.add(Box.createVerticalGlue());

            add(panel);

            setVisible(true);
        }
    }
}
