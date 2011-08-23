package threedc.github.com;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

public class StlbEncoder implements Encoder
{

	File filePath;

	public boolean encode(Model model, String output_path) throws IOException
	{

		filePath = new File(output_path);

		FileOutputStream out = new FileOutputStream(filePath);
		byte[] description = model.getDescription().getBytes(Charset.forName("US-ASCII"));
		out.write(description);

		for (int i = description.length; i < 0x50; ++i)
		{
			out.write('\0');
		}

		int triangle_count = model.triangle_count();

		if (triangle_count > 0)
		{
			writeInt(out, triangle_count);

			for (int i = 0; i < triangle_count; ++i)
			{
				WriteStlbTriangle(out, model.get_triangle(i));
			}
			out.close();
		}

		return true;
	}

	// Writes the contents of a Triangle to the output stream
	void WriteStlbTriangle(OutputStream out, Triangle t) throws IOException
	{
		WriteStlbVertex(out, t.getNorm());
		WriteStlbVertex(out, t.getV1());
		WriteStlbVertex(out, t.getV2());
		WriteStlbVertex(out, t.getV3());
		//out.write("  ".getBytes(Charset.forName("US-ASCII")));
		byte[] intZero = new byte[] {0,0};
		out.write(intZero);
	}

	// Writes the contents of a Vertex to the output stream in IEEE 754 floating
	// point number format
	void WriteStlbVertex(OutputStream out, Vertex v) throws IOException
	{
		writeFloat(out, v.x_);
		writeFloat(out, v.y_);
		writeFloat(out, v.z_);
	}

	private void writeFloat(OutputStream out, float value) throws IOException
	{
		ByteBuffer bb = ByteBuffer.allocate(Float.SIZE / 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.putFloat( value);
		out.write(bb.array());

	}

	private void writeInt(OutputStream out, int value) throws IOException
	{
		ByteBuffer bb = ByteBuffer.allocate(Integer.SIZE / 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.putInt( value);
		out.write(bb.array());
	}

	
	public String getFilePath() throws IOException
	{
		return filePath.getCanonicalPath();
	}
}
