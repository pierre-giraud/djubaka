package xml;

import builders.MediaBuilder;
import medias.MediaLoader;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class XMLMediaLoader implements MediaLoader {
    @Override
    public void load(String filename, MediaBuilder builder){
        try {
            InputSource is = new InputSource(new BufferedInputStream(new FileInputStream(filename)));
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setValidating(true);
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();
            HandlerImpl handler = new HandlerImpl(builder);
            xr.setContentHandler(handler);
            xr.setErrorHandler(handler);

            xr.parse(is);
        } catch (ParserConfigurationException | IOException | SAXException e){
            e.printStackTrace();
        }
    }
}
