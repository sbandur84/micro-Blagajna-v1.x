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
import java.sql.*;

/**
 *
 * @author JG uniCenta
 */
public abstract class JDBCSentence extends BaseSentence {
    
    // Conexion
    // protected Connection m_c;

    /**
     *
     */
        protected Session m_s;
    
    /** Creates a new instance of BaseSentence
     * @param s */
    public JDBCSentence(Session s) {
        super();
        m_s = s; 
    }

    /**
     *
     */
    protected static final class JDBCDataResultSet implements DataResultSet {
        
        private ResultSet m_rs;
        private SerializerRead m_serread;
//        private int m_iColumnCount;

            /**
             *
             * @param rs
             * @param serread
             */
            public JDBCDataResultSet(ResultSet rs, SerializerRead serread) {
            m_rs = rs;
            m_serread = serread;
//            m_iColumnCount = -1;
        }

            /**
             *
             * @param columnIndex
             * @return
             * @throws BasicException
             */
            public Integer getInt(int columnIndex) throws BasicException {
            try {
                int iValue = m_rs.getInt(columnIndex);
                return m_rs.wasNull() ? null : new Integer(iValue);
            } catch (SQLException eSQL) {
                throw new BasicException(eSQL);
            }
        }

            /**
             *
             * @param columnIndex
             * @return
             * @throws BasicException
             */
            public String getString(int columnIndex) throws BasicException {
            try {
                return m_rs.getString(columnIndex);
            } catch (SQLException eSQL) {
                throw new BasicException(eSQL);
            }
        }

            /**
             *
             * @param columnIndex
             * @return
             * @throws BasicException
             */
            public Double getDouble(int columnIndex) throws BasicException {
            try {
                double dValue = m_rs.getDouble(columnIndex);
                return m_rs.wasNull() ? null : new Double(dValue);
            } catch (SQLException eSQL) {
                throw new BasicException(eSQL);
            }
        }

            /**
             *
             * @param columnIndex
             * @return
             * @throws BasicException
             */
            public Boolean getBoolean(int columnIndex) throws BasicException {
            try {
                boolean bValue = m_rs.getBoolean(columnIndex);
                return m_rs.wasNull() ? null : new Boolean(bValue);
            } catch (SQLException eSQL) {
                throw new BasicException(eSQL);
            }
        }
        
            /**
             *
             * @param columnIndex
             * @return
             * @throws BasicException
             */
            public java.util.Date getTimestamp(int columnIndex) throws BasicException {        
            try {
                java.sql.Timestamp ts = m_rs.getTimestamp(columnIndex);
                return ts == null ? null : new java.util.Date(ts.getTime());
            } catch (SQLException eSQL) {
                throw new BasicException(eSQL);
            }
        }

            /**
             *
             * @param columnIndex
             * @return
             * @throws BasicException
             */
            public byte[] getBytes(int columnIndex) throws BasicException {
            try {
                return m_rs.getBytes(columnIndex);
            } catch (SQLException eSQL) {
                throw new BasicException(eSQL);
            }
        }

            /**
             *
             * @param columnIndex
             * @return
             * @throws BasicException
             */
            public Object getObject(int columnIndex) throws BasicException {
            try {
                return m_rs.getObject(columnIndex);
            } catch (SQLException eSQL) {
                throw new BasicException(eSQL);
            }
        }

            /**
             *
             * @return
             * @throws BasicException
             */
            public DataField[] getDataField() throws BasicException {
            try {
                ResultSetMetaData md = m_rs.getMetaData();
                DataField[] df = new DataField[md.getColumnCount()];
                for (int i = 0; i < df.length; i++) {
                    df[i] = new DataField();
                    df[i].Name = md.getColumnName(i + 1);
                    df[i].Size = md.getColumnDisplaySize(i + 1);
                    df[i].Type = md.getColumnType(i + 1);
                }
                return df;
            } catch (SQLException eSQL) {
                throw new BasicException(eSQL);
            }
        }

            /**
             *
             * @return
             * @throws BasicException
             */
            @Override
        public Object getCurrent() throws BasicException {
            return m_serread.readValues(this);
        }

            /**
             *
             * @return
             * @throws BasicException
             */
            @Override
        public boolean next() throws BasicException {
            try {
                return m_rs.next();
            } catch (SQLException eSQL) {
                throw new BasicException(eSQL);
            }
        }

            /**
             *
             * @throws BasicException
             */
            @Override
        public void close() throws BasicException {
            try {
                m_rs.close();
            } catch (SQLException eSQL) {
                throw new BasicException(eSQL);
            }
        }

            /**
             *
             * @return
             * @throws BasicException
             */
            @Override
        public int updateCount() throws BasicException {
            return -1; // es decir somos datos.
        }        
    }    
}
