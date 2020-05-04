import media.ListMedia;
import media.Media;
import media.StdMusic;
import media.StdVideo;

public class Test {

    public static void main(String[] args){
        ListMedia list = new ListMedia();
        list.setName("Origin");
        ListMedia currentList = list;

        currentList.add(new StdVideo(20, "Video 1", "1980x1080"));
        currentList.add(new StdVideo(20, "Video 2", "1980x1080"));

        System.out.println("Current list children : ");
        for (Media m : currentList.getChildren()) System.out.println("Durée : " + m.getDuration() + ", name = " + m.getName());

        ListMedia subList = new ListMedia();
        subList.setName("SubList");

        subList.add(new StdMusic(180, "Musique 1", "Quelqu'un"));
        subList.add(new StdMusic(180, "Musique 2", "Quelqu'un"));
        subList.add(new StdVideo(200, "Une vidéo", "1980x1080"));

        currentList.add(subList);

        System.out.println("Current list children : ");
        for (Media m : currentList.getChildren()) System.out.println("Durée : " + m.getDuration() + ", name = " + m.getName());

        System.out.println("\nTest parenté\n");

        Media media = currentList.getChild(2);
        if (media instanceof ListMedia) {
            ListMedia sub = (ListMedia) media;
            sub.setParent(currentList);
            currentList = subList;
        }

        System.out.println("Current list children : ");
        System.out.println("Name : " + currentList.getName());
        currentList.add(new StdMusic(1, "Test music", "oui"));
        for (Media m : currentList.getChildren()) System.out.println("Durée : " + m.getDuration() + ", name = " + m.getName());
        System.out.println("Parent : ");
        for (Media m : currentList.getParent().getChildren()) System.out.println("Durée : " + m.getDuration() + ", name = " + m.getName());


        System.out.println("Global list children : ");
        System.out.println("Name : " + list.getName());
        for (Media m : list.getChildren()) System.out.println("Durée : " + m.getDuration() + ", name = " + m.getName());

        currentList = currentList.getParent();
        System.out.println("Current list children : ");
        System.out.println("Name : " + currentList.getName());
        for (Media m : currentList.getChildren()) System.out.println("Durée : " + m.getDuration() + ", name = " + m.getName());

        System.out.println("Global list children 3 : ");
        ListMedia li = (ListMedia) list.getChild(2);
        System.out.println("Name : " + li.getName());
        for (Media m : li.getChildren()) System.out.println("Durée : " + m.getDuration() + ", name = " + m.getName());
    }
}
