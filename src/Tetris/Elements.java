package Tetris;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Elements {
    List<List> ElementList = new ArrayList<List>();
    List<Block> I = new ArrayList<Block>();
    List<Block> J = new ArrayList<Block>();
    List<Block> L = new ArrayList<Block>();
    List<Block> O = new ArrayList<Block>();
    List<Block> S = new ArrayList<Block>();
    List<Block> T = new ArrayList<Block>();
    List<Block> Z = new ArrayList<Block>();

    {
        I.add(new Block(0, 0, Color.CYAN, new Color(145, 237, 243)));
        I.add(new Block(1, 0, Color.CYAN, new Color(145, 237, 243)));
        I.add(new Block(-1, 0, Color.CYAN, new Color(145, 237, 243)));
        I.add(new Block(2, 0, Color.CYAN, new Color(145, 237, 243)));

        L.add(new Block(0, 0, Color.BLUE, new Color(79, 127, 240)));
        L.add(new Block(1, 0, Color.BLUE, new Color(79, 127, 240)));
        L.add(new Block(-1, 0, Color.BLUE, new Color(79, 127, 240)));
        L.add(new Block(-1, 1, Color.BLUE, new Color(79, 127, 240)));

        J.add(new Block(0, 0, Color.ORANGE, new Color(243, 190, 97)));
        J.add(new Block(-1, 0, Color.ORANGE, new Color(243, 190, 97)));
        J.add(new Block(1, 0, Color.ORANGE, new Color(243, 190, 97)));
        J.add(new Block(1, 1, Color.ORANGE, new Color(243, 190, 97)));

        O.add(new Block(0, 0, Color.YELLOW, new Color(255, 250, 124)));
        O.add(new Block(0, 1, Color.YELLOW, new Color(255, 250, 124)));
        O.add(new Block(1, 1, Color.YELLOW, new Color(255, 250, 124)));
        O.add(new Block(1, 0, Color.YELLOW, new Color(255, 250, 124)));

        S.add(new Block(0, 0, Color.GREEN, new Color(124, 255, 137)));
        S.add(new Block(1, 0, Color.GREEN, new Color(124, 255, 137)));
        S.add(new Block(0, 1, Color.GREEN, new Color(124, 255, 137)));
        S.add(new Block(-1, 1, Color.GREEN, new Color(124, 255, 137)));

        T.add(new Block(0, 0, Color.MAGENTA, new Color(246, 124, 255)));
        T.add(new Block(1, 0, Color.MAGENTA, new Color(246, 124, 255)));
        T.add(new Block(-1, 0, Color.MAGENTA, new Color(246, 124, 255)));
        T.add(new Block(0, 1, Color.MAGENTA, new Color(246, 124, 255)));

        Z.add(new Block(0, 0, Color.RED, new Color(255, 124, 124)));
        Z.add(new Block(-1, 0, Color.RED, new Color(255, 124, 124)));
        Z.add(new Block(0, 1, Color.RED, new Color(255, 124, 124)));
        Z.add(new Block(1, 1, Color.RED, new Color(255, 124, 124)));

        ElementList.add(I);
        ElementList.add(J);
        ElementList.add(L);
        ElementList.add(O);
        ElementList.add(S);
        ElementList.add(T);
        ElementList.add(Z);
    }

    public List getRandomElement() {
        Random rand = new Random();
        int x = rand.nextInt(7);
        return ElementList.get(x);
    }
}
