package threedc.github.com.amf;


public class V3Handler extends Handler
{

	@Override
	public void endElement(ParserState currentParser, String currentValue)
	{
		currentParser.setCurrentV3(currentValue);
	}

}
