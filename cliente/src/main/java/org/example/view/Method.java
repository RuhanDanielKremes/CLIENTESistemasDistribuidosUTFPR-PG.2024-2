package org.example.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Method extends JFrame{
    
    public Method(){
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
            public void actionPerformed(ActionEvent e){
                // TODO
                new Login();
                dispose();
            }
        });
        login.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton signUp = new JButton("SignUp");
        signUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // TODO
                new SignUp();
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
