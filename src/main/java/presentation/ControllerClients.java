package presentation;

import bll.ClientBLL;
import model.Client;
import start.ReflectionExample;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static start.ReflectionExample.getValues;

public class ControllerClients extends JFrame {

    int id;
    Client client;
    JButton be;

    public ControllerClients(){
        ClientBLL clientBLL = new ClientBLL();
        GridLayout layout = new GridLayout(4,1,30,30);

        List<Client> list = null;

        JButton b1 = new JButton("Add new client");
        b1.setBackground(new Color(0xff6200));
        b1.setForeground(Color.WHITE);
        b1.setSize(200,50);
        b1.setFont(new Font("Calibri",Font.BOLD,20));
        b1.setBorder(BorderFactory.createLineBorder(Color.black));
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == b1) {
                    JFrame frame = new JFrame("Add client");

                    JTextField t2 = new JTextField("Name");
                    t2.setFont(new Font("Consolas", Font.PLAIN, 20));
                    t2.setPreferredSize(new Dimension(400, 30));

                    JTextField t3 = new JTextField("Address");
                    t3.setFont(new Font("Consolas", Font.PLAIN, 20));
                    t3.setPreferredSize(new Dimension(400, 30));

                    JTextField t4 = new JTextField("Email");
                    t4.setFont(new Font("Consolas", Font.PLAIN, 20));
                    t4.setPreferredSize(new Dimension(400, 30));

                    JTextField t5 = new JTextField("Age");
                    t5.setFont(new Font("Consolas", Font.PLAIN, 20));
                    t5.setPreferredSize(new Dimension(400, 30));

                    JButton b = new JButton("Add");
                    b.setFont(new Font("Consolas", Font.PLAIN, 15));
                    b.setBackground(new Color(0xff6200));
                    b.setForeground(Color.WHITE);
                    b.setPreferredSize(new Dimension(100, 40));
                    b.setBorder(BorderFactory.createLineBorder(Color.black));
                    b.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Client cl = new Client(t2.getText(), t3.getText(), t4.getText(), Integer.parseInt(t5.getText()));
                            clientBLL.insertClient(cl);
                        }
                    });

                    frame.setLayout(new FlowLayout());
                    frame.add(t2);
                    frame.add(t3);
                    frame.add(t4);
                    frame.add(t5);
                    frame.add(b);
                    frame.setSize(500, 280);
                    frame.getContentPane().setBackground(new Color(0x742020));
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.setResizable(false);
                    frame.setVisible(true);
                }
            }
        });

        JButton b2 = new JButton("Edit client");
        b2.setBackground(new Color(0xff6200));
        b2.setForeground(Color.WHITE);
        b2.setSize(200,50);
        b2.setFont(new Font("Calibri",Font.BOLD,20));
        b2.setBorder(BorderFactory.createLineBorder(Color.black));
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == b2) {
                    new EditClient();
                }
            }
        });

        JButton b3 = new JButton("Delete client");
        b3.setBackground(new Color(0xff6200));
        b3.setForeground(Color.WHITE);
        b3.setSize(200,50);
        b3.setFont(new Font("Calibri",Font.BOLD,20));
        b3.setBorder(BorderFactory.createLineBorder(Color.black));
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == b3){
                    JFrame frame = new JFrame("Delete client");

                    JTextField t2 = new JTextField("Name");
                    t2.setFont(new Font("Consolas", Font.PLAIN, 20));
                    t2.setPreferredSize(new Dimension(400, 30));

                    JButton b = new JButton("Delete");
                    b.setFont(new Font("Consolas", Font.PLAIN, 15));
                    b.setBackground(new Color(0xff6200));
                    b.setForeground(Color.WHITE);
                    b.setPreferredSize(new Dimension(100, 40));
                    b.setBorder(BorderFactory.createLineBorder(Color.black));
                    b.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            clientBLL.deleteClient(t2.getText());
                        }
                    });

                    frame.setLayout(new FlowLayout());
                    frame.add(t2);
                    frame.add(b);
                    frame.setSize(500, 140);
                    frame.getContentPane().setBackground(new Color(0x742020));
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.setResizable(false);
                    frame.setVisible(true);
                }
            }
        });

        JButton b4 = new JButton("View clients");
        b4.setBackground(new Color(0xff6200));
        b4.setForeground(Color.WHITE);
        b4.setSize(200,50);
        b4.setFont(new Font("Calibri",Font.BOLD,20));
        b4.setBorder(BorderFactory.createLineBorder(Color.black));
        b4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("View clients");
                List<Client> clients = clientBLL.findAllClients();
                List<String> fieldsList = ReflectionExample.getFields(clients.get(0));
                String[] fields = new String[fieldsList.size()];
                int i = 0;
                for(String field:fieldsList){
                    fields[i++] = field;
                }

                Object[][] data = new Object[clients.size()][fieldsList.size()];
                i=0;
                for(Client client:clients){
                    ArrayList<Object> obj = ReflectionExample.getValues(client);
                    int j=0;
                    for(Object o : obj){
                        data[i][j++] = o;
                    }
                    i++;
                }

                JTable table = new JTable(data, fields);
                table.setPreferredSize(new Dimension(600,400));

                JScrollPane scrollPane = new JScrollPane(table);
                table.setFillsViewportHeight(true);

                frame.add(scrollPane);

                frame.setLayout(new FlowLayout());
                frame.setSize(700, 500);
                frame.getContentPane().setBackground(new Color(0x742020));
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setResizable(false);
                frame.setVisible(true);
            }
        });

        this.setLayout(layout);
        this.add(b1);
        this.add(b2);
        this.add(b3);
        this.add(b4);
        this.setSize(400,400);
        this.setTitle("Client Controller");
        this.getContentPane().setBackground(new Color(0x742020));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }
}

