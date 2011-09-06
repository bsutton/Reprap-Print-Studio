package threedc.github.com.amf;

import org.xml.sax.Attributes;

public class TriangleHandler extends Handler
{
	@Override
	public void endElement(ParserState currentParser, String currentValue)
	{
		int a = 1;
		if (currentParser.getPrintableObject().getTriangleCount() == 1883)
			a = 2;
		
		// 1066,
		//1072,
		// 1062 - 0.408113, 0.891547, 1.17969
		
			

		currentParser.endTriangle();
		
	}

	@Override
	public ParserState startElement(ParserState currentParser, Attributes attributes)
	{
		currentParser.startTriangle();
		return currentParser;
	}


}
