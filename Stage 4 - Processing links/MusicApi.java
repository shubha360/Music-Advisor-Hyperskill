package advisor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class MusicApi {

    static String API_LINK = "https://api.spotify.com";
    static HashMap<String, String> categoryNameAndId = new HashMap<>();
    static boolean categoriesObtained = false;

    static void getAllCategories() {

        String requestUri = API_LINK + "/v1/browse/categories";

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + Authentication.ACCESS_TOKEN)
                .uri(URI.create(requestUri))
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newBuilder().build();

        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
//            System.out.println(response.statusCode());

            JsonObject object = JsonParser.parseString(response.body()).
                    getAsJsonObject().
                    getAsJsonObject("categories");

            JsonArray array = object.getAsJsonArray("items");

            for (JsonElement item : array) {

                String name = item.getAsJsonObject().get("name").getAsString();
                String id = item.getAsJsonObject().get("id").getAsString();
                categoryNameAndId.put(name, id);
                categoriesObtained = true;
            }

        } catch (InterruptedException | IOException e) {
            System.out.println("Problem in handling category response.");
            e.printStackTrace();

        }
    }

    static void printCategoryList() {
        System.out.println("Top Lists");
        for (var name : categoryNameAndId.keySet()) {
            System.out.println(name);
        }
    }

    static void getFeaturedPlaylists() {

        String requestUri = API_LINK + "/v1/browse/featured-playlists";

        HttpRequest request = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + Authentication.ACCESS_TOKEN)
                .uri(URI.create(requestUri))
                .GET()
                .build();

        HttpClient client = HttpClient.newBuilder().build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            LinkedHashMap<String, String> fPlaylists = new LinkedHashMap<>();

            JsonObject object = JsonParser.parseString(response.body())
                    .getAsJsonObject()
                    .getAsJsonObject("playlists");

            JsonArray array = object.getAsJsonArray("items");

            for (JsonElement item : array) {

                String url = item.getAsJsonObject()
                        .get("external_urls").getAsJsonObject()
                        .get("spotify").getAsString();

                String name = item.getAsJsonObject().get("name").getAsString();

                fPlaylists.put(name, url);
            }

            for (var entry : fPlaylists.entrySet()) {
                System.out.println(entry.getKey());
                System.out.println(entry.getValue());
                System.out.println();
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Problem in handling featured playlist response.");
            e.printStackTrace();
        }
    }

    static void getNewReleases() {

        String requestUri = API_LINK + "/v1/browse/new-releases";

        HttpRequest request = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + Authentication.ACCESS_TOKEN)
                .uri(URI.create(requestUri))
                .GET()
                .build();

        HttpClient client = HttpClient.newBuilder().build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonArray array = JsonParser.parseString(response.body()).getAsJsonObject()
                    .get("albums").getAsJsonObject()
                    .getAsJsonArray("items");

            for (JsonElement item : array) {


                String albumName = item.getAsJsonObject().get("name").getAsString();
                String albumLink = item.getAsJsonObject()
                        .get("external_urls").getAsJsonObject()
                        .get("spotify").getAsString();

                ArrayList<String> artistList = new ArrayList<>();

                JsonArray artists = item.getAsJsonObject().get("artists").getAsJsonArray();

                for (JsonElement artist : artists) {
                    artistList.add(artist.getAsJsonObject().get("name").getAsString());
                }

                System.out.println(albumName);
                System.out.println(artistList);
                System.out.println(albumLink);
                System.out.println();
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Problem in handling new releases response.");
            e.printStackTrace();
        }
    }

    static void getSelectedPlaylists(String categoryName) {

        if (!categoriesObtained) {
            getAllCategories();
            categoriesObtained = true;
        }

        if (!categoryNameAndId.containsKey(categoryName)) {
            System.out.println("Unknown category name.");
            return;
        }

        String categoryID = categoryNameAndId.get(categoryName);
        String requestUri = API_LINK + "/v1/browse/categories/" + categoryID + "/playlists";

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + Authentication.ACCESS_TOKEN)
                .uri(URI.create(requestUri))
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newBuilder().build();

        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.body().contains("error") && response.body().contains("404")) {

                JsonObject object = JsonParser.parseString(response.body()).getAsJsonObject();
                String message = object.get("error").getAsJsonObject()
                        .get("message").getAsString();
                System.out.println(message);
                return;
            }

            JsonObject object = JsonParser.parseString(response.body()).getAsJsonObject()
                    .get("playlists").getAsJsonObject();

            JsonArray array = object.get("items").getAsJsonArray();

            for (JsonElement item : array) {

                String name = item.getAsJsonObject().get("name").getAsString();
                String link = item.getAsJsonObject().get("external_urls").getAsJsonObject()
                        .get("spotify").getAsString();

                System.out.println(name);
                System.out.println(link);
                System.out.println();
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Problem in handling categorised playlist response.");
            e.printStackTrace();
        }
    }
}