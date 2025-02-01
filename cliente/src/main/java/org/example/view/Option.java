package org.example.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Option extends JFrame{

    public Option(){
        setTitle("Options");
        setSize(400,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(Box.createVerticalGlue());

        JButton logout = new JButton("Logout");
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // TODO
                new Method();
                dispose();
            }
        });
        logout.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton category = new JButton("Categorias");
        category.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // TODO
                new Category();
                dispose();
            }
        });
        category.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton warning = new JButton("Avisos");
        warning.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // TODO
                new Warning();
                dispose();
            }
        });
        warning.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(logout);
        panel.add(category);
        panel.add(warning);

        panel.add(Box.createVerticalGlue());

        add(panel);
        
        setVisible(true);
    }
}
