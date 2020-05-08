package media;

import exceptions.InvalidBuilderOperationException;
import media.ListMedia;

public interface MediaBuilder {
    void startMedia() throws InvalidBuilderOperationException;
    void startList() throws InvalidBuilderOperationException;

    void setListName(String name) throws InvalidBuilderOperationException;
    void setDuration(int duration) throws InvalidBuilderOperationException;
    void setName(String name) throws InvalidBuilderOperationException;

    void stopMedia() throws InvalidBuilderOperationException;
    void stopList() throws InvalidBuilderOperationException;

    ListMedia getList();
    void resetList();
}
