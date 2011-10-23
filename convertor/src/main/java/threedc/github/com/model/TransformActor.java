package threedc.github.com.model;

import threedc.github.com.model.transforms.Transform;
import threedc.github.com.util.Actor;

class TransformActor implements Actor<Vertex>
{
	final Transform transform;

	TransformActor(Transform transforms)
	{
		this.transform = transforms;
	}

	@Override
	public void prep()
	{
		// NO OP

	}

	@Override
	public void apply(Vertex vertex) throws Exception
	{
		transform.apply(vertex);

	}
}