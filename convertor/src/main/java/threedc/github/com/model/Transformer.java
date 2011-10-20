package threedc.github.com.model;

import java.util.ArrayList;

import threedc.github.com.model.transforms.Transform;

class Transformer extends Thread
{
	final ArrayList<Vertex> vertexes;
	final int first;
	final int last;
	final Transform[] transforms;

	Transformer(ArrayList<Vertex> vertexes, int first, int last, Transform[] transforms)
	{
		this.vertexes = vertexes;
		this.first = first;
		this.last = last;
		this.transforms = transforms;
		this.setPriority(MIN_PRIORITY);
		this.setName("Transformer");
	}

	public void run()
	{
		// Apply the set of transforms to each vertex.
		for (Vertex vertex : vertexes.subList(first, last))
		{
			for (Transform transform : transforms)
			{
				if (transform != null)
					transform.apply(vertex);
			}
		}
	}
}