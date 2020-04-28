package editor;

import builders.MediaBuilder;
import exceptions.BadMediaTypeException;
import medias.Media;
import medias.MediaLoader;
import medias.ListMedia;
import medias.MediaSaver;

public class StdEditorModel implements EditorModel {

    private MediaLoader stdLoader;
    private MediaLoader fileLoader;
    private MediaSaver saver;

    private ListMedia listMedia;
    private ListMedia currentList;

    public StdEditorModel(MediaLoader stdLoader, MediaLoader fileLoader, MediaSaver saver){
        this.stdLoader = stdLoader;
        this.fileLoader = fileLoader;
        this.saver = saver;
    }

    @Override
    public void createPlaylist(String name) {
        listMedia = new ListMedia();
        listMedia.setName(name);
        currentList = listMedia;
    }

    @Override
    public void loadPlaylist(String filename, MediaBuilder builder) {
        fileLoader.load(filename, builder);
        listMedia = builder.getList();
        currentList = listMedia;
        builder.setList(null);
    }

    @Override
    public void savePlaylist(String filename) {
        saver.save(filename, listMedia);
    }

    @Override
    public void importMedia(String filename, MediaBuilder builder) {
        stdLoader.load(filename, builder);
        currentList.add(builder.getList().getChild(0));
        builder.setList(null);
    }

    @Override
    public void importFolderMedia(String folder) {

    }

    @Override
    public void importList(String filename, MediaBuilder builder) {
        fileLoader.load(filename, builder);
        listMedia.add(builder.getList());
        builder.setList(null);
    }

    @Override
    public void enterSubList(int num) throws BadMediaTypeException {
        Media m = currentList.getChild(num);
        if (m instanceof ListMedia){
            ListMedia sublist = (ListMedia) m;
            sublist.setParent(currentList);
            currentList = sublist;
        } else {
            throw new BadMediaTypeException("Entry n°" + num + " is not a sublist");
        }
    }

    @Override
    public void returnToParentList() {
        if (currentList.getParent() != null){
            currentList = currentList.getParent();
        }
    }

    @Override
    public ListMedia getCurrentList() {
        return currentList;
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
