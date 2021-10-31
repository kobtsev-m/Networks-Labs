package components;

import javax.swing.*;

public class Window extends JFrame {

    private final String WINDOW_TITLE = "Async requests app";
    private final int[] WINDOW_SIZE = new int[] {600, 500};

    public Window() {
        setTitle(WINDOW_TITLE);
        setSize(WINDOW_SIZE[0], WINDOW_SIZE[1]);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(new Container());
        setVisible(true);
    }
}
