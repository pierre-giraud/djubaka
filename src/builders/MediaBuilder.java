package builders;

import exceptions.InvalidBuilderOperationException;
import medias.Media;

public interface MediaBuilder {
    void startMusic() throws InvalidBuilderOperationException;
    void startPlaylist() throws InvalidBuilderOperationException;
    void startVideo() throws InvalidBuilderOperationException;

    void setPlayListName(String name) throws InvalidBuilderOperationException;
    void setDuration(int duration) throws InvalidBuilderOperationException;
    void setName(String name) throws InvalidBuilderOperationException;
    void setArtist(String artist) throws InvalidBuilderOperationException;
    void setResolution(String resolution) throws InvalidBuilderOperationException;

    void stopMusic() throws InvalidBuilderOperationException;
    void stopPlaylist() throws InvalidBuilderOperationException;
    void stopVideo() throws InvalidBuilderOperationException;

    Media getPlaylist();
}
