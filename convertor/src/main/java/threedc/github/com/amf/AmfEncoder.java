package threedc.github.com.amf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import threedc.github.com.Encoder;
import threedc.github.com.model.Model;
import threedc.github.com.model.PrintableObject;
import threedc.github.com.model.Triangle;
import threedc.github.com.model.Vertex;

public class AmfEncoder implements Encoder
{
	
	public boolean encode(Model model, String output_path) throws IOException
	{
		FileOutputStream xmlFile = new FileOutputStream(new File(output_path));
		StreamResult streamResult = new StreamResult(xmlFile);
		SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();

		try
		{
			// SAX2.0 ContentHandler.
			TransformerHandler hd = tf.newTransformerHandler();
			Transformer serializer = hd.getTransformer();
			serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			//serializer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "AMF 1.0.xsd");
			serializer.setOutputProperty(OutputKeys.INDENT, "yes");
			hd.setResult(streamResult);
			hd.startDocument();
			AttributesImpl atts = new AttributesImpl();
			// amf tag
			atts.addAttribute("", "", "version", "CDATA", model.getVersion());
			atts.addAttribute("", "", "units", "CDATA", model.getUnits().name());
			hd.startElement("", "", "amf", atts);
			{
				for (PrintableObject object : model.getPrintableObjects())
				{
					atts.clear();
					atts.addAttribute("", "", "id", "CDATA", object.getId());
					hd.startElement("", "", "object", atts);
					atts.clear();
					hd.startElement("", "", "mesh", atts);
					hd.startElement("", "", "vertices", atts);

					// Output the list of vertices.
					for (int i = 0; i < object.getVertexCount(); i++)
					{
						Vertex vertex = object.getVertex(i);
						atts.clear();
						hd.startElement("", "", "vertex", atts);
						hd.startElement("", "", "coordinates", atts);
						element(hd, "x", vertex.getX());
						element(hd, "y", vertex.getY());
						element(hd, "z", vertex.getZ());
						hd.endElement("", "", "coordinates");
						hd.endElement("", "", "vertex");
					}
					hd.endElement("", "", "vertices");
					atts.clear();
					hd.startElement("", "", "region", atts);

					// Output the list of triangles
					for (int i = 0; i < object.getTriangleCount(); i++)
					{
						Triangle triangle = object.getTriangle(i);
						atts.clear();
						hd.startElement("", "", "triangle", atts);
						element(hd, "v1", triangle.getV1().getOrdinal());
						element(hd, "v2", triangle.getV2().getOrdinal());
						element(hd, "v3", triangle.getV3().getOrdinal());
						hd.endElement("", "", "triangle");
					}

					hd.endElement("", "", "region");
					hd.endElement("", "", "mesh");
					hd.endElement("", "", "object");
				}
			}
			hd.endElement("", "", "amf");
			hd.endDocument();
		}
		catch (SAXException e)
		{
			throw new RuntimeException(e);
		}
		catch (TransformerConfigurationException e)
		{
			throw new RuntimeException(e);
		}

		return false;
	}

	private void element(TransformerHandler hd, String name, int value) throws SAXException
	{
		AttributesImpl atts = new AttributesImpl();
		hd.startElement("", "", name, atts);
		String sValue = Integer.toString(value);
		hd.characters(sValue.toCharArray(), 0, sValue.length());
		hd.endElement("", "", name);

	}

	private void element(TransformerHandler hd, String name, float value) throws SAXException
	{
		AttributesImpl atts = new AttributesImpl();
		hd.startElement("", "", name, atts);
		String sValue = Float.toString(value);
		hd.characters(sValue.toCharArray(), 0, sValue.length());
		hd.endElement("", "", name);

	}

	public String getFilePath() throws IOException
	{
		// TODO Auto-generated method stub
		return null;
	}

	public boolean encode(Model model, File outputPath) throws IOException
	{
		return encode(model, outputPath.getAbsolutePath());
	}

}
