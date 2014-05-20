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

package com.openbravo.pos.util;

import java.text.*;

/**
 *
 * @author JG uniCenta
 */
public final class CurrencyChange {

    /**
     *
     */
    public final static double EUROS_CHANGE = 166.386;

    private CurrencyChange() {
    }
  
    /**
     *
     * @param dEuros
     * @return
     */
    public static double changeEurosToPts(double dEuros) {        
        return Math.rint(dEuros * EUROS_CHANGE);
    }

    /**
     *
     * @param dPts
     * @return
     */
    public static double changePtsToEuros(double dPts) {        
        return Math.rint(100.0 * dPts / EUROS_CHANGE) / 100.0;
    }   
}
