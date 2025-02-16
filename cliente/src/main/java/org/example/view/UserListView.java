package org.example.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.example.model.User;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class UserListView extends JFrame {
    
    public UserListView(Socket socket, BufferedReader in, PrintWriter out, User user, List<User> users) {
        setTitle("User List");
        setSize(400,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton back = new JButton("Voltar");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                new Option(socket, in, out, user);
                dispose();
            }
        });
        back.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("User List");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        String[] columnNames = {"Nome", "RA", "Senha" };
        Object[][] data = new Object[users.size()][4];
        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            data[i][0] = u.getName();
            data[i][1] = u.getRa();
            data[i][2] = u.getPassword();
        }

        JTable table = new JTable(data, columnNames);
        // JScrollPane scrollPane = new JScrollPane(table);
        // JPanel tablePanel = new JPanel();
        JScrollPane scroll = new JScrollPane(table);

        panel.add(back);
        panel.add(title);
        panel.add(scroll);

        panel.add(Box.createVerticalGlue());
        
        add(panel);

        setVisible(true);
    }
    
}