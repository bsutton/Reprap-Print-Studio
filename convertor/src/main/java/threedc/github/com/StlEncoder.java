package threedc.github.com;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DecimalFormat;

//Writes the contents of a Model to a binary STL file

public class StlEncoder implements Encoder
{

	File filePath;

	public boolean encode(Model model, String output_path) throws IOException
	{
		int triangle_count = model.triangle_count();
		filePath = new File(output_path);

		Writer out = new FileWriter(filePath);

		out.write("solid output\r\n");

		for (int i = 0; i < triangle_count; ++i)
		{
			WriteStlTriangle(out, model.get_triangle(i));
		}

		out.write("endsolid output\r\n");

		out.close();

		return true;
	}

	private void WriteStlTriangle(Writer out, Triangle t) throws IOException
	{
		out.write("facet normal ");
		WriteStlVertex(out, t.getNorm());
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
}
