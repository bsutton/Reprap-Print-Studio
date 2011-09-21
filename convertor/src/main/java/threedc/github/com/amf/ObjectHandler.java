package threedc.github.com.amf;

import org.xml.sax.Attributes;

import threedc.github.com.model.PrintableObject;

public class ObjectHandler extends Handler
{

	private static final int OBJECT_ID = 0;

	@Override
	public void endElement(ParserState currentParser, String currentValue)
	{
		currentParser.notifyObjectComplete(currentParser.getPrintableObject());
	}

	@Override
	public void startElement(ParserState parserState, Attributes attributes)
	{
		parserState.addPrintableObject(new PrintableObject(attributes.getValue(OBJECT_ID), PrintableObject.VertexMode.Ordered, parserState.getUnits()));
	}
}
