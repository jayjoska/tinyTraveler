# Git

* [git handbook](https://guides.github.com/introduction/git-handbook/) for reference
* tutorial on [git branching](https://learngitbranching.js.org/) - **follow!**
* [visualizing git tutorial](http://git-school.github.io/visualizing-git/) - **follow!** 

# Setting up Phabricator / Arcanist 

* [Arcanist Reference](https://our.intern.facebook.com/intern/wiki/Arcanist/#installing-an-arcanist-c)

1. Install an Arcanist Certificate: open terminal and enter the line ```arc set-config default https://phabricator.intern.facebook.com/```

2. Enter ```arc install-certificate``` and click on the link under "Login to Phabricator" as shown below.

![image](https://i.imgur.com/HX463YP.png)

Paste the token from the webpage into the prompt on terminal.   

# Submitting a Diff to Phabricator / Arcanist 

3.  ```git pull``` to make sure you have the latest version on git. 

4. Make sure you are on a different branch (not master) when trying to submit something to Arcanist. Check using ```git branch```. If you are on master, checkout a new branch using ```git checkout -b branchName```. 

3. Make sure all the changes you have made have been committed on that separate branch. Multiple commits are okay.

4. Submit this change for review using ```arc diff```. 

5. View the diff made by holding command and hovering over the hyperlink on terminal. 

5. Make sure to add a summary, a test plan, link the task that you are completing with this diff, and add all teammates (Karena, Jaynicka, Jose, Michelle) excluding yourself for review.

6. Once your diff has been approved, land it using ```arc diff```. 

* [Arcanist tutorial](https://secure.phabricator.com/book/phabricator/article/arcanist/)

* [Example commit](https://our.intern.facebook.com/intern/diff/D8863972/)

* [Task list](https://our.intern.facebook.com/intern/tasks/?q=237727237042012)

* use ```arc help``` for a list of arc commands. Similarly, use ```git help``` for a list of git commands. 


#References
* [Syncing Google Places with Yelp] https://7webpages.com/blog/yelp-and-google-places-api-combined/

