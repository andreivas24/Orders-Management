package presentation;

import bll.ClientBLL;
import bll.ProductBLL;
import model.Client;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

public class NamesBox extends JComboBox implements ItemListener {

    Client cl=null;
    Product pr=null;
    ClientBLL clientBLL;
    ProductBLL productBLL;
    String[] names;
    boolean isClient;

    NamesBox(String[] names,boolean isClient){
        super(names);
        this.names = names;
        this.setBackground(new Color(0xff6200));
        this.setForeground(Color.WHITE);
        this.setPreferredSize(new Dimension(150,30));
        this.setFont(new Font("Consolas", Font.PLAIN, 20));
        this.addItemListener(this);
        clientBLL = new ClientBLL();
        productBLL = new ProductBLL();
        this.isClient = isClient;
        if(isClient == true){
            cl = clientBLL.findClientByName(names[0]);
        } else {
            pr = productBLL.findProductByName(names[0]);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getSource() == this){
            if(isClient == true)
            {
                cl = clientBLL.findClientByName((String) this.getSelectedItem());
            } else {
                pr = productBLL.findProductByName((String) this.getSelectedItem());
            }
        }
    }
}
