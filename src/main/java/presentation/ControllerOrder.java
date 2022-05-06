package presentation;

import bll.ClientBLL;
import bll.OrderBLL;
import bll.ProductBLL;
import model.Client;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ControllerOrder extends JFrame {

    ControllerOrder() {
        OrderBLL orderBLL = new OrderBLL();
        JLabel l1 = new JLabel("Choose Client");
        l1.setFont(new Font("Calibri",Font.BOLD,18));
        l1.setForeground(Color.WHITE);
        l1.setSize(200,20);

        ClientBLL clientBLL = new ClientBLL();
        List<Client> clients = clientBLL.findAllClients();
        String[] names = new String[clients.size()];
        int i = 0;
        for(Client cl:clients){
            names[i++] = cl.getName();
        }
        NamesBox nb1 = new NamesBox(names,true);

        JLabel l2 = new JLabel("Choose Product");
        l2.setFont(new Font("Calibri",Font.BOLD,18));
        l2.setForeground(Color.WHITE);
        l2.setSize(200,20);

        ProductBLL productBLL = new ProductBLL();
        List<Product> products = productBLL.findAllProducts();
        String[] names1 = new String[products.size()];
        i = 0;
        for(Product pd:products){
            names1[i++] = pd.getName();
        }
        NamesBox nb2 = new NamesBox(names1,false);

        JTextField t1 = new JTextField("Quantity");
        t1.setFont(new Font("Consolas", Font.PLAIN, 20));
        t1.setPreferredSize(new Dimension(300, 30));

        JButton b = new JButton("Order");
        b.setFont(new Font("Consolas", Font.PLAIN, 15));
        b.setBackground(new Color(0xff6200));
        b.setForeground(Color.WHITE);
        b.setPreferredSize(new Dimension(100, 40));
        b.setBorder(BorderFactory.createLineBorder(Color.black));
        b.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if(e.getSource() == b) {
                                        Product p = nb2.pr;
                                        int quantity = Integer.parseInt(t1.getText());
                                        if (p.getStock() < quantity) {
                                            final JOptionPane optionPane = new JOptionPane(
                                                    "Cantitatea introdusa este mai mare decat stocul produsului.\n" +
                                                            "Vrei sa introduci din nou?",
                                                    JOptionPane.QUESTION_MESSAGE,
                                                    JOptionPane.YES_NO_OPTION);

                                            final JDialog dialog = new JDialog((Dialog) getOwner(), "Click a button", true);
                                            dialog.setContentPane(optionPane);
                                            dialog.setDefaultCloseOperation(
                                                    JDialog.DO_NOTHING_ON_CLOSE);

                                            dialog.addWindowListener(new WindowAdapter() {
                                                public void windowClosing(WindowEvent we) {
                                                    optionPane.setVisible(false);
                                                    dialog.setVisible(false);
                                                }
                                            });
                                            optionPane.addPropertyChangeListener(
                                                    new PropertyChangeListener() {
                                                        public void propertyChange(PropertyChangeEvent e) {
                                                            String prop = e.getPropertyName();

                                                            if (dialog.isVisible() && (e.getSource() == optionPane) && (prop.equals(JOptionPane.VALUE_PROPERTY))) {
                                                                //If you were going to check something
                                                                //before closing the window, you'd do
                                                                //it here.
                                                                dialog.setVisible(false);
                                                            }
                                                        }
                                                    });
                                            dialog.pack();
                                            dialog.setVisible(true);
                                            int value = ((Integer) optionPane.getValue()).intValue();
                                            if (value == JOptionPane.YES_OPTION) {
                                                optionPane.setVisible(false);
                                                dialog.setVisible(false);
                                            } else if (value == JOptionPane.NO_OPTION) {
                                                optionPane.setVisible(false);
                                                dialog.setVisible(false);
                                                ControllerOrder.super.dispose();
                                            }
                                        } else {
                                            FileWriter file;
                                            BufferedWriter writer;
                                            try {
                                                file = new FileWriter("nota.txt");
                                                writer = new BufferedWriter(file);
                                                double rez = quantity * p.getPrice();
                                                writer.write("---------------------NOTA DE PLATA---------------------\n\n\n");
                                                writer.write("Clientul " + nb1.cl.getName() + " a comandat cu mandrie astazi\n");
                                                writer.write(p.getName() + "--------------------------------" + quantity+"\n");
                                                writer.write(p.getPrice() + "-----------------------------------" + rez+"\n");
                                                writer.write("----------------------------Suma finala: " + rez+"\n\n\n");
                                                writer.write("-----------------------Multumim-----------------------");
                                                writer.close();
                                            } catch (IOException ei) {
                                                ei.printStackTrace();
                                            }
                                            orderBLL.placeOrder(nb1.cl.getId(), nb2.pr.getId(), quantity);
                                        }
                                    }
                                }
                            }
        );

        this.add(l1);
        this.add(nb1);
        this.add(l2);
        this.add(nb2);
        this.add(t1);
        this.add(b);
        this.setLayout(new FlowLayout());
        this.setSize(400, 400);
        this.setTitle("Order Controller");
        this.getContentPane().setBackground(new Color(0x742020));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);

    }
}
