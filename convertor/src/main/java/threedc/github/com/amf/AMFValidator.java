package threedc.github.com.amf;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class AMFValidator
{

	private static final String AMF_SOURCE = "/home/bsutton/git/3dc/convertor/src/test/resources/Rook.amf";

	public static void main(String[] args) throws SAXException, IOException
	{

		// 5. Check the document
		try
		{
			AMFValidator validator = new AMFValidator();
			validator.validate(new File(AMF_SOURCE));
			System.out.println(AMF_SOURCE + " is valid.");
		}
		catch (SAXParseException ex)
		{
			System.out.println(AMF_SOURCE + " is not valid because ");
			System.out.println(ex.getMessage());
			System.out.println("Error occured on line: " + ex.getLineNumber() + " at column " + ex.getColumnNumber()
					+ ".");
		}
	}

	public void validate(File xmlSource) throws SAXException, IOException
	{
		// 1. Lookup a factory for the W3C XML Schema language
		SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");

		// 2. Compile the schema.
		// Here the schema is loaded from a java.io.File, but you could use
		// a java.net.URL or a javax.xml.transform.Source instead.
		URL schemaLocation = ClassLoader.getSystemResource("resources/AMF 1.0.xsd");
		Schema schema = factory.newSchema(schemaLocation);

		// 3. Get a validator from the schema.
		Validator validator = schema.newValidator();

		// 4. Parse the document you want to check.
		Source source = new StreamSource(xmlSource);

		// 5. Check the document
		validator.validate(source);
	}

}
