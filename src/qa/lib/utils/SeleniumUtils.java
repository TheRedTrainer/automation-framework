package qa.lib.utils;

import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.Platform;

public class SeleniumUtils {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		System.out.println(Platform.valueOf("PATATA").toString());
		System.out.println(""+createRandomString(16,true,true,true,true));
	}
	
	public static String createRandomString(int length, boolean alphaUpper, boolean alphaLower, boolean numeric, boolean special)
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
