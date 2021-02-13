package qa.lib.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import org.apache.commons.lang3.RandomUtils;
import org.testng.annotations.DataProvider;

import qa.lib.base.ContextLoader;

public class CSVDataProvider {

	
	public CSVDataProvider() {
	}

	
	@DataProvider(name = "CSVDataProvider",parallel = true)
	public Iterator<Object[]> getDataProvider(Method m) throws IOException{
		return parseCSVData(ContextLoader.getAttributeString("csv-data-providers-path") 
				+ "/" + m.getDeclaringClass().getSimpleName()+"."+m.getName()+".csv");
	}
	
	public Iterator<Object[]> parseCSVData(String csvFilePath) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(csvFilePath));
		String line = "";
		String separator = ContextLoader.getAttributeString("csv-separator");
		ArrayList<Object[]> rows = new ArrayList<Object[]>();

		// Leo la primera linea para remover encabezados de csv
		line = br.readLine();

		while ((line = br.readLine()) != null) {
			List<Object> csvArray = new ArrayList<Object>();
			for (String s : line.trim().split(separator)) {
				
				if (s.startsWith("__"))
					s = generateTestData(s);
				//incluir método para reemplazar keywords para obtener datos de prueba generados
				csvArray.add(s);
			}
			
			rows.add(csvArray.toArray());
		}
		br.close();
		
		return rows.iterator();
	}
	
	public String generateTestData(String keyword) 
	{
		String result = "";
		if (keyword.contains(":"))
		{
			String dataType = keyword.split(":")[0];
			int dataLength = Integer.parseInt(keyword.split(":")[1]);
			
			switch (dataType) {
			case "__alpha":
				result = createRandomString(dataLength, true, true, false, false);
				break;
			case "__alphanumeric":
				result = createRandomString(dataLength, true, true, true, false);
				break;
			case "__numeric":
				result = createRandomString(dataLength, false, false, true, false);
				break;
			case "__special":
				result = createRandomString(dataLength, false, false, false, true);
				break;
			case "__alphanumericspecial":
				result = createRandomString(dataLength, true, true, true, true);
				break;
			case "__numericspecial":
				result = createRandomString(dataLength, false, false, true, true);
				break;
			case "__alphaspecial":
				result = createRandomString(dataLength, true, true, false, true);
				break;
			case "__mxdomain":
				result = createRandomString(dataLength, false, true, true, false)+".mx";
				break;
			}
		}
		
		return result;
	}
	
	public String createRandomString(int length, boolean alphaUpper, boolean alphaLower, boolean numeric, boolean special)
	{
		String alphaUpperSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String alphaLowerSet = "abcdefghijklmnopqrstuvwzyx";
		String numericSet =	"1234567890";
		String specialSet = "°!\"#$%&/()=?¡'¿|*+´[]}{_:;,.-";
		String charSet = "";
		
		String randomString = "";
		
		if (alphaUpper)
			charSet=charSet.concat(alphaUpperSet);
		if (alphaLower)
			charSet=charSet.concat(alphaLowerSet);
		if (numeric)
			charSet=charSet.concat(numericSet);
		if (special)
			charSet=charSet.concat(specialSet);
		
		for (int i = 0 ; i < length ; i++)
				randomString=randomString.concat(charSet.charAt(RandomUtils.nextInt(0,charSet.length()))+"");
		
		return randomString;
	}

}
