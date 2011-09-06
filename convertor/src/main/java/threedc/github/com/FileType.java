package threedc.github.com;

import java.io.IOException;

import threedc.github.com.amf.AmfDecoder;
import threedc.github.com.amf.AmfEncoder;
import threedc.github.com.obj.ObjDecoder;
import threedc.github.com.obj.ObjEncoder;
import threedc.github.com.stl.StlDecoder;
import threedc.github.com.stl.StlEncoder;
import threedc.github.com.stl.StlbDecoder;
import threedc.github.com.stl.StlbEncoder;

public enum FileType
{
	STL
	{
		@Override
		public Encoder getEncoder()
		{
			return new StlEncoder();
		}

		@Override
		public Decoder getDecoder(String path) throws IOException
		{
			return new StlDecoder(path);
		}
	},
	STLB
	{
		@Override
		public Encoder getEncoder()
		{
			return new StlbEncoder();
		}

		@Override
		public Decoder getDecoder(String path) throws IOException
		{
			return new StlbDecoder(path);		}
	},
	OBJ
	{
		@Override
		public Encoder getEncoder()
		{
			return new ObjEncoder();
		}

		@Override
		public Decoder getDecoder(String path) throws IOException
		{
			return new ObjDecoder(path);		}
	},
	AMF
	{
		@Override
		public Encoder getEncoder()
		{
			return new AmfEncoder();
		}

		@Override
		public Decoder getDecoder(String path) throws IOException
		{
			return new AmfDecoder(path);
		}
	};

	abstract public Encoder getEncoder();

	abstract public Decoder getDecoder(String path) throws IOException;
}
