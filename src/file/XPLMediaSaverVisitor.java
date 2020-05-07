package file;

import media.*;

import java.io.PrintStream;

public class XPLMediaSaverVisitor implements MediaVisitor {

    private PrintStream stream;

    public XPLMediaSaverVisitor(PrintStream stream){
        this.stream = stream;
    }

    @Override
    public void visitMedia(StdMedia m) {
        stream.append("<media duration=\"").append(String.valueOf(m.getDuration())).append("\">\n");
        stream.append("\t<name>").append(m.getName()).append("</name>\n");
        stream.append("</media>\n");
    }

    @Override
    public void visitMusic(StdMusic m) {
        throw new UnsupportedOperationException("This method is not supported");
    }

    @Override
    public void visitVideo(StdVideo v) {
        throw new UnsupportedOperationException("This method is not supported");
    }

    @Override
    public void visitList(ListMedia p) {
        stream.append("<list name=\"").append(p.getName()).append("\">\n");
        for (Media m : p.getChildren()){ m.accept(this); }
        stream.append("</list>\n");
    }
}
