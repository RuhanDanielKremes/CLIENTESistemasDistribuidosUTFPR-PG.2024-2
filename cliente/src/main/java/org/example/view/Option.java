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
import javax.swing.JPanel;

import org.example.model.User;

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
                    // TODO
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
                    new Category();
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
