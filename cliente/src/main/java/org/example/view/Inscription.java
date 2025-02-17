package org.example.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Inscription extends JFrame{

    public Inscription(){
        setTitle("Inscriptions");
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
                // new Category();
                dispose();
            }
        });
        back.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("Lista Categorias");
        
        String[] teste = {"Abacate","Banana","Pera"};
        JList<String> list = new JList<>(teste);
        JScrollPane scroll = new JScrollPane(list);

        JButton apply = new JButton("Aplicar");
        apply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // TODO
                // new Category();
                dispose();
            }
        });
        apply.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(back);
        panel.add(title);
        panel.add(scroll);
        panel.add(apply);
        
        panel.add(Box.createVerticalGlue());

        add(panel);
        
        setVisible(true);
    }
}
