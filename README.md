# Alexml
Alexml is a tool for creating scene2d actors to be used in libgdx games.
It works by you writing an xml file and splitting your sprite down into it's component parts like the head, the body, and so on.
You then write animations for each of those subsprites, with the draw coordinates built in.
Importing them in as actors mean that you can have one static asset of a head for the entire character's animations.
You can also do anything you can do to a scene2d sprite, so rotations, scaling, stretching can all be done.

---

## Using Alexml
The wiki contains specific information on the nuts and bolts of how the xml file is structured.
To get started, add this line to your libgdx build.gradle. 

Then, once your xml is written it is a simple as:
AlexSprite mySprite = new alexSprite("mySprite.xml");