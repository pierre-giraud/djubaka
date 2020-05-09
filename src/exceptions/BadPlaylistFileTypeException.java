package exceptions;

public class BadPlaylistFileTypeException extends Exception {
    public BadPlaylistFileTypeException(String msg){
        super(msg);
    }
}
