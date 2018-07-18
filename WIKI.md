# Git

* [git handbook](https://guides.github.com/introduction/git-handbook/) for reference
* tutorial on [git branching](https://learngitbranching.js.org/) - **follow!**
* [visualizing git tutorial](http://git-school.github.io/visualizing-git/) - **follow!** 

# Coding Conventions

* Add a description at the top of each Java class and each significant method. For now just adding an @brief is fine, but later when we have many more components try to be more detailed for our models (e.g. Place.java) so we know which method to use. 
```
/* 
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
# Setting up Phabricator / Arcanist 

* [Arcanist Reference](https://our.intern.facebook.com/intern/wiki/Arcanist/#installing-an-arcanist-c)

1. Install an Arcanist Certificate: open terminal and enter the line ```arc set-config default https://phabricator.intern.facebook.com/```

2. Enter ```arc install-certificate``` and click on the link under "Login to Phabricator" as shown below.

![image](https://i.imgur.com/HX463YP.png)

Paste the token from the webpage into the prompt on terminal.   

# Submitting a Diff to Phabricator / Arcanist 

3.  ```git pull``` to make sure you have the latest version on git. 

4. Make sure you are on a different branch (not master) when trying to submit something to Arcanist. Check using ```git branch```. If you are on master, checkout a new branch using ```git checkout -b karena/branchName```. Always preface the name of your branch with your name so we know whose branch is whose.

3. Make sure all the changes you have made have been committed on that separate branch. Multiple commits are okay.

4. Submit this change for review using ```arc diff```. If you have multiple commits (e.g. 5), use ```arc diff HEAD^^^^^```

5. View the diff made by holding command and hovering over the hyperlink on terminal. 

5. Make sure to add a summary, a test plan, link the task that you are completing with this diff, and add all teammates (Karena, Jaynicka, Jose, Michelle) excluding yourself for review.

6. To update your diff, make sure you are on a branch called ```arc-patch[D1243]```. Once you have commited the changes you want to make, do ```arc patch --update D1243```.

7. Once your diff has been approved, land it using ```arc diff```. 

# Arcanist help 

* [Arcanist tutorial](https://secure.phabricator.com/book/phabricator/article/arcanist/)
* use ```arc help``` for a list of arc commands. Similarly, use ```git help``` for a list of git commands. 

* [Example diff](https://our.intern.facebook.com/intern/diff/D8863972/)

# Task list 
* [Task list](https://our.intern.facebook.com/intern/tasks/?q=237727237042012)

# References
* [Syncing Google Places with Yelp](https://7webpages.com/blog/yelp-and-google-places-api-combined/)
* [interesting read](https://books.google.com/books?id=rFxCDwAAQBAJ&pg=PA298&lpg=PA298&dq=yelp+categories+aliases+api+call+example&source=bl&ots=Mqi2cgvGfq&sig=Ckyr8otWOuijnwTNoCVOpnAFMfQ&hl=en&sa=X&ved=0ahUKEwiX6tjg86TcAhUtnOAKHUjPAKcQ6AEIaTAF#v=onepage&q&f=true) about places of interest from yelp, google, facebook, and foursquare (p. 297-300)
