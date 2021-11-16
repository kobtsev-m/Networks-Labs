package view;

import javax.swing.*;

public class WindowView extends JFrame {

    private final String WINDOW_TITLE = "Async requests app";
    private final int[] WINDOW_SIZE = new int[] {700, 500};

    private static WindowView instance;

    private WindowView() {
        setTitle(WINDOW_TITLE);
        setSize(WINDOW_SIZE[0], WINDOW_SIZE[1]);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(new ContainerView());
        setVisible(true);
    }

    public static WindowView getInstance() {
        if (instance == null) {
            instance = new WindowView();
        }
        return instance;
    }
}
