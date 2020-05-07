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
    private BufferedReader consoleReader;

    public Editor(EditorModel model){
        this.model = model;

        ChangeListener changeListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                System.out.println("Current list : " + Editor.this.model.getCurrentList().getName());
            }
        };

        this.model.addChangeListener(changeListener);
        consoleReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void run() throws IOException {
        String command = "";

        System.out.println("Welcome to the playlist editor ! Type 'help' to see the commands.");

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

    public void createList(String command){
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

    public void loadList(String command){
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
            model.loadPlaylist(arg[1]);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveList(String command){
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

    public void printCurrentList(){
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

    public void enterSubList(String command){
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

    public void backToParentList(){
        try {
            model.returnToParentList();
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    public void importMedia(String command){
        try {
            String[] arg;
            if (command.matches("import media \".*\"")){
                arg = command.split("\"");

                if (arg.length != 2) {
                    System.out.println("ERROR : you have to give the media file to import");
                    return;
                }

                System.out.println(arg[0] + " - " + arg[1]);
                model.importMedia(arg[1]);
            } else {
                arg = command.split(" ");

                if (arg.length != 3) {
                    System.out.println("ERROR : you have to give the media file to import");
                    return;
                }

                System.out.println(arg[0] + " " + arg[1] + " - " + arg[2]);
                model.importMedia(arg[2]);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void importFolder(String command){
        try {
            String[] arg;
            if (command.matches("import folder \".*\"")){
                arg = command.split("\"");

                if (arg.length != 2) {
                    System.out.println("ERROR : you have to give the media folder");
                    return;
                }

                System.out.println(arg[0] + " - " + arg[1]);
                model.importFolderMedia(arg[1]);
            } else {
                arg = command.split(" ");

                if (arg.length != 3) {
                    System.out.println("ERROR : you have to give the media folder");
                    return;
                }

                System.out.println(arg[0] + " " + arg[1] + " - " + arg[2]);
                model.importFolderMedia(arg[2]);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void importList(String command){
        try {
            String[] arg;
            if (command.matches("import list \".*\"")){
                arg = command.split("\"");

                if (arg.length != 2) {
                    System.out.println("ERROR : you have to give the list file to import");
                    return;
                }

                System.out.println(arg[0] + " - " + arg[1]);
                model.importList(arg[1]);
            } else {
                arg = command.split(" ");

                if (arg.length != 3) {
                    System.out.println("ERROR : you have to give the list file to import");
                    return;
                }

                System.out.println(arg[0] + " " + arg[1] + " - " + arg[2]);
                model.importList(arg[2]);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public String getHelpInfo(){
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

    public EditorModel getModel() { return model; }
    public void setModel(EditorModel model) { this.model = model; }
    public BufferedReader getConsoleReader() { return consoleReader; }
    public void setConsoleReader(BufferedReader consoleReader) { this.consoleReader = consoleReader; }

    public static void main(String[] args){
        EditorModel model = new StdEditorModel();
        Editor editor = new Editor(model);

        try {
            editor.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
