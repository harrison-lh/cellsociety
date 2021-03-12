# Cell Society Project Analysis
### Name: Harrison Huang

## Overall Design

### High-level Design
The configuration `SimulationEngine` serves as the link between the simulation and
visualization, or model and view. The controller can access data such as the grid
and pass this over to the view. Ideally, the controller would have sole control over
the view and model, but in its current implementation, the view has access to the 
controller in order to allow for functional buttons that alter the state of the 
simulation. Otherwise, they are largely independent of one another.

Follow these steps to add a new kind of simulation: Create a new Rules class 
for the simulation. Pass the necessary parameters to the constructor. 
Add the possible types of the model to `possibleTypes` List. Add the possible colors 
of the model to `possibleColors` List. Write your logic for updating each cell in 
`decideState`. Make sure you understand the parameters that are passed into the 
`decideState` method. Make sure you understand how State class works.

Dependencies are mostly straightforward, though there is the aforementioned
dependency of the view on `SimulationEngine` as the view contains buttons that
must act to alter the simulation itself and thus the controller.

### Two Examples for Core Concepts
1. The `SimulationScreen` class is 292 lines including comments, which hints that
   it is trying to do too many things for a single class, failing the Single
   Responsibility Principle. My general sentiment is that its implementation is 
   a bit too rigid, not allowing for too much modular customization. There really 
   should be a class devoted to the layout of the screen, like a controller of the 
   view, which would allow for easier extension such as having multiple grids in 
   the same window.
   
2. I had lots of trouble deciding exactly which type of Pane I wanted to use for
   the various panels of the screen, like the controller panel on the side
   (`SidePanel`) or the grid as a whole (which changed from being a `GridPane` to
   an `AnchorPane` by the end of complete). Since I added each of these panes to
   a `BorderPane`, I only needed to add `Node` objects, so it was quite easy to
   use the Liskov Substitution Principle to allow for flexibility in what sort
   of Pane I wanted to add without having to change much of the implementation.
   
### One Benefit of Code's Design
Because I had utilized a resource property for displaying text in the basic version,
it was much easier to add support for additional languages, since it only required
(1) new property files for each additional language and (2) a method of choosing
which language was to be displayed, which ended up being done through a combo box.
Since I had already implemented a resources file to grab text, I didn't need to
replace any code there to grab different text.

### Two Features by a Teammate
1. The abstract Rules class is good in that it allows rules for different 
   simulations to be written in the same template design. All the simulations
   are written as implementations of Rules, so they follow the same format. It
   does depend on both State and GridManager to work properly, so it is not the
   most closed. The design seems to hold up very well however, as all of the 
   simulations that were written follow the abstract Rules class. In the case that
   a new feature were needed in all of the simulations' Rules classes, it could be
   remedied by adding a new method or variable in the abstract class.
   
2. The Decoder class, in general, contains a large amount of code that would ideally
   be generalized in future implementations. The Decoder aims to interpret the
   XML files into actual usable data by the model, so it is quite important in the
   grand scope of the project. It does keep many variables private, but the bigger
   picture problem is that many variables are declared within the class, each of
   which is specific to one simulation. In this sense, it is not very efficient,
   and though it would not be difficult to add one more simulation, it would
   gradually get more difficult over time as more and more simulation types
   crowd the code. It would be quite difficult, for example, to implement a
   101-type rock-paper-scissors, as it would require one new variable for each
   type.
   
### Conclusion
While this project supports adding new simulation rules and view elements rather
well, there is a bit of a bottleneck in being able to parse each simulation type in
general. I like that we have the ability to import from an XML file, which allows
us to create new simulations with different parameters quite easily. We are also
able to change all text displayed on screen (aside from that directly imported from
the XML files) and also all the styling on screen.

## Your Design

### Overall Design
I have a base "main" view class, which is `SimulationScreen`. There are other
components that interact with this class, like `TopPanel`, `SidePanel`, and
`GridGraphics`, which all compose the normal view screen. There is also
`StartScreen` which displays the splash screen at the beginning, as the precursor
to the normal simulation screen. Last, I have `TriangleCell` and `HexagonCell`,
which the grid uses in order to generate different tiling. I would say there are
two big issues. One is the double dependency between `SimulationScreen` and the
controller,`SimulationEngine`, which in its current implementation, is necessary 
for button functionality. The other issue is that `SimulationScreen` violates
the single responsibility principle, as it both manages the screen elements but
also acts to manage the view as a whole. For the design checklist, aside from
the aforementioned single purpose violation, my code follows the principles well.
Communication is good (I consistently use meaningful variable names), as well as
modularity (as I delegate responsibility of updating the grid to the grid itself 
rather than to the screen). My code is not repetitive, and my other classes have
a single purpose (like how `TriangleCell` and `HexagonCell` simply are used in
order to create the triangle and hexagon `Polygon` objects, which makes the math
much easier from the grid's perspective).

### One Abstraction
Though it still required an if tree, I liked my implementation of `TriangleCell` 
and `HexagonCell` in order to generate grids with different tiles in a way that
makes sense. The abstraction delegated responsibility of calculating where the
points of the polygon would lie to these classes instead of the grid class itself,
so that was a major improvement. I recognize that there is still room for
improvement here, including the ability for implementing more tile shapes in an
actual abstract manner, but I found an effective way to incorporate different 
tilings here, at least to start.

### Two Features
1. I really like my implementation of being able to change the view's appearance
   according to various resource property files according to language or CSS files
   for coloring and styles. The vast majority of this was done by
   `SimulationScreen`, as it can apply the results of each file to its
   components, allowing for dynamic changes as well. A few of the commits that
   contributed to this were "added color palette switching", "generalized css 
   selection text into resources", and "finished language resources", so my
   commits were fine. I could have further broken them down into more steps, but
   this would not be strictly necessary. This feature follows open-closed quite
   well, as new configurations can be used by changing each of these files,
   but any particular values are stored privately within the class.
   
2. My implementation of having two independent views (both grid and graph) wasn't
   as good as I had wanted it to be. I ended up doing this solely in
   `SimulationScreen`, as it served as my controller to all of the view
   components. While the actual implementation was fine, the UI design and effect
   was not very useful. Since I had already programmed a graph view in a
   separate window, I initially wanted to also separate the grid from the controls
   which determine view. These controls would then have exclusive control over
   the grid and graph. At this point, however, it was too difficult for me
   to reconfigure and overcome the rigidity of `SimulationScreen`, so I came up
   with an alternative to hide the grid in the main view instead, which in effect
   allows each of grid and graph to be displayed independently of one another.

### Conclusion
Because I was dealing with a very concrete role in view, I didn't do much in the
way of abstraction. The biggest instance of this, however, was the same as the one
where I implement the different types of tiles as new classes, in `TriangleCell` 
and `HexagonCell`. I did do good encapsulation, however, in splitting 
responsibilities of each part of view to different classes, which sorted out
panels each into their own thing. By having grid and graph classes, they could
manage data themselves without needing the screen class to calculate for it.

## Alternate Designs

### Changing Grid's Data Structure
GridManager, since it must deal with the original grid and also determine neighbors.

### Original Design
I believe that the original design held up even after the extensions in complete.
Though some things were made slightly more difficult by virtue of having more
features to implement, the original design held through by the end. We did have an
idea to generalize the decoder by having variables obtain predetermined generalized
slots, with the variable names be done through a resource property file, but this
idea fell through as it was too difficult to do by that time. Otherwise, coding
was not made more difficult because of our design. For example, I had to change a
few things around in order to allow the grid to be tiled by different polygons,
like the exact implementation of each of the shapes, the row and column indexing,
and the exact type of pane I was using for the grid itself. In this example, though,
it was honestly not too difficult to implement as the code was easily
manipulable to allow different tiles.

### One Assumption/Choice
With the aforementioned example of the decoder having all the model-specific
variable names, it really made the decoder class messy, since many lines were filled
with declarations of local variables, many of which are left unused in any
particular run. This, however, did leave the rules classes to be more interpretable
specific to each model type, since the variable names could be used natively.
This includes passing specific parameters all the way to view, such as shape 
(square, triangle, or hexagon).

### Two Design Decisions
1. Grid implementation:
    - The final version used a 2-D array of State objects, which allowed more
      information to be stored than if a 2-D array of ints was used (which was
      in the original plan).
      
    - We considered using a List of Lists, but this was worse because the varying
      list size would be difficult to work with. The 2-D array allows for much
      easier indexing.
      
    - The 2-D array of State objects ended up being the way to go because we
      could store adequate information, and we never dealt with grids that could
      not be indexed by row and column.
      
2. Passing data for the graph
    - Two choices were considered here: either having the view calculate the counts
      for each of the possible cell states or having the model pass such a map
      already calculated for the graph.
      
    - We chose to go with the latter because it removes the role of view to do
      additional work outside its normal jurisdiction. While it did add an extra
      parameter to the `GridManager`, it ended up being an easy addition, while
      also removing what would have been a lot more logic in the graph or view
      classes.

### Conclusion
I think the biggest thing that affects encapsulation and overall design must be
dependencies on specific implementations. As an example, the view should not
depend on how exactly the model calculates states. The view simply updates based
on what the model explicitly gives it, in our case, a 2-D array of colors. If one
previous choice is not altered early on in the process, then it can make it more
difficult to avoid by the end, like our decoder. It simply made it much easier
to keep with the same design rather than trying something ambitious to improve
future flexibility.

## Conclusions

### Learned about design
There is never truly a right or wrong answer to design, only tradeoffs in either 
direction. There will always be ways to "improve" code, and whether it's worth
pursuing such improvements also has a cost-benefit analysis attached to it.

### Improve process
I did do this a lot more during this project, but I would like to refactor even
more as I go along. It made everything cleaner as I kept going, and I felt like
my coding was less cluttered as I cleaned things up.

### Perspective changes
Your team has to be on the same page for teamwork to be effective. Being in the
same mindset and cooperating as equals helps everybody succeed together. Everybody
has to pull their weight, as there's too much work to go around for others to pick
up where some can't. At the same time, you can still make the lives of others
easier by having good implementations and doing some types of work on behalf of
other components.

### To be a better designer
Start doing differently: Always think about flexibility and modulation (in order
to implement independent views better without much more work)

Keep doing the same: Adding inheritance and refactoring

Stop doing: being lazy in implementations? (implementing suboptimal solutions, 
like nearly useless toggle button)