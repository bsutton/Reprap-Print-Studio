package threedc.github.com.obj;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import threedc.github.com.Encoder;
import threedc.github.com.model.ModelImpl;
import threedc.github.com.model.PrintableObject;
import threedc.github.com.model.Triangle;
import threedc.github.com.model.Vertex;
import threedc.github.com.util.FileUtility;

public class ObjEncoder implements Encoder
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
			// If there are multiple objects in the model then write each to an individual file
			// with the objects 'id' inserted into the file name.
			if (model.getPrintableObjects().size() > 1)
			{
				filePath = new File(output.getParent(), FileUtility.getNamePart(output) + "." + object.getId() + "."
						+ FileUtility.getExtension(output));
			}
			else
				filePath = output;

			int triangle_count = model.getTriangleCount();
			Writer out = new FileWriter(output);

			out.write("# Exported to OBJ by 3dc\r\n");
			out.write("# github.com/bsutton/3dc\r\n");

			for (int i = 0; i < triangle_count; ++i)
			{
				WriteVerticies(out, object.getTriangle(i));
			}

			for (int i = 0; i < triangle_count; ++i)
			{
				WriteNormal(out, (object.getTriangle(i)).getNormal());
			}

			for (int i = 0; i < triangle_count; ++i)
			{
				WriteFace(out, i);
			}

			out.close();
		}
	}

	// Writes all the verticies for a triangle
	void WriteVerticies(Writer out, Triangle t) throws IOException
	{

		out.write("v ");
		out.write(t.getV1().getX() + " ");
		out.write(t.getV1().getY() + " ");
		out.write(t.getV1().getZ() + "\r\n");
		out.write("v ");
		out.write(t.getV2().getX() + " ");
		out.write(t.getV2().getY() + " ");
		out.write(t.getV2().getZ() + "\r\n");
		out.write("v ");
		out.write(t.getV3().getX() + " ");
		out.write(t.getV3().getY() + " ");
		out.write(t.getV3().getZ() + "\r\n");
	}

	void WriteNormal(Writer out, Vertex n) throws IOException
	{

		out.write("n ");
		out.write(n.getX() + " ");
		out.write(n.getY() + " ");
		out.write(n.getZ() + "\r\n");
	}

	void WriteFace(Writer out, int i) throws IOException
	{

		out.write("f ");
		out.write((i * 3) + 1 + "//" + i + 1 + " ");
		out.write((i * 3) + 2 + "//" + i + 1 + " ");
		out.write((i * 3) + 3 + "//" + i + 1 + "\r\n");
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
