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

package com.openbravo.pos.ticket;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.SerializableRead;
import com.openbravo.format.Formats;
import java.util.Date;

/**
 *
 * @author  Mikel irurita
 */
public class FindTicketsInfo implements SerializableRead {
    
    private int ticketid;
    private int tickettype;
    private Date date;
    private String name;
    private String customer;
    private double total;
    
    /** Creates new ProductInfo */
    public FindTicketsInfo() {
        
    }
    
    /**
     *
     * @param dr
     * @throws BasicException
     */
    @Override
    public void readValues(DataRead dr) throws BasicException {
        
        ticketid = dr.getInt(1);
        tickettype = dr.getInt(2);
        date = dr.getTimestamp(3);
        name = dr.getString(4);
        customer = dr.getString(5);
        total = (dr.getObject(6) == null) ? 0.0 : dr.getDouble(6);
    }
    
    @Override
    public String toString(){
        
        String sCustomer = (customer==null) ? "" : customer;

        String sHtml = "<tr><td width=\"50\">"+ "["+ ticketid +"]" +"</td>" +
                "<td width=\"100\">"+ Formats.TIMESTAMP.formatValue(date) +"</td>" +
                "<td align=\"center\" width=\"100\">"+ sCustomer +"</td>" +
                "<td align=\"right\" width=\"100\">"+ Formats.CURRENCY.formatValue(total) +"</td>"+
                "<td width=\"100\">"+ Formats.STRING.formatValue(name) +"</td></tr>";
        
        return sHtml;
    }
    
    /**
     *
     * @return
     */
    public int getTicketId(){
        return this.ticketid;
    }
    
    /**
     *
     * @return
     */
    public int getTicketType(){
        return this.tickettype;
    }
    
    
}
