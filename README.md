# N-Body Problem 
### Assignment-1

Summary: A demonstrative use of both uses of the List data structure; LinkedList and ArrayList

Tasks: 
   - Read the data file containing details of several celestial bodies
   - Stimulate the motion and effects of gravity on the bodies
     
*NOTE: Must use two different implementations of the List data structure*
 
 1. Read the data file
    - The implementation will be supplied with a data file on the command line
    - The first line of the data file will indicate the type of data structure to be used *(ArrayList or LinkedList)*
    - The second line indicates the "scale" *(distance in meters represented by one pixel)*

 Note: Each new record appears on a new line & each field in a record is separated by commas
 
 EACH RECORD DEFINES A CELESTIAL BODY
 - Celestial Body Fields:
    - Name
    - Mass in kg
    - Initial x-coordinate on the JFrame
    - Initial y-coordinate on the JFrame
    - Initial x-direction velocity
    - Initial y-direction velocity
    - Size (in pixels) for rendering

   2. Create, instantiate & maintain a List of celestial bodies
        - We have to instantiate an ArrayList/Linkedlist depending on the value in the data file
        - Basically, write a list that contains the celestial bodies
        - BOTH the List interface & its two realisations are required to be of my own making
        (I have to write them from scratch & include them in the submission)

    3. Render each celestial body
        - We create a 2-dimensional output of the celestial bodies we created in the list
        - JFrame parameters = 768 * 768 pixels
        - ^^^ Implementation may make this size configurable

          - x-axis: horizontal axis; therefore, if one Object in a JFrame has a greater x value than another, the Object appears to the right of the other.
          - y-axis: Increasing this makes it lower on the JFrame

          - For each celestial body in the comma part of the data file, the implementation must create a circular shape (fillOval)
          - The Size (in pixels) field in the data file indicates the radius of the circle
          - The initial x-coordinate on the JFrame & initial y-coordinate indicate the starting position of the celestial body
          - These initial coordinates are not influenced by the Scale field

        - Implementation will not be informed about the number of celestial bodies at compile time, so be prepared to use (thousands) of colours for the collection of celestial bodies

    4.
        - Using Newton's Laws of Motion, update the position of the celestial bodies.
        - These laws:
            - Unless acted on by an external force, a body in motion will stay in motion
            (external force, like gravity)
            - ^^ this means, the simulation of one celestial body with an initial velocity must go in a straight line until it exceeds the boundaries of the JFrame
            - F = G(m1*m2)/r^2 --> the force which two objects exert on each other
                a) G : (gravitational constant) =
                b) m1 : (mass of first celestial body)
                c) m2 : (mass of second celestial body)
                d) r^2 : square of the distance between the centers of each celestial body in meters
                
