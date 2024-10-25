package org.example.exam.ui;

import org.example.exam.ejb.AdminService;
import org.example.exam.ejb.ClientService;
import org.example.exam.entity.CD;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdminApp extends JFrame {
    private AdminService adminService;
    private ClientService clientService;

    private JTextField cdIdField;
    private JTextField cdTitleField;
    private JTextField cdArtistField;
    private JTextArea displayArea;

    public AdminApp() {
        try {
            InitialContext ctx = new InitialContext();
            adminService = (AdminService) ctx.lookup("java:module/AdminService");
            clientService = (ClientService) ctx.lookup("java:module/ClientService");
        } catch (NamingException e) {
            e.printStackTrace();
        }

        setTitle("Admin and Client Application");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(7, 2));
        inputPanel.add(new JLabel("CD ID:"));
        cdIdField = new JTextField();
        inputPanel.add(cdIdField);

        inputPanel.add(new JLabel("CD Title:"));
        cdTitleField = new JTextField();
        inputPanel.add(cdTitleField);

        inputPanel.add(new JLabel("CD Artist:"));
        cdArtistField = new JTextField();
        inputPanel.add(cdArtistField);

        JButton addButton = new JButton("Add CD");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCD();
            }
        });
        inputPanel.add(addButton);

        JButton updateButton = new JButton("Update CD");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCD();
            }
        });
        inputPanel.add(updateButton);

        JButton deleteButton = new JButton("Delete CD");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteCD();
            }
        });
        inputPanel.add(deleteButton);

        JButton listButton = new JButton("List CDs");
        listButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listCDs();
            }
        });
        inputPanel.add(listButton);

        JButton borrowedButton = new JButton("View Borrowed CDs");
        borrowedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewBorrowedCDs();
            }
        });
        inputPanel.add(borrowedButton);

        JButton borrowButton = new JButton("Borrow CD");
        borrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                borrowCD();
            }
        });
        inputPanel.add(borrowButton);

        JButton returnButton = new JButton("Return CD");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnCD();
            }
        });
        inputPanel.add(returnButton);

        add(inputPanel, BorderLayout.NORTH);

        displayArea = new JTextArea();
        add(new JScrollPane(displayArea), BorderLayout.CENTER);
    }

    private void addCD() {
        String cdTitle = cdTitleField.getText();
        String cdArtist = cdArtistField.getText();
        if (!cdTitle.isEmpty() && !cdArtist.isEmpty()) {
            CD cd = new CD();
            cd.setTitle(cdTitle);
            cd.setArtist(cdArtist);
            adminService.addCD(cd);
        }
        clearFields();
    }

    private void updateCD() {
        String cdId = cdIdField.getText();
        String cdTitle = cdTitleField.getText();
        String cdArtist = cdArtistField.getText();
        if (!cdId.isEmpty() && !cdTitle.isEmpty() && !cdArtist.isEmpty()) {
            CD cd = new CD();
            cd.setId(Long.parseLong(cdId));
            cd.setTitle(cdTitle);
            cd.setArtist(cdArtist);
            adminService.updateCD(cd);
        }
        clearFields();
    }

    private void deleteCD() {
        String cdId = cdIdField.getText();
        if (!cdId.isEmpty()) {
            adminService.deleteCD(Long.parseLong(cdId));
        }
        clearFields();
    }

    private void listCDs() {
        List<CD> cds = adminService.getAllCDs();
        displayArea.setText("CDs:\n");
        for (CD cd : cds) {
            displayArea.append(cd.getId() + ": " + cd.getTitle() + " by " + cd.getArtist() + "\n");
        }
    }

    private void viewBorrowedCDs() {
        List<CD> borrowedCDs = adminService.getBorrowedCDs();
        displayArea.setText("Borrowed CDs:\n");
        for (CD cd : borrowedCDs) {
            displayArea.append(cd.getId() + ": " + cd.getTitle() + " by " + cd.getArtist() + "\n");
        }
    }

    private void borrowCD() {
        String cdId = cdIdField.getText();
        if (!cdId.isEmpty()) {
            clientService.borrowCD(Long.parseLong(cdId));
        }
        clearFields();
    }

    private void returnCD() {
        String cdId = cdIdField.getText();
        if (!cdId.isEmpty()) {
            clientService.returnCD(Long.parseLong(cdId));
        }
        clearFields();
    }

    private void clearFields() {
        cdIdField.setText("");
        cdTitleField.setText("");
        cdArtistField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AdminApp().setVisible(true);
            }
        });
    }
}