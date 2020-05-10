package file;

import exceptions.BadPlaylistFileTypeException;
import media.Media;
import media.MediaVisitor;

import java.io.File;
import java.io.PrintStream;

public class XPLPlaylistSaver {
    public static void saveXPL(String filename, Media m) throws Exception {
        if (!filename.substring(filename.length() - 4).equals(".xpl")) throw new BadPlaylistFileTypeException("This file type is not supported");

        PrintStream stream = new PrintStream(new File(filename));
        MediaVisitor visitor = new XPLMediaSaver(stream);

        stream.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        stream.append("<!DOCTYPE playlist SYSTEM \"resources/playlist.dtd\">\n");
        stream.append("<playlist>\n");

        m.accept(visitor);

        stream.append("</playlist>");
    }
}
