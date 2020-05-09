package editor;

import exceptions.BadMediaTypeException;
import file.MediaFileLoader;
import media.Media;
import media.ListMedia;
import file.XPLPlaylistLoader;
import file.XPLPlaylistSaver;
import observable.ObservableSubject;

import java.io.File;

public class StdEditorModel extends ObservableSubject implements EditorModel {

    private ListMedia listMedia;
    private ListMedia currentList;

    private XPLPlaylistLoader mediaLoader;

    public StdEditorModel(){
        super();
        mediaLoader = new XPLPlaylistLoader();
    }

    @Override
    public void createPlaylist(String name) {
        listMedia = new ListMedia();
        listMedia.setName(name);
        currentList = listMedia;
        fireStateChanged();
    }

    @Override
    public void loadPlaylist(String filename) throws Exception {
        listMedia = mediaLoader.loadListFromXPL(filename);
        currentList = listMedia;
        fireStateChanged();
    }

    @Override
    public void savePlaylist(String filename) throws Exception {
        XPLPlaylistSaver.saveXPL(filename, listMedia);
    }

    @Override
    public void importMedia(String filename) throws Exception {
        if (currentList == null) throw new NullPointerException("Cannot import file : no list has been created");
        Media m = MediaFileLoader.loadStdMediaMediaFile(filename);
        currentList.add(m);
    }

    @Override
    public void importFolderMedia(String folder) throws Exception {
        if (currentList == null) throw new NullPointerException("Cannot import folder : no list has been created");

        File fold = new File(folder);
        if (fold.listFiles() == null) throw new NullPointerException("This folder is empty or does not exist");

        for (File entry : fold.listFiles()) {
            if (entry.isDirectory()) {
                importFolderMedia(entry.getPath());
            } else {
                try {
                    importMedia(entry.getPath());
                } catch (Exception e){
                    System.out.println(e.getMessage() + " (" + entry.getPath() + ")");
                }
            }
        }
    }

    @Override
    public void importList(String filename) throws Exception {
        if (currentList == null) throw new NullPointerException("Cannot import list : no list has been created");
        ListMedia l = mediaLoader.loadListFromXPL(filename);
        l.setParent(currentList);
        currentList.add(l);
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

    public void setListMedia(ListMedia listMedia) {
        this.listMedia = listMedia;
    }

    public void setCurrentList(ListMedia currentList) {
        this.currentList = currentList;
    }

    public XPLPlaylistLoader getMediaLoader() {
        return mediaLoader;
    }

    public void setMediaLoader(XPLPlaylistLoader mediaLoader) {
        this.mediaLoader = mediaLoader;
    }
}
