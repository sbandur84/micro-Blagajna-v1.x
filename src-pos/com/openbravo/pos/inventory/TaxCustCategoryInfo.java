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

/**
 *
 * @author  adrianromero
 * @version 
 */
public class TaxCustCategoryInfo implements IKeyed {
    
    private static final long serialVersionUID = 8936482715929L;
    private String m_sID;
    private String m_sName;
    
    /** Creates new CategoryInfo
     * @param id
     * @param name */
    public TaxCustCategoryInfo(String id, String name) {
        m_sID = id;
        m_sName = name;
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
