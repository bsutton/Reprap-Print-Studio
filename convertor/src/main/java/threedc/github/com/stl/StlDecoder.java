package threedc.github.com.stl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import threedc.github.com.Decoder;
import threedc.github.com.model.Model;
import threedc.github.com.model.PrintableObject;
import threedc.github.com.model.Triangle;
import threedc.github.com.model.Vertex;

import com.sun.media.sound.InvalidFormatException;

public class StlDecoder implements Decoder
{

	private static final int DESCRIPTION = 1;

	BufferedReader br;
	int lineCount = 0; // The current line being processed

	File filePath;

	public StlDecoder(String path) throws IOException
	{
		filePath = new File(path);

		br = new BufferedReader(new FileReader(path));
	}

	public Model decode() throws IOException
	{
		String header = "solid";
		String facet = "facet";
		String normal = "normal";
		String outer = "outer";
		String loop = "loop";
		String vertex = "vertex";
		String endloop = "endloop";
		String endfacet = "endfacet";
		String endsolid = "endsolid";

		int triangles = 0;

		Vector<String> tokens = nextLine();
		if (tokens.size() == 0 || tokens.elementAt(0).compareTo(header) != 0)
		{
			throw new InvalidFormatException("Error: STL missing 'header' (" + header + ") at line " + lineCount);
		}

		PrintableObject printableObject = new PrintableObject(
				(tokens.size() > DESCRIPTION ? tokens.elementAt(DESCRIPTION) : ""), PrintableObject.VertexMode.Ordered);

		Vector<Vertex> cache = new Vector<Vertex>();
		tokens = nextLine();
		while (!tokens.isEmpty() && tokens.elementAt(0).compareTo(endsolid) != 0)
		{

			if (tokens.elementAt(0).compareTo(facet) == 0)
			{
				if (tokens.elementAt(1).compareTo(normal) == 0)
				{
					float x = Float.parseFloat(tokens.elementAt(2));
					float y = Float.parseFloat(tokens.elementAt(3));
					float z = Float.parseFloat(tokens.elementAt(4));
					cache.add(printableObject.addVertex(x, y, z));
				}
				else
				{
					throw new InvalidFormatException("Error: STL missing 'normal' at line " + lineCount);
				}
			}
			else
			{
				throw new InvalidFormatException("Error: STL missing 'facet' at line " + lineCount);
			}

			tokens = nextLine();

			if (tokens.elementAt(0).compareTo(outer) != 0 || tokens.elementAt(1).compareTo(loop) != 0)
			{
				throw new InvalidFormatException("Error: STL missing 'outer loop' at line " + lineCount);
			}

			for (int i = 3; i > 0; --i)
			{
				tokens = nextLine();

				if (tokens.elementAt(0).compareTo(vertex) == 0)
				{
					float x = Float.parseFloat(tokens.elementAt(1));
					float y = Float.parseFloat(tokens.elementAt(2));
					float z = Float.parseFloat(tokens.elementAt(3));
					cache.add(printableObject.addVertex(x, y, z));

				}
				else
				{
					throw new InvalidFormatException("Error: STL missing 'vertex' at line " + lineCount);
				}
			}

			tokens = nextLine();
			if (tokens.elementAt(0).compareTo(endloop) != 0)
			{
				throw new InvalidFormatException("Error: STL missing 'endloop' at line " + lineCount);
			}

			tokens = nextLine();
			if (tokens.elementAt(0).compareTo(endfacet) != 0)
			{
				throw new InvalidFormatException("Error: STL missing 'endfacet' at line " + lineCount);
			}

			Triangle t = new Triangle(cache.elementAt(1), cache.elementAt(2), cache.elementAt(3), cache.elementAt(0));
			printableObject.addTriangle(t);
			triangles++;
			cache.clear();

			tokens = nextLine();
		}

		if (tokens.isEmpty())
			throw new InvalidFormatException("Error: STL missing 'endsolid' at line " + lineCount);

		Model model = new Model();
		model.addPrintableObject(printableObject);

		return model;
	}

	Vector<String> nextLine() throws IOException
	{
		String line = br.readLine();
		lineCount++;
		return tokenize(line);
	}

	Vector<String> tokenize(String line)
	{
		Vector<String> tokens = new Vector<String>();

		String[] split = line.split("\\s+");

		for (String token : split)
		{
			// we don't copy empty tokens
			if (token.length() > 0)
				tokens.add(token);
		}

		return tokens;
	}

	public String getFilePath() throws IOException
	{
		return filePath.getCanonicalPath();
	}

}
