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

import com.openbravo.data.loader.IKeyed;
import java.io.Serializable;

/**
 *
 * @author  adrianromero
 * @version 
 */
public class TaxCategoryInfo implements Serializable, IKeyed {
    
    private static final long serialVersionUID = 8959679342805L;
    private String m_sID;
    private String m_sName;
       
    /**
     *
     * @param sID
     * @param sName
     */
    public TaxCategoryInfo(String sID, String sName) {
        m_sID = sID;
        m_sName = sName;      
    }
    
    /**
     *
     * @return
     */
    @Override
    public Object getKey() {
        return m_sID;
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
   
    @Override
    public String toString(){
        return m_sName;
    }
}