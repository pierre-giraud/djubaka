package editor;

import builders.MediaBuilder;
import exceptions.BadFileExtensionException;
import exceptions.BadMediaTypeException;
import medias.Media;
import medias.MediaLoader;
import medias.ListMedia;
import medias.MediaSaver;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;
import java.io.File;

public class StdEditorModel implements EditorModel {

    private MediaLoader stdLoader;
    private MediaLoader fileLoader;
    private MediaSaver saver;

    private ListMedia listMedia;
    private ListMedia currentList;

    private ChangeEvent changeEvent;
    private EventListenerList listenerList;

    public StdEditorModel(MediaLoader stdLoader, MediaLoader fileLoader, MediaSaver saver){
        this.stdLoader = stdLoader;
        this.fileLoader = fileLoader;
        this.saver = saver;

        listenerList = new EventListenerList();
    }

    @Override
    public void createPlaylist(String name) {
        listMedia = new ListMedia();
        listMedia.setName(name);
        currentList = listMedia;
        fireStateChanged();
    }

    @Override
    public void loadPlaylist(String filename, MediaBuilder builder) throws Exception {
        if (!filename.substring(filename.length() - 4).equals(".xpl")) throw new BadFileExtensionException("This file type is not supported");
        fileLoader.load(filename, builder);
        listMedia = builder.getList();
        currentList = listMedia;
        builder.resetList();
        fireStateChanged();
    }

    @Override
    public void savePlaylist(String filename) throws Exception {
        if (!filename.substring(filename.length() - 4).equals(".xpl")) throw new BadFileExtensionException("This file type is not supported");
        saver.save(filename, listMedia);
    }

    @Override
    public void importMedia(String filename, MediaBuilder builder) throws Exception {
        if (currentList == null) throw new NullPointerException("Cannot import file : no list has been created");
        stdLoader.load(filename, builder);
        currentList.add(builder.getList().getChild(0));
        builder.resetList();
    }

    @Override
    public void importFolderMedia(String folder, MediaBuilder builder) throws Exception {
        if (currentList == null) throw new NullPointerException("Cannot import file : no list has been created");

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
        if (!filename.substring(filename.length() - 4).equals(".xpl")) throw new BadFileExtensionException("This file type is not supported");
        fileLoader.load(filename, builder);
        currentList.add(builder.getList());
        builder.resetList();
    }

    @Override
    public void enterSubList(int num) throws BadMediaTypeException {
        if (currentList == null) throw new NullPointerException("No list has been created");
        if (num >= currentList.getChildren().size()) throw new NullPointerException("No child n°" + num + " found");

        Media m = currentList.getChild(num);

        if (m instanceof ListMedia){
            ListMedia sublist = (ListMedia) m;
            sublist.setParent(currentList);
            currentList = sublist;
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
    public void addChangeListener(ChangeListener listener) {
        listenerList.add(ChangeListener.class, listener);
    }

    @Override
    public void removeChangeListener(ChangeListener listener) {
        listenerList.remove(ChangeListener.class, listener);
    }

    protected void fireStateChanged(){
        ChangeListener[] listeners = listenerList.getListeners(ChangeListener.class);

        for (ChangeListener cl : listeners){
            if (changeEvent == null) changeEvent = new ChangeEvent(this);
            cl.stateChanged(changeEvent);
        }
    }

    public MediaLoader getStdLoader() {
        return stdLoader;
    }

    public void setStdLoader(MediaLoader stdLoader) {
        this.stdLoader = stdLoader;
    }

    public MediaLoader getFileLoader() {
        return fileLoader;
    }

    public void setFileLoader(MediaLoader fileLoader) {
        this.fileLoader = fileLoader;
    }

    public MediaSaver getSaver() {
        return saver;
    }

    public void setSaver(MediaSaver saver) {
        this.saver = saver;
    }

    public ListMedia getListMedia() {
        return listMedia;
    }

    public void setListMedia(ListMedia listMedia) {
        this.listMedia = listMedia;
    }
}
