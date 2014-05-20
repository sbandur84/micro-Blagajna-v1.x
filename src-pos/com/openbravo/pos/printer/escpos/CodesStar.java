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

package com.openbravo.pos.printer.escpos;

import com.openbravo.pos.printer.DevicePrinter;
import com.openbravo.pos.printer.DeviceTicket;
import java.awt.image.BufferedImage;

/**
 *
 * @author JG uniCenta
 */
public class CodesStar extends Codes {

    // set line interspacing to 4mm

    /**
     *
     */
        public static final byte[] INITSEQUENCE = {0x1B, 0x7A, 0x01};

    private static final byte[] CHAR_SIZE_0 = {0x1B, 0x69, 0x00, 0x00};
    private static final byte[] CHAR_SIZE_1 = {0x1B, 0x69, 0x01, 0x00};
    private static final byte[] CHAR_SIZE_2 = {0x1B, 0x69, 0x00, 0x01};
    private static final byte[] CHAR_SIZE_3 = {0x1B, 0x69, 0x01, 0x01};

    private static final byte[] BOLD_SET = {0x1B, 0x45};
    private static final byte[] BOLD_RESET = {0x1B, 0x46};

    private static final byte[] UNDERLINE_SET = {0x1B, 0x2D, 0x01};
    private static final byte[] UNDERLINE_RESET = {0x1B, 0x2D, 0x00};

    private static final byte[] OPEN_DRAWER = {0x1C};    
    private static final byte[] PARTIAL_CUT = {0x1B, 0x64, 0x30};
    private static final byte[] IMAGE_BEGIN = {0x1B, 0x30};
    private static final byte[] IMAGE_END = {0x1B, 0x7A, 0x01};
    private static final byte[] IMAGE_HEADER = {0x1B, 0x4B};
    private static final byte[] IMAGE_LOGO = {0x1B, 0x1C, 0x70,0x01, 0x00};
    private static final byte[] NEW_LINE = {0x0D, 0x0A}; // Print and carriage return

    
    /** Creates a new instance of CodesStar */
    public CodesStar() {
    }

    /**
     *
     * @return
     */
    @Override
    public byte[] getInitSequence() { return INITSEQUENCE; }
     
    /**
     *
     * @return
     */
    @Override
    public byte[] getSize0() { return CHAR_SIZE_0; }

    /**
     *
     * @return
     */
    @Override
    public byte[] getSize1() { return CHAR_SIZE_1; }

    /**
     *
     * @return
     */
    @Override
    public byte[] getSize2() { return CHAR_SIZE_2; }

    /**
     *
     * @return
     */
    @Override
    public byte[] getSize3() { return CHAR_SIZE_3; }

    /**
     *
     * @return
     */
    @Override
    public byte[] getBoldSet() { return BOLD_SET; }

    /**
     *
     * @return
     */
    @Override
    public byte[] getBoldReset() { return BOLD_RESET; }

    /**
     *
     * @return
     */
    @Override
    public byte[] getUnderlineSet() { return UNDERLINE_SET; }

    /**
     *
     * @return
     */
    @Override
    public byte[] getUnderlineReset() { return UNDERLINE_RESET; }
    
    /**
     *
     * @return
     */
    @Override
    public byte[] getOpenDrawer() { return OPEN_DRAWER; }    

    /**
     *
     * @return
     */
    @Override
    public byte[] getCutReceipt() { return PARTIAL_CUT; }   

    /**
     *
     * @return
     */
    @Override
    public byte[] getNewLine() { return NEW_LINE; } 

    /**
     *
     * @return
     */
    @Override
    public byte[] getImageHeader() { return IMAGE_HEADER; }     

    /**
     *
     * @return
     */
    @Override
    public int getImageWidth() { return 192; }
    
    /**
     *
     * @return
     */
    @Override
    public byte[] getImageLogo(){ return IMAGE_LOGO; }

    /**
     *
     * @param image
     * @return
     */
    @Override
    public byte[] transImage(BufferedImage image) {

        CenteredImage centeredimage = new CenteredImage(image, getImageWidth());
                        
        int iWidth = centeredimage.getWidth();
        int iHeight = (centeredimage.getHeight() + 7) / 8; //
        
        // Array de datos
        byte[] bData = new byte[
                IMAGE_BEGIN.length +
                (getImageHeader().length + 2 + iWidth + getNewLine().length) * iHeight +
                IMAGE_END.length];
        
        // Comando de impresion de imagen
        
        int index = 0;

        System.arraycopy(IMAGE_BEGIN, 0, bData, index, IMAGE_BEGIN.length);
        index += IMAGE_BEGIN.length;

        // Raw data
        int p;
        for (int i = 0; i < centeredimage.getHeight(); i += 8) {
            System.arraycopy(getImageHeader(), 0, bData, index, getImageHeader().length);
            index += getImageHeader().length;
            
        // Line Dimension
        // JG note: nested ++'s not good construct need change later            
            bData[index ++] = (byte) (iWidth % 256);
            bData[index ++] = (byte) (iWidth / 256);           
            
            for (int j = 0; j < centeredimage.getWidth(); j++) {
                p = 0x00;
                for (int d = 0; d < 8; d ++) {
                    p = p << 1;
                   if (centeredimage.isBlack(j, i + d)) {
                        p = p | 0x01;
                    }
                }
                
                bData[index ++] = (byte) p;
            }
            System.arraycopy(getNewLine(), 0, bData, index, getNewLine().length);
            index += getNewLine().length;
        
        }

        System.arraycopy(IMAGE_END, 0, bData, index, IMAGE_END.length);
        index += IMAGE_END.length;

        return bData;
    }

    /**
     *
     * @param out
     * @param type
     * @param position
     * @param code
     */
    @Override
    public void printBarcode(PrinterWritter out, String type, String position, String code) {

        if (DevicePrinter.BARCODE_EAN13.equals(type)) {

            // out.write(getNewLine());

            out.write(new byte[] {0x1B, 0x1D, 0x61, 0x01}); // Align center

            out.write(new byte[] {0x1B, 0x62, 0x03});
            if (DevicePrinter.POSITION_NONE.equals(position)) {
                out.write(new byte[]{0x01});
            } else {
                out.write(new byte[]{0x02});
            }
            out.write(new byte[]{0x02}); // dots
            out.write(new byte[]{0x50}); // height
            out.write(DeviceTicket.transNumber(DeviceTicket.alignBarCode(code,13).substring(0,12)));
            out.write(new byte[] { 0x1E }); // end char

            out.write(new byte[] {0x1B, 0x1D, 0x61, 0x00}); // Align left

        }
    }
}
