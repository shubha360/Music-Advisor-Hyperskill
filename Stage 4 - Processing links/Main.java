package advisor;


public class Main {
    public static void main(String[] args) {

        if (args.length > 0) {

            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-access")) {
                    i++;
                    Authentication.SERVER_PATH = args[i];
                } else if (args[i].equals("-resource")) {
                    i++;
                    MusicApi.API_LINK = args[i];
                }
            }
        }

        MusicAdvisor musicAdvisor = new MusicAdvisor();
        musicAdvisor.start();
    }
}
