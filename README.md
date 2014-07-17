Applaudo Assignment
=================================

Interview Homework Project (Android)

Introduction
=================================

I use the Android code template tool to use the Master Detail Flow Template. 
The tool automatically created an application compatible with tablets and it 
prepares the list and items details activities and fragments.
I included  a couple of external libraries to help me building the application .

I adapted the template with the file organization that I am used to work with. 

I created a package for every categories of classes so it makes the application 
easier to read and maintain:

* activities
* adapters
* controllers
* fragments
* model
* tools

Even if some of the packages have only one single element, the application is 
ready for future changes and evolutions.

I used a “Controller” to handle the lit of venues. This is actually a singleton
that will prepare and store the data during the application lifetime. The list
of books will by this way be accessible everywhere. This will save a lot of 
calls to the server and reduce the amount of code in the activities. 
Activities that will just have to display the data. 

This is the only place where I store the data in the assignment but I could have
 used a database and store the images on the disk if needed.

The model contains the object book and interfaces.

Design
=================================

I wanted to reuse the design of the company in adding the icon, the font and 
the white theme. 
I decided to add the images of the venues in the list as the list was looking
pretty empty. I saw that I could get smaller images from the server (100*100)
that would not take to much to load. 
Thanks to the Picasso library, once the image is loaded it can be reused in 
the details. 
I also added tiny icons in the list item to show what venues had schedule and
tickets. In the venues details I added the buttons to call, launch browser
to buy tickets and open maps to get directions.
The application supports rotation of the phone.

![Screenshot-1](http://oi58.tinypic.com/9lfoeo.jpg)

Compatibility
=================================
Android Smartphones and tablets version 2.3 + 

Third-party Libraries
=================================

The following dependencies are bundled with the assignment:

* [appcompat_7](https://developer.android.com/tools/support-library/features.html) 

The following libraries used in the project

* [Picasso](http://square.github.io/picasso/) Image loading
* (GSON)(https://code.google.com/p/google-gson/) JSON parsing
* (Volley)(https://developers.google.com/events/io/sessions/325304728) Network requests

