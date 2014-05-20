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

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.IKeyed;
import com.openbravo.data.loader.SerializableRead;

/**
 *
 * @author adrianromero
 * Created on February 13, 2007, 10:13 AM
 *
 */
public class LocationInfo implements SerializableRead, IKeyed {
    
    private static final long serialVersionUID = 9032683595230L;
    private String m_sID;
    private String m_sName;
    private String m_sAddress;
    
    /** Creates a new instance of LocationInfo */
    public LocationInfo() {
        m_sID = null;
        m_sName = null;
        m_sAddress = null;
    }
    
    /**
     *
     * @return
     */
    public Object getKey() {
        return m_sID;
    }

    /**
     *
     * @param dr
     * @throws BasicException
     */
    public void readValues(DataRead dr) throws BasicException {
        m_sID = dr.getString(1);
        m_sName = dr.getString(2);
        m_sAddress = dr.getString(3);
    }

    /**
     *
     * @param sID
     */
    public void setID(String sID) {
        m_sID = sID;
    }
    
    /**
     *
     * @return
     */
    public String getID() {
        return m_sID;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return m_sName;
    }
    
    /**
     *
     * @param sName
     */
    public void setName(String sName) {
        m_sName = sName;
    }  

    /**
     *
     * @return
     */
    public String getAddress() {
        return m_sAddress;
    }
    
    /**
     *
     * @param sAddress
     */
    public void setAddress(String sAddress) {
        m_sAddress = sAddress;
    } 
    
    public String toString(){
        return m_sName;
    }    
}
