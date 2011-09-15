package threedc.github.com.stl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

import threedc.github.com.Encoder;
import threedc.github.com.model.ModelImpl;
import threedc.github.com.model.PrintableObject;
import threedc.github.com.model.Triangle;
import threedc.github.com.model.Vertex;
import threedc.github.com.util.FileUtility;

public class StlbEncoder implements Encoder
{
	File filePath;

	public void encode(ModelImpl model, String output_path) throws IOException
	{
		encode(model, new File(output_path));
		
	}

	public void encode(ModelImpl model, File output, boolean split) throws IOException
	{
		for (PrintableObject object : model.getPrintableObjects())
		{
			// If there are multiple objects in the model then write each to an
			// individual file
			// with the objects 'id' inserted into the file name.
			if (model.getPrintableObjects().size() > 1)
			{
				filePath = new File(output.getParent(), FileUtility.getNamePart(output) + "." + object.getId() + "."
						+ FileUtility.getExtension(output));
			}
			else
				filePath = output;


			FileOutputStream out = new FileOutputStream(filePath);
			byte[] description = object.getId().getBytes(Charset.forName("US-ASCII"));
			out.write(description);

			for (int i = description.length; i < 0x50; ++i)
			{
				out.write('\0');
			}

			int triangle_count = model.getTriangleCount();

			if (triangle_count > 0)
			{
				writeInt(out, triangle_count);

				for (int i = 0; i < triangle_count; ++i)
				{
					WriteStlbTriangle(out, object.getTriangle(i));
				}
				out.close();
			}
		}
	}

	// Writes the contents of a Triangle to the output stream
	void WriteStlbTriangle(OutputStream out, Triangle t) throws IOException
	{
		WriteStlbVertex(out, t.getNormal());
		WriteStlbVertex(out, t.getV1());
		WriteStlbVertex(out, t.getV2());
		WriteStlbVertex(out, t.getV3());
		// out.write("  ".getBytes(Charset.forName("US-ASCII")));
		byte[] intZero = new byte[]
		{ 0, 0 };
		out.write(intZero);
	}

	// Writes the contents of a Vertex to the output stream in IEEE 754 floating
	// point number format
	void WriteStlbVertex(OutputStream out, Vertex v) throws IOException
	{
		writeFloat(out, v.getX());
		writeFloat(out, v.getY());
		writeFloat(out, v.getZ());
	}

	private void writeFloat(OutputStream out, float value) throws IOException
	{
		ByteBuffer bb = ByteBuffer.allocate(Float.SIZE / 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.putFloat(value);
		out.write(bb.array());

	}

	private void writeInt(OutputStream out, int value) throws IOException
	{
		ByteBuffer bb = ByteBuffer.allocate(Integer.SIZE / 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.putInt(value);
		out.write(bb.array());
	}

	public String getFilePath() throws IOException
	{
		return filePath.getCanonicalPath();
	}
	
	public void encode(ModelImpl model, File outputPath) throws IOException
	{
		encode(model, outputPath.getAbsolutePath());
	}

}
