package threedc.github.com.stl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DecimalFormat;

import threedc.github.com.Encoder;
import threedc.github.com.model.ModelImpl;
import threedc.github.com.model.PrintableObject;
import threedc.github.com.model.Triangle;
import threedc.github.com.model.Vertex;
import threedc.github.com.util.FileUtility;

//Writes the contents of a Model to a binary STL file

public class StlEncoder implements Encoder
{

	File filePath;

	public void encode(ModelImpl model, String output_path) throws IOException
	{
		File output = new File(output_path);
		encode(model, output, true);
	}

	public void encode(ModelImpl model, File output, boolean split) throws IOException
	{
		if (split)
		{

			for (PrintableObject object : model.getPrintableObjects())
			{
				// If there are multiple objects in the model then write each to
				// an
				// individual file
				// with the objects 'id' inserted into the file name.
				if (model.getPrintableObjects().size() > 1)
				{

					filePath = new File(output.getParent(), FileUtility.getNamePart(output) + "." + object.getId()
							+ "." + FileUtility.getExtension(output));
				}
				else
					filePath = output;

				int triangle_count = object.getTriangleCount();

				Writer out = new FileWriter(filePath);

				out.write("solid output\r\n");

				for (int i = 0; i < triangle_count; ++i)
				{
					WriteStlTriangle(out, object.getTriangle(i));
				}

				out.write("endsolid output\r\n");

				out.close();
			}
		}
		else
		{
			filePath = output;
			Writer out = new FileWriter(filePath);

			out.write("solid output\r\n");

			for (PrintableObject object : model.getPrintableObjects())
			{

				int triangle_count = object.getTriangleCount();

				for (int i = 0; i < triangle_count; ++i)
				{
					WriteStlTriangle(out, object.getTriangle(i));
				}
			}
			out.write("endsolid output\r\n");

			out.close();
		}
	}

	private void WriteStlTriangle(Writer out, Triangle t) throws IOException
	{
		out.write("facet normal ");
		WriteStlVertex(out, t.getNormal());
		out.write("   outer loop\r\n");
		out.write("      vertex ");
		WriteStlVertex(out, t.getV1());
		out.write("      vertex ");
		WriteStlVertex(out, t.getV2());
		out.write("      vertex ");
		WriteStlVertex(out, t.getV3());
		out.write("   endloop\r\n");
		out.write("endfacet\r\n");
	}

	private void WriteStlVertex(Writer out, Vertex v) throws IOException
	{
		DecimalFormat nf15 = new DecimalFormat();
		nf15.setMinimumFractionDigits(15);
		nf15.setMaximumFractionDigits(15);
		out.write(nf15.format(v.getX()));
		out.write(" ");
		out.write(nf15.format(v.getY()));
		out.write(" ");
		out.write(nf15.format(v.getZ()));
		out.write("\r\n");
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
