/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.InvoiceHeader;
import model.InvoiceHeaderTableModel;
import model.InvoiceLine;
import model.InvoiceLineTableModel;
import view.InvoiceFrame;
import view.NewInvoiceDialog;
import view.NewLineDialog;

/**
 *
 * @author ShafikS
 */
public class InvoiceController implements ActionListener, ListSelectionListener {

    private InvoiceFrame invoiceFrame;
    private NewInvoiceDialog newInvoiceDialog;
    private NewLineDialog newLineDialog;
    private InvoiceHeaderTableModel invoiceHeaderTableModel;
    public static SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy"); 

    // to get a refrence from the frame
    public InvoiceController(InvoiceFrame invoiceFrame) {
        this.invoiceFrame = invoiceFrame;
    }
    
    @Override
    public void valueChanged(ListSelectionEvent e) {
        int row= invoiceFrame.getInvoicesTable().getSelectedRow();
        if(row == -1)
        {
            invoiceFrame.getInvoiceNumber().setText("");
            invoiceFrame.getInvoiceDate().setText("");
            invoiceFrame.getCustomerName().setText("");
            invoiceFrame.getInvoiceTotal().setText("");
            invoiceFrame.getInvoiceItemTable().setModel(new InvoiceLineTableModel());      
        }
        else
        {
            InvoiceHeader invoiceHeader= invoiceFrame.getInvoiceHeaders().get(row);
            invoiceFrame.getInvoiceNumber().setText(String.valueOf(invoiceHeader.getInvoiceNum()));
            invoiceFrame.getInvoiceDate().setText(dateFormat.format(invoiceHeader.getInvoiceDate()));
            invoiceFrame.getCustomerName().setText(invoiceHeader.getCustomerName());
            invoiceFrame.getInvoiceTotal().setText(String.valueOf(invoiceHeader.getTotal()));
            invoiceFrame.getInvoiceItemTable().setModel(new InvoiceLineTableModel(invoiceHeader.getInvoiceLines()));
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        // to get the action command for each button and Menu item
        String actionCommand = e.getActionCommand();

        switch (actionCommand) {
            case "Create New Invoice":
                createNewInvoice();
                break;

            case "Delete Invoice":
                deleteInvoice();
                break;

            case "New Item":
                createInvoiceItem();
                break;

            case "Delete Item":
                deletelInvoiceItem();
                break;

            case "LoadFile":
                loadFile(null,null);
                break;

            case "SaveFile":
                saveFile();
                break;
                
            case "newInvoiceOk":
            {
                try {
                    newInvoiceOk();
                } catch (ParseException ex) {
                }
            }
                break;
               
            case "cancelInvoiceOk":
                cancelInvoiceOk();
                break;
                
            case "newLineOk":
                newLineOk();
                break;
                
            case "newLineCancel":
                newLineCancel();
                break;
                

        }

    }

    private void createNewInvoice() {
        newInvoice();
    }

    private void deleteInvoice() {
        int row= invoiceFrame.getInvoicesTable().getSelectedRow();
        if(row != -1)
        {
            InvoiceHeader invoiceHeader= invoiceFrame.getInvoiceHeaders().get(row);
            invoiceFrame.getInvoiceHeaders().remove(row);
            invoiceFrame.getInvoicesTable().setModel(new InvoiceHeaderTableModel(invoiceFrame.getInvoiceHeaders()));
        }
        else
        {
            JOptionPane.showMessageDialog(invoiceFrame, "Please select an invoice " , "Invoice isn't selected", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void createInvoiceItem() {
        int row= invoiceFrame.getInvoicesTable().getSelectedRow();
        if(row == -1)
        {
            JOptionPane.showMessageDialog(invoiceFrame, "Please select an invoice" , "Invoice isn't selected", JOptionPane.WARNING_MESSAGE);
        }
        else
        {
            newLineDialog= new NewLineDialog(invoiceFrame);
            newLineDialog.setVisible(true);

        }
        
    }

    private void deletelInvoiceItem() {
        int invoiceRow= invoiceFrame.getInvoicesTable().getSelectedRow();
        int lineRow= invoiceFrame.getInvoiceItemTable().getSelectedRow();
        if(invoiceRow != -1 && lineRow != -1)
        {
            InvoiceHeader invoiceHeader= invoiceFrame.getInvoiceHeaders().get(invoiceRow);
            invoiceHeader.getInvoiceLines().remove(lineRow);
            invoiceFrame.getInvoicesTable().setModel(new InvoiceHeaderTableModel(invoiceFrame.getInvoiceHeaders()));
            invoiceFrame.getInvoiceItemTable().setModel(new InvoiceLineTableModel(invoiceHeader.getInvoiceLines()));
            // to fire valueChanged(ListSelectionEvent e) function
            invoiceFrame.getInvoicesTable().setRowSelectionInterval(invoiceRow, invoiceRow);
        }
        else if(invoiceRow == -1)
        {
            JOptionPane.showMessageDialog(invoiceFrame, "Please select an invoice " , "Invoice isn't selected", JOptionPane.WARNING_MESSAGE);
        }
        
        else if(lineRow == -1)
        {
            JOptionPane.showMessageDialog(invoiceFrame, "Please select an invoice item " , "Invoice item isn't selected", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void loadFile(String headerFilePath, String lineFilePath){
        File headerFile = null;
        File lineFile = null;
        
        if(headerFilePath !=null && lineFilePath!= null)
        {
            headerFile= new File(headerFilePath);
            lineFile= new File(lineFilePath);
            
        }
        else
        {
            JFileChooser file= new JFileChooser();
            JOptionPane.showMessageDialog(invoiceFrame, "Please choose invoice header file" , "Invoice Header", JOptionPane.WARNING_MESSAGE);
            if(file.showOpenDialog(invoiceFrame)== JFileChooser.APPROVE_OPTION)
            {
                headerFile= file.getSelectedFile();
            }
            JOptionPane.showMessageDialog(invoiceFrame, "Please choose invoice line file" , "Invoice Line", JOptionPane.WARNING_MESSAGE);
            if(file.showOpenDialog(invoiceFrame)== JFileChooser.APPROVE_OPTION)
            {
                lineFile= file.getSelectedFile();
            }
        }
        
        if(headerFile != null && lineFile!=null)
        {
            ArrayList<String> headers= new ArrayList();
            ArrayList<String> lines= new ArrayList() ;
            InvoiceHeader invoiceHeader;
            InvoiceLine invoiceLine;
            Date date = new Date(); 
            try
            {
                headers= readFileData(headerFile);
                invoiceFrame.getInvoiceHeaders().clear();
                for(String row: headers)
                {
                    String[] fields= row.split(",");
                    int num= Integer.parseInt(fields[0]);
                    try { 
                        date= dateFormat.parse(fields[1]);
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(invoiceFrame, "Error while parsing date: " + ex.getMessage(), "Date Error", JOptionPane.ERROR_MESSAGE);
                    }
                   String customer= fields[2];
                   invoiceHeader= new InvoiceHeader(num,date,customer);
                   invoiceFrame.getInvoiceHeaders().add(invoiceHeader);
                   invoiceFrame.getInvoicesTable().setModel(new InvoiceHeaderTableModel(invoiceFrame.getInvoiceHeaders()));
                }
                
                lines= readFileData(lineFile);
                for(String row: lines)
                {
                    String[] fields= row.split(",");
                    String itemName= fields[1];
                    double itemPrice= Double.parseDouble(fields[2]);
                    int count= Integer.parseInt(fields[3]);
                    InvoiceHeader invoice= invoiceFrame.getInvoiceByNum(Integer.parseInt(fields[0]));
                    invoiceLine= new InvoiceLine(itemName,itemPrice,count,invoice);
                    invoice.getInvoiceLines().add(invoiceLine);
                }
            }
            catch(FileNotFoundException e)
            {
                e.printStackTrace();
                JOptionPane.showMessageDialog(invoiceFrame, "File not found" + e.getMessage(), "File Parsing Error", JOptionPane.ERROR_MESSAGE);
            }
            catch(IOException e)
            {
                e.printStackTrace();
                JOptionPane.showMessageDialog(invoiceFrame, "Error while parsing CSV file: " + e.getMessage(), "File Parsing Error", JOptionPane.ERROR_MESSAGE);
            }    
        } 
        
        // To print the Invoices data and its items
        printData();
    }

    private void saveFile() {
        
        File headerFile = null;
        File lineFile = null;
        JFileChooser file= new JFileChooser();
        JOptionPane.showMessageDialog(invoiceFrame, "Please choose invoice header file to save on it" , "Invoice Header", JOptionPane.WARNING_MESSAGE);
        if(file.showOpenDialog(invoiceFrame)== JFileChooser.APPROVE_OPTION)
        {
            headerFile= file.getSelectedFile();
        }
        JOptionPane.showMessageDialog(invoiceFrame, "Please choose invoice line file to save on it" , "Invoice Line", JOptionPane.WARNING_MESSAGE);
        if(file.showOpenDialog(invoiceFrame)== JFileChooser.APPROVE_OPTION)
        {
            lineFile= file.getSelectedFile();
        }
        
        if(headerFile != null && lineFile != null)
        {
            String headerFileContent="";
            String lineFileContent="";
            ArrayList<InvoiceHeader> invoiceHeaders= invoiceFrame.getInvoiceHeaders();
            
            for(InvoiceHeader invoice:invoiceHeaders)
            {
                headerFileContent +=invoice.getInvoiceData();
                headerFileContent += "\n";
                if(invoice.getInvoiceLines() !=null)
                {
                    for(InvoiceLine line: invoice.getInvoiceLines())
                    {
                        lineFileContent += line.getLineData();
                        lineFileContent += "\n";
                    }
                }

            }
            try {
            FileWriter headerWriter= new FileWriter(headerFile);
            FileWriter lineWriter= new FileWriter(lineFile);
            
            headerWriter.write(headerFileContent);
            lineWriter.write(lineFileContent);
            
            headerWriter.flush();
            lineWriter.flush();
            
            headerWriter.close();
            lineWriter.close();
            
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(invoiceFrame, "Error while writing to CSV file: " + ex.getMessage(), "File writing Error", JOptionPane.ERROR_MESSAGE);
            }
        }          
    }
    
    private void newInvoice()
    {
        newInvoiceDialog= new NewInvoiceDialog(invoiceFrame);
        newInvoiceDialog.setVisible(true);      
    }

    private void newInvoiceOk() throws ParseException
    {
        String customerName= newInvoiceDialog.getCustomerName();
        int num= invoiceFrame.getNewInvoiceNum();
        Date date= new Date();
        try {
            date=  dateFormat.parse(newInvoiceDialog.getInvoiceDate());
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(invoiceFrame, "Error while parsing date: " + ex.getMessage(), "Date Error", JOptionPane.ERROR_MESSAGE);
            throw new ParseException("Enter valid date format", 0);
        }
        InvoiceHeader invoiceHeader= new InvoiceHeader(num,date,customerName);
        invoiceFrame.getInvoiceHeaders().add(invoiceHeader);
        invoiceFrame.getInvoicesTable().setModel(new InvoiceHeaderTableModel(invoiceFrame.getInvoiceHeaders()));
        cancelInvoiceOk();
        
    }
    
    private void cancelInvoiceOk()
    {
        newInvoiceDialog.setVisible(false);
        newInvoiceDialog.dispose();
    }
    
    private void newLineOk()
    {
        String itemName= newLineDialog.getItemName();
        double itemPrice= Double.parseDouble(newLineDialog.getItemPrice());
        int itemCount= Integer.parseInt(newLineDialog.getItemCount());
        
        int row= invoiceFrame.getInvoicesTable().getSelectedRow();
        InvoiceHeader invoiceHeader= invoiceFrame.getInvoiceHeaders().get(row);
        invoiceHeader.getInvoiceLines().add(new InvoiceLine(itemName,itemPrice,itemCount,invoiceHeader));
       
        invoiceFrame.getInvoicesTable().setModel(new InvoiceHeaderTableModel(invoiceFrame.getInvoiceHeaders()));
        invoiceFrame.getInvoiceItemTable().setModel(new InvoiceLineTableModel(invoiceHeader.getInvoiceLines()));
        
        // to fire valueChanged(ListSelectionEvent e) function
        invoiceFrame.getInvoicesTable().setRowSelectionInterval(row, row);
        newLineCancel();
    }
    
    private void newLineCancel()
    {
        newLineDialog.setVisible(false);
        newLineDialog.dispose();
    }
    
    private ArrayList<String> readFileData(File file) throws IOException
    {
        String row = "";
        ArrayList<String> rows= new ArrayList();
        
        //using BufferedReader class to parse the CSV file  
        BufferedReader br = new BufferedReader(new FileReader(file));
        while ((row = br.readLine()) != null) 
        {
          rows.add(row);
        }
    
    return rows;
  }
    
   private void printData()
   {
       
   }
    

    
}
