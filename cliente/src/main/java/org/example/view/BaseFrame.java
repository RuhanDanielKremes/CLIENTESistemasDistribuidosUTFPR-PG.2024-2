package org.example.view;

import javax.swing.*;
import java.awt.*;

public class BaseFrame extends JFrame {
    protected JTextArea areaLog;
    protected JToolBar toolBar;

    public BaseFrame(String title) {
        setTitle(title);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Criando a Barra de Ferramentas (Toolbar)
        toolBar = new JToolBar();
        toolBar.setFloatable(false); // Impede que a barra seja movida

        // Criando um botão de ação
        JButton btnAdicionarLog = new JButton("Registrar Log");

        // Criando a área de log
        areaLog = new JTextArea(5, 30);
        areaLog.setEditable(false); // Impede edição
        JScrollPane scrollLog = new JScrollPane(areaLog);

        // Adicionando botão à barra de ferramentas
        toolBar.add(btnAdicionarLog);

        // Adicionando componentes à janela
        add(toolBar, BorderLayout.NORTH); // Barra no topo
        add(scrollLog, BorderLayout.SOUTH); // Log na parte inferior
    }

    // Método para adicionar logs de outras partes do sistema
    public void adicionarLog(String mensagem) {
        areaLog.append(mensagem + "\n");
    }
}
