[WorkoutLog - V 1.2]
--------------------

V 1.2 Updates:
- Splash Fragment Added
- Image added to home page

Introduction
------------

This app aims to log exercises based categorized by there split day/muscle group.
- Utilizes SQL queries to store exercises and their information in the Room database.
- Navaigation component for moving between fragments and setting arguments based on the desired split day.
- ViewModel implementation with LiveData componentsto make changes to data that will be passed to the fragments.

Thoughts
--------

I a hit a snag while implementing the first of the fragments that would display the database data.
As it turns out, the annotation and kapt extensions are out dated and it is recommended by the 
Android team to implement KSP instead. Also, I needed to add the KTX room extension to get the 
app to execute properly. And apparently SQL has changed since the code-lab for the inventory app was 
made. 'BY' was not an acceptable keyword so I erased it all together. Who needs ascending order. 
lol.

An idea I had was to add an attribute that identifies the exercise by what muscle group that it 
belongs to so I may have to rework the layouts and the SQL queries to support that. The attribute 
can be set utilizing the navArgs properties from the nav graph.

TO-DO List
----------

- Fix splash fragment layout
- fix the navigation up from main menu to splash fragment
- Add transitions
- Add updatable date tags
- Add notes function
- Implement unit and instrument tests
