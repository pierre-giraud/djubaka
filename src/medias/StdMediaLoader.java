package medias;

import builders.MediaBuilder;
import exceptions.BadMediaTypeException;

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

        if (filename.contains(".mta")) {
            builder.startList();
            builder.setListName("none");
            builder.startMusic();
            builder.setDuration(Integer.parseInt(data.get(0)));
            builder.setName(data.get(1));
            builder.setArtist(data.get(2));
            builder.stopMusic();
            builder.stopList();
        } else if (filename.contains(".mtv")){
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
}
