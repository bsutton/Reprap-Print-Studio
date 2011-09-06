package threedc.github.com.amf;

import org.xml.sax.Attributes;

import threedc.github.com.model.PrintableObject;

public class ObjectHandler extends Handler
{

	@Override
	public void endElement(ParserState currentParser, String currentValue)
	{
		currentParser.notifyObjectComplete(currentParser.getPrintableObject());
	}


	@Override
	public ParserState startElement(ParserState currentState, Attributes attributes)
	{
		currentState = new ParserState();
		currentState.setPrintableObject(new PrintableObject(attributes.getValue(0), PrintableObject.VertexMode.Ordered));
		return currentState;
	}

}
