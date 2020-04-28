package visitors;

import medias.Music;
import medias.Media;
import medias.ListMedia;
import medias.Video;

import java.io.PrintStream;

public class XmlMediaSaverVisitor implements MediaVisitor {

    private PrintStream stream;

    public XmlMediaSaverVisitor(PrintStream stream){
        this.stream = stream;
    }

    @Override
    public void visitMusic(Music m) {
        stream.append("<music duration=\"").append(String.valueOf(m.getDuration())).append("\">\n");
        stream.append("\t<name>").append(m.getName()).append("</name>\n");
        stream.append("\t<artist>").append(m.getArtist()).append("</artist>\n");
        stream.append("</music>\n");
    }

    @Override
    public void visitVideo(Video v) {
        stream.append("<video duration=\"").append(String.valueOf(v.getDuration())).append("\">\n");
        stream.append("\t<title>").append(v.getName()).append("</title>\n");
        stream.append("\t<resolution>").append(v.getResolution()).append("</resolution>\n");
        stream.append("</video>\n");
    }

    @Override
    public void visitList(ListMedia p) {
        stream.append("<playlist name=\"").append(p.getName()).append("\">\n");
        for (Media m : p.getChildren()){ m.accept(this); }
        stream.append("</playlist>\n");
    }
}
