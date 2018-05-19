/*  made by Antonella on may.19.2018
 * for news stage 1 app exercise for project 6
 * in Udacity ABND course. It uses as model the
 * Quake Report app of the lesson on JSON Parsing that is under this licence:
 *
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.antonella.news1;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving article data from www.theguardian.com .
 */
final class QueryUtils {

    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Query the www.theguardian.com dataset and return a list of {@link Article} objects.
     */
    public static List<Article> fetchNewsData(String requestUrl) {

        // comment and uncomment this code  to text the progress bar
        // try {
        //    Thread.sleep(2000);
        // } catch (InterruptedException e) {
        //    e.printStackTrace();
        // }


        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.i(LOG_TAG, "Problem making the HTTP request.", e);
        }

         // Return the list of news {@link Article}s extracted from the JSON response
        return extractNewsFromJson(jsonResponse);

    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link Article} objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<Article> extractNewsFromJson(String articleJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(articleJSON)) {
            return null;
        }

        // Create an empty ArrayList news that we can start adding articles to
        List<Article> news = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string

            JSONObject baseJsonResponse = new JSONObject(articleJSON);
            JSONObject response = baseJsonResponse.getJSONObject("response");


            // Extract the JSONArray, in response object, associated with the key called "results",
            // which represents a list of news results.
            JSONArray newsArray = response.getJSONArray("results");

            // For each article in the newsArray, create an {@link Article} object
            for (int i = 0; i < newsArray.length(); i++) {

                // Get a single article at position i within the result array jsonobject
                JSONObject currentArticle = newsArray.getJSONObject(i);

                // extract the the value for the key called "webTitle"

                String title = currentArticle.getString("webTitle");

                // Extract the value for the key called "sectionName"
                String section = currentArticle.getString("sectionName");

                // Extract the value for the author from the field tags and key called "webTitle"
                JSONArray tags = currentArticle.getJSONArray("tags");
                // set currentTag as the first JSONObject contributor
                JSONObject currentTag = tags.getJSONObject(0);
                // Extract the value for the author from the current tag with key called "webTitle"
                String author = currentTag.optString("webTitle");

                // Extract the value for the key called "time"
                String date = currentArticle.optString("webPublicationDate");

                // Extract the value for the key called "url"
                String url = currentArticle.getString("webUrl");
                // Create a new {@link Article} object with title,section,author,date
                // and url from the JSON response.
                Article article = new Article(title, section, author, date, url);

                // Add the new {@link article} to the list of news.
                news.add(article);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of articles
        return news;
    }

}
