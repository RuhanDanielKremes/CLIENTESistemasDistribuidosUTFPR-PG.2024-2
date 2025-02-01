package org.example.view;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Find extends JFrame{
    
    public Find(){
        setTitle("Find");
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
                new Category();
                dispose();
            }
        });
        back.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel categoryLine = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JTextField category = new JTextField(15);
        categoryLine.add(category);

        JButton find = new JButton("Procurar");
        find.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // TODO
                new Inscription();
                dispose();
            }
        });
        find.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(back);
        panel.add(categoryLine);
        panel.add(find);

        panel.add(Box.createVerticalGlue());

        add(panel);
        
        setVisible(true);
    }
}
