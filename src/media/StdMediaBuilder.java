package media;

import exceptions.InvalidBuilderOperationException;

import java.util.ArrayDeque;
import java.util.Deque;

public class StdMediaBuilder implements MediaBuilder {
    private int duration;
    private String name;

    private BuilderState state;
    private ListMedia listMedia;
    private Deque<ListMedia> listMediaStack;

    public StdMediaBuilder(){
        listMediaStack = new ArrayDeque<>();
        state = BuilderState.NONE;
    }

    @Override
    public void startMedia() throws InvalidBuilderOperationException {
        if (state != BuilderState.NONE) throw new InvalidBuilderOperationException("The previous media is not finished");
        state = BuilderState.MEDIA;
        duration = -1;
        name = null;
    }

    @Override
    public void startList() throws InvalidBuilderOperationException {
        if (state != BuilderState.NONE) throw new InvalidBuilderOperationException("The previous media is not finished");
        if (listMedia == null) listMedia = new ListMedia();
        else listMediaStack.addFirst(new ListMedia());
    }

    @Override
    public void setListName(String name) throws InvalidBuilderOperationException {
        if (listMediaStack.size() == 0){
            if (listMedia.getName() != null) throw new InvalidBuilderOperationException("The main playlist is already named");
            listMedia.setName(name);
        } else {
            if (listMediaStack.getFirst().getName() != null) throw new InvalidBuilderOperationException("This list is already named");
            listMediaStack.getFirst().setName(name);
        }
    }

    @Override
    public void setDuration(int duration) throws InvalidBuilderOperationException {
        if (this.duration != -1) throw new InvalidBuilderOperationException("The duration has already been set");
        this.duration = duration;
    }

    @Override
    public void setName(String name) throws InvalidBuilderOperationException {
        if (this.name != null) throw new InvalidBuilderOperationException("The name has already been set");
        this.name = name;
    }

    @Override
    public void stopMedia() throws InvalidBuilderOperationException {
        if (state != BuilderState.MEDIA) throw new InvalidBuilderOperationException("No media to stop");
        if (duration == -1 || name == null) throw new InvalidBuilderOperationException("Not all parameters have been set for this media");

        StdMedia m = new StdMedia(duration, name);

        if (listMediaStack.size() != 0) listMediaStack.getFirst().add(m);
        else listMedia.add(m);

        state = BuilderState.NONE;
    }

    @Override
    public void stopList() throws InvalidBuilderOperationException {
        if (listMedia != null){
            if (listMediaStack.size() == 1){
                if (listMediaStack.getFirst().getName() == null) throw new InvalidBuilderOperationException("This playlist does not have any name");
                listMediaStack.getFirst().setParent(listMedia);
                listMedia.add(listMediaStack.removeFirst());
            } else if(listMediaStack.size() > 1) {
                if (listMediaStack.getFirst().getName() == null) throw new InvalidBuilderOperationException("This playlist does not have any name");
                ListMedia l = listMediaStack.removeFirst();
                l.setParent(listMediaStack.getFirst());
                listMediaStack.getFirst().add(l);
            } else {
                if (listMedia.getName() == null) throw new InvalidBuilderOperationException("The main playlist does not have any name");
            }
        } else throw new InvalidBuilderOperationException("There is no playlist to stop");
    }

    @Override
    public ListMedia getList() {
        return listMedia;
    }

    @Override
    public void resetList() {
        listMedia = null;
        state = BuilderState.NONE;
        listMediaStack.clear();
    }
}
