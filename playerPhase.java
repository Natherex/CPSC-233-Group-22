import java.util.Scanner;

public class playerPhase{

  private String playerNumber;
  private int piecesLeft;
  
  public playerPhase(){
    
    this.playerNumber = "One"
  
  }
  
  public playerPhase(String number){
  
    this.playerNumber = number;
    
  }
  
  public String getPlayer(){
  
    return ("Player " + playerNumber);
  
  }
  
  public int currentPieces(){
  
    return piecesleft;
  
  }
  
  public String choosePiece(){
  
    System.out.print("Select piece to move: ")
    Scanner choosePiece = new Scanner(System.in);
    String currentPosition = choosePiece;
    return currentPosition.toLowerCase();
  
  }
  //use isValidLocation(string)
  public String movePiece(){
  
    System.out.print("Select space to move piece to: ")
    Scanner boardSpace = new Scanner(System.in);
    String newPosition = boardSpace;
    return newPosition.toLowerCase();
  
  }
  




}