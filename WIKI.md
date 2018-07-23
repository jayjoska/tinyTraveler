# 1. Objectives

* a complete, 2-minute demonstration of the most critical and useful features in our application 
* Functionality is not as potential in terms in features 

# 2. Coding Conventions

* Add a description at the top of each Java class and each significant method. For now just adding an @brief is fine, but later when we have many more components try to be more detailed for our models (e.g. Place.java) so we know which method to use. 
```
/**
 *@brief Factorial takes in a whole number and computes its factorial. 
 * 
 * @input  int   n   takes in a whole number n
 * @output int   n!  returns the value n!
 */
``` 
* for xml files, add a brief description on the second line under the xml version using ```<!-- [comment content] -->```. Ex:
```
<!-- @brief item_category displays a single category item in the Recyclerview grid. 
@utilizes CategoryAdapter
-->
```
* for for xml ids, use ```rv + name```

# 3. Git

* [git handbook](https://guides.github.com/introduction/git-handbook/) for reference
* tutorial on [git branching](https://learngitbranching.js.org/) - **follow!**
* [visualizing git tutorial](http://git-school.github.io/visualizing-git/) - **follow!** 

# 4. Facebook Android source code search 

* if you want to search for a certain string in the Facebook android search code, search with the format ```abgs stringToSearch``` in bunnylol (android big grep string). For instance, if you want to see how often the .replace method for fragments is used, search [```abds .replace(```](https://our.intern.facebook.com/intern/codesearch/?q=.replace%28+repo%3Afbandroid+regex%3Aoff&source=redirect). 

# 4. Submitting a Diff to Phabricator / Arcanist 

1.  ```git pull``` to make sure you have the latest version on git. 

2. Make sure you are on a different branch (not master) when trying to submit something to Arcanist. Check using ```git branch```. If you are on master, checkout a new branch using ```git checkout -b karena/branchName```. Always preface the name of your branch with your name so we know whose branch is whose.

3. Make sure all the changes you have made have been committed on that separate branch. Multiple commits are okay.

4. Submit this change for review using ```arc diff```. If you have multiple commits (e.g. 5), use ```arc diff HEAD^^^^^```. If you are unsure of how many commits you want to include in your diff, use ```git log``` and enter ```q``` for quit when you are done. 

5. If you are blocked and want to show other people your code, use ```arc diff --only```. This will create an unofficial diff.(make sure you have commited all the changes you want other people to see as described in later 2 steps). More commits can be specified using ```arc diff HEAD^^^ --only```. 

6. View the diff made by holding command and hovering over the hyperlink on terminal. 

7. Make sure to add a summary, a test plan, link the task that you are completing with this diff, and add all teammates (Karena, Jaynicka, Jose, Michelle) excluding yourself for review.

8. To update your diff, make sure you are on a branch called ```arc-patch[D1243]```. Once you have commited the changes you want to make, do ```arc patch --update D1243```.

9. Once your diff has been approved, land it using ```arc diff```. 

# 5. Arcanist troubleshooting 

* [Arcanist tutorial](https://secure.phabricator.com/book/phabricator/article/arcanist/) for a list of commonly used arc commands 
* use ```arc help``` for a list of arc commands. Similarly, use ```git help``` for a list of git commands. 

* [Example diff](https://our.intern.facebook.com/intern/diff/D8863972/)

# 6. Task list 
* [Task list](https://our.intern.facebook.com/intern/tasks/?q=237727237042012)

# 7. References
* [Syncing Google Places with Yelp](https://7webpages.com/blog/yelp-and-google-places-api-combined/)
* [interesting read](https://books.google.com/books?id=rFxCDwAAQBAJ&pg=PA298&lpg=PA298&dq=yelp+categories+aliases+api+call+example&source=bl&ots=Mqi2cgvGfq&sig=Ckyr8otWOuijnwTNoCVOpnAFMfQ&hl=en&sa=X&ved=0ahUKEwiX6tjg86TcAhUtnOAKHUjPAKcQ6AEIaTAF#v=onepage&q&f=true) about places of interest from yelp, google, facebook, and foursquare (p. 297-300)
* [Documentation on the Distance Matrix API](https://developers.google.com/maps/documentation/distance-matrix/intro) which is used for calculating distances and travel times between points.
* [Communicating between fragments](https://developer.android.com/training/basics/fragments/communicating) 

# 8. Setting up Phabricator / Arcanist 

* [Arcanist Reference](https://our.intern.facebook.com/intern/wiki/Arcanist/#installing-an-arcanist-c)

1. Install an Arcanist Certificate: open terminal and enter the line ```arc set-config default https://phabricator.intern.facebook.com/```

2. Enter ```arc install-certificate``` and click on the link under "Login to Phabricator" as shown below.

![image](https://i.imgur.com/HX463YP.png)

Paste the token from the webpage into the prompt on terminal.   
