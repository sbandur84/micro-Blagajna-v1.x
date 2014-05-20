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

package com.openbravo.pos.payment;

import javax.swing.JComponent;

/**
 *
 * @author JG uniCenta
 */
public interface PaymentPanel {
    
    /**
     *
     * @param sTransaction
     * @param dTotal
     */
    public void activate(String sTransaction, double dTotal);

    /**
     *
     * @return
     */
    public JComponent getComponent();

    /**
     *
     * @return
     */
    public PaymentInfoMagcard getPaymentInfoMagcard();
}
