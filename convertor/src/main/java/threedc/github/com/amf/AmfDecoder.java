package threedc.github.com.amf;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import threedc.github.com.DecodeException;
import threedc.github.com.Decoder;
import threedc.github.com.model.ModelImpl;

public class AmfDecoder implements Decoder
{
	Vector<InputStream> fileList = new Vector<InputStream>();
	File path;

	public AmfDecoder(String path) throws IOException
	{
		// AMF files can either be compressed or plain text.

		this.path = new File(path);

		// Lets try zip first
		ZipFile zf = null;

		try
		{
			zf = new ZipFile(this.path);

			// Extract the list of amf files contained in the zip.
			for (Enumeration<? extends ZipEntry> e = zf.entries(); e.hasMoreElements();)
			{
				ZipEntry ze = e.nextElement();
				String name = ze.getName();
				if (name.endsWith(".amf"))
				{
					fileList.add(zf.getInputStream(ze));
				}
			}
		}
		catch (ZipException e)
		{
			// So we have a zip exception so lets assume its a plain text (xml)
			// file.
			fileList.add(new FileInputStream(this.path));
		}
		finally
		{
			if (zf != null)
				zf.close();
		}
	}

	public ModelImpl decode() throws EOFException, IOException, DecodeException
	{
		ModelImpl model = new ModelImpl();

		XMLParser parser;
		try
		{
			for (InputStream stream : fileList)
			{
				parser = new XMLParser(model);
				parser.parse(stream);
			}
		}
		catch (ParserConfigurationException e)
		{
			throw new DecodeException(e);
		}
		catch (SAXException e)
		{
			throw new DecodeException(e);
		}

		return model;
	}

	public String getFilePath() throws IOException
	{
		return this.path.getCanonicalPath();
	}
}
