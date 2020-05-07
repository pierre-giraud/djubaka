package file;

import exceptions.BadFileExtensionException;
import media.ListMedia;
import media.MediaVisitor;

import java.io.File;
import java.io.PrintStream;

public class XPLMediaSaver {
    public static void saveXPL(String filename, ListMedia list) throws Exception {
        if (!filename.substring(filename.length() - 4).equals(".xpl")) throw new BadFileExtensionException("This file type is not supported");

        PrintStream stream = new PrintStream(new File(filename));
        MediaVisitor visitor = new XPLMediaSaverVisitor(stream);

        stream.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        stream.append("<!DOCTYPE playlist SYSTEM \"resources/playlist.dtd\">\n");
        stream.append("<playlist>\n");

        list.accept(visitor);

        stream.append("</playlist>");
    }
}
