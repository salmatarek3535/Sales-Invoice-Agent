/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ShafikS
 */
public class InvoiceLineTableModel extends AbstractTableModel{
    
    private String[] columns={"No.","Item Name","Item Price","Count", "Item Total"};
    private ArrayList<InvoiceLine> invoiceLines;
    
    public InvoiceLineTableModel(ArrayList<InvoiceLine> invoiceLines)
    {
        this.invoiceLines= invoiceLines;
    }

    public InvoiceLineTableModel() {
        invoiceLines= new ArrayList();
    }

     @Override
    public int getRowCount() {
        return invoiceLines.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceLine invoiceLine= invoiceLines.get(rowIndex);
        switch (columnIndex)
        {
              case 0:
                  return invoiceLine.getInvoiceHeader().getInvoiceNum();
                
              case 1:
                 return invoiceLine.getItemName(); 
                 
              case 2:
                 return invoiceLine.getItemPrice();  
                  
              case 3:
                  return invoiceLine.getCount(); 
                  
              case 4:
                  return invoiceLine.getTotal(); 
                  

        }
        return null;              
    }
    
    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
    
}
