This is a JavaFx application that uses Spring-boot.

Two databases have been used for this application "MySQL" and MongoDB.
The application simulates the subscription of a user to any kind of store/service. The user, also has comments, and this are saved
in the mongo db as documents "Jsons".

It has a many to many relation between the store and user. To simulate a one to many relation between user and comments, every comment 
has a field "user_id". This ensures that the linkage between tables actually exists.

For the UI, JavaFx has been used,creating everything with scenebuilder.

A problem that I encounterd was that the context had to be sent whenever the "scene" was changed. Also the Controller classes in the
controller package had to have "@Component".

Usage: 
An existing user has to login, if there is no account, there is the register button.
After login the Left list view represents the Stores which the user did not subcribed yet to. The right one shows the stores where the
user is subscribed to.
--> Click each element in the left list view to subscribe and unsubcribe by clicking the elements in the other list.

Press the "see comments" button to navigate to the comment section. There is a listView that shows every comment that the user saved.
Save the comment by clicking "Add comment" or by pressing ENTER.

Every Scene has a built in ENTER or ESCAPE button, to go forward or backwors. So the possibility of going back will be by pressing the 
"back" button or by pressing ESCAPE.
