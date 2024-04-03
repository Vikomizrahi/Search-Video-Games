package com.example.searchvideogames.services;

import android.os.StrictMode;


import com.example.searchvideogames.models.Game;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class DataService {

    public static ArrayList<Game> getArrGame() {
        ArrayList<Game> arrGame = new ArrayList<>();
        String apiKey = "98a3ffceb49a423984b6b648ffff1798";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        for (int page = 1; page <= 50; page++) {
            String sUrl = "https://api.rawg.io/api/games?&key=" + apiKey + "&page=" + page;

            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                JsonParser jsonParser = new JsonParser();
                JsonElement root = jsonParser.parse(new InputStreamReader(connection.getInputStream()));
                JsonObject rootObj = root.getAsJsonObject();
                JsonArray results = rootObj.getAsJsonArray("results");

                for (JsonElement element : results) {
                    JsonObject obj = element.getAsJsonObject();
                    String name = obj.get("name").getAsString();
                    String imageUrl = obj.get("background_image").getAsString();
                    String releaseDate = obj.get("released").getAsString();
                    String ratingString = obj.get("rating").getAsString();
                    double rating = Double.parseDouble(ratingString);


                    JsonArray platformsArray = obj.getAsJsonArray("parent_platforms");
                    List<String> platformsList = new ArrayList<>();
                    for (JsonElement platformElement : platformsArray) {
                        JsonObject platformObj = platformElement.getAsJsonObject();
                        JsonObject platform = platformObj.getAsJsonObject("platform");
                        String platformName = platform.get("name").getAsString();
                        platformsList.add(platformName);
                    }

                    arrGame.add(new Game(name, imageUrl, releaseDate,rating,platformsList));
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }

        return arrGame;
    }


    public static ArrayList<Game> getGamesByGenre(String genre) {
        ArrayList<Game> arrGame = new ArrayList<>();
        String apiKey = "98a3ffceb49a423984b6b648ffff1798";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        for (int page = 1; page <= 50; page++) {
            String sUrl = "https://api.rawg.io/api/games?&key=" + apiKey + "&page=" + page;

            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                JsonParser jsonParser = new JsonParser();
                JsonElement root = jsonParser.parse(new InputStreamReader(connection.getInputStream()));
                JsonObject rootObj = root.getAsJsonObject();
                JsonArray results = rootObj.getAsJsonArray("results");

                for (JsonElement element : results) {
                    JsonObject obj = element.getAsJsonObject();
                    JsonArray genres = obj.getAsJsonArray("genres");
                    for (JsonElement genreElement : genres) {
                        JsonObject genreObj = genreElement.getAsJsonObject();
                        String name = genreObj.get("name").getAsString();
                        if (name.equalsIgnoreCase(genre)) {
                            String gameName = obj.get("name").getAsString();
                            String imageUrl = obj.get("background_image").getAsString();
                            String releaseDate = obj.get("released").getAsString();
                            String ratingString = obj.get("rating").getAsString();
                            double rating = Double.parseDouble(ratingString);

                            JsonArray platformsArray = obj.getAsJsonArray("parent_platforms");
                            List<String> platformsList = new ArrayList<>();
                            for (JsonElement platformElement : platformsArray) {
                                JsonObject platformObj = platformElement.getAsJsonObject();
                                JsonObject platform = platformObj.getAsJsonObject("platform");
                                String platformName = platform.get("name").getAsString();
                                platformsList.add(platformName);
                            }
                            arrGame.add(new Game(gameName, imageUrl, releaseDate,rating,platformsList));
                            break; // break out of the inner loop once a match is found
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }

        return arrGame;
    }

    public static ArrayList<Game> getGamesByYear(String year) {
        ArrayList<Game> arrGame = new ArrayList<>();
        String apiKey = "98a3ffceb49a423984b6b648ffff1798";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        for (int page = 1; page <= 50; page++) {
            String sUrl = "https://api.rawg.io/api/games?&key=" + apiKey + "&page=" + page;

            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                JsonParser jsonParser = new JsonParser();
                JsonElement root = jsonParser.parse(new InputStreamReader(connection.getInputStream()));
                JsonObject rootObj = root.getAsJsonObject();
                JsonArray results = rootObj.getAsJsonArray("results");

                for (JsonElement element : results) {
                    JsonObject obj = element.getAsJsonObject();
                    String releaseDate = obj.get("released").getAsString();
                    String gameYear = releaseDate.substring(0, 4); // Extract the year from the release date
                    if (gameYear.equals(year)) {
                        String gameName = obj.get("name").getAsString();
                        String imageUrl = obj.get("background_image").getAsString();
                        String ratingString = obj.get("rating").getAsString();
                        double rating = Double.parseDouble(ratingString);

                        JsonArray platformsArray = obj.getAsJsonArray("parent_platforms");
                        List<String> platformsList = new ArrayList<>();
                        for (JsonElement platformElement : platformsArray) {
                            JsonObject platformObj = platformElement.getAsJsonObject();
                            JsonObject platform = platformObj.getAsJsonObject("platform");
                            String platformName = platform.get("name").getAsString();
                            platformsList.add(platformName);
                        }


                        arrGame.add(new Game(gameName, imageUrl, releaseDate,rating,platformsList));
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }

        return arrGame;
    }


    public static ArrayList<Game> getGamesByRating() {
        ArrayList<Game> arrGame = new ArrayList<>();
        String apiKey = "98a3ffceb49a423984b6b648ffff1798";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        for (int page = 1; page <= 50; page++) {
            String sUrl = "https://api.rawg.io/api/games?&key=" + apiKey + "&page=" + page;

            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                JsonParser jsonParser = new JsonParser();
                JsonElement root = jsonParser.parse(new InputStreamReader(connection.getInputStream()));
                JsonObject rootObj = root.getAsJsonObject();
                JsonArray results = rootObj.getAsJsonArray("results");

                for (JsonElement element : results) {
                    JsonObject obj = element.getAsJsonObject();
                    String name = obj.get("name").getAsString();
                    String imageUrl = obj.get("background_image").getAsString();
                    String releaseDate = obj.get("released").getAsString();
                    String ratingString = obj.get("rating").getAsString();
                    double rating = Double.parseDouble(ratingString);

                    if (rating >= 4.5) {
                        JsonArray platformsArray = obj.getAsJsonArray("parent_platforms");
                        List<String> platformsList = new ArrayList<>();
                        for (JsonElement platformElement : platformsArray) {
                            JsonObject platformObj = platformElement.getAsJsonObject();
                            JsonObject platform = platformObj.getAsJsonObject("platform");
                            String platformName = platform.get("name").getAsString();
                            platformsList.add(platformName);
                        }

                        arrGame.add(new Game(name, imageUrl, releaseDate, rating, platformsList));
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }

        return arrGame;
    }

}
