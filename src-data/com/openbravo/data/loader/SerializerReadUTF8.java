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
 * @author JG uniCenta
 */
public class SerializerReadUTF8 implements SerializerRead {
    
    /**
     *
     */
    public static final SerializerRead INSTANCE = new SerializerReadUTF8();
    
    /** Creates a new instance of SerializerReadImage */
    private SerializerReadUTF8() {
    }
    
    /**
     *
     * @param dr
     * @return
     * @throws BasicException
     */
    public Object readValues(DataRead dr) throws BasicException {
        try {
            return new String((byte[]) Datas.BYTES.getValue(dr,1), "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
    }
}
