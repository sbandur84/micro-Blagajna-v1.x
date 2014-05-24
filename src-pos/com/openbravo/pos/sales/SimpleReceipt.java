//    uniCenta oPOS  - Touch Friendly Point Of Sale
//    Copyright (c) 2009-2014 uniCenta & previous Openbravo POS works
//    http://www.unicenta.com
//
//    This file is part of uniCenta oPOS
//
//    uniCenta oPOS is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//   uniCenta oPOS is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with uniCenta oPOS.  If not, see <http://www.gnu.org/licenses/>.

package com.openbravo.pos.sales;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.pos.customers.DataLogicCustomers;
import com.openbravo.pos.customers.JCustomerFinder;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.ticket.TicketInfo;
import com.openbravo.pos.ticket.TicketLineInfo;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author  adrian
 */
public class SimpleReceipt extends javax.swing.JPanel {
    
    /**
     *
     */
    protected DataLogicCustomers dlCustomers;

    /**
     *
     */
    protected DataLogicSales dlSales;

    /**
     *
     */
    protected TaxesLogic taxeslogic;
        
    private JTicketLines ticketlines;
    private TicketInfo ticket;
    private Object ticketext;
    
    /** Creates new form SimpleReceipt
     * @param ticketline
     * @param dlSales
     * @param taxeslogic
     * @param dlCustomers */
    public SimpleReceipt(String ticketline, DataLogicSales dlSales, DataLogicCustomers dlCustomers, TaxesLogic taxeslogic) {        
        
        initComponents();
        
        // dlSystem.getResourceAsXML("Ticket.Line")
        ticketlines = new JTicketLines(ticketline);
        this.dlCustomers = dlCustomers;
        this.dlSales = dlSales;
        this.taxeslogic = taxeslogic;
        
        jPanel2.add(ticketlines, BorderLayout.CENTER);
    }
    
    /**
     *
     * @param value
     */
    public void setCustomerEnabled(boolean value) {
        btnCustomer.setEnabled(value);
    }
    
    /**
     *
     * @param ticket
     * @param ticketext
     */
    public void setTicket(TicketInfo ticket, Object ticketext) {
        
        this.ticket = ticket;
        this.ticketext = ticketext;
        
        // The ticket name
        m_jTicketId.setText(ticket.getName(ticketext));        
        
        ticketlines.clearTicketLines();
        for (int i = 0; i < ticket.getLinesCount(); i++) {
            ticketlines.addTicketLine(ticket.getLine(i));
        }
        
        if (ticket.getLinesCount() > 0) {
            ticketlines.setSelectedIndex(0);
        }
        
        printTotals();
               
    }
    
    private void refreshTicketTaxes() {
        
        for (TicketLineInfo line : ticket.getLines()) {
            line.setTaxInfo(taxeslogic.getTaxInfo(line.getProductTaxCategoryID(), ticket.getCustomer()));
        }
    }
    
    private void printTotals() {
        
        if (ticket.getLinesCount() == 0) {
            m_jSubtotalEuros.setText(null);
            m_jTaxesEuros.setText(null);
            m_jTotalEuros.setText(null);
        } else {
            m_jSubtotalEuros.setText(ticket.printSubTotal());
            m_jTaxesEuros.setText(ticket.printTax());
            m_jTotalEuros.setText(ticket.printTotal());
        }
    }
    
    /**
     *
     * @return
     */
    public TicketInfo getTicket()  {
        return ticket;
    }
    
    private int findFirstNonAuxiliarLine() {
        
        int i = ticketlines.getSelectedIndex();       
	while (i >= 0 && ticket.getLine(i).isProductCom()) {
	    i--;
        } 
        return i;
    }
    
    /**
     *
     * @return
     */
    public TicketLineInfo[] getSelectedLines() {
        
        // never returns an empty array, or null, or an array with at least one element.
               
        int i = findFirstNonAuxiliarLine();       
       
        if (i >= 0) {

            List<TicketLineInfo> l = new ArrayList<>();
            
            TicketLineInfo line = ticket.getLine(i);
            l.add(line);
            ticket.removeLine(i);
            ticketlines.removeTicketLine(i);
            
            // add also auxiliars
            while (i < ticket.getLinesCount() && ticket.getLine(i).isProductCom()) {
                l.add(ticket.getLine(i));
                ticket.removeLine(i);
                ticketlines.removeTicketLine(i);
            }        
            printTotals();
            return l.toArray(new TicketLineInfo[l.size()]);
        } else {
            return null;
        }
    }
    
    /**
     *
     * @return
     */
    public TicketLineInfo[] getSelectedLinesUnit() {

       // never returns an empty array, or null, or an array with at least one element.

        int i = findFirstNonAuxiliarLine();
        
        if (i >= 0) {       
            
            TicketLineInfo line = ticket.getLine(i);
            
            if (line.getMultiply() >= 1.0) {
                
                List<TicketLineInfo> l = new ArrayList<>();
                
                if (line.getMultiply() > 1.0) {
                    line.setMultiply(line.getMultiply() -1.0);
                    ticketlines.setTicketLine(i, line);
                    line = line.copyTicketLine();
                    line.setMultiply(1.0);
                    l.add(line);  
                    i++;
                } else { // == 1.0
                    l.add(line);
                    ticket.removeLine(i);
                    ticketlines.removeTicketLine(i);
                }
                
                // add also auxiliars
                while (i < ticket.getLinesCount() && ticket.getLine(i).isProductCom()) {
                    l.add(ticket.getLine(i));
                    ticket.removeLine(i);
                    ticketlines.removeTicketLine(i);
                }              
                printTotals();
                return l.toArray(new TicketLineInfo[l.size()]);                    
            } else { // < 1.0
                return null;
            }            
        } else {
            return null;
        }
    }

    /**
     *
     * @param lines
     */
    public void addSelectedLines(TicketLineInfo[] lines) {
        
        int i = findFirstNonAuxiliarLine();         
              
        TicketLineInfo firstline = lines[0];
        
        if (i >= 0 
                && ticket.getLine(i).getProductID() != null && firstline.getProductID() != null && ticket.getLine(i).getProductID().equals(firstline.getProductID())
                && ticket.getLine(i).getTaxInfo().getId().equals(firstline.getTaxInfo().getId())
                && ticket.getLine(i).getPrice() == firstline.getPrice()) {  
            
            // add the auxiliars.
            for (int j = 1; j < lines.length; j++) {
                ticket.insertLine(i + 1, lines[j]);
                ticketlines.insertTicketLine(i + 1, lines[j]);
            }
            
            // inc the line
            ticket.getLine(i).setMultiply(ticket.getLine(i).getMultiply() + firstline.getMultiply());
            ticketlines.setTicketLine(i, ticket.getLine(i));  
            ticketlines.setSelectedIndex(i);
            
        } else {
            // add all at the end in inverse order.
            int insertpoint = ticket.getLinesCount();
            for (int j = lines.length - 1; j >= 0; j--) {
                ticket.insertLine(insertpoint, lines[j]);
                ticketlines.insertTicketLine(insertpoint, lines[j]);
            }
        }       
        
        printTotals();
    }
  
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        m_jPanTotals = new javax.swing.JPanel();
        m_jTotalEuros = new javax.swing.JLabel();
        m_jLblTotalEuros1 = new javax.swing.JLabel();
        m_jSubtotalEuros = new javax.swing.JLabel();
        m_jTaxesEuros = new javax.swing.JLabel();
        m_jLblTotalEuros2 = new javax.swing.JLabel();
        m_jLblTotalEuros3 = new javax.swing.JLabel();
        m_jButtons = new javax.swing.JPanel();
        m_jTicketId = new javax.swing.JLabel();
        btnCustomer = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jPanel1.setLayout(new java.awt.BorderLayout());

        m_jPanTotals.setLayout(new java.awt.GridBagLayout());

        m_jTotalEuros.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jTotalEuros.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        m_jTotalEuros.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.darkShadow")), javax.swing.BorderFactory.createEmptyBorder(1, 4, 1, 4)));
        m_jTotalEuros.setOpaque(true);
        m_jTotalEuros.setPreferredSize(new java.awt.Dimension(150, 25));
        m_jTotalEuros.setRequestFocusEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        m_jPanTotals.add(m_jTotalEuros, gridBagConstraints);

        m_jLblTotalEuros1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jLblTotalEuros1.setText(AppLocal.getIntString("label.totalcash")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        m_jPanTotals.add(m_jLblTotalEuros1, gridBagConstraints);

        m_jSubtotalEuros.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jSubtotalEuros.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        m_jSubtotalEuros.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.darkShadow")), javax.swing.BorderFactory.createEmptyBorder(1, 4, 1, 4)));
        m_jSubtotalEuros.setOpaque(true);
        m_jSubtotalEuros.setPreferredSize(new java.awt.Dimension(150, 25));
        m_jSubtotalEuros.setRequestFocusEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        m_jPanTotals.add(m_jSubtotalEuros, gridBagConstraints);

        m_jTaxesEuros.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jTaxesEuros.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        m_jTaxesEuros.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.darkShadow")), javax.swing.BorderFactory.createEmptyBorder(1, 4, 1, 4)));
        m_jTaxesEuros.setOpaque(true);
        m_jTaxesEuros.setPreferredSize(new java.awt.Dimension(150, 25));
        m_jTaxesEuros.setRequestFocusEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        m_jPanTotals.add(m_jTaxesEuros, gridBagConstraints);

        m_jLblTotalEuros2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jLblTotalEuros2.setText(AppLocal.getIntString("label.taxcash")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        m_jPanTotals.add(m_jLblTotalEuros2, gridBagConstraints);

        m_jLblTotalEuros3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jLblTotalEuros3.setText(AppLocal.getIntString("label.subtotalcash")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        m_jPanTotals.add(m_jLblTotalEuros3, gridBagConstraints);

        jPanel1.add(m_jPanTotals, java.awt.BorderLayout.EAST);

        add(jPanel1, java.awt.BorderLayout.SOUTH);

        m_jButtons.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        m_jTicketId.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jTicketId.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        m_jTicketId.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.darkShadow")), javax.swing.BorderFactory.createEmptyBorder(1, 4, 1, 4)));
        m_jTicketId.setOpaque(true);
        m_jTicketId.setPreferredSize(new java.awt.Dimension(160, 25));
        m_jTicketId.setRequestFocusEnabled(false);
        m_jButtons.add(m_jTicketId);

        btnCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/customer_sml.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("pos_messages"); // NOI18N
        btnCustomer.setToolTipText(bundle.getString("caption.selectcustomer")); // NOI18N
        btnCustomer.setFocusPainted(false);
        btnCustomer.setFocusable(false);
        btnCustomer.setMargin(new java.awt.Insets(8, 14, 8, 14));
        btnCustomer.setMaximumSize(new java.awt.Dimension(50, 40));
        btnCustomer.setMinimumSize(new java.awt.Dimension(50, 40));
        btnCustomer.setPreferredSize(new java.awt.Dimension(50, 40));
        btnCustomer.setRequestFocusEnabled(false);
        btnCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCustomerActionPerformed(evt);
            }
        });
        m_jButtons.add(btnCustomer);

        add(m_jButtons, java.awt.BorderLayout.NORTH);

        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jPanel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel2.setLayout(new java.awt.BorderLayout());
        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCustomerActionPerformed
        
        JCustomerFinder finder = JCustomerFinder.getCustomerFinder(this, dlCustomers);
        finder.search(ticket.getCustomer());
        finder.setVisible(true);
        
        try {
            ticket.setCustomer(finder.getSelectedCustomer() == null
                    ? null
                    : dlSales.loadCustomerExt(finder.getSelectedCustomer().getId()));
        } catch (BasicException e) {
            MessageInf msg = new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.cannotfindcustomer"), e);
            msg.show(this);            
        }
        
        // The ticket name
        m_jTicketId.setText(ticket.getName(ticketext));
        
        refreshTicketTaxes();     
        
        // refresh the receipt....
        setTicket(ticket, ticketext);
        
    }//GEN-LAST:event_btnCustomerActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCustomer;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel m_jButtons;
    private javax.swing.JLabel m_jLblTotalEuros1;
    private javax.swing.JLabel m_jLblTotalEuros2;
    private javax.swing.JLabel m_jLblTotalEuros3;
    private javax.swing.JPanel m_jPanTotals;
    private javax.swing.JLabel m_jSubtotalEuros;
    private javax.swing.JLabel m_jTaxesEuros;
    private javax.swing.JLabel m_jTicketId;
    private javax.swing.JLabel m_jTotalEuros;
    // End of variables declaration//GEN-END:variables
    
}
