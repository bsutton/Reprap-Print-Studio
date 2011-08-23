package threedc.github.com;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class ObjDecoder implements Decoder
{

	BufferedReader br;
	int size = 0;
	int lineCount = 0; // The current line being processed

	public ObjDecoder(String path) throws IOException
	{
		br = new BufferedReader(new FileReader(path));
	}

	public Model decode() throws EOFException, IOException
	{
		int triangles = 0;

		Vector<Vertex> verts = new Vector<Vertex>();
		Vector<Vertex> norms = new Vector<Vertex>();
		Vector<String> tokens = new Vector<String>();
		Model model = new Model("");

		while ((tokens = nextLine()) != null)
		{
			if (tokens.size() > 0)
			{
				if (tokens.elementAt(0) == "#")
				{
					// this line is a comment - skip it
				}
				else if (tokens.elementAt(0) == "v")
				{
					// found a vertex
					float x = Float.parseFloat(tokens.elementAt(1));
					float y = Float.parseFloat(tokens.elementAt(2));
					float z = Float.parseFloat(tokens.elementAt(3));
					Vertex v = new Vertex(x, y, z);
					verts.add(v);
				}
				else if (tokens.elementAt(0) == "n")
				{
					// found a normal
					float x = Float.parseFloat(tokens.elementAt(1));
					float y = Float.parseFloat(tokens.elementAt(2));
					float z = Float.parseFloat(tokens.elementAt(3));
					Vertex n = new Vertex(x, y, z);
					norms.add(n);
				}
				else if (tokens.elementAt(0) == "f")
				{
					// -1 to each of these because OBJ uses 1-based indexing
					int x = Integer.parseInt(tokens.elementAt(1)) - 1;
					int y = Integer.parseInt(tokens.elementAt(4)) - 1;
					int z = Integer.parseInt(tokens.elementAt(7)) - 1;
					int n = Integer.parseInt(tokens.elementAt(9)) - 1;
					Triangle t = new Triangle(verts.elementAt(x), verts.elementAt(y), verts.elementAt(z),
							norms.elementAt(n));
					model.add_triangle(t);
					triangles++;
				}
			}
			else
			{
				break;
			}
		}

		return model;
	}

	Vector<String> nextLine() throws IOException
	{
		String line = br.readLine();
		lineCount++;
		return line == null ? null : tokenize(line);
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

	public String getFilePath()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
