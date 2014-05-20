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

package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;

/**
 *
 * @author  adrian
 */
public class SerializerReadBuilder  implements SerializerRead {
    
    private SerializableBuilder m_sb;
    
    /** Creates a new instance of SerializerReadBuilder
     * @param sb */
    public SerializerReadBuilder(SerializableBuilder sb) {
        m_sb = sb;
    }
    
    /**
     *
     * @param dr
     * @return
     * @throws BasicException
     */
    public Object readValues(DataRead dr) throws BasicException {
        SerializableRead sr = m_sb.createNew();
        sr.readValues(dr);
        return sr;
    }
    
}
