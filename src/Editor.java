import builders.MediaBuilder;
import builders.StdMediaBuilder;
import editor.EditorModel;
import editor.StdEditorModel;
import media.Media;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Editor {

    private EditorModel model;
    private MediaBuilder builder;
    private ChangeListener changeListener;

    private BufferedReader consoleReader;

    public Editor(EditorModel model, MediaBuilder builder){
        this.model = model;
        this.builder = builder;

        changeListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                System.out.println("Current list : " + getModel().getCurrentList().getName());
            }
        };

        this.model.addChangeListener(changeListener);
        consoleReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void run() throws IOException {
        String command = "";

        while (!command.equals("stop")){
            command = consoleReader.readLine();

            if (command.contains("create")){
                createList(command);
            } else if (command.contains("load")){
                loadList(command);
            } else if (command.contains("save")){
                saveList(command);
            } else if (command.equals("list")) {
                printCurrentList();
            } else if (command.contains("enter")){
                enterSubList(command);
            } else if (command.equals("back")){
                backToParentList();
            } else if (command.contains("import media")){
                importMedia(command);
            } else if(command.contains("import list")){
                importList(command);
            } else if(command.contains("import folder")) {
                importFolder(command);
            } else if (command.equals("help")){
                System.out.println(getHelpInfo());
            } else {
                System.out.println("Unknown command");
            }
        }
    }

    private void createList(String command){
        String[] arg;
        if (command.matches("create \".*\"")){
            arg = command.split("\"");
        } else {
            arg = command.split(" ");
        }

        if (arg.length < 2) {
            System.out.println("ERROR : you have to give a name to your new playlist");
            return;
        }

        try {
            model.getCurrentList();
            System.out.println("Replacing current playlist ...");
        } catch (NullPointerException ignored){}

        model.createPlaylist(arg[1]);
    }

    private void loadList(String command){
        String[] arg;
        if (command.matches("load \".*\"")){
            arg = command.split("\"");
        } else {
            arg = command.split(" ");
        }

        if (arg.length < 2) {
            System.out.println("ERROR : you have to give the name of a file");
            return;
        }

        try {
            model.getCurrentList();
            System.out.println("Replacing current playlist ...");
        } catch (NullPointerException ignored){}

        try {
            model.loadPlaylist(arg[1], builder);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void saveList(String command){
        String[] arg;
        if (command.matches("save \".*\"")){
            arg = command.split("\"");
        } else {
            arg = command.split(" ");
        }

        if (arg.length < 2) {
            System.out.println("ERROR : you have to give the name of a file");
            return;
        }

        try {
            model.savePlaylist(arg[1]);
            System.out.println("Playlist saved");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void printCurrentList(){
        try {
            StringBuilder output = new StringBuilder("List : " + model.getCurrentList().getName());

            if (model.getCurrentList().getChildren().size() == 0) {
                output.append(" (empty)");
            } else {
                output.append("\n");
                int n = 0;

                for(Media m : model.getCurrentList().getChildren()){
                    output.append("\t").append(n++).append(" : ").append(m.toString()).append("\n");
                }
            }

            System.out.println(output);
        } catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
    }

    private void enterSubList(String command){
        String[] arg = command.split(" ");
        if (arg.length != 2) {
            System.out.println("ERROR : you have to give the number of the child");
            return;
        }

        int num = Integer.parseInt(arg[1]);

        try {
            model.enterSubList(num);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void backToParentList(){
        try {
            model.returnToParentList();
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    private void importMedia(String command){
        try {
            String[] arg;
            if (command.matches("import media \".*\"")){
                arg = command.split("\"");

                if (arg.length != 2) {
                    System.out.println("ERROR : you have to give the media file to import");
                    return;
                }

                System.out.println(arg[0] + " - " + arg[1]);
                model.importMedia(arg[1], builder);
            } else {
                arg = command.split(" ");

                if (arg.length != 3) {
                    System.out.println("ERROR : you have to give the media file to import");
                    return;
                }

                System.out.println(arg[0] + " " + arg[1] + " - " + arg[2]);
                model.importMedia(arg[2], builder);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void importFolder(String command){
        try {
            String[] arg;
            if (command.matches("import folder \".*\"")){
                arg = command.split("\"");

                if (arg.length != 2) {
                    System.out.println("ERROR : you have to give the media folder");
                    return;
                }

                System.out.println(arg[0] + " - " + arg[1]);
                model.importFolderMedia(arg[1], builder);
            } else {
                arg = command.split(" ");

                if (arg.length != 3) {
                    System.out.println("ERROR : you have to give the media folder");
                    return;
                }

                System.out.println(arg[0] + " " + arg[1] + " - " + arg[2]);
                model.importFolderMedia(arg[2], builder);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void importList(String command){
        try {
            String[] arg;
            if (command.matches("import list \".*\"")){
                arg = command.split("\"");

                if (arg.length != 2) {
                    System.out.println("ERROR : you have to give the list file to import");
                    return;
                }

                System.out.println(arg[0] + " - " + arg[1]);
                model.importList(arg[1], builder);
            } else {
                arg = command.split(" ");

                if (arg.length != 3) {
                    System.out.println("ERROR : you have to give the list file to import");
                    return;
                }

                System.out.println(arg[0] + " " + arg[1] + " - " + arg[2]);
                model.importList(arg[2], builder);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private String getHelpInfo(){
        String help = "List of allowed commands : \n";
        help += String.format("%-42s","\tcreate {listname|\"list name\"}");
        help += " : Create a new list, replace the old one if exists\n";
        help += String.format("%-42s","\tload {filepath|\"file path\"}");
        help += " : Load the list saved in the file specified by the path\n";
        help += String.format("%-42s","\tsave {filepath|\"file path\"}");
        help += " : Save the current list into the file specified by the path\n";
        help += String.format("%-42s","\tlist");
        help += " : List the content of the current list\n";
        help += String.format("%-42s","\tenter {numeric value}");
        help += " : Enter in the sublist specified by the numeric value\n";
        help += String.format("%-42s","\tback");
        help += " : Return to parent list if exists\n";
        help += String.format("%-42s","\timport media {filepath|\"file path\"}");
        help += " : Import a media into the current list from its file specified by the path\n";
        help += String.format("%-42s","\timport folder {folderpath|\"folder path\"}");
        help += " : Import all the medias into the current list from the folder specified by the path\n";
        help += String.format("%-42s","\timport list {filepath|\"file path\"}");
        help += " : Import a list as a sublist into the current list from its file specified by the path\n";
        help += String.format("%-42s","\thelp");
        help += " : Print theses information\n";
        help += String.format("%-42s","\tstop");
        help += " : Leave this program";

        return help;
    }

    public EditorModel getModel() {
        return model;
    }

    public void setModel(EditorModel model) {
        this.model = model;
    }

    public static void main(String[] args){
        EditorModel model = new StdEditorModel();
        MediaBuilder builder = new StdMediaBuilder();
        Editor editor = new Editor(model, builder);

        try {
            editor.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
