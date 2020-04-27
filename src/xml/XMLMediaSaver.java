package xml;

import medias.Media;
import visitors.MediaVisitor;
import visitors.XmlMediaSaverVisitor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class XMLMediaSaver {
    public static void save(String fileName, Media playlist) throws FileNotFoundException {
        PrintStream stream = new PrintStream(new File(fileName));
        MediaVisitor visitor = new XmlMediaSaverVisitor(stream);

        stream.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        stream.append("<!DOCTYPE playlist SYSTEM \".\\resources\\playlist.dtd\">\n");
        stream.append("<playlist xmlns=\"http://www.univ-rouen.fr/playlist\">\n");

        playlist.accept(visitor);

        stream.append("</playlist>");
    }
}
