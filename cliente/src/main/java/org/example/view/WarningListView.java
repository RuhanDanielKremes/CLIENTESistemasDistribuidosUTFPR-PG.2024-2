package org.example.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.example.model.User;
import org.example.model.Avisos;
import org.example.model.Category;

public class WarningListView extends JFrame {
    
    public WarningListView(Socket socket, BufferedReader in, PrintWriter out, User user, List<Avisos> warning, List<Category> category) {
        setTitle("Warnings");
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
                new Warning(socket, in, out, user);
                dispose();
            }
        });
        back.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("Avisos");
                
        String[] columnNames = { "idCategoria", "Nome", "Avisos" };
        Object[][] data = new Object[category.size()][3];
        for (int i = 0; i < category.size(); i++) {
            Category c = category.get(i);
            data[i][0] = c.getId();
            data[i][1] = c.getName();
            String[] warnigsColumnNames = {"idAviso", "Título", "Descricao" };
            Object [][] warningsData = new Object[warning.size()][3];
            int numberOfWarnings = 0;
            for (int j = 0; j < warning.size(); j++) {
                Avisos w = warning.get(j);
                // if (w.getCategoria() == c.getId()) {
                //     warningsData[numberOfWarnings][0] = w.getId();
                //     warningsData[numberOfWarnings][1] = w.getTitle();
                //     warningsData[numberOfWarnings][2] = w.getDescription();
                //     numberOfWarnings++;
                // }
            }
            
            JTable tableWarnigs = new JTable(warningsData, warnigsColumnNames);
            JScrollPane scroll = new JScrollPane(tableWarnigs);
        }

        JTable table = new JTable(data, columnNames);

        JScrollPane scroll2 = new JScrollPane(table);

        panel.add(back);
        panel.add(title);
        panel.add(scroll2);
        
        panel.add(Box.createVerticalGlue());

        add(panel);
        
        setVisible(true);
    }

}
