DESIGN.
Three classes are used to implement the design. One class was used
implement the player logic, another to initialize the board and finally
one to handle the game logic.
Board class is used to initialize the board with dots in random
positions. Each dot is randomly initialized in the intGameBoard method
and then the board is printed or uploaded using json using the
printBoard method.
Players class is used to player positions and movements. Here a
private class called Details was created to join each player with their
score and position. Player class uses the method initPlayer to initialize
the initial positions of players. The move method is used to update the
position of the players. The gotPoints method is used to update player
scores based on different colors and reduce scores when there are
collisions between players. The reset class is used to reinitialize a board
and players and finally the printPlayer class Updates the game with
player position by sending json updates.
GameServer class uses the doPost method to get the movements or
key pressed from the web page to the servlet and the doGet method is
used to upload the web page with the board and player positions. The
game won’t start until all four players have joined. Once the game is
finished the game will restart within two seconds.
IMPLEMENTATION.
Class players
Let’s first consider the player and its implementation in detail. A
private class Details was created to combine the player with the score
and the position. The player position is described by two points to
depict the x and y axis. In the method initPlayers Four objects of details
are created to depict each player. After giving each object initial
positions they are added to an array list.
The method move gets the value which is gotten by the doPost
method (different value for each arrow key) and then changes the
value of the position in the array list which was initialized above. This
also handles the movement of a player from each edge, if a player
move up from the top most corner then the player appears from the
bottom of the board and so on. Here a parameter canMove is checked
to ensure that players can’t move until all four players are connected.
The method gotPoints first get the board and checks if any dot
overlaps with a current player position, if it does according to the color
of the dot the player is awarded points. Also, this method checks if any
player positions overlaps and if they do it means a collision has
happened then each player involved with get their scores subtracted.
The reset method is used to restart the game by reinitializing
the players and the board.
Finally, the print player method is used to print all the
parameters of the player in json format.
Class board
This class is used to initialize the board. An array of size twelve is used
to initialize each dot with their color. And an array list of an integer
array is used to keep positions for each dot. Each position is
randomized and then added to the list. And finally, the print board class
is used to send the positions of each dot in json format.
Class gameServer
This is the class which handles the game logic. In the init class the
game board and the players are initialized by calling the respective
function mentioned above.
Let’s consider the doPost which takes the values of keypressed in web
page and inserts them into the player class. Each player gets a session
rather than using the isNew method in session, it is checked if the
attribute of the session gotten from get session is null if it is the n a new
session is created and the parameter the player number is set for each
session. To wait till all the four players here a variable playerNo is used
it is incremented each time a session is created and only when it
becomes four will the Boolean conditions canMove allow the players to
move. For each arrow key pressed the web page posts a different value
which is gotten as movement in this method. Next before passing this
into the player call a synchronized block is used since four different
players will be accessing the player and board, some may be updating
the while some may be reading so then we need to give it a lock so that
each thread can access it only one at a time. And finally, inside the
synchronized block notifyAll is called. This can be explained later in the
doGet method.
In the doGet method the same process used to check and create a
session is used. And the player number is found so that it can identify
which player out of the four is currently going to update. And again,
inside a synchronized block the player data and board data is posted to
the web page here again its synchronized because here the data is read
while in the doPost it was written so that there are no race conditions.
Here after sending data wait is called which means it gives away the
lock or this thread is blocked, as mentioned earlier when the data is
gotten and updated in the doPost a notify all is called which means
redundant data won’t be posted since only when new data is created it
notifies to post data. And finally as mentioned earlier the design if all
twelve dots have been occupied the reset function is called after a
delay of two seconds which reinitializes the board and players 
