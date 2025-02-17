package org.example.view;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Init extends JFrame{

    private Socket socket;
    
    public Init(){
        setTitle("Init");
        setSize(400,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createVerticalGlue());

        JPanel ipLine = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel portLine = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel ip = new JLabel("IP");
        JLabel port = new JLabel("PORTA");
        JTextField ipField = new JTextField(25);
        JTextField portField = new JTextField(10);

        ipLine.add(ip);
        ipLine.add(ipField);
        portLine.add(port);
        portLine.add(portField);

        JButton send = new JButton("Enviar");
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    String inputString = portField.getText();
                    String socketNumber = ipField.getText();
                    System.out.println("IP: " + socketNumber + " PORTA: " + inputString);
                    socket = new Socket(socketNumber, Integer.parseInt(inputString));
                    new Method(socket, null, null);
                    dispose();
                } catch (Exception er) {
                    JOptionPane.showMessageDialog(null, "Erro ao conectar com o servidor", "Erro", JOptionPane.ERROR_MESSAGE);  
                }
            }
        });
        send.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(ipLine);
        panel.add(portLine);
        panel.add(send);

        panel.add(Box.createVerticalGlue());

        add(panel);
        
        setVisible(true);
    }
}
