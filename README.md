Applaudo Assignment
=================================

Interview Homework Project (Android)

Indrodution
=================================

I use the Android code template tool to use the Master Detail Flow Template. 
The tool automatically created an application compatible with tablets and it 
prepares the list and items details activites and fragments.
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

I wanted to reuse a little bit the design of the company in adding the icon, 
the font and the white theme. 
I decided to add the images of the venues in the list as the list was looking
pretty empty. I saw I could get smaller images from the server (100*100) that
would reduce the data to load. 
Thanks to the Picasso library, once the image is loaded it can be reused in 
the details. 
The application supports rotation of the phone.

![Screenshot-1](http://oi57.tinypic.com/108af49.jpg)

Compatibility
=================================
Smartphones and tablets version 2.3 + 

Third-party Libraries
=================================

The following dependencies are bundled with the assignment:

* [appcompat_7](https://developer.android.com/tools/support-library/features.html) 

The following libraries used in the project

* [Picasso] (http://square.github.io/picasso/)

* (GSON) (https://code.google.com/p/google-gson/)
* (Volley) (https://developers.google.com/events/io/sessions/325304728)
* (Android Infinite Scroll List view) 
