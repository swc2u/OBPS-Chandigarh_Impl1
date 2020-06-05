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
	
	public static void main(String[] args) {
		LinkedHashMap< String, String > properties = new LinkedHashMap< String, String >();
		properties.put("key", "value");
		writeToPropertiesFile(properties,"resources.properties");
	}
	
	public static void writeToPropertiesFile(Map<String, String> properties,String propertiesPath) {

		// Creating a new Properties object
		Properties props = new Properties();

		// Creating a File object which will point to location of 
		// properties file
		File propertiesFile = new File(propertiesPath);

		try {

			// create a FileOutputStream by passing above properties file 
			FileOutputStream xlsFos = new FileOutputStream(propertiesFile);

			// Taking hashMaps keys by first converting it to Set and than
			// taking iterator over it.
			Iterator mapIterator = properties.keySet().iterator();

			// looping over iterator properties
			while(mapIterator.hasNext()) {

				// extracting keys and values based on the keys
				String key = mapIterator.next().toString();

				String value = properties.get(key);

				// setting each properties file in props Object 
				// created above
				//System.out.println(key+"  "+value);
				if(key!=null && value!=null)
				props.setProperty(key, value);

			}

			// Finally storing the properties to real 
			// properties file.
			props.store(xlsFos, null);

		} catch (FileNotFoundException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

}
