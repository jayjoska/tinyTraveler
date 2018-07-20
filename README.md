# jayjoska

## App Description
Jayjoska allows users to create a customized travel itinerary based off a preselected list of interests. Upon filling out their interests, a list of recommended places to visit is provided. Users can then add their most interesting stops to a day calendar and customize time spent, location, travel time. Jayjoska is aimed towards travelers in new cities or people who want to visit new places in their hometown.


##  Required User Stories
* [X] User can see a grid view of categories
* [X] User can select 5 categories
* [X] User can press a "Done" button to go to the recommendations
* [x] The list of places includes a Name, Rating, Distance, and Category
* [ ] User can tap on one of the suggestions (from the recommendations view) to see a detail view.
------------
* [ ] The Recommendations activity shows a map and a list of all the places
* [ ] Using the bottom navigation bar, the user can access his/her itinerary
* [ ] User has a way to include (or not) each element of the list in his/her final itinerary
* [ ] The itinerary includes a list of the places, which can be sorted to make the final itinerary
* [ ] Each element of the list includes a Picture, Name, and Time to spend in that place (which the user can specify)
------------
* [ ] The itinerary includes a map with a drawn out route
* [ ] The map has pins on all the locations in the suggestions
* [ ] There is a "Recalculate" button to recalculate the route after the user reorganizes the suggestions

## Optional User Stories
* [ ] There should be a button to export/share
* The detail view includes Image, Name, Rating, Price, Hours, Learn More (a link te Yelp), and Reviews.
------------
* Recommendations change based on size of geofence
* Add subinterests to main categories
------------
* Look at FB profile for interests
* Sharing locations/post features 
* Recommend cities/areas that contain multiple matches to user's interests
* Login screen (maybe)
* One-day itinerary by minimizing distance

## Models 
* place model - name, rating, location, category imageUrl
* destinations model extends place model - includes time 

## Week 1 goals 
* onboarding 5 task selection 
* framework / rough UI - bottom navigation view, 3 screens available, map view + recycler itinerary view - must be reorderable 
* populate place class 
