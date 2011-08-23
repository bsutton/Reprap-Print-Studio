package threedc.github.com;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

//A wrapper for the binary contents of a file. Makes the bytes of a file available like an array.
// We can handle at most MAX_INT bytes (2GB).
// TODO: generalise this method so we can manage bigger files and not have to load the
// whole thing into memory.
//

public class FileBlobBinary
{

	private File file_path_;
	private long file_size_;
	private byte[] bytes_ = new byte[1024];

	private RandomAccessFile ras;

	FileBlobBinary(String path) throws IOException
	{
		setFilePath(new File(path));
		
		ras = new RandomAccessFile(path, "r");
		file_size_ = ras.length();
	}
	
	void close() throws IOException
	{
		ras.close();
	}
	
	long size()
	{
		return file_size_;
	}

	public String getString(long offset, int length) throws IOException
	{
		read(offset, length);

		return new String(bytes_, 0, length, "US-ASCII");
	}

	public float getFloat(long offset) throws EOFException, IOException
	{
		read(offset, Float.SIZE / 8);
		ByteBuffer bb = ByteBuffer.wrap(bytes_);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		return bb.getFloat(0);
	}

	public long getInteger(long offset) throws EOFException, IOException
	{
		read(offset, Integer.SIZE / 8);
		ByteBuffer bb = ByteBuffer.wrap(bytes_);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		return ByteBufferUtilities.getUnsignedInt(bb, 0);
	}

	// Reads data into the internal bytes_ array.
	private void read(long offset, int length) throws IOException, EOFException
	{
		ras.seek(offset);
		int read = ras.read(bytes_, 0, length);
		if (read != length)
			throw new EOFException();
	}

	public void setFilePath(File file_path_)
	{
		this.file_path_ = file_path_;
	}

	public String getFilePath()
	{
		return file_path_.getName();
	}

}
