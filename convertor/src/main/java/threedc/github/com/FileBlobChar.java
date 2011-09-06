package threedc.github.com;

import java.io.EOFException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

//A wrapper for the binary contents of a file. Makes the bytes of a file available like an array.
public class FileBlobChar
{

	File filePath;
	long fileSize;
	char[] bytes;

	FileBlobChar(String path) throws IOException
	{
		filePath = new File(path);
		fileSize = 0;
		bytes = null;
		Init();
	}

	long Size()
	{
		return fileSize;
	}

	void Init() throws IOException
	{
		FileReader fr = new FileReader(filePath);
		fileSize = filePath.length();
		bytes = new char[(int) fileSize];
		fr.read(bytes, 0, (int) fileSize);
	}

	public char at(int ni) throws EOFException
	{
		if (ni >= fileSize)
			throw new EOFException();
		
		return bytes[ni];
	}

	public String getString(int startOfString, int length)
	{
		return new String(bytes, startOfString, length);
	}

}
