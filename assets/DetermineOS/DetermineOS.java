package test;

import java.util.Locale;

/**
 * DetermineOS
 * 
 * @version 1.0 [2017. 8. 10.]
 * @author Choi
 */
public class DetermineOS {
	public static void main(String...args) {
		log(getOperatingSystemName());
	}
	
	private static String getOperatingSystemName() {
		String osName = System.getProperty("os.name").toLowerCase(Locale.ROOT);
		if(osName.startsWith("windows"))
			return "windows";
		else if(osName.startsWith("linux"))
			return "linux";
		else if(osName.startsWith("mac os"))
			return "darwin";
		else if(osName.startsWith("sunos")||osName.startsWith("solaris"))
			return "solaris";
		else return osName;
	}
	
	private static void log(String text) {
		System.out.println(text);
	}
}
