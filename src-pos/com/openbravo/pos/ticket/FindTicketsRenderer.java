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

package com.openbravo.pos.ticket;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JList;

/**
 *
 * @author Mikel Irurita
 */
public class FindTicketsRenderer extends DefaultListCellRenderer {
    
    private Icon icoTicketNormal;
    private Icon icoTicketRefund;

    /**
     *
     */
    public static final int RECEIPT_NORMAL = 0;
    
    /** Creates a new instance of ProductRenderer */
    public FindTicketsRenderer() {
        this.icoTicketNormal = new ImageIcon(getClass().getClassLoader().getResource("com/openbravo/images/pay.png"));
        this.icoTicketRefund = new ImageIcon(getClass().getClassLoader().getResource("com/openbravo/images/refundit.png"));
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, null, index, isSelected, cellHasFocus);

        int ticketType = ((FindTicketsInfo)value).getTicketType();
        setText("<html><table>" + value.toString() +"</table></html>");
        if (ticketType == RECEIPT_NORMAL) {
            setIcon(icoTicketNormal);
        } else {
            setIcon(icoTicketRefund);
        }
        
        return this;
    }   
}
