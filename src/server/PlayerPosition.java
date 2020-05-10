package server;

import java.io.Serializable;

public class PlayerPosition implements Serializable{
   /**
    *
    */
   private static final long serialVersionUID = 1L;
   public int posX = 0;
   public int posY = 0;
   public int direction = 0;

   public PlayerPosition(int posX, int posY, int direction){
      this.posX = posX;
      this.posY = posY;
      this.direction = direction;
   }
}
