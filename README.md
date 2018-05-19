# News1
News Stage 1 UDACITY ABND<br>
This app is created to display a list of news stories, given from the guardian site.<br>
Each list item on the main screen displays relevant text and information about the story:<br>
Title of news article <br>
Name of the section <br>
Name of the author<br>
Date of publication <br>
Alla this informations are modeled with a custom class Article <br>
Clicking on a story uses an intent to open the story in the user’s browser.<br>
When there is no data to display, the app shows a default TextView that informs the user with "no data news found".<br>
The app checks whether the device is connected to the internet and responds appropriately. <br>
The result of the request is validated to account for a bad server response or lack of server response.<br>
If no connection or bad connection the message "no internet connection, check your device settings" is displayed to the user<br>
Networking operations are done using a NewsLoader, that perform the network request,<br>
parse the response, and extract the news articles in background thread.<br>
# TEST
The code runs without errors on HAWEI JMM-L22 Android 7.0 Api 24.<br>
The Android Project is built  for Phone and Tablet with LEVEL API 15: Android 4.0.3 (IceCreamSandwich)<br>
in QueryUtils class there is some commented code that needs to test spinner progress bar.<br> 
The message of no internet connection appares if you test the app in airplain mode.<br>
build.gradle dependencies {<br>
    implementation fileTree(dir: 'libs', include: ['*.jar'])<br>
    implementation 'com.android.support:appcompat-v7:27.1.1'<br>
    implementation 'com.android.support.constraint:constraint-layout:1.1.0'<br>
    testImplementation 'junit:junit:4.12'<br>
    androidTestImplementation 'com.android.support.test:runner:1.0.2'<br>
    androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.2'<br>
}<br>
# LICENCE <br>
/*  made by Antonella on may.19.2018<br>
 * for news stage 1 app exercise for project 6<br>
 * in Udacity ABND course. It uses as model the<br>
 * Quake Report app of the lesson on JSON Parsing that is under this licence:<br>
 *<br>
 * Copyright (C) 2018 The Android Open Source Project<br>
 *<br>
 * Licensed under the Apache License, Version 2.0 (the "License");<br>
 * you may not use this file except in compliance with the License.<br>
 * You may obtain a copy of the License at<br>
 *<br>
 *      http://www.apache.org/licenses/LICENSE-2.0<br>
 *<br>
 * Unless required by applicable law or agreed to in writing, software<br>
 * distributed under the License is distributed on an "AS IS" BASIS,<br>
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.<br>
 * See the License for the specific language governing permissions and<br>
 * limitations under the License.<br>
 */<br>

All news displayed come from https://open-platform.theguardian.com
© 2016 Guardian News and Media Limited or its affiliated companies. All rights reserved.
