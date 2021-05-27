package advisor;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean authorized = false;

        while (true) {

            String input = scanner.nextLine();

            switch (input) {

                case "auth":
                    System.out.println("https://accounts.spotify.com/authorize?client_id=d5e4a5e431d9471e97fec15d26509715&response_type=code&redirect_uri=https://open.spotify.com/");
                    System.out.println("---SUCCESS---");
                    authorized = true;
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

                case "playlists Mood":

                    break;

                case "exit":
                    System.out.println("---GOODBYE!---");
                    return;
            }
        }
    }

    static void printFeatured(boolean auth) {

        if (auth) {

            System.out.println("---FEATURED---\n" +
                    "Mellow Morning\n" +
                    "Wake Up and Smell the Coffee\n" +
                    "Monday Motivation\n" +
                    "Songs to Sing in the Shower");
        } else {
            System.out.println("Please, provide access for application.");
        }
    }

    static void printNew(boolean auth) {

        if (auth) {

            System.out.println("---NEW RELEASES---\n" +
                    "Mountains [Sia, Diplo, Labrinth]\n" +
                    "Runaway [Lil Peep]\n" +
                    "The Greatest Show [Panic! At The Disco]\n" +
                    "All Out Life [Slipknot]");
        } else {
            System.out.println("Please, provide access for application.");
        }
    }

    static void printCategories(boolean auth) {

        if (auth) {

            System.out.println("---CATEGORIES---\n" +
                    "Top Lists\n" +
                    "Pop\n" +
                    "Mood\n" +
                    "Latin");
        } else {
            System.out.println("Please, provide access for application.");
        }
    }

    static void printPlayList(boolean auth) {

        if (auth) {

            System.out.println("---MOOD PLAYLISTS---\n" +
                    "Walk Like A Badass  \n" +
                    "Rage Beats  \n" +
                    "Arab Mood Booster  \n" +
                    "Sunday Stroll");
        } else {
            System.out.println("Please, provide access for application.");
        }
    }
}
