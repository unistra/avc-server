package org.ulpmm.language;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;


/**
 * Class which gives methods for internationalisation
 * @author ULP Multimédia - 2007
 *
 */
public class Messages {
	
	/**
	 *  The name of the bundle to search the corresponding language properties files
	 */
	private static final String BUNDLE_NAME = "org.ulpmm.language.messages"; 
	
	/**
	 * Uses Java ResourceBundles to get the translation of a String by specifying its key and locale
	 * @param key The key which defines the String in any language
	 * @param locale The Locale object of the language in which the translation must be returned
	 * @return The String translation in the language corresponding to the locale
	 */
	public static String _(String key, Locale locale) {
		
		try {	
			ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
			
			return bundle.getString(key);
			
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
