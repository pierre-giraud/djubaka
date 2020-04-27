package xml;

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

        switch (top) {
            case "name" :
            case "title" :
                try {
                    builder.setName(str);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "artist" :
                try {
                    builder.setArtist(str);
                } catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case "resolution" :
                try {
                    builder.setResolution(str);
                } catch (Exception e){
                    e.printStackTrace();
                }
                break;
            default: break;
        }
    }

    public void endDocument() {
        stack.clear();
    }

    public void endElement(String uri, String localName, String qName) {
        switch (qName) {
            case "music":
                try {
                    builder.stopMusic();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "video":
                try {
                    builder.stopVideo();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "playlist":
                try {
                    builder.stopPlaylist();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "duration":
            case "name":
            case "title":
            case "artist":
            case "resolution":
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
            case "music":
                try {
                    builder.startMusic();
                    builder.setDuration(Integer.parseInt(atts.getValue(0)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "video":
                try {
                    builder.startVideo();
                    builder.setDuration(Integer.parseInt(atts.getValue(0)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "playlist":
                try {
                    builder.startPlaylist();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "name":
            case "title":
            case "artist":
            case "resolution":
                stack.addFirst(qName);
                break;
            default: break;
        }
    }
}
