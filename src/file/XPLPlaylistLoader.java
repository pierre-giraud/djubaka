package file;

import media.MediaBuilder;
import media.StdMediaBuilder;
import exceptions.BadPlaylistFileTypeException;
import media.*;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;

public class XPLPlaylistLoader {

    private MediaBuilder builder;

    public XPLPlaylistLoader(){
        builder = new StdMediaBuilder();
    }

    public ListMedia loadListFromXPL(String filename) throws Exception {
        if (!filename.substring(filename.length() - 4).equals(".xpl")) throw new BadPlaylistFileTypeException("This file type is not supported");

        InputSource is = new InputSource(new BufferedInputStream(new FileInputStream(filename)));
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setValidating(true);
        SAXParser sp = spf.newSAXParser();
        XMLReader xr = sp.getXMLReader();
        XPLHandler handler = new XPLHandler(builder);
        xr.setContentHandler(handler);
        xr.setErrorHandler(handler);

        xr.parse(is);

        ListMedia list = builder.getList();
        builder.resetList();

        return list;
    }

    public MediaBuilder getBuilder() {
        return builder;
    }

    public void setBuilder(MediaBuilder builder) {
        this.builder = builder;
    }
}
