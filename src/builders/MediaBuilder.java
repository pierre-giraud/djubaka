package builders;

import exceptions.InvalidBuilderOperationException;
import medias.ListMedia;

public interface MediaBuilder {
    void startMusic() throws InvalidBuilderOperationException;
    void startList() throws InvalidBuilderOperationException;
    void startVideo() throws InvalidBuilderOperationException;

    void setListName(String name) throws InvalidBuilderOperationException;
    void setDuration(int duration) throws InvalidBuilderOperationException;
    void setName(String name) throws InvalidBuilderOperationException;
    void setArtist(String artist) throws InvalidBuilderOperationException;
    void setResolution(String resolution) throws InvalidBuilderOperationException;

    void stopMusic() throws InvalidBuilderOperationException;
    void stopList() throws InvalidBuilderOperationException;
    void stopVideo() throws InvalidBuilderOperationException;

    ListMedia getList();
    void setList(ListMedia l);
}
