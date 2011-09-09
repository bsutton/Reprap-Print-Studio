package threedc.github.com;

import java.io.File;
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

		@Override
		public Decoder getDecoder(File from) throws IOException
		{
			return getDecoder(from.getAbsolutePath());
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
			return new StlbDecoder(path);
		}

		@Override
		public Decoder getDecoder(File from) throws IOException
		{
			return getDecoder(from.getAbsolutePath());
		}

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
			return new ObjDecoder(path);
		}

		@Override
		public Decoder getDecoder(File from) throws IOException
		{
			return getDecoder(from.getAbsolutePath());
		}

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

		@Override
		public Decoder getDecoder(File from) throws IOException
		{
			return getDecoder(from.getAbsolutePath());
		}

	};

	abstract public Encoder getEncoder();

	abstract public Decoder getDecoder(String path) throws IOException;

	abstract public Decoder getDecoder(File from) throws IOException;
}
