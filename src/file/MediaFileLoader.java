package file;

import exceptions.BadMediaTypeException;
import exceptions.InvalidBuilderOperationException;
import media.Media;
import media.StdMedia;
import media.StdMusic;
import media.StdVideo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class MediaFileLoader {
    public static Media loadStdMediaMediaFile(String filename) throws Exception {
        File file = new File(filename);

        String ext = filename.substring(filename.length() - 4);
        if (!ext.equals(".mta") && !ext.equals(".mtv")) throw new BadMediaTypeException("Media type not supported");

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String duration = reader.readLine();
        if (duration == null || duration.equals("")) throw new InvalidBuilderOperationException("Cannot load : this file does not contain any duration");

        return new StdMedia(Integer.parseInt(duration), filename);
    }

    public static Media loadRealMediaFromFile(String filename) throws Exception {
        File file = new File(filename);

        String ext = filename.substring(filename.length() - 4);
        if (!ext.equals(".mta") && !ext.equals(".mtv")) throw new BadMediaTypeException("Media type not supported");

        List<String> data = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;

        while ((line = reader.readLine()) != null){
            data.add(line);
        }

        if (data.size() < 3) throw new InvalidBuilderOperationException("Cannot load : this file does not contain all the information required");
        else if (data.get(0).equals("")) throw new InvalidBuilderOperationException("Cannot load : this file does not contain any duration");

        if (ext.equals(".mta")) return new StdMusic(Integer.parseInt(data.get(0)), data.get(1), data.get(2));
        return new StdVideo(Integer.parseInt(data.get(0)), data.get(1), data.get(2));
    }

}
