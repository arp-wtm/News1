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

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Loads a list of articles by using an AsyncTask to perform the
 * network request to the given URL.
 */
class NewsLoader extends AsyncTaskLoader<List<Article>> {

    /**
     * Query URL to find news articles
     */
    private String newsUrl;

    /**
     * Constructs a new {@link NewsLoader}.
     *
     * @param context of the activity
     * @param url     to load data from guardian
     */
    NewsLoader(Context context, String url) {
        super(context);
        newsUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<Article> loadInBackground() {

        if (newsUrl == null) return null;

        // Perform the network request, parse the response, and extract the news list.
        return QueryUtils.fetchNewsData(newsUrl);
    }
}