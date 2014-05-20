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

package com.openbravo.pos.panels;

import com.openbravo.basic.BasicException;
import com.openbravo.data.user.ListProvider;
import com.openbravo.data.user.ListProviderCreator;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.ticket.ProductFilterSales;
import com.openbravo.pos.ticket.ProductInfoExt;
import com.openbravo.pos.ticket.ProductRenderer;
import java.awt.*;
import javax.swing.JFrame;

/**
 *
 * @author adrianromero
 */
public class JProductFinder extends javax.swing.JDialog {

    private ProductInfoExt m_ReturnProduct;
    private ListProvider lpr;
    
    /**
     *
     */
    public final static int PRODUCT_ALL = 0;

    /**
     *
     */
    public final static int PRODUCT_NORMAL = 1;

    /**
     *
     */
    public final static int PRODUCT_AUXILIAR = 2;
    
    /** Creates new form JProductFinder */
    private JProductFinder(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
    }
    /** Creates new form JProductFinder */
    private JProductFinder(java.awt.Dialog parent, boolean modal) {
        super(parent, modal);
    }    
    
    private ProductInfoExt init(DataLogicSales dlSales, int productsType) {

        initComponents();
        
        jScrollPane1.getVerticalScrollBar().setPreferredSize(new Dimension(35, 35));

        //ProductFilter jproductfilter = new ProductFilter(app);
        ProductFilterSales jproductfilter = new ProductFilterSales(dlSales, m_jKeys);
        jproductfilter.activate();
        m_jProductSelect.add(jproductfilter, BorderLayout.CENTER);
        switch (productsType) {
            case PRODUCT_NORMAL:
                lpr = new ListProviderCreator(dlSales.getProductListNormal(), jproductfilter);
                break;
            case PRODUCT_AUXILIAR:               
                lpr = new ListProviderCreator(dlSales.getProductListAuxiliar(), jproductfilter);
                break;
            default: // PRODUCT_ALL
                lpr = new ListProviderCreator(dlSales.getProductList(), jproductfilter);
                break;
                
        }
       
        jListProducts.setCellRenderer(new ProductRenderer());
        
        getRootPane().setDefaultButton(jcmdOK);   
   
        m_ReturnProduct = null;
        
        //show();
        setVisible(true);
        
        return m_ReturnProduct;
    }
    
    
    private static Window getWindow(Component parent) {
        if (parent == null) {
            return new JFrame();
        } else if (parent instanceof Frame || parent instanceof Dialog) {
            return (Window)parent;
        } else {
            return getWindow(parent.getParent());
        }
    }

    /**
     *
     * @param parent
     * @param dlSales
     * @return
     */
    public static ProductInfoExt showMessage(Component parent, DataLogicSales dlSales) {
        return showMessage(parent, dlSales, PRODUCT_ALL);
    }

    /**
     *
     * @param parent
     * @param dlSales
     * @param productsType
     * @return
     */
    public static ProductInfoExt showMessage(Component parent, DataLogicSales dlSales, int productsType) {

        Window window = getWindow(parent);

        JProductFinder myMsg;
        if (window instanceof Frame) {
            myMsg = new JProductFinder((Frame) window, true);
        } else {
            myMsg = new JProductFinder((Dialog) window, true);
        }
        return myMsg.init(dlSales, productsType);
    }
    
    private static class MyListData extends javax.swing.AbstractListModel {
        
        private java.util.List m_data;
        
        public MyListData(java.util.List data) {
            m_data = data;
        }
        
        @Override
        public Object getElementAt(int index) {
            return m_data.get(index);
        }
        
        @Override
        public int getSize() {
            return m_data.size();
        } 
    }   
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        m_jKeys = new com.openbravo.editor.JEditorKeys();
        jPanel2 = new javax.swing.JPanel();
        m_jProductSelect = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListProducts = new javax.swing.JList();
        jPanel1 = new javax.swing.JPanel();
        jcmdCancel = new javax.swing.JButton();
        jcmdOK = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(AppLocal.getIntString("form.productslist")); // NOI18N

        jPanel4.setLayout(new java.awt.BorderLayout());
        jPanel4.add(m_jKeys, java.awt.BorderLayout.NORTH);

        getContentPane().add(jPanel4, java.awt.BorderLayout.LINE_END);

        jPanel2.setLayout(new java.awt.BorderLayout());

        m_jProductSelect.setLayout(new java.awt.BorderLayout());

        jButton3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/ok.png"))); // NOI18N
        jButton3.setText(AppLocal.getIntString("button.executefilter")); // NOI18N
        jButton3.setToolTipText("Execute Filter");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton3);

        m_jProductSelect.add(jPanel3, java.awt.BorderLayout.SOUTH);

        jPanel2.add(m_jProductSelect, java.awt.BorderLayout.NORTH);

        jPanel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jListProducts.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jListProducts.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListProducts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListProductsMouseClicked(evt);
            }
        });
        jListProducts.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListProductsValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jListProducts);

        jPanel5.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jcmdCancel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jcmdCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/cancel.png"))); // NOI18N
        jcmdCancel.setText(AppLocal.getIntString("Button.Cancel")); // NOI18N
        jcmdCancel.setMargin(new java.awt.Insets(8, 16, 8, 16));
        jcmdCancel.setMaximumSize(new java.awt.Dimension(103, 44));
        jcmdCancel.setMinimumSize(new java.awt.Dimension(103, 44));
        jcmdCancel.setPreferredSize(new java.awt.Dimension(103, 44));
        jcmdCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcmdCancelActionPerformed(evt);
            }
        });
        jPanel1.add(jcmdCancel);

        jcmdOK.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jcmdOK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/ok.png"))); // NOI18N
        jcmdOK.setText(AppLocal.getIntString("Button.OK")); // NOI18N
        jcmdOK.setEnabled(false);
        jcmdOK.setMargin(new java.awt.Insets(8, 16, 8, 16));
        jcmdOK.setMaximumSize(new java.awt.Dimension(103, 44));
        jcmdOK.setMinimumSize(new java.awt.Dimension(103, 44));
        jcmdOK.setPreferredSize(new java.awt.Dimension(103, 44));
        jcmdOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcmdOKActionPerformed(evt);
            }
        });
        jPanel1.add(jcmdOK);

        jPanel2.add(jPanel1, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-665)/2, (screenSize.height-565)/2, 665, 565);
    }// </editor-fold>//GEN-END:initComponents

    private void jListProductsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListProductsMouseClicked

        if (evt.getClickCount() == 2) {
            m_ReturnProduct = (ProductInfoExt) jListProducts.getSelectedValue();
            dispose();
        }
        
    }//GEN-LAST:event_jListProductsMouseClicked

    private void jcmdOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcmdOKActionPerformed
        
        m_ReturnProduct = (ProductInfoExt) jListProducts.getSelectedValue();
        dispose();
        
    }//GEN-LAST:event_jcmdOKActionPerformed

    private void jcmdCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcmdCancelActionPerformed
        
        dispose();
        
    }//GEN-LAST:event_jcmdCancelActionPerformed

    private void jListProductsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListProductsValueChanged

        jcmdOK.setEnabled(jListProducts.getSelectedValue() != null);
        
    }//GEN-LAST:event_jListProductsValueChanged

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        try {
            jListProducts.setModel(new MyListData(lpr.loadData()));
            if (jListProducts.getModel().getSize() > 0) {
                jListProducts.setSelectedIndex(0);
            }
        } catch (BasicException e) {
        }
        
    }//GEN-LAST:event_jButton3ActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton3;
    private javax.swing.JList jListProducts;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jcmdCancel;
    private javax.swing.JButton jcmdOK;
    private com.openbravo.editor.JEditorKeys m_jKeys;
    private javax.swing.JPanel m_jProductSelect;
    // End of variables declaration//GEN-END:variables
    
}
