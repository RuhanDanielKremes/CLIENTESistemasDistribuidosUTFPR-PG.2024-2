package org.example.view;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.example.OperationController;
import org.example.model.Json;
import org.example.model.JsonResponse;
import org.example.model.User;

import com.google.gson.Gson;

public class UserEditView extends JFrame {
    
    private boolean raLenght;
    private boolean raTypeFormat;
    private boolean passwordLenght;
    private boolean passwordTypeFormat;
    private boolean nameLenght;
    private boolean nameTypeFormat;
    private boolean retypePassword;
    
    public UserEditView(Socket socket, BufferedReader in, PrintWriter out, User user) {
        {

        setTitle("Editar Usuário");
        setSize(400,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createVerticalGlue());
        JPanel nameLine = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel raLine = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel passwordLine = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel retypePasswordLine = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel name = new JLabel("Nome");
        JTextField nameField = new JTextField(20);
        JLabel ra = new JLabel("RA");
        JTextField raField = new JTextField(5);
        JLabel password = new JLabel("Senha");
        JPasswordField passwordField = new JPasswordField(10);
        JPasswordField retypePasswordField = new JPasswordField(10);
        JLabel raStatusLabelLenght = new JLabel("❌ RA precisa ter 7 caracteres");
        JLabel raStatusLabelTypeFormat = new JLabel("❌ RA precisa ser apenas números");
        JLabel passwordStatusLabel = new JLabel("❌ Senha precisa ter entre 8 e 20 caracteres");
        JLabel passwordStatusLabelTypeFormat = new JLabel("❌ Senha precisa ter apenas letras");
        JLabel nameStatusLabel = new JLabel("❌ Nome precisa ter até 50 caracteres");
        JLabel nameStatusLabelTypeFormat = new JLabel("❌ Nome precisa ter apenas letras e espaços");
        JLabel retypePasswordStatusLabel = new JLabel("❌ Senhas não conferem");

        raField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (raField.getText().length() == 7) {
                    raLenght = true;
                    raStatusLabelLenght.setText("✔️");
                } else {
                    raLenght = false;
                    raStatusLabelLenght.setText("❌ RA precisa ter 7 caracteres");
                }
                if (raField.getText().matches("[0-9]+")) {
                    raTypeFormat = true;
                    raStatusLabelTypeFormat.setText("✔️");
                } else {
                    raTypeFormat = false;
                    raStatusLabelTypeFormat.setText("❌ RA precisa ser apenas números");
                }
            }
        });

        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (passwordField.getPassword().length >= 8 && passwordField.getPassword().length <= 20) {
                    passwordLenght = true;
                    passwordStatusLabel.setText("✔️");
                } else {
                    passwordLenght = false;
                    passwordStatusLabel.setText("❌ Senha precisa ter entre 8 e 20 caracteres");
                }
                if (new String(passwordField.getPassword()).matches("[a-zA-Z]+")) {
                    passwordTypeFormat = true;
                    passwordStatusLabelTypeFormat.setText("✔️");
                } else {
                    passwordTypeFormat = false;
                    passwordStatusLabelTypeFormat.setText("❌ Senha precisa ter apenas letras");
                }
            }
        });

        nameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (nameField.getText().length() <= 50) {
                    nameLenght = true;
                    nameStatusLabel.setText("✔️");
                } else {
                    nameLenght = false;
                    nameStatusLabel.setText("❌ Nome precisa ter até 50 caracteres");
                }
                if (nameField.getText().matches("[a-zA-Z ]+")) {
                    nameTypeFormat = true;
                    nameStatusLabelTypeFormat.setText("✔️");
                } else {
                    nameTypeFormat = false;
                    nameStatusLabelTypeFormat.setText("❌ Nome precisa ter apenas letras e espaços");
                }
            }
        });

        retypePasswordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (new String(passwordField.getPassword()).equals(new String(retypePasswordField.getPassword()))) {
                    retypePassword = true;
                    retypePasswordStatusLabel.setText("✔️");
                } else {
                    retypePassword = false;
                    retypePasswordStatusLabel.setText("❌ Senhas não conferem");
                }
            }
        });

        nameLine.add(name);
        nameLine.add(nameField);
        nameLine.add(nameStatusLabel);
        nameLine.add(nameStatusLabelTypeFormat);
        raLine.add(ra);
        raLine.add(raField);
        raLine.add(raStatusLabelLenght);
        raLine.add(raStatusLabelTypeFormat);
        passwordLine.add(password);
        passwordLine.add(passwordField);
        passwordLine.add(passwordStatusLabel);
        passwordLine.add(passwordStatusLabelTypeFormat);
        retypePasswordLine.add(retypePasswordField);
        retypePasswordLine.add(retypePasswordStatusLabel);
        
        JButton send = new JButton("Enviar");
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if (raLenght && raTypeFormat && passwordLenght && passwordTypeFormat && nameLenght && nameTypeFormat && retypePassword) {
                    User useraux = new User();
                    useraux.setToken(user.getToken());
                    useraux.setName(nameField.getText());
                    useraux.setRa(raField.getText());
                    useraux.setPassword(new String(passwordField.getPassword()));
                    try{
                        Json json = new OperationController().editUser(useraux);
                        System.out.println("Client:" + json.toString());
                        out.println(json.toString());
                        out.flush();
                        JsonResponse jsonResponse = new Gson().fromJson(in.readLine(), JsonResponse.class);
                        System.out.println("Server:" + jsonResponse);
                        if(jsonResponse.getStatus() == 201){
                            JOptionPane.showMessageDialog(null, "Usuário editado com sucesso");
                            new Option(socket, in, out, user);
                            dispose();
                        }else{
                            JOptionPane.showMessageDialog(null, jsonResponse.getMensagem());
                        }
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos corretamente");
                }
            }
        });
        send.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(nameLine);
        panel.add(raLine);
        panel.add(passwordLine);
        panel.add(retypePasswordLine);
        panel.add(send);

        panel.add(Box.createVerticalGlue());

        add(panel);
        
        setVisible(true);
    }
    }
}