package medias;

import builders.MediaBuilder;

public interface MediaLoader {
    void load(String filename, MediaBuilder builder) throws Exception;
}
