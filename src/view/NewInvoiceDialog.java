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
public class NewInvoiceDialog extends JDialog {
    
    private JLabel invoiceDateLabel;
    private JLabel customerNameLabel;
    private JTextField invoiceDate;
    private JTextField customerName;
    private JButton okBtn;
    private JButton cancelBtn;
    
    public NewInvoiceDialog(InvoiceFrame frame)
    {
        invoiceDateLabel= new JLabel("Invoice Date");
        customerNameLabel=new JLabel("Customer Name");
        invoiceDate = new JTextField(15);
        customerName = new JTextField(15);
        okBtn= new JButton("Ok");
        cancelBtn= new JButton("Cancel");
        
        okBtn.setActionCommand("newInvoiceOk");
        cancelBtn.setActionCommand("cancelInvoiceOk");
        
        okBtn.addActionListener(frame.getController());
        cancelBtn.addActionListener(frame.getController());
        setLayout(new GridLayout(3, 2));
        
        add(invoiceDateLabel);
        add(invoiceDate);
        add(customerNameLabel);
        add(customerName);
        add(okBtn);
        add(cancelBtn);
        pack();

    }
    
    public String getInvoiceDate()
    {
        return invoiceDate.getText();
    }
    
        public String getCustomerName()
    {
        return customerName.getText();
    }
    
    
}
