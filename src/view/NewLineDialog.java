/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.plaf.basic.DefaultMenuLayout;

/**
 *
 * @author ShafikS
 */
public class NewLineDialog extends JDialog {
    
    private JLabel itemNameLabel;
    private JLabel itemPriceLabel;
    private JLabel itemCountLabel;
    private JTextField itemName;
    private JTextField itemPrice;
    private JTextField itemCount;
    private JButton okBtn;
    private JButton cancelBtn;
    
    public NewLineDialog(InvoiceFrame frame)
    {
        itemNameLabel= new JLabel("Item Name");
        itemPriceLabel=new JLabel("Item Price");
        itemCountLabel= new JLabel("Item Count");
        itemName = new JTextField(15);
        itemPrice = new JTextField(15);
        itemCount= new JTextField(15);
        okBtn= new JButton("Ok");
        cancelBtn= new JButton("Cancel");
        
        okBtn.setActionCommand("newLineOk");
        cancelBtn.setActionCommand("newLineCancel");
        
        okBtn.addActionListener(frame.getController());
        cancelBtn.addActionListener(frame.getController());
        setLayout(new GridLayout(4, 2));
        
        add(itemNameLabel);
        add(itemName);
        add(itemPriceLabel);
        add(itemPrice);
        add(itemCountLabel);
        add(itemCount);
        add(okBtn);
        add(cancelBtn);
        pack();

    }
    
    public String getItemName()
    {
        return itemName.getText();
    }
    
    public String getItemPrice()
    {
        return itemPrice.getText();
    }
    
    public String getItemCount()
    {
        return itemCount.getText();
    }
    
}
