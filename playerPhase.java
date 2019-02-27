import java.util.Scanner;

public class playerPhase{

  private String playerColor;
  private int piecesLeft;
  
  public playerPhase(){
    
    this.playerNumber = "One"
  
  }
  
  public playerPhase(String color){
  
    this.playerColor = color;
    
  }
  
  public String getPlayer(){
  
    return (playerColor);
  
  }
  
  public int currentPieces(){
  
    return piecesleft;
  
  }
  
  public String choosePiece(){
  
    System.out.print("Select piece to move: ")
    Scanner choosePiece = new Scanner(System.in);
    String currentPosition = choosePiece;
    return currentPosition.toUpperCase();
  
  }
  
  //use isValidLocation(string)
  public String placeToMove(){
  
    System.out.print("Select space to move piece to: ")
    Scanner boardSpace = new Scanner(System.in);
    String newPosition = boardSpace;
    return newPosition.toUpperCase();
  
  }
  




}
