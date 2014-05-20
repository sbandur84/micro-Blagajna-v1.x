/**
 * <p>Title: AIM Java Version 1.4.1_02-b06</p>
 * <p>Description: Advanced Integration Method</p>
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: BluePay</p>
 * @author BluePay
 * @version 3.1
 */

/**
 *  Based on sample code and snipptes provided by:
 *  Patrick Phelan, phelan@choicelogic.com
 *  Roedy Green, Canadian Mind Products
 */

// Modifications by Adrian Romero & Mikel Irurita and further modified for blue pay by walter wojcik

package com.openbravo.pos.payment;

import com.openbravo.data.loader.LocalRes;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppProperties;
import com.openbravo.pos.util.AltEncrypter;
import java.io.*;
import java.net.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author JG uniCenta
 */
public class PaymentGatewayBluePayAUTHNETEMU implements PaymentGateway {
    
    private static String ENDPOINTADDRESS;
    private static final String OPERATIONVALIDATE = "AUTH_CAPTURE";
    private static final String OPERATIONREFUND = "CREDIT";
    private static final String APPROVED = "1";
    
    private String m_sCommerceID;
    private String m_sCommercePassword;
    private boolean m_bTestMode;

    /** Creates a new instance of PaymentGatewayBluePayAUTHNETEMU
     * @param props */
    public PaymentGatewayBluePayAUTHNETEMU(AppProperties props) {
        // Grab some configuration variables
        m_sCommerceID = props.getProperty("payment.BluePay.accountID");
        
        this.m_sCommercePassword = props.getProperty("payment.BluePay.secretKey");

        m_bTestMode = Boolean.valueOf(props.getProperty("payment.testmode")).booleanValue();
        
        ENDPOINTADDRESS = props.getProperty( "payment.BluePay.URL" );
    }

    /**
     *
     */
    public PaymentGatewayBluePayAUTHNETEMU() {
        
    }

    /**
     *
     * @param payinfo
     */
    @Override
    public void execute(PaymentInfoMagcard payinfo) {

// JG 16 May 12 use StringBuilder in place of StringBuilder
        StringBuilder sb = new StringBuilder();
        try {
            //test -> login:44CWBFp7wh9 / pass:43P7s8qb84CVT9Jx
            sb.append("x_cpversion=1.0");
            
            sb.append("&x_market_type=2");
            
            sb.append("&x_device_type=1");
            
            sb.append("&x_login=");        
            sb.append(URLEncoder.encode(m_sCommerceID, "UTF-8"));

            sb.append("&x_tran_key=");
            sb.append(URLEncoder.encode(m_sCommercePassword, "UTF-8"));
             
            sb.append("&x_amount=");
            NumberFormat formatter = new DecimalFormat("0000.00");
            String amount = formatter.format(Math.abs(payinfo.getTotal()));
            sb.append(URLEncoder.encode(amount.replace(',', '.'), "UTF-8"));
            
            if (payinfo.getTrack1(true) == null) {
                sb.append("&x_card_num=");
                sb.append(URLEncoder.encode(payinfo.getCardNumber(), "UTF-8"));

                sb.append("&x_exp_date=");
                String tmp = payinfo.getExpirationDate();
                sb.append(URLEncoder.encode(tmp, "UTF-8"));

                String[] cc_name = payinfo.getHolderName().split(" ");
                sb.append("&x_first_name=");
                if (cc_name.length > 0) {
                   sb.append(URLEncoder.encode(cc_name[0], "UTF-8"));
                }
                sb.append("&x_last_name=");
                if (cc_name.length > 1) {
                   sb.append(URLEncoder.encode(cc_name[1], "UTF-8"));
                }
            } else {
                // Example Track1
                // %B4111111111111111^PADILLA VISDOMINE/LUIS^0905123000000000000002212322222?5
                sb.append("&x_track1=");
                sb.append(payinfo.getTrack1(false));
                sb.append("&x_track2=");
                sb.append(payinfo.getTrack2(false));
            }
            
            sb.append("&x_method=CC");
            sb.append("&x_version=3.1");
            sb.append("&x_delim_data=TRUE");
            sb.append("&x_delim_char=|");
            sb.append("&x_relay_response=FALSE");
            sb.append("&x_test_request=");
            sb.append(m_bTestMode);
            
            //PAYMENT
            if (payinfo.getTotal() >= 0.0) {
                sb.append("&x_type=");
                sb.append(OPERATIONVALIDATE);
                //sb.append("&x_card_code=340"); //CCV
            }
            //REFUND
            else {
                sb.append("&x_type=");
                sb.append(OPERATIONREFUND);
                sb.append("&x_trans_id=");
                sb.append(payinfo.getTransactionID());
            }

            // open secure connection
            URL url = new URL(ENDPOINTADDRESS);

            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            //System.out.println(ENDPOINTADDRESS + "?" + sb.toString() );
            // not necessarily required but fixes a bug with some servers
            // JG May 12 added try-with-resources
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            try (DataOutputStream out = new DataOutputStream(connection.getOutputStream())) {
                out.write(sb.toString().getBytes());
                out.flush();
            }
            String returned;
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                returned = in.readLine();
            }
            
            BluePayParser anp = new BluePayParser(returned);
            Map props = anp.splitXML();
            //System.out.println(returned);
            if (anp.getResult().equals(LocalRes.getIntString("button.ok"))) {
                if (APPROVED.equals(props.get("ResponseCode"))) {
                    //Transaction approved
                    payinfo.paymentOK((String) props.get("AuthCode"), (String) props.get("TransID"), returned);
                } else {
                    StringBuilder errorLine = new StringBuilder();
                    //Transaction declined
                    if (anp.getNumErrors()>0) {
                        
                        for (int i=1; i<=anp.getNumErrors(); i++) {
                            errorLine.append(props.get("ErrorCode"+Integer.toString(i)));
                            errorLine.append(": ");
                            errorLine.append(props.get("ErrorText"+Integer.toString(i)));
                            errorLine.append("\n");
                        }
                    }
                    payinfo.paymentError(AppLocal.getIntString("message.paymenterror"), errorLine.toString());
                }
            }
            else {
                payinfo.paymentError(anp.getResult(), "");
            }
           
// JG 16 May 12 use multicatch
        } catch (UnsupportedEncodingException | MalformedURLException eUE) {
            payinfo.paymentError(AppLocal.getIntString("message.paymentexceptionservice"), eUE.getMessage());
        } catch(IOException e){
            payinfo.paymentError(AppLocal.getIntString("message.paymenterror"), e.getMessage());
        }
        
    }
    
    private class BluePayParser extends DefaultHandler {
    
    private SAXParser m_sp = null;
    private final Map props = new HashMap();
    private String text;
    private final InputStream is;
    private String result;
    private int numMessages = 0;
    private int numErrors = 0;
    
    public BluePayParser(String input) {
        is = new ByteArrayInputStream(input.getBytes());
    }
    
    public Map splitXML(){
        try {
            if (m_sp == null) {
                SAXParserFactory spf = SAXParserFactory.newInstance();
                m_sp = spf.newSAXParser();
            }
            m_sp.parse(is, this);
        } catch (ParserConfigurationException ePC) {
            result = LocalRes.getIntString("exception.parserconfig");
        } catch (SAXException eSAX) {
            result = LocalRes.getIntString("exception.xmlfile");
        } catch (IOException eIO) {
            result = LocalRes.getIntString("exception.iofile");
        }
        result = LocalRes.getIntString("button.ok");
        return props;
    }
      
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        try {
// JG 16 May 12 use switch
            switch (qName) {
                    case "ResponseCode":
                        props.put("ResponseCode", URLDecoder.decode(text, "UTF-8"));
                        text="";
                        break;
                    case "ErrorCode":
                        numErrors++;
                        props.put("ErrorCode"+Integer.toString(numErrors), URLDecoder.decode(text, "UTF-8"));
                        text = "";
                        break;
                    case "ErrorText":
                        props.put("ErrorText"+Integer.toString(numErrors), URLDecoder.decode(text, "UTF-8"));
                        text="";
                        break;
                    case "Code":
                        numMessages++;
                        props.put("Code"+Integer.toString(numMessages), URLDecoder.decode(text, "UTF-8"));
                        text = "";
                        break;
                    case "Description":
                        props.put("Description"+Integer.toString(numMessages), URLDecoder.decode(text, "UTF-8"));
                        text="";
                        break;
                    case "AuthCode":
                        props.put("AuthCode", URLDecoder.decode(text, "UTF-8"));
                        text="";
                        break;
                    case "AVSResultCode":
                        props.put("AVSResultCode", URLDecoder.decode(text, "UTF-8"));
                        text="";
                        break;
                    case "CVVResultCode":
                        props.put("CVVResultCode", URLDecoder.decode(text, "UTF-8"));
                        text="";
                        break;
                    case "TransID":
                        props.put("TransID", URLDecoder.decode(text, "UTF-8"));
                        text="";
                        break;
                    case "RefTransID":
                        props.put("RefTransID", URLDecoder.decode(text, "UTF-8"));
                        text="";
                        break;
                    case "TransHash":
                        props.put("TransHash", URLDecoder.decode(text, "UTF-8"));
                        text="";
                        break;
                    case "TestMode":
                        props.put("TestMode", URLDecoder.decode(text, "UTF-8"));
                        text="";
                        break;
                    case "UserRef":
                        props.put("UserRef", URLDecoder.decode(text, "UTF-8"));
                        text="";
                        break;
                }
        }
        catch(UnsupportedEncodingException eUE){
            result = eUE.getMessage();
        }
    }
    
    @Override
    public void startDocument() throws SAXException {
        text = new String();
    }

    @Override
    public void endDocument() throws SAXException {
    }
    
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (text!=null) {
            text = new String(ch, start, length);
        }
    }
    
    public String getResult(){
        return this.result;
    }
    
    public int getNumErrors(){
        return numErrors;
    }
    
    public int getNumMessages(){
        return numMessages;
    }
    
 }

}