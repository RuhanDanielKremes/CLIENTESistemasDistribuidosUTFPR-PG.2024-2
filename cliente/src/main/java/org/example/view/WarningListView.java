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
        {
            setTitle("Warnings");
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            panel.add(Box.createVerticalGlue());

            if (category != null && category.size() > 0) {
                

                JLabel titleCat = new JLabel("Categorias");

                String[] categoryColumnNames = { "idCategoria", "Nome" };
                Object[][] categoryData = new Object[category.size()][2];
                for (int j = 0; j < category.size(); j++) {
                    categoryData[j][0] = category.get(j).getId();
                    categoryData[j][1] = category.get(j).getName();
                }
                JTable tableCategory = new JTable(categoryData, categoryColumnNames);
                JScrollPane scrollCategory = new JScrollPane(tableCategory);

                panel.add(titleCat);
                panel.add(scrollCategory);

            }

            if (warning != null && warning.size() > 0) {

                JLabel title = new JLabel("Avisos");

                String[] warnigsColumnNames = { "idAviso", "Título", "Descricao", "idCategoria", "Categoria" };
                Object[][] warningsData = new Object[warning.size()][5];
                for (int j = 0; j < warning.size(); j++) {
                    warningsData[j][0] = warning.get(j).getId();
                    warningsData[j][1] = warning.get(j).getTitle();
                    warningsData[j][2] = warning.get(j).getDescription();
                    warningsData[j][3] = warning.get(j).getCategory().getId();
                    warningsData[j][4] = warning.get(j).getCategory().getName();
                }
                JTable tableWarnigs = new JTable(warningsData, warnigsColumnNames);
                JScrollPane scrollWarning = new JScrollPane(tableWarnigs);

                panel.add(title);
                panel.add(scrollWarning);
            }

            JButton back = new JButton("Voltar");
            back.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new Option(socket, in, out, user);
                    dispose();
                }
            });
            back.setAlignmentX(Component.CENTER_ALIGNMENT);

            panel.add(back);

            panel.add(Box.createVerticalGlue());

            add(panel);

            setVisible(true);
        }

    }
}
