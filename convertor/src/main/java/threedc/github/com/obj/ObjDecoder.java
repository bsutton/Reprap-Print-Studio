package threedc.github.com.obj;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import threedc.github.com.Decoder;
import threedc.github.com.model.ModelImpl;
import threedc.github.com.model.PrintableObject;
import threedc.github.com.model.Triangle;

public class ObjDecoder implements Decoder
{

	private static final int NORMAL_TOKEN_IDX = 9;
	private static final int Z_TOKEN_IDX = 7;
	private static final int Y_TOKEN_IDX = 4;
	private static final int X_TOKEN_IDX = 1;
	BufferedReader br;
	int size = 0;
	int lineCount = 0; // The current line being processed

	public ObjDecoder(String path) throws IOException
	{
		br = new BufferedReader(new FileReader(path));
	}

	public ModelImpl decode() throws EOFException, IOException
	{
		int triangles = 0;

		PrintableObject printableObject = new PrintableObject("0", PrintableObject.VertexMode.Ordered);

		Vector<String> tokens = new Vector<String>();
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
					printableObject.addVertex(x,y,z);
				}
				else if (tokens.elementAt(0) == "n")
				{
					// found a normal
					float x = Float.parseFloat(tokens.elementAt(1));
					float y = Float.parseFloat(tokens.elementAt(2));
					float z = Float.parseFloat(tokens.elementAt(3));
					printableObject.addVertex(x,y,z);
				}
				else if (tokens.elementAt(0) == "f")
				{
					// -1 to each of these because OBJ uses 1-based indexing
					int x = Integer.parseInt(tokens.elementAt(X_TOKEN_IDX)) - 1;
					int y = Integer.parseInt(tokens.elementAt(Y_TOKEN_IDX)) - 1;
					int z = Integer.parseInt(tokens.elementAt(Z_TOKEN_IDX)) - 1;
					int n = Integer.parseInt(tokens.elementAt(NORMAL_TOKEN_IDX)) - 1;
					Triangle t = new Triangle(printableObject.getVertex(x), printableObject.getVertex(y), printableObject.getVertex(z),
							printableObject.getNormal(n));
					printableObject.addTriangle(t);
					triangles++;
				}
			}
			else
			{
				break;
			}
		}
		
		ModelImpl model = new ModelImpl();
		model.addPrintableObject(printableObject);

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
