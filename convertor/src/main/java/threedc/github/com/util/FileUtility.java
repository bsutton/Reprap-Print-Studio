package threedc.github.com.util;

import java.io.File;

public class FileUtility
{
	public static String getNamePart(java.io.File file)
	{
		String name = file.getName();
		
		int delimiterIndex = name.lastIndexOf('.');
		if (delimiterIndex > 0)
			name = name.substring(0, delimiterIndex);
		else if (delimiterIndex == 0) // the first characters is a dot.
			name = "";
		return name;
			
	}
	
	/** 
	 * Returns the extension of a filename excluding the dot.
	 * @param file
	 * @return
	 */
	static public String getExtension(File file)
	{
		String extension = "";
		String name = file.getName();
		
		int delimiterIndex = name.lastIndexOf('.');
		if (delimiterIndex != -1)
			extension = name.substring(delimiterIndex + 1);
		
		return extension;
			
	}
}
