package com.pwc.XlsxReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public class Utill {

	public static void writeToPropertiesFile(HashMap<String, String> properties, String propertiesPath) {
		Properties props = new Properties();
		File propertiesFile = new File(propertiesPath);
		try {
			FileOutputStream xlsFos = new FileOutputStream(propertiesFile);

			Iterator mapIterator = properties.keySet().iterator();
			while (mapIterator.hasNext()) {
				String key = mapIterator.next().toString();
				String value = properties.get(key);
				if (key != null && value != null)
					props.setProperty(key, value);
				else
					System.out.println("key "+key+" | value "+value);
			}
			props.store(xlsFos, null);
			System.out.println(propertiesPath+" "+properties.size()+" props "+props.size());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
