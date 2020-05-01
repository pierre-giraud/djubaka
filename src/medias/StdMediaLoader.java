package medias;

import builders.MediaBuilder;
import exceptions.BadMediaTypeException;
import exceptions.InvalidBuilderOperationException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class StdMediaLoader implements MediaLoader{
    @Override
    public void load(String filename, MediaBuilder builder) throws Exception {
        File file = new File(filename);
        List<String> data;

        BufferedReader reader = new BufferedReader(new FileReader(file));
        data = new ArrayList<>();
        String line;

        while ((line = reader.readLine()) != null){
            data.add(line);
        }

        if (data.size() < 3 || hasNullInfo(data)) throw new InvalidBuilderOperationException("Cannot load : this file does not contain the minimum required information");

        String ext = filename.substring(filename.length() - 4);
        if (ext.equals(".mta")) {
            builder.startList();
            builder.setListName("none");
            builder.startMusic();
            builder.setDuration(Integer.parseInt(data.get(0)));
            builder.setName(data.get(1));
            builder.setArtist(data.get(2));
            builder.stopMusic();
            builder.stopList();
        } else if (ext.equals(".mtv")){
            builder.startList();
            builder.setListName("none");
            builder.startVideo();
            builder.setDuration(Integer.parseInt(data.get(0)));
            builder.setName(data.get(1));
            builder.setResolution(data.get(2));
            builder.stopVideo();
            builder.stopList();
        } else {
            throw new BadMediaTypeException("Incorrect media type");
        }
    }

    private boolean hasNullInfo(List<String> data) {
        boolean bool = false;

        for (int i = 0; i < 3; i++) {
            if (data.get(i).equals("")){
                bool = true;
                break;
            }
        }

        return bool;
    }
}
