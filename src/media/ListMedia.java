package media;

import java.util.ArrayList;
import java.util.List;

public class ListMedia implements Media {

    private String name;
    private List<Media> children;
    private ListMedia parent;

    public ListMedia(){
        children = new ArrayList<>();
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

    public void setName(String name) {
        this.name = name;
    }

    public List<Media> getChildren() {
        return children;
    }

    public void setChildren(List<Media> children) {
        this.children = children;
    }

    public ListMedia getParent() {
        return parent;
    }

    public void setParent(ListMedia parent) {
        this.parent = parent;
    }

    @Override
    public int getDuration() {
        int duration = 0;
        for (Media m : children) duration += m.getDuration();

        return duration;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void accept(MediaVisitor mv) {
        mv.visitList(this);
    }

    @Override
    public String toString() {
        return "(List) " + name + " -- " + getDuration() + " sec";
    }
}
