package org.example.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Method extends JFrame {
    
    Socket socket;
    BufferedReader in;
    PrintWriter out;

    public Method(Socket socket, BufferedReader a, PrintWriter b) {
        this.socket = socket;
        try {
            if (a != null) {
                a.close();
            }
            if (b != null) {
                b.close();
            }
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error initializing input/output streams", e);
        }
        
        setTitle("Methods");
        setSize(400,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(Box.createVerticalGlue());

        JButton login = new JButton("Login");
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    new Login(socket, in, out); // Passando o log para a tela
                    setVisible(false);
            }
        });
        login.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton signUp = new JButton("SignUp");
        signUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // TODO
                new SignUp(socket, in, out);
                dispose();
            }
        });
        signUp.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton host = new JButton("Change Host");
        host.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // TODO
                new Init();
                dispose();
            }
        });
        host.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(login);
        panel.add(signUp);
        panel.add(host);

        panel.add(Box.createVerticalGlue());

        add(panel);
        
        setVisible(true);
    }


}
