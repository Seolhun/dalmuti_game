package com.sist.common;

import java.io.File;
import java.util.*;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class DAOContainer {
	Map<String, DBConnector> map = new HashMap<String, DBConnector>();

	public DAOContainer() {
		// TODO Auto-generated constructor stub

		try {
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();

			File file = new File("daoxml/dao.xml");
			System.out.println(file.getPath());
			MyHandler my = new MyHandler();
			sp.parse(file, my);
			map = my.map;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public DBConnector getBean(String key) {
		return map.get(key);
	}
}
