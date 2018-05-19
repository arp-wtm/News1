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

/**
 * custom class that is the model for the single item to show
 * with Title, Section, Author, Date and Url fields
 */

class Article {

    private String aTitle;
    private String aSection;
    private String aAuthor;
    private String aPublicationDate;
    private String aWebUrl;

    /**
     * Constructs a new {@link Article} object.
     *
     * @param title           is the title of the article
     * @param section         is the section of the article
     * @param author          is the writer of the article
     * @param publicationDate is the time in milliseconds when the article is published
     * @param webUrl          is the website URL to find more details about the article
     */

    Article(String title, String section, String author, String publicationDate, String webUrl) {
        aTitle = title;
        aSection = section;
        aAuthor = author;
        aPublicationDate = publicationDate;
        aWebUrl = webUrl;
    }

    public String getTitle() {
        return aTitle;
    }

    public String getSection() {
        return aSection;
    }

    public String getAuthor() {
        return aAuthor;
    }

    public String getWebPublicationDate() {
        return aPublicationDate;
    }

    public String getWebUrl() {
        return aWebUrl;
    }
}
