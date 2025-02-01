package org.example.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Category extends JFrame{
    
    public Category(){
        setTitle("Categories");
        setSize(400,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(Box.createVerticalGlue());

        JButton back = new JButton("Voltar");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // TODO
                new Option();
                dispose();
            }
        });
        back.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton all = new JButton("Listar todas");
        all.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // TODO
                new Inscription();
                dispose();
            }
        });
        all.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton find = new JButton("Procurar");
        find.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // TODO
                new Find();
                dispose();
            }
        });
        find.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton inscription = new JButton("Listar Inscrições");
        inscription.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // TODO
                new Inscription();
                dispose();
            }
        });
        inscription.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(back);
        panel.add(all);
        panel.add(find);
        panel.add(inscription);

        panel.add(Box.createVerticalGlue());

        add(panel);
        
        setVisible(true);
    }
}
