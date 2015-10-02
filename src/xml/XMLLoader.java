/*
 * Copyright (C) 2015 Nicola Dileo
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package xml;


import events.Event;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author nicola
 */
public class XMLLoader {
    
    private DocumentBuilder db;
    private Document dom;
    
    public XMLLoader() {
        
    }
    
    public XMLErrorCode getXmlFile(String url) {
        XMLErrorCode code;
        
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            dom = db.parse(new URL(url).openStream());
            code = XMLErrorCode.GETOK;
            } 
        catch (SAXException ex) {
            Logger.getLogger(XMLLoader.class.getName()).log(Level.SEVERE, null, ex);
            code = XMLErrorCode.SAXEXCEPTION;
            } 
        catch (IOException ex) {
            Logger.getLogger(XMLLoader.class.getName()).log(Level.SEVERE, null, ex);
            code = XMLErrorCode.IOEXCEPTION;
            }
        catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLLoader.class.getName()).log(Level.SEVERE, null, ex);
            code = XMLErrorCode.PCEXCEPTION;            
        }
        
        return code;
    }
    
    public List<Event> parseDocument() {
        ArrayList<Event> events = new ArrayList<>();
        Element root = dom.getDocumentElement();
        
        NodeList nodes = root.getElementsByTagName("event");
        
        if (nodes != null && nodes.getLength() > 0) {
           for (int i = 0; i < nodes.getLength();i++) {
                Element el = (Element) nodes.item(i);
                Event e = getEvent(el);
                events.add(e);
            }
        }
        
        return events;
    }

    private Event getEvent(Element el) {
        String text = getTextValue(el, "text");
        //String time = getTextValue(el, "time");
        double longitude = Math.round(getDoubleValue(el, "longitude") * 100.0)/100.0;
        double latitude = Math.round(getDoubleValue(el, "latitude") * 100.0)/100.0;
        double depth = Math.round(getDoubleValue(el, "depth") * 100.0)/100.0;
        double magnitude = Math.round(getDoubleValue(el, "magnitude") * 100.0)/100.0;
        String time = getTimeValue(el);
        String type = getTextValue(el,"type");
        
        Event event = new Event(text, time, longitude, latitude, depth, magnitude, type);
        return event;
    }
    
    private String getTextValue(Element elem, String tagName) {
        String textVal = null;
        NodeList nl = elem.getElementsByTagName(tagName);
        
        if(nl != null && nl.getLength() > 0) {
            Element el = (Element)nl.item(0);
            textVal = el.getFirstChild().getNodeValue();
        }

        return textVal;
    }
    
    private double getDoubleValue(Element elem, String tagName) {
        double val = 0.0;
        NodeList nl = elem.getElementsByTagName(tagName);
        
        if(nl != null && nl.getLength() > 0) {
            Element temp = (Element)nl.item(0);
            NodeList tempList = temp.getElementsByTagName("value");
            if (tempList.getLength() > 0) {
                val = Double.parseDouble(((Element)tempList.item(0)).getFirstChild().getNodeValue());
            }
        }

        return val;
    }
    
    private String getTimeValue(Element elem) {
        String time = "";
        NodeList nl = elem.getElementsByTagName("time");
        
        if(nl != null && nl.getLength() > 0) {
            Element temp = (Element)nl.item(0);
            NodeList tempList = temp.getElementsByTagName("value");
            if (tempList.getLength() > 0) {
                time = (((Element)tempList.item(0)).getFirstChild().getNodeValue());
            }
        }
        
       
        return time;
    }
}
