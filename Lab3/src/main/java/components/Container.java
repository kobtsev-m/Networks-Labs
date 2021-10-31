package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Container extends JPanel {

    JTextField searchInput = new JTextField(30);
    JButton searchButton = new JButton("Search");
    JLabel resultLabel = new JLabel();

    String searchInputValue = "";

    public Container() {
        createLayout();
        addEvents();
    }

    private void createLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx      = 0;
        gbc.gridy      = 0;
        gbc.gridwidth  = 2;
        add(searchInput, gbc);

        gbc.gridx      = 2;
        gbc.gridy      = 0;
        gbc.gridwidth  = 1;
        add(searchButton, gbc);

        gbc.gridx      = 0;
        gbc.gridy      = 1;
        gbc.gridwidth  = 3;
        add(resultLabel, gbc);
    }

    private void addEvents() {
        searchInput.addActionListener(this::handleSearchEvent);
        searchButton.addActionListener(this::handleSearchEvent);
    }

    private void handleSearchEvent(ActionEvent event) {
        searchInputValue = searchInput.getText();
        resultLabel.setText(searchInputValue);
    }
}
