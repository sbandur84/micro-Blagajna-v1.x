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

package com.openbravo.pos.comm;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.ParallelPort;
import gnu.io.SerialPort;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author adrianromero
 */
public class CommStream {    
    
    private String m_sPort;

    private CommPortIdentifier m_PortIdPrinter;
    private CommPort m_CommPortPrinter;  

    private OutputStream m_out;
    private InputStream m_in;
    
    /** Creates a new instance of CommPort
     * @param port */
    public CommStream(String port) {
        m_sPort = port;
        
        m_PortIdPrinter = null;
        m_CommPortPrinter = null;  
        m_out = null;
        m_in = null;
    }
    
    private void init() {
        
        try {  
            if (m_out == null) {
                m_PortIdPrinter = CommPortIdentifier.getPortIdentifier(m_sPort); // Tomamos el puerto                   
                m_CommPortPrinter = m_PortIdPrinter.open("PORTID", 2000); // Abrimos el puerto       

                m_out = m_CommPortPrinter.getOutputStream(); // Tomamos el chorro de escritura   

                if (m_PortIdPrinter.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                    ((SerialPort)m_CommPortPrinter).setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE); // Configuramos el puerto
                } else if (m_PortIdPrinter.getPortType() == CommPortIdentifier.PORT_PARALLEL) {
                    ((ParallelPort)m_CommPortPrinter).setMode(1);
                }
            }
        } catch (Exception e) {
            m_PortIdPrinter = null;
            m_CommPortPrinter = null;  
            m_out = null;
            m_in = null;
//        } catch (NoSuchPortException e) {
//        } catch (PortInUseException e) {
//        } catch (UnsupportedCommOperationException e) {
//        } catch (IOException e) {
        } 
    }
    
    /**
     *
     * @throws IOException
     */
    public void closeAll() throws IOException {
        
        if (m_out != null) {
            m_in = null;
            m_out.flush();
            m_out.close();
            m_out = null;
            m_CommPortPrinter = null;
            m_PortIdPrinter = null;
        }
    }
    
    /**
     *
     * @return
     */
    public String getPort() {
        return m_sPort;
    }
    
    /**
     *
     * @return
     */
    public InputStream getIn() {
        init();
        return m_in;
    }
    
    /**
     *
     * @return
     */
    public OutputStream getOut() {
        init();
        return m_out;
    }    
}
