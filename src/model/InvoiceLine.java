/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author ShafikS
 */
public class InvoiceLine {
    
    private String itemName;
    private double itemPrice;
    private int count;
    private InvoiceHeader invoiceHeader;

    public InvoiceLine(String itemName, double itemPrice, int count, InvoiceHeader invoiceHeader)
    {
        this.itemName= itemName;
        this.itemPrice= itemPrice;
        this.count= count;
        this.invoiceHeader= invoiceHeader;
    }
    
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public InvoiceHeader getInvoiceHeader() {
        return invoiceHeader;
    }

    public void setInvoiceHeader(InvoiceHeader invoiceHeader) {
        this.invoiceHeader = invoiceHeader;
    }
    
    public double getTotal()
    {
        return itemPrice*count;
    }
    
    public String getLineData()
    {
        return invoiceHeader.getInvoiceNum()+ ","+ itemName+ ","+ itemPrice+ ","+ count;
    }
    
     @Override
    public String toString() {
        return "InvoiceLine{" + "itemName=" + itemName + ", itemPrice=" + itemPrice + ", count=" + count + ", invoiceHeader=" + invoiceHeader + '}';
    }
    
    
    
}
