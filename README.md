# CPSC-233-Chess-Game

Ever wanted to play a game of Chess on the go? Not enough space to have a proper Chess setup? This project is the solution for you! 
A Simple Chess game made using Java.

## How to Download and Run Files

1. To download the game, click the dropdown menu on the top left and select "**master** as the branch.
2. Then, click the green button at the top right of the screen labeled "**Clone or Download**".
3. Afterwards, select the option labeled "**Download ZIP**"
4. Open the newly download ZIP file and extract the folder to anywhere on the PC
5. Run the correct file depending on your OS (Operating System) using the steps below:

## **For Windows:**
  ### If playing the text-based version:
      __Run "runText.bat"__
  ### If playing the GUI-based version:
     __Run "runGUI.bat"__
    
## **For Linux:**

   ### If playing the text-based version:
    1. Right click the file named "__runText.sh__" and Select "__Properties__"
    2. Select the "**Permissions**" tab
    3. Check the box labeled "**Allow executing file as a program**" and close the window
    4. Double click __Run "runGUI.sh"__ and select either **Run in Terminal** or **Run**
    
   ### If playing the GUI-based version:
    1. Right click the file named "__runGUI.sh__" and Select "__Properties__"
    2. Select the "**Permissions**" tab
    3. Check the box labeled "**Allow executing file as a program**" and close the window
    4. Double click __Run "runGUI.sh"__ and select either **Run in Terminal** or **Run**

### How to Play (GUI-Based Version)

1. To play, the user is promted with a standard Chessboard. Player One is white side and begins first. The current player's turn is denoted by the text at the top of the window.
2. To move a piece, select a piece of the appropriate color (white pieces if white side, black pieces if black)
3. After selecting a piece, click another space that the corrosponding piece can move to (either empty or with a black piece).    If move is illegal, the piece will not move and you will have to reselect a piece again.
4. After the move completes, it is now Player Two's turn (black side) and repeat steps 2-3 for their turn.

### How to Play (Text-Based Version)

1. To play, the user is prompted with a command console with a picture of the board state. Player One plays first.
2. To select a piece to move, type in the coordinate of the piece that corresponds to your color. (ie. Player One only selects white pieces, Player Two only selects black pieces)
3. After selecting a piece, type in the coordinate of the space you want to move your piece to.
4. If the piece selected matches the player and the move is valid, the play proceeds and the turn will swap to the opponent.
5. Repeat steps 2-4 for Player Two.
