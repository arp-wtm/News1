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

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * {@link ArticleAdapter} needs to create a list item layout for each article
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */

public class ArticleAdapter extends ArrayAdapter<Article> {
    /**
     * Constructs a new {@link ArticleAdapter}.
     *
     * @param context  of the app
     * @param newsData is the list of articles, which is the data source of the adapter
     */
    ArticleAdapter(Context context, List<Article> newsData) {
        super(context, 0, newsData);
    }

    /**
     * Returns a list item view that displays information about the earthquake at the given position
     * in the list of earthquakes.
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.article_item, parent, false);
        }

        // Find the  current Article at the given position in the list of news data
        Article currentArticle = getItem(position);

        // Find the TextView with view ID title_article
        TextView titleArticleView = listItemView.findViewById(R.id.title_article);
        // Display the title of the current article in that TextView
        if (currentArticle != null) {
            titleArticleView.setText(currentArticle.getTitle());


            // Find the TextView with view ID section_name
            TextView sectioNameView = listItemView.findViewById(R.id.section_name);
            // Display the location of the current article in that TextView

            sectioNameView.setText(currentArticle.getSection());


            // Find the TextView with view ID author
            TextView authorView = listItemView.findViewById(R.id.author);
            // Display the author of the current article in that TextView

            authorView.setText(currentArticle.getAuthor());


            // Find the TextView with view ID date
            TextView dateView = listItemView.findViewById(R.id.date);
            // Display the date of the current earthquake in that TextView

            dateView.setText(currentArticle.getWebPublicationDate());

        }
        // Return the list item view that is now showing the appropriate data
        return listItemView;

    }

}
