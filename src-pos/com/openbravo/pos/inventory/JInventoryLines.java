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

package com.openbravo.pos.inventory;

import com.openbravo.format.Formats;
import com.openbravo.pos.forms.AppLocal;
import java.awt.Component;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author adrianromero
 */
public class JInventoryLines extends javax.swing.JPanel {

    private InventoryTableModel m_inventorylines;
    
    /** Creates new form JInventoryLines */
    public JInventoryLines() {
        
        initComponents();
        
        DefaultTableColumnModel columns = new DefaultTableColumnModel();
        TableColumn c;
        
        c = new TableColumn(0, 200
                , new DataCellRenderer(javax.swing.SwingConstants.LEFT)
                , new DefaultCellEditor(new JTextField()));
        c.setHeaderValue(AppLocal.getIntString("label.item"));
        columns.addColumn(c);
        c = new TableColumn(1, 75
                , new DataCellRenderer(javax.swing.SwingConstants.RIGHT)
                , new DefaultCellEditor(new JTextField()));
        c.setHeaderValue(AppLocal.getIntString("label.units"));
        columns.addColumn(c);
        c = new TableColumn(2, 75
                , new DataCellRenderer(javax.swing.SwingConstants.RIGHT)
                , new DefaultCellEditor(new JTextField()));
        c.setHeaderValue(AppLocal.getIntString("label.price"));
        columns.addColumn(c);
        
        m_tableinventory.setColumnModel(columns);       
        
        m_tableinventory.getTableHeader().setReorderingAllowed(false);         
        m_tableinventory.setRowHeight(40);
        m_tableinventory.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
        m_tableinventory.setIntercellSpacing(new java.awt.Dimension(0, 1));       
        
        m_inventorylines = new InventoryTableModel();
        m_tableinventory.setModel(m_inventorylines);        
    }
    
    /**
     *
     */
    public void clear() {
        m_inventorylines.clear();
    }
    
    /**
     *
     * @param i
     */
    public void addLine(InventoryLine i) {
        m_inventorylines.addRow(i);
        setSelectedIndex(m_inventorylines.getRowCount() - 1);        
    }
    
    /**
     *
     * @param index
     */
    public void deleteLine(int index) {
        m_inventorylines.removeRow(index);

        // Escojo una a seleccionar
        if (index >= m_inventorylines.getRowCount()) {
            index = m_inventorylines.getRowCount() - 1;
        }

        if ((index >= 0) && (index < m_inventorylines.getRowCount())) {
            // Solo seleccionamos si podemos.
            setSelectedIndex(index);
        }         
    }
    
    /**
     *
     * @param index
     * @param i
     */
    public void setLine(int index, InventoryLine i) {
        m_inventorylines.setRow(index, i);
        setSelectedIndex(index);
    }
    
    /**
     *
     * @param index
     * @return
     */
    public InventoryLine getLine(int index) {
        return m_inventorylines.getRow(index);
    }
    
    /**
     *
     * @return
     */
    public List<InventoryLine> getLines() {
        return m_inventorylines.getLines();
    }
    
    /**
     *
     * @return
     */
    public int getCount() {
        return m_inventorylines.getRowCount();
    }
    
    /**
     *
     * @return
     */
    public int getSelectedRow() {
        return m_tableinventory.getSelectedRow();
    }

    /**
     *
     * @param i
     */
    public void setSelectedIndex(int i){
        
        // Seleccionamos
        m_tableinventory.getSelectionModel().setSelectionInterval(i, i);

        // Hacemos visible la seleccion.
        Rectangle oRect = m_tableinventory.getCellRect(i, 0, true);
        m_tableinventory.scrollRectToVisible(oRect);
    }
    
    /**
     *
     */
    public void goDown() {

        int i = m_tableinventory.getSelectionModel().getMaxSelectionIndex();
        if (i < 0){
            i =  0; // No hay ninguna seleccionada
        } else {
            i ++;
            if (i >= m_inventorylines.getRowCount()) {
                i = m_inventorylines.getRowCount() - 1;
            }
        }

        if ((i >= 0) && (i < m_inventorylines.getRowCount())) {
            // Solo seleccionamos si podemos.
     
            setSelectedIndex(i);
        }        
    }
    
    /**
     *
     */
    public void goUp() {
        int i = m_tableinventory.getSelectionModel().getMinSelectionIndex();
        if (i < 0){
            i = m_inventorylines.getRowCount() - 1; // No hay ninguna seleccionada
        } else {
            i --;
            if (i < 0) {
                i = 0;
            }
        }

        if ((i >= 0) && (i < m_inventorylines.getRowCount())) {
            // Solo seleccionamos si podemos.
            setSelectedIndex(i);
        }        
    }
    
    private static class InventoryTableModel extends AbstractTableModel {
// JG 16 May 2013 use diamond inference        
        private ArrayList<InventoryLine> m_rows = new ArrayList<>();
        
        @Override
        public int getRowCount() {
            return m_rows.size();
        }
        @Override
        public int getColumnCount() {
            return 3;
        }
        @Override
        public String getColumnName(int column) {
            //return AppLocal.getIntString(m_acolumns[column].name);
            return "a";
        }
        @Override
        public Object getValueAt(int row, int column) {
            
            InventoryLine i = m_rows.get(row);
            switch (column) {
                case 0: return "<html>" + i.getProductName() + (
                        i.getProductAttSetInstDesc() == null
                        ? ""
                        : "<br>" + i.getProductAttSetInstDesc());
                case 1: return "x" + Formats.DOUBLE.formatValue(i.getMultiply());
                case 2: return Formats.CURRENCY.formatValue(i.getPrice());
                default: return null;
            }

        }
  
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
        
        public void clear() {
            int old = getRowCount();
            if (old > 0) { 
                m_rows.clear();
                fireTableRowsDeleted(0, old - 1);
            }
        }
    
        public List<InventoryLine> getLines() {
            return m_rows;
        }
    
        public InventoryLine getRow(int index) {
            return m_rows.get(index);
        }
        
        public void setRow(int index, InventoryLine oLine){
            
            m_rows.set(index, oLine);
            fireTableRowsUpdated(index, index);            
        }        
        
        public void addRow(InventoryLine oLine) {
            
            insertRow(m_rows.size(), oLine);
        }
        
        public void insertRow(int index, InventoryLine oLine) {
            
            m_rows.add(index, oLine);
            fireTableRowsInserted(index, index);
        }
        
        public void removeRow(int row) {
            m_rows.remove(row);
            fireTableRowsDeleted(row, row);
        }        
    }
    private static class DataCellRenderer extends DefaultTableCellRenderer {

        private int m_iAlignment;
        
        public DataCellRenderer(int align) {
            m_iAlignment = align;
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
            
            JLabel aux = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            aux.setVerticalAlignment(javax.swing.SwingConstants.TOP);
            aux.setHorizontalAlignment(m_iAlignment);
            if (!isSelected) {
                aux.setBackground(javax.swing.UIManager.getDefaults().getColor("TextField.disabledBackground"));
            }
            return aux;
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        m_tableinventory = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        jScrollPane1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        m_tableinventory.setAutoCreateColumnsFromModel(false);
        m_tableinventory.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        m_tableinventory.setFocusable(false);
        m_tableinventory.setRequestFocusEnabled(false);
        m_tableinventory.setShowVerticalLines(false);
        jScrollPane1.setViewportView(m_tableinventory);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable m_tableinventory;
    // End of variables declaration//GEN-END:variables
    
}
