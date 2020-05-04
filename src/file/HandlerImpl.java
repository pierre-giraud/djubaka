package file;

import builders.MediaBuilder;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayDeque;
import java.util.Deque;

public class HandlerImpl extends DefaultHandler implements ContentHandler, ErrorHandler {

    private Deque<String> stack;
    private MediaBuilder builder;

    public HandlerImpl(MediaBuilder builder) {
        this.builder = builder;
    }

    public void characters(char[] data, int start, int length) {
        String str = new String(data, start, length);
        String top = stack.getFirst().trim();

        if ("name".equals(top)) {
            try {
                builder.setName(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void endDocument() {
        stack.clear();
    }

    public void endElement(String uri, String localName, String qName) {
        switch (qName) {
            case "media":
                try {
                    builder.stopMedia();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "list":
                try {
                    builder.stopList();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "name":
                stack.removeFirst();
                break;
            default: break;
        }
    }

    public void startDocument() {
        stack = new ArrayDeque<>();
    }

    public void startElement(String uri, String localName, String qName, Attributes atts) {
        switch (qName) {
            case "media":
                try {
                    builder.startMedia();
                    builder.setDuration(Integer.parseInt(atts.getValue(0)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "list":
                try {
                    builder.startList();
                    builder.setListName(atts.getValue(0));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "name":
                stack.addFirst(qName);
                break;
            default: break;
        }
    }
}
