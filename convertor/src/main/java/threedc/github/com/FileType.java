package threedc.github.com;

import java.io.File;
import java.io.IOException;

import threedc.github.com.amf.AmfDecoder;
import threedc.github.com.amf.AmfEncoder;
import threedc.github.com.model.Units;
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
		public Decoder getDecoder(String path, Units unit) throws IOException
		{
			return new StlDecoder(path, unit);
		}

		@Override
		public Decoder getDecoder(File from, Units unit) throws IOException
		{
			return getDecoder(from.getAbsolutePath(), unit);
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
		public Decoder getDecoder(String path, Units unit) throws IOException
		{
			return new StlbDecoder(path, unit);
		}

		@Override
		public Decoder getDecoder(File from, Units unit) throws IOException
		{
			return getDecoder(from.getAbsolutePath(), unit);
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
		public Decoder getDecoder(String path, Units unit) throws IOException
		{
			return new ObjDecoder(path, unit);
		}

		@Override
		public Decoder getDecoder(File from, Units unit) throws IOException
		{
			return getDecoder(from.getAbsolutePath(), unit);
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
		public Decoder getDecoder(String path, Units unit) throws IOException
		{
			return new AmfDecoder(path);
		}

		@Override
		public Decoder getDecoder(File from, Units unit) throws IOException
		{
			return getDecoder(from.getAbsolutePath(), unit);
		}

	};

	abstract public Encoder getEncoder();

	abstract public Decoder getDecoder(String path, Units unit) throws IOException;

	abstract public Decoder getDecoder(File from, Units unit) throws IOException;
}
