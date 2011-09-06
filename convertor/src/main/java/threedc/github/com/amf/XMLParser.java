package threedc.github.com.amf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import threedc.github.com.model.PrintableObject;

public class XMLParser
{
	/** Create Object For SiteList Class */
	static Vector<PrintableObject> printableObjectList = new Vector<PrintableObject>();

	private static final String AMF_SOURCE = "/home/bsutton/git/3dc/convertor/src/test/resources/rook.amf";

	static public void main(String args[])

	{
		XMLParser parser = new XMLParser();
		try
		{
			parser.parse(new File(AMF_SOURCE));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (ParserConfigurationException e)
		{
			e.printStackTrace();
		}
		catch (SAXException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void parse(File source) throws ParserConfigurationException, SAXException, IOException
	{
		// First lets validate the xml
		AMFValidator validator = new AMFValidator();
		validator.validate(source);

		parse(new FileInputStream(source));
	}

	public void parse(InputStream inputstream) throws ParserConfigurationException, SAXException, IOException
	{
		/** Handling XML */
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();
		
		/** Create handler to handle XML Tags */
		XMLHandler xmlHandler = new XMLHandler(this);
		xr.setContentHandler(xmlHandler);
		xr.parse(new InputSource(inputstream));

		printableObjectList = xmlHandler.getPrintableObjectList();
	}

	public Vector<PrintableObject> getPrintableObjects()
	{
		return printableObjectList;
	}
}