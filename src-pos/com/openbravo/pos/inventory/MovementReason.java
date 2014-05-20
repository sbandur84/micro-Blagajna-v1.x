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

import com.openbravo.pos.panels.ComboItemLocal;

/**
 *
 * @author adrianromero
 */
public class MovementReason extends ComboItemLocal {
    
    // public static final MovementReason NULL = new MovementReason(null, "");

    /**
     *
     */
        public static final MovementReason IN_PURCHASE = new MovementReason(+1, "stock.in.purchase");

    /**
     *
     */
    public static final MovementReason IN_REFUND = new MovementReason(+2, "stock.in.refund");

    /**
     *
     */
    public static final MovementReason IN_MOVEMENT = new MovementReason(+4, "stock.in.movement");

    /**
     *
     */
    public static final MovementReason OUT_SALE = new MovementReason(-1, "stock.out.sale");

    /**
     *
     */
    public static final MovementReason OUT_REFUND = new MovementReason(-2, "stock.out.refund");

    /**
     *
     */
    public static final MovementReason OUT_BREAK = new MovementReason(-3, "stock.out.break");

    /**
     *
     */
    public static final MovementReason OUT_MOVEMENT = new MovementReason(-4, "stock.out.movement");
    
    /**
     *
     */
    public static final MovementReason OUT_CROSSING = new MovementReason(1000, "stock.out.crossing");
   
    private MovementReason(Integer iKey, String sKeyValue) {
        super(iKey, sKeyValue);
    }

    /**
     *
     * @return
     */
    public boolean isInput() {
        return m_iKey.intValue() > 0;
    }

    /**
     *
     * @param d
     * @return
     */
    public Double samesignum(Double d) {
        
        if (d == null || m_iKey == null) {
            return d;
        } else if ((m_iKey.intValue() > 0 && d.doubleValue() < 0.0) ||
            (m_iKey.intValue() < 0 && d.doubleValue() > 0.0)) {
            return new Double(-d.doubleValue());
        } else {
            return d;
        }            
    }

    /**
     *
     * @param dBuyPrice
     * @param dSellPrice
     * @return
     */
    public Double getPrice(Double dBuyPrice, Double dSellPrice) {
        
        if (this == IN_PURCHASE || this == OUT_REFUND || this == OUT_BREAK) {
            return dBuyPrice;
        } else if (this == OUT_SALE || this == IN_REFUND) {
            return dSellPrice;
        } else {
            return null;
        }
    }
}
