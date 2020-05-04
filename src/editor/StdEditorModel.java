package editor;

import builders.MediaBuilder;
import exceptions.BadMediaTypeException;
import media.Media;
import media.ListMedia;
import file.MediaLoader;
import file.MediaSaver;
import observable.ObservableSubject;

import java.io.File;

public class StdEditorModel extends ObservableSubject implements EditorModel {

    private ListMedia listMedia;
    private ListMedia currentList;

    public StdEditorModel(){ super(); }

    @Override
    public void createPlaylist(String name) {
        listMedia = new ListMedia();
        listMedia.setName(name);
        currentList = listMedia;
        fireStateChanged();
    }

    @Override
    public void loadPlaylist(String filename, MediaBuilder builder) throws Exception {
        listMedia = MediaLoader.loadListFromXPL(filename, builder);
        currentList = listMedia;
        builder.resetList();
        fireStateChanged();
    }

    @Override
    public void savePlaylist(String filename) throws Exception {
        MediaSaver.saveXPL(filename, listMedia);
    }

    @Override
    public void importMedia(String filename, MediaBuilder builder) throws Exception {
        if (currentList == null) throw new NullPointerException("Cannot import file : no list has been created");
        Media m = MediaLoader.loadStdMediaFromMediaFile(filename);
        currentList.add(m);
        builder.resetList();
    }

    @Override
    public void importFolderMedia(String folder, MediaBuilder builder) throws Exception {
        if (currentList == null) throw new NullPointerException("Cannot import folder : no list has been created");

        File fold = new File(folder);
        if (fold.listFiles() == null) throw new NullPointerException("This folder is empty or does not exist");

        for (File entry : fold.listFiles()) {
            if (entry.isDirectory()) {
                importFolderMedia(entry.getPath(), builder);
            } else {
                try {
                    importMedia(entry.getPath(), builder);
                } catch (Exception e){
                    System.out.println(e.getMessage() + " (" + entry.getPath() + ")");
                    builder.resetList();
                }
            }
        }
    }

    @Override
    public void importList(String filename, MediaBuilder builder) throws Exception {
        if (currentList == null) throw new NullPointerException("Cannot import list : no list has been created");
        ListMedia l = MediaLoader.loadListFromXPL(filename, builder);
        l.setParent(currentList);
        currentList.add(l);
        builder.resetList();
    }

    @Override
    public void enterSubList(int num) throws BadMediaTypeException {
        if (currentList == null) throw new NullPointerException("No list has been created");
        if (num >= currentList.getChildren().size()) throw new NullPointerException("No child n°" + num + " found");

        Media m = currentList.getChild(num);

        if (m instanceof ListMedia){
            currentList = (ListMedia) m;
            fireStateChanged();
        } else {
            throw new BadMediaTypeException("Entry " + num + " is not a sublist");
        }
    }

    @Override
    public void returnToParentList() {
        if (currentList == null) throw new NullPointerException("No list has been created");
        else if (currentList.getParent() != null){
            currentList = currentList.getParent();
            fireStateChanged();
        } else throw new NullPointerException("This list has no parents");
    }

    @Override
    public ListMedia getCurrentList() {
        if (currentList != null) return currentList;
        else throw new NullPointerException("No list has been created");
    }

    @Override
    public ListMedia getPlaylist() {
        if (listMedia != null) return listMedia;
        else throw new NullPointerException("No list has been created");
    }
}
