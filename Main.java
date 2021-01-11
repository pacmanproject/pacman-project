//TODO: add maven to import gson automatically



//Test

import src.gui.Gui;
import src.models.Pacman;
import src.models.World;

import java.util.concurrent.TimeUnit;



public class Main {
    static World w = new World("map3.json");
    static Pacman p = new Pacman(w);
    static Gui g = new Gui(w, p);

    public static void main(String[] args) {
        g.start();


        while(true) {
            try {
                TimeUnit.MILLISECONDS.sleep(500); // zeit die zwischen einer bewegung von Pacman vergeht
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            p.move();
        }
    }
}