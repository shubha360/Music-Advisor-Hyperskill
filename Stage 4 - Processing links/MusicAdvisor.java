package advisor;

import java.util.Scanner;

public class MusicAdvisor {

    Scanner scanner = new Scanner(System.in);
    boolean authorized = false;

    public void start() {

        while (true) {

            String input = scanner.nextLine();

            switch (input) {

                case "auth":
                    authorizeUser();
                    break;

                case "featured":
                    printFeatured(authorized);
                    break;

                case "new":
                    printNew(authorized);
                    break;

                case "categories":
                    printCategories(authorized);
                    break;

                case "exit":
                    System.out.println("---GOODBYE!---");
                    return;

                default:

                    if (input.matches("playlists .+")) {

                        String cName = input.substring(10);
                        printPlayListWithCName(authorized, cName);
                    }
            }
        }
    }

    public void authorizeUser() {

        Authentication authentication = new Authentication();
        authentication.getAccessCode();
        authentication.getAccessToken();
        authorized = true;
    }



    private void printFeatured(boolean auth) {

        if (auth) {
            MusicApi.getFeaturedPlaylists();
        } else {
            System.out.println("Please, provide access for application.");
        }
    }

    private void printNew(boolean auth) {

        if (auth) {
            MusicApi.getNewReleases();
        } else {
            System.out.println("Please, provide access for application.");
        }
    }

    private void printCategories(boolean auth) {

        if (auth) {
            MusicApi.getAllCategories();
            MusicApi.printCategoryList();
        } else {
            System.out.println("Please, provide access for application.");
        }
    }

    private void printPlayListWithCName(boolean auth, String cName) {

        if (auth) {
            MusicApi.getSelectedPlaylists(cName);
        } else {
            System.out.println("Please, provide access for application.");
        }
    }
}
