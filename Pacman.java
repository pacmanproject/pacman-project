public class Pacman {
    int posX = 1;
    int posY = 3;

    int posXnew = 0;
    int posYnew = 0;

    //UP: Y wird kleiner
    public void moveUp(){
        posXnew = posX;
        posYnew = posY-1;
        if(posXnew >= 0 && posYnew >= 0 && getxyWelt(posXnew, posYnew ) == true){
            posY = posYnew;
        }

    }

    //DOWN: Y wird größer
    public void moveDown(){
        posXnew = posX;
        posYnew = posY+1;
        if(posXnew >= 0 && posYnew >= 0 && getxyWelt(posXnew, posYnew ) == true){
            posY = posYnew;
        }
    }

    //LEFT: X wird kleiner
    public void moveLeft(){
        posXnew = posX -1;
        posYnew = posY;
        if(posXnew >= 0 && posYnew >= 0 && getxyWelt(posXnew, posYnew ) == true){
            posX = posXnew;
        }
    }

    //RIGHT: X wird größer
    public void moveRight(){
        posXnew = posX +1;
        posYnew = posY;
        if(posXnew >= 0 && posYnew >= 0 && getxyWelt(posXnew, posYnew ) == true){
            posX = posXnew;
        }
    }
}
