package threedc.github.com.amf;

import java.util.Vector;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import threedc.github.com.model.PrintableObject;

public class XMLHandler extends DefaultHandler implements Subscriber
{
	static Logger logger = Logger.getLogger(XMLHandler.class);
	Boolean currentElement = false;
	StringBuilder currentValue = new StringBuilder();

	ParserState currentState = null;

	public Vector<PrintableObject> printableObjects = new Vector<PrintableObject>();
	private XMLParser xmlParser;

	public XMLHandler(XMLParser xmlParser)
	{
		this.xmlParser = xmlParser;
	}

	public Vector<PrintableObject> getPrintableObjectList()
	{
		return printableObjects;
	}

	// public static void setPrintableObjectList(Vector<PrintableObject>
	// modelList)
	// {
	// XMLHandler.printableObjects = modelList;
	// }

	/**
	 * Called when tag starts e.g.- <v1>5</v1> qName will contain v1
	 */
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
	{
		print("start", uri, localName, qName, attributes);

		currentElement = true;
		currentValue.setLength(0);
		Element element = Element.valueOf(qName);
		currentState = element.getHandler().startElement(currentState, attributes);

		if (element == Element.object)
			currentState.subscribe(this);
	}

	/**
	 * Called when tag closing e.g.- <v1>5</v1> qName will contain v1
	 */
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		currentElement = false;

		print("end", uri, localName, qName, null);

		Element element = Element.valueOf(qName);

		element.getHandler().endElement(currentState, currentValue.toString().trim());

	}

	/**
	 * Called to get tag characters eg:- <11>5</v1> returns '5'
	 * 
	 * This method may be called multiple times for one element if the data
	 * breaks over a 2K boundary.
	 */
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException
	{

		if (currentElement)
		{
			currentValue.append(new String(ch, start, length));
		}

	}

	private void print(String reason, String uri, String localName, String qName, Attributes attributes)
	{
		if (logger.isDebugEnabled())
		{
			if (qName.compareToIgnoreCase("vertex") == 0)
				return;

			if (qName.compareToIgnoreCase("triangle") == 0)
				if (this.currentState.getPrintableObject().getTriangleCount() == 1883)
					return;
				else
					return;

			if (qName.compareToIgnoreCase("v1") == 0)
				if (this.currentState.getPrintableObject().getTriangleCount() == 1883)
					return;
				else
					return;

			if (qName.compareToIgnoreCase("v2") == 0)
				if (this.currentState.getPrintableObject().getTriangleCount() == 1883)
					return;
				else
					return;

			if (qName.compareToIgnoreCase("v3") == 0)
				if (this.currentState.getPrintableObject().getTriangleCount() == 1884)
					return;
				else
					return;

			if (qName.compareToIgnoreCase("coordinates") == 0)
				return;
			if (qName.compareToIgnoreCase("x") == 0)
				return;

			if (qName.compareToIgnoreCase("y") == 0)
				return;

			if (qName.compareToIgnoreCase("z") == 0)
				return;

			StringBuilder sb = new StringBuilder();
			sb.append(reason + " uri:" + uri + ",localName:" + localName + ",qName:" + qName);

			if (attributes != null)
			{
				for (int i = 0; i < attributes.getLength(); i++)
				{
					sb.append(", attributes:" + attributes.getQName(i));
				}

			}
			logger.debug(sb);
		}
	}

	public void notifyObjectComplete(PrintableObject object)
	{
		printableObjects.add(currentState.getPrintableObject());
	}

	public XMLParser getParser()
	{
		return xmlParser;
	}

}