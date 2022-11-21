/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.InvoiceController;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author ShafikS
 */
public class InvoiceHeader {
    
    private int invoiceNum;
    private Date invoiceDate;
    private String customerName;
    private ArrayList<InvoiceLine> invoiceLines;

    public InvoiceHeader(int invoiceNum, Date invoiceDate, String customerName) {
    this.invoiceNum = invoiceNum;
    this.invoiceDate = invoiceDate;
    this.customerName = customerName;
    }
        
    public int getInvoiceNum() {
        return invoiceNum;
    }

    public void setInvoiceNum(int invoiceNum) {
        this.invoiceNum = invoiceNum;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public ArrayList<InvoiceLine> getInvoiceLines() {
        // To avoid if invoiceLines is null
        if(invoiceLines == null)
        {
            invoiceLines = new ArrayList();
        }
        return invoiceLines;
    }
    
    //get the total price of the invoice
    public double getTotal()
    {
        double total=0.0;
        for(InvoiceLine line: getInvoiceLines())
        {
            total+= line.getTotal();
        }
        return total;
    }
    
    public String getInvoiceData()
    {
        return invoiceNum + "," + InvoiceController.dateFormat.format(invoiceDate) + "," + customerName;
    }

    @Override
    public String toString() {
        return "InvoiceHeader{" + "invoiceNum=" + invoiceNum + ", invoiceDate=" + invoiceDate + ", customerName=" + customerName + '}';
    }
    
  
}
