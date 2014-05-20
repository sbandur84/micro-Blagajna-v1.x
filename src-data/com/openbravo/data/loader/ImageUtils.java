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

import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

/**
 *
 * @author JG uniCenta
 */
public class ImageUtils {
    
    private static char[] HEXCHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    
    /** Creates a new instance of ImageUtils */
    private ImageUtils() {
    }
    
    private static byte[] readStream(InputStream in) throws IOException {
        byte[] buffer = new byte[1024];
        byte[] resource = new byte[0];             
        int n;
        
        while ((n = in.read(buffer)) != -1) {
            byte[] b = new byte[resource.length + n];
            System.arraycopy(resource, 0, b, 0, resource.length);
            System.arraycopy(buffer, 0, b, resource.length, n);
            resource = b;
        }
        return resource;       
    }
    
    /**
     *
     * @param file
     * @return
     */
    public static byte[] getBytesFromResource(String file) {
        
        InputStream in = ImageUtils.class.getResourceAsStream(file);
        
        if (in == null) {
            return null;
        } else {        
            try {
                return ImageUtils.readStream(in);
            } catch (IOException e) {
                return new byte[0];
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
    }
    
    /**
     *
     * @param file
     * @return
     */
    public static BufferedImage readImageFromResource(String file) {
        return readImage(getBytesFromResource(file));
    }
    
    /**
     *
     * @param url
     * @return
     */
    public static BufferedImage readImage(String url) {
        try {
            return readImage(new URL(url));
        } catch (MalformedURLException e) {
            return null;
        }
    }
    
    /**
     *
     * @param url
     * @return
     */
    public static BufferedImage readImage(URL url) {
        
        InputStream in = null;
        
        try {
            URLConnection urlConnection = url.openConnection();
            in = urlConnection.getInputStream();
            return readImage(readStream(in));
        } catch (IOException e) {
            return null;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
            }
        }
    }
    
    /**
     *
     * @param b
     * @return
     */
    public static BufferedImage readImage(byte[] b) {
        if (b == null) {
            return null;
        } else {
            try {
                return ImageIO.read(new ByteArrayInputStream(b));
            } catch(IOException e) {
                return null;
            }
        }
    }
    
    /**
     *
     * @param img
     * @return
     */
    public static byte[] writeImage(BufferedImage img) {
        if (img == null) {
            return null;
        } else {        
            try {
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                ImageIO.write(img, "png", b);
                b.flush();
                b.close();
                return b.toByteArray();
            } catch(IOException e) {
                return null;
            }
        }
    }
    
    /**
     *
     * @param b
     * @return
     */
    public static Object readSerializable(byte[] b) {
        if (b == null) {
            return null;
        } else {        
            try {
                ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(b));
                Object obj = in.readObject();
                in.close();
                return obj;
            } catch (ClassNotFoundException eCNF) {
                //logger.error("Cannot create lists object", eCNF);    
                return null;
            } catch (IOException eIO) {
                //logger.error("Cannot load lists file", eIO);
                return null;
            }
        }
    }
    
    /**
     *
     * @param o
     * @return
     */
    public static byte[] writeSerializable(Object o) {
        
        if (o == null) {
            return null;
        } else {            
            try {
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(b);
                out.writeObject(o);
                out.flush();
                out.close();
                return b.toByteArray();
            } catch (IOException eIO) {
                eIO.printStackTrace();
                return null;
            }
        }
    }

    /**
     *
     * @param b
     * @return
     */
    public static Properties readProperties(byte b[]) {
        Properties prop = new Properties();
        try {
            if (b != null) {
                prop.loadFromXML(new ByteArrayInputStream(b));
            }
        } catch (IOException e) {
        }
        return prop;
    }
       
    /**
     *
     * @param binput
     * @return
     */
    public static String bytes2hex(byte[] binput) {
        
        StringBuilder s = new StringBuilder(binput.length *2);
        for (int i = 0; i < binput.length; i++) {
            byte b = binput[i];
            s.append(HEXCHARS[(b & 0xF0) >> 4]);
            s.append(HEXCHARS[b & 0x0F]);            
        }
        return s.toString();
    }    
}
