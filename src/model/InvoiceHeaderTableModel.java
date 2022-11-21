/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.InvoiceController;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ShafikS
 */
public class InvoiceHeaderTableModel extends AbstractTableModel{
    
    private String[] columns={"No.","Date","Customer","Total"};
    private ArrayList<InvoiceHeader> invoiceHeaders;
    
    public InvoiceHeaderTableModel(ArrayList<InvoiceHeader> invoiceHeaders)
    {
        this.invoiceHeaders= invoiceHeaders;
    }

    @Override
    public int getRowCount() {
        return invoiceHeaders.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceHeader invoiceHeader= invoiceHeaders.get(rowIndex);
        switch (columnIndex)
        {
              case 0:
                  return invoiceHeader.getInvoiceNum();
                
              case 1:
                 return InvoiceController.dateFormat.format(invoiceHeader.getInvoiceDate()); 
                 
              case 2:
                 return invoiceHeader.getCustomerName();  
                  
              case 3:
                  return invoiceHeader.getTotal();                                 
        }
        return null;              
    }
    
    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
    
}
