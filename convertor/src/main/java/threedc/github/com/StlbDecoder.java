package threedc.github.com;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;

import com.sun.media.sound.InvalidFormatException;

public class StlbDecoder implements Decoder
{
	private FileBlobBinary b;
	String description;

	File filePath;

	
	public StlbDecoder(String path) throws IOException
	{
		filePath = new File(path);

		b = new FileBlobBinary(path);
	}

	public Model decode() throws IOException, EOFException
	{
		long fsize = b.size();

		// Get the description stored in the first 80 chars.
		description = b.getString(0, 80).trim();
		Model model = new Model(description.length() == 0 ? b.getFilePath() : description);
		
		// Get the facet count at offset 80.
		long i = 0, triangle_count = b.getInteger(80);

		for (i = 0x54; i < fsize; i += 0x32)
		{
			Vertex normal = new Vertex(b.getFloat(i), b.getFloat(i + 0x4), b.getFloat(i + 0x8));
			Vertex v1 = new Vertex(b.getFloat(i + 0xC), b.getFloat(i + 0x10), b.getFloat(i + 0x14));
			Vertex v2 = new Vertex(b.getFloat(i + 0x18), b.getFloat(i + 0x1C), b.getFloat(i + 0x20));
			Vertex v3 = new Vertex(b.getFloat(i + 0x24), b.getFloat(i + 0x28), b.getFloat(i + 0x2C));

			Triangle tri = new Triangle(v1, v2, v3, normal);
			model.add_triangle(tri);
		}

		if (triangle_count != model.triangle_count())
		{
			throw new InvalidFormatException("Error: triangle count mismatch. Expected " + triangle_count
					+ " got " + model.triangle_count());
		}

		return model;
	}
	
	String getDescription()
	{
		return description;
	}

	public String getFilePath() throws IOException
	{
		return filePath.getCanonicalPath();
	}
}
