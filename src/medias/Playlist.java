package medias;

import visitors.MediaVisitor;

import java.util.ArrayList;
import java.util.List;

public class Playlist implements Media {

    private String name;
    private List<Media> children;

    public Playlist(){
        children = new ArrayList<Media>();
    }

    public void add(Media m){
        children.add(m);
    }

    public void remove(Media m){
        children.remove(m);
    }

    public Media getChild(int num){
        return children.get(num);
    }
    public List<Media> getChildren() { return children; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void accept(MediaVisitor mv) {
        mv.visitPlaylist(this);
    }
}
