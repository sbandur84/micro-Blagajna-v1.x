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

package com.openbravo.pos.printer;

import com.openbravo.pos.forms.AppLocal;

/**
 *
 * @author JG uniCenta
 */
public class DeviceDisplayNull implements DeviceDisplay {
    
    private String m_sName;
    private String m_sDescription;
    
    /** Creates a new instance of DeviceDisplayNull */
    public DeviceDisplayNull() {
        this(null);
    }
    
    /** Creates a new instance of DeviceDisplayNull
     * @param desc */
    public DeviceDisplayNull(String desc) {
        m_sName = AppLocal.getIntString("Display.Null");
        m_sDescription = desc;
    }

    /**
     *
     * @return
     */
    @Override
    public String getDisplayName() {
        return m_sName;
    }    

    /**
     *
     * @return
     */
    @Override
    public String getDisplayDescription() {
        return m_sDescription;
    }        

    /**
     *
     * @return
     */
    @Override
    public javax.swing.JComponent getDisplayComponent() {
        return null;
    }
    
    /**
     *
     */
    @Override
    public void clearVisor() {
    }      

    /**
     *
     * @param sLine1
     * @param sLine2
     */
    @Override
    public void writeVisor(String sLine1, String sLine2) {
    } 

    /**
     *
     * @param animation
     * @param sLine1
     * @param sLine2
     */
    @Override
    public void writeVisor(int animation, String sLine1, String sLine2) {
    } 
}
