package medias;

import builders.MediaBuilder;
import exceptions.BadMediaFormatException;
import exceptions.InvalidBuilderOperationException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StdMediaLoader implements MediaLoader {
    @Override
    public void load(String filename, MediaBuilder builder) {
        File file = new File(filename);
        List<String> data;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            data = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null){
                data.add(line);
            }

            if (filename.contains(".mta")) {
                builder.startMusic();
                builder.setDuration(Integer.parseInt(data.get(0)));
                builder.setName(data.get(1));
                builder.setArtist(data.get(2));
                builder.stopMusic();
            } else if (filename.contains(".mtv")){
                builder.startVideo();
                builder.setDuration(Integer.parseInt(data.get(0)));
                builder.setName(data.get(1));
                builder.setResolution(data.get(2));
                builder.stopVideo();
            } else {
                try {
                    throw new BadMediaFormatException("Format de fichier Media incompatible");
                } catch (BadMediaFormatException e){
                    e.printStackTrace();
                }
            }
        } catch (IOException | InvalidBuilderOperationException e) {
            e.printStackTrace();
        }
    }
}
