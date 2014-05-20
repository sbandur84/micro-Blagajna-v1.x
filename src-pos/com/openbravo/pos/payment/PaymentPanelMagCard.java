//    uniCenta oPOS  - Touch Friendly Point Of Sale
//    Copyright (c) 2009-2014 uniCenta
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

package com.openbravo.pos.payment;

import com.openbravo.pos.forms.AppLocal;
import javax.swing.JComponent;

/**
 *
 * @author JG uniCenta
 */
public class PaymentPanelMagCard extends javax.swing.JPanel implements PaymentPanel {
    
    private JPaymentNotifier m_notifier;
    private MagCardReader m_cardreader;
    private String track1 = null;
    private String track2 = null;
    private String track3 = null;
    private String m_sTransactionID;
    private double m_dTotal;
    
    /** Creates new form JMagCardReader
     * @param cardreader
     * @param notifier */
    // public PaymentPanelMagCard(String sReader, JPaymentNotifier notifier) {
    public PaymentPanelMagCard(MagCardReader cardreader, JPaymentNotifier notifier) {
        
        m_notifier = notifier;
        m_cardreader = cardreader;
        // m_cardreader = MagCardReaderFac.getMagCardReader(sReader);

        initComponents();
        
        if (m_cardreader != null) {
            // Se van a poder efectuar pagos con tarjeta
            m_jKeyFactory.addKeyListener(new KeyBarsListener());   
            jReset.setEnabled(true);
        } else {
            jReset.setEnabled(false);
        }
    }
    
    /**
     *
     * @return
     */
    @Override
    public JComponent getComponent(){
        return this;
    }
    
    /**
     *
     * @param sTransaction
     * @param dTotal
     */
    @Override
    public void activate(String sTransaction, double dTotal) {
        
        m_sTransactionID = sTransaction;
        m_dTotal = dTotal;
        
        resetState();
        
        m_jKeyFactory.setText(null);       
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                m_jKeyFactory.requestFocus();
            }
        });
    }
    
    private void resetState() {
        
        m_notifier.setStatus(false, false);  
              
        m_jHolderName.setText(null);
        m_jCardNumber.setText(null);
        m_jExpirationDate.setText(null);
        track1 = null;
        track2 = null;
        track3 = null;
        
        if (m_cardreader != null) {
            // Se van a poder efectuar pagos con tarjeta
            m_cardreader.reset();
        }
    }
    
    /**
     *
     * @return
     */
    @Override
    public PaymentInfoMagcard getPaymentInfoMagcard() {

        if (m_dTotal > 0.0) {
            return new PaymentInfoMagcard(
                    m_jHolderName.getText(),
                    m_jCardNumber.getText(), 
                    m_jExpirationDate.getText(),
                    track1,
                    track2,
                    track3,
                    m_sTransactionID,
                    m_dTotal);
        } else {
            return new PaymentInfoMagcardRefund(
                    m_jHolderName.getText(),
                    m_jCardNumber.getText(), 
                    m_jExpirationDate.getText(),
                    track1,
                    track2,
                    track3,
                    m_sTransactionID,
                    m_dTotal);
        }
    } 
    
    private void stateTransition(char cTrans) {
        
        m_cardreader.appendChar(cTrans);
        
        if (m_cardreader.isComplete()) {
            m_jHolderName.setText(m_cardreader.getHolderName());
            m_jCardNumber.setText(m_cardreader.getCardNumber());
            m_jExpirationDate.setText(m_cardreader.getExpirationDate()); 
            track1 = m_cardreader.getTrack1();
            track2 = m_cardreader.getTrack2();
            track3 = m_cardreader.getTrack3();
            m_notifier.setStatus(true, true);  
        } else {
            m_jHolderName.setText(null);
            m_jCardNumber.setText(null);
            m_jExpirationDate.setText(null); 
            track1 = null;
            track3 = null;
            track3 = null;
            m_notifier.setStatus(false, false);  
        }      
    }    
    
    private class KeyBarsListener extends java.awt.event.KeyAdapter {
        
        @Override
        public void keyTyped(java.awt.event.KeyEvent e){
            m_jKeyFactory.setText(null);
            stateTransition(e.getKeyChar());
        }
    }   
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jReset = new javax.swing.JButton();
        m_jKeyFactory = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        m_jExpirationDate = new javax.swing.JLabel();
        m_jCardNumber = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        m_jHolderName = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setText(AppLocal.getIntString("message.paymentgatewayswipe")); // NOI18N
        jPanel2.add(jLabel1);

        add(jPanel2, java.awt.BorderLayout.NORTH);

        jPanel1.setLayout(null);

        jReset.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/reload.png"))); // NOI18N
        jReset.setText(AppLocal.getIntString("button.reset")); // NOI18N
        jReset.setFocusPainted(false);
        jReset.setFocusable(false);
        jReset.setRequestFocusEnabled(false);
        jReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jResetActionPerformed(evt);
            }
        });
        jPanel1.add(jReset);
        jReset.setBounds(310, 20, 90, 30);
        jPanel1.add(m_jKeyFactory);
        m_jKeyFactory.setBounds(0, 0, 0, 0);

        jLabel6.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel6.setText(AppLocal.getIntString("label.cardnumber")); // NOI18N
        jPanel1.add(jLabel6);
        jLabel6.setBounds(20, 50, 100, 25);

        jLabel7.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel7.setText(AppLocal.getIntString("label.cardexpdate")); // NOI18N
        jPanel1.add(jLabel7);
        jLabel7.setBounds(20, 80, 100, 25);

        m_jExpirationDate.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jExpirationDate.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.darkShadow")), javax.swing.BorderFactory.createEmptyBorder(1, 4, 1, 4)));
        m_jExpirationDate.setOpaque(true);
        m_jExpirationDate.setPreferredSize(new java.awt.Dimension(150, 25));
        jPanel1.add(m_jExpirationDate);
        m_jExpirationDate.setBounds(120, 80, 70, 25);

        m_jCardNumber.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jCardNumber.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.darkShadow")), javax.swing.BorderFactory.createEmptyBorder(1, 4, 1, 4)));
        m_jCardNumber.setOpaque(true);
        m_jCardNumber.setPreferredSize(new java.awt.Dimension(180, 25));
        jPanel1.add(m_jCardNumber);
        m_jCardNumber.setBounds(120, 50, 180, 25);

        jLabel8.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel8.setText(AppLocal.getIntString("label.cardholder")); // NOI18N
        jPanel1.add(jLabel8);
        jLabel8.setBounds(20, 20, 100, 25);

        m_jHolderName.setBackground(java.awt.Color.white);
        m_jHolderName.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jHolderName.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.darkShadow")), javax.swing.BorderFactory.createEmptyBorder(1, 4, 1, 4)));
        m_jHolderName.setOpaque(true);
        m_jHolderName.setPreferredSize(new java.awt.Dimension(180, 25));
        jPanel1.add(m_jHolderName);
        m_jHolderName.setBounds(120, 20, 180, 25);

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jResetActionPerformed

        resetState();
        
    }//GEN-LAST:event_jResetActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton jReset;
    private javax.swing.JLabel m_jCardNumber;
    private javax.swing.JLabel m_jExpirationDate;
    private javax.swing.JLabel m_jHolderName;
    private javax.swing.JTextArea m_jKeyFactory;
    // End of variables declaration//GEN-END:variables
    
}
