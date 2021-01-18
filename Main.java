import src.gui.Gui;
import src.models.Pacman;
import src.models.World;

import java.util.concurrent.TimeUnit;

public class Main {
    static World w = new World("map2.json");
    static Pacman p = new Pacman(w);
    static Gui g = new Gui(w, p);

    public static void main(String[] args) {
        p.start();
        g.start();
    }
}
