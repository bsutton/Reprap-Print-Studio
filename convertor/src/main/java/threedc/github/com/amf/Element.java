package threedc.github.com.amf;


public enum Element
{
	amf
	{
		@Override
		public Handler getHandler()
		{
			return new AmfHandler();
		}
	},
	object
	{
		@Override
		public Handler getHandler()
		{
			return new ObjectHandler();
		}
	},
	mesh
	{
		@Override
		public Handler getHandler()
		{
			return new MeshHandler();
		}
	},
	region
	{
		@Override
		public Handler getHandler()
		{
			return new RegionHandler();
		}
	},
	triangle
	{
		@Override
		public Handler getHandler()
		{
			return new TriangleHandler();
		}
	},
	v1
	{
		@Override
		public Handler getHandler()
		{
			return new V1Handler();
		}
	},
	v2
	{
		@Override
		public Handler getHandler()
		{
			return new V2Handler();
		}
	},
	v3
	{
		@Override
		public Handler getHandler()
		{
			return new V3Handler();
		}
	},
	x
	{
		@Override
		public Handler getHandler()
		{
			return new XHandler();
		}
	},
	y
	{
		@Override
		public Handler getHandler()
		{
			return new YHandler();
		}
	},
	z
	{
		@Override
		public Handler getHandler()
		{
			return new ZHandler();
		}
	},
	coordinates
	{
		@Override
		public Handler getHandler()
		{
			return new CoordinatesHandler();
		}
	},
	vertex
	{
		@Override
		public Handler getHandler()
		{
			return new VertexHandler();
		}
	},
	vertices
	{
		@Override
		public Handler getHandler()
		{
			return new VerticesHandler();
		}
	};

	abstract public Handler getHandler();

}
