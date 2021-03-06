// Initial file creator: https://github.com/SomeOtherGod

package src.gui;

import src.models.Map;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

public class Map_Designer {
    static JFrame jFrame = new JFrame("Map-designer");
    static final JLabel jl1 = new JLabel("rows: 1-30");
    static final JLabel jl2 = new JLabel("columns: 1-30");
    static final JTextField jt1 = new JTextField("10");
    static final JTextField jt2 = new JTextField("10");
    static final JTextField jt3 = new JTextField("x of pacman");
    static final JTextField jt4 = new JTextField("y of pacman");
    static final JTextField jt5 = new JTextField("Map_Name");
    static final JButton b1 = new JButton("proceed");
    static final JButton b2 = new JButton("finish");

    static int rows, columns, pacmanX, pacmanY;
    static MapPanel jp;

    static int[][] item;

    static final ActionListener actionListener1 = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            jFrame.setVisible(false);
            try { // die eingegebenen daten werdein geschpeichert
                rows = Integer.parseInt(jt1.getText()) % 31;
                columns = Integer.parseInt(jt2.getText()) % 31;
                if (rows <= 0) {
                    rows = 1;
                }
                if (columns <= 0) {
                    columns = 1;
                }
                pacmanX = Integer.parseInt(jt4.getText()) % (rows);
                pacmanY = Integer.parseInt(jt3.getText()) % (columns);
                if (pacmanX < 0) {
                    pacmanX = 0;
                }
                if (pacmanY < 0) {
                    pacmanY = 0;
                }
            } catch (NumberFormatException n) {
                System.out.println("Illegal character used as number");
                jFrame.dispose();
            }
            jFrame.removeAll(); //frame wird gereinigt um die neue anzeige auszufüren
            jFrame.revalidate();
            jFrame.repaint();
            jFrame = new JFrame("Map Designer");
            jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            jFrame.setSize(columns*30,rows*30 + 60);
            jFrame.setLocationRelativeTo(null);
            jp = new MapPanel(rows, columns, pacmanX, pacmanY); // erzeugt ein feld mit den voher angegebenen daten
            jFrame.setLayout(new BorderLayout());
            jFrame.add(jp, BorderLayout.CENTER);
            jFrame.add(b2, BorderLayout.SOUTH);
            jFrame.setVisible(true);
            JOptionPane.showMessageDialog(jFrame, "Click on element to change it to the next\nBlack: Path\nBlue: Wall\nRed: Cherry Item"); //öffnent ein Info fenster mit den funktionen
        }
    };

    static final ActionListener actionListener2 = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            item = jp.getElements();
            Map map = new Map(jt5.getText(), pacmanY, pacmanX, item); //erschafft eine neue map Klasse um die neue Map als datei in resource/data/maps zu speichern
            jFrame.dispose(); //schließt den Map_Designer
        }
    };

    public void start() {
        b1.addActionListener(actionListener1);
        b2.addActionListener(actionListener2);
        jFrame.setLayout(new GridLayout(4,2));
        jFrame.add(jl1);
        jFrame.add(jl2);
        jFrame.add(jt1);
        jFrame.add(jt2);
        jFrame.add(jt3);
        jFrame.add(jt4);
        jFrame.add(jt5);
        jFrame.add(b1);
        jFrame.setSize(211, 111);
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.setVisible(true);
    }

    public static class MapPanel extends JPanel {
        final ArrayList<ElementPanel> elementList;
        final int arrayWith;

        public MapPanel(int rows, int columns, int pacmanRow, int pacmanCol) {
            this.setLayout(new GridLayout(rows, columns));
            this.arrayWith = columns;

            //füllt das neue Jpanel mit Interactieven Jpannels welche Cherry, Pacman, Wand oder Weg darstellen.
            elementList = new ArrayList<>(rows*columns);
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < columns; col++) {

                    ElementPanel elementPanel;
                    if (row == pacmanRow && col == pacmanCol) {
                        elementPanel = new PacmanPanel();
                    } else {
                        elementPanel = new ElementPanel();
                    }
                    Border border;
                    if (row < rows - 1) {
                        if (col < columns - 1) {
                            border = new MatteBorder(1, 1, 0, 0, Color.DARK_GRAY);
                        } else {
                            border = new MatteBorder(1, 1, 0, 1, Color.DARK_GRAY);
                        }
                    } else {
                        if (col < columns - 1) {
                            border = new MatteBorder(1, 1, 1, 0, Color.DARK_GRAY);
                        } else {
                            border = new MatteBorder(1, 1, 1, 1, Color.DARK_GRAY);
                        }
                    }
                    elementPanel.setBorder(border);
                    this.add(elementPanel);
                    elementList.add(elementPanel);
                }
            }
        }

        public int[][] getElements() { //erzeugt ein 2D int array auß dem erstellten JPanels
            int xOfArray = 0, yOfArray = 0;
            int[][] elements = new int[elementList.size()/arrayWith][arrayWith];
            for (ElementPanel element : elementList) {
                elements[xOfArray][yOfArray]= element.getElementData();
                yOfArray++;
                if (yOfArray == arrayWith) {
                    yOfArray = 0;
                    xOfArray++;
                }
            }
            return elements;
        }
    }

    public static class ElementPanel extends JPanel {


        final ArrayList<Color> colors = new ArrayList<>(); // Liste mit den farben, die die Felder haben können

        public ElementPanel() {
            setBackground(Color.BLACK);
            colors.add(Color.BLUE);
            colors.add(Color.RED);
            colors.add(Color.BLACK);
            final Iterator[] i = {colors.iterator()};
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) { //Ändert die Farbe des JPanels zum nächsten ellement der colors ArrayList wenn maus geklickt wird
                    if (i[0].hasNext()) {
                        setBackground((Color) i[0].next());
                    } else {
                        setBackground(colors.get(0));
                    }
                    if (!i[0].hasNext()) {
                        i[0] = colors.iterator();
                    }
                }
            });
        }

        public int getElementData() {
            if (getBackground() == Color.BLUE) {
                return 0;
            } else if (getBackground() == Color.RED) {
                return 2;
            } else {
                return 1;
            }
        }
    }

    public static class PacmanPanel extends ElementPanel {
        BufferedImage pacmanIcon;
        public PacmanPanel() {
            try {
                pacmanIcon = ImageIO.read(new File(Paths.get(System.getProperty("user.dir"), "resources", "img", "General Sprites.png").toString())).getSubimage(16, 0, 13, 13);
            } catch (IOException e) {
                e.printStackTrace();
            }
            setBackground(Color.BLACK);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.drawImage(pacmanIcon,1,1, getWidth()-1, getHeight()-1, this);
        }

        @Override
        public int getElementData() {
            return 1;
        }
    }
}