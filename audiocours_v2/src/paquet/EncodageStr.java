package paquet;

import java.io.UnsupportedEncodingException;

public class EncodageStr {
/** 
  * Convertit les chaînes de caractère ISO8859-1 envoyées par défaut par les navigateurs
  * vers des chaînes de caractère en UTF-8
  */
	public static String toUTF8(String isoString) {
		String utf8String = null;
		if (null != isoString && !isoString.equals(""))
		{
			try
			{
				byte[] stringBytesISO = isoString.getBytes("ISO-8859-1");
				utf8String = new String(stringBytesISO, "UTF-8");
			}
			catch(UnsupportedEncodingException e)
			{
				utf8String = isoString;
			}
		}
		else
		{
			utf8String = isoString;
		}
		return utf8String;
	} 
}
