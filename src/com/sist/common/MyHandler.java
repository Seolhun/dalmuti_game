package com.sist.common;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.*;
//SAX(Simple API for XML) ÇÑÁÙ¾¿ , DOM(Document Object Model) // RDONLY
public class MyHandler extends DefaultHandler{
	Map<String , DBConnector> map = new HashMap<String, DBConnector>();
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		try {
			if(qName.equals("bean")){
				String key = attributes.getValue("id");
				String clsName = attributes.getValue("class");
				Class cls = Class.forName(clsName);
				DBConnector i = (DBConnector)cls.newInstance();
				map.put(key, i);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
