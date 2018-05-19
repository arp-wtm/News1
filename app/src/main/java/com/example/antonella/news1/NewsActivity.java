
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

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the main activity class that display first screen.
 * It implements LoaderManager.LoaderCallback interface.
 */
public class NewsActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Article>> {


    /**
     * Constant value for the article loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int ARTICLE_LOADER_ID = 1;

    /**
     * URL for new data from the content.guardianapis.com dataset
     * i use a different query with &show-tags=contributor for display also author
     */
    private static final String REQUEST_URL =
            "https://content.guardianapis.com/search?&show-tags=contributor&api-key=test";
    /**
     * to show a circle spinner running if connection is slow
     */
    public ProgressBar newsProgress;
    /**
     * Adapter for the list of article
     */
    private ArticleAdapter aAdapter;
    /**
     * to send a msg to the user when there are not data to display
     */
    private TextView newsEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        /*
         * Find a reference to the {@link Progress Bar} with loading_spinner resource
         * ID in the layout activity_news
         */
        newsProgress = findViewById(R.id.loading_spinner);

        // Find a reference to the {@link ListView} in the layout activity_news
        ListView newsListView = findViewById(R.id.list);

        // Find a reference to the {@link TextView with empty_view resource ID} in the layout
        newsEmptyStateTextView = findViewById(R.id.empty_view);

        // show a message to user if there are no data available
        newsListView.setEmptyView(newsEmptyStateTextView);


        // Create a new adapter that takes an empty list of articles as input
        aAdapter = new ArticleAdapter(this, new ArrayList<Article>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        newsListView.setAdapter(aAdapter);


        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected article.
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                // Find the current article that was clicked on
                Article currentArticle = aAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri articleUri = null;
                if (currentArticle != null) {
                    articleUri = Uri.parse(currentArticle.getWebUrl());
                }

                // Create a new intent to view the article URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, articleUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();

        // control internet connectivity

        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        if (isConnected) {
            loaderManager.initLoader(ARTICLE_LOADER_ID, null, this);
        } else {
            // Set empty state text to display "No earthquakes found."
            newsEmptyStateTextView.setText(R.string.no_internet_connection);
        }

    }

    @Override
    public Loader<List<Article>> onCreateLoader(int id, Bundle args) {
        // Create a new loader for the given URL
        return new NewsLoader(this, REQUEST_URL);

    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> newsData) {
        // Set empty state text to display "News data not found."
        newsEmptyStateTextView.setText(R.string.no_news);

        // Clear the adapter of previous news data
        aAdapter.clear();

        // If there is a valid  news list of {@link Article}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (newsData != null && !newsData.isEmpty()) {
            // hide spinner progress bar
            newsProgress.setVisibility(View.GONE);

            aAdapter.addAll(newsData);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {
        // Loader reset, so we can clear out our existing data.
        aAdapter.clear();

    }
}
