//    uniCenta oPOS  - Touch Friendly Point Of Sale
//    Copyright (C) 2009 Openbravo, S.L.
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

package com.openbravo.pos.config;

import com.openbravo.data.user.DirtyManager;
import com.openbravo.pos.util.StringParser;
import java.awt.Component;

/**
 *
 * @author adrian
 */
public interface ParametersConfig {

    /**
     *
     * @return
     */
    public Component getComponent();
    
    /**
     *
     * @param dirty
     */
    public void addDirtyManager(DirtyManager dirty);

    /**
     *
     * @param p
     */
    public void setParameters(StringParser p);

    /**
     *
     * @return
     */
    public String getParameters();

}
