package builders;

import exceptions.InvalidBuilderOperationException;
import medias.Music;
import medias.ListMedia;
import medias.Video;

import java.util.ArrayDeque;
import java.util.Deque;

public class StdMediaBuilder implements MediaBuilder {
    private int duration;
    private String name;
    private String artist;
    private String resolution;

    private BuilderState state;
    private ListMedia listMedia;
    private Deque<ListMedia> listMediaStack;

    public StdMediaBuilder(){
        listMediaStack = new ArrayDeque<>();
        state = BuilderState.NONE;
    }

    @Override
    public void startMusic() throws InvalidBuilderOperationException {
        if (state != BuilderState.NONE) throw new InvalidBuilderOperationException("The previous media is not finished");
        state = BuilderState.MUSIC;
        duration = -1;
        name = artist = resolution = null;
    }

    @Override
    public void startList() throws InvalidBuilderOperationException {
        if (state != BuilderState.NONE) throw new InvalidBuilderOperationException("The previous media is not finished");
        if (listMedia == null) listMedia = new ListMedia();
        else listMediaStack.addFirst(new ListMedia());
    }

    @Override
    public void startVideo() throws InvalidBuilderOperationException {
        if (state != BuilderState.NONE) throw new InvalidBuilderOperationException("The previous media is not finished");
        state = BuilderState.VIDEO;
        duration = -1;
        name = artist = resolution = null;
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
    public void setArtist(String artist) throws InvalidBuilderOperationException {
        if (state != BuilderState.MUSIC) throw new InvalidBuilderOperationException("This media is not a music");
        if (this.artist != null) throw new InvalidBuilderOperationException("The artist has already been set");
        this.artist = artist;
    }

    @Override
    public void setResolution(String resolution) throws InvalidBuilderOperationException {
        if (state != BuilderState.VIDEO) throw new InvalidBuilderOperationException("This media is not a video");
        if (this.resolution != null) throw new InvalidBuilderOperationException("The artist has already been set");
        this.resolution = resolution;
    }

    @Override
    public void stopMusic() throws InvalidBuilderOperationException {
        if (state != BuilderState.MUSIC) throw new InvalidBuilderOperationException("This media is not a music");
        if (duration == -1 || name == null || artist == null) throw new InvalidBuilderOperationException("Not all parameters have been set for this media");

        Music m = new Music(duration, name, artist);

        if (listMediaStack.size() != 0) listMediaStack.getFirst().add(m);
        else listMedia.add(m);

        state = BuilderState.NONE;
    }

    @Override
    public void stopList() throws InvalidBuilderOperationException {
        if (listMedia != null){
            if (listMediaStack.size() != 0){
                if (listMediaStack.getFirst().getName() == null) throw new InvalidBuilderOperationException("This playlist does not have any name");
                listMedia.add(listMediaStack.removeFirst());
            } else {
                if (listMedia.getName() == null) throw new InvalidBuilderOperationException("The main playlist does not have any name");
            }
        } else throw new InvalidBuilderOperationException("There is no playlist to stop");
    }

    @Override
    public void stopVideo() throws InvalidBuilderOperationException {
        if (state != BuilderState.VIDEO) throw new InvalidBuilderOperationException("This media is not a video");
        if (duration == -1 || name == null || resolution == null) throw new InvalidBuilderOperationException("Not all parameters have been set for this media");

        Video v = new Video(duration, name, resolution);

        if (listMediaStack.size() != 0) listMediaStack.getFirst().add(v);
        else listMedia.add(v);

        state = BuilderState.NONE;
    }

    @Override
    public ListMedia getList() {
        return listMedia;
    }

    @Override
    public void resetList() {
        this.listMedia = null;
    }
}
