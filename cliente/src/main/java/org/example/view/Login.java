package org.example.view;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame{
    
    public Login(){
        setTitle("Login");
        setSize(400,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createVerticalGlue());

        JPanel raLine = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel passwordLine = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel ra = new JLabel("RA");
        JLabel password = new JLabel("Senha");
        JTextField raField = new JTextField(25);
        JPasswordField passwordField = new JPasswordField(25);

        raLine.add(ra);
        raLine.add(raField);
        passwordLine.add(password);
        passwordLine.add(passwordField);

        JButton send = new JButton("Enviar");
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // TODO
                new Option();
                dispose();
            }
        });
        send.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(raLine);
        panel.add(passwordLine);
        panel.add(send);

        panel.add(Box.createVerticalGlue());

        add(panel);
        
        setVisible(true);
    }
}
