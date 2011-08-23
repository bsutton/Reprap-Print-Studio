package threedc.github.com;

import java.io.EOFException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

//A wrapper for the binary contents of a file. Makes the bytes of a file available like an array.

public class FileBlobChar
{

	File file_path_;
	long file_size_;
	char[] bytes_;

	FileBlobChar(String path) throws IOException
	{
		file_path_ = new File(path);
		file_size_ = 0;
		bytes_ = null;
		Init();
	}

	long Size()
	{
		return file_size_;
	}

	void Init() throws IOException
	{
		FileReader fr = new FileReader(file_path_);
		file_size_ = file_path_.length();
		bytes_ = new char[(int) file_size_];
		fr.read(bytes_, 0, (int) file_size_);
	}

	public char at(int ni) throws EOFException
	{
		if (ni >= file_size_)
			throw new EOFException();
		
		return bytes_[ni];
	}

	public String getString(int startOfString, int length)
	{
		return new String(bytes_, startOfString, length);
	}

}
