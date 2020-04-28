package xml;

import medias.ListMedia;
import medias.MediaSaver;
import visitors.MediaVisitor;
import visitors.XmlMediaSaverVisitor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class XMLMediaSaver implements MediaSaver {
    @Override
    public void save(String fileName, ListMedia list) {
        try {
            PrintStream stream = new PrintStream(new File(fileName));
            MediaVisitor visitor = new XmlMediaSaverVisitor(stream);

            stream.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            stream.append("<!DOCTYPE playlist SYSTEM \".\\resources\\playlist.dtd\">\n");
            stream.append("<playlist xmlns=\"http://www.univ-rouen.fr/playlist\">\n");

            list.accept(visitor);

            stream.append("</playlist>");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
