package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ContainerView extends JPanel {

    JTextField searchInput = new JTextField(30);
    JButton searchButton = new JButton("Search");
    JPanel addressListTable = new AddressListView();

    String searchInputValue = "";

    public ContainerView() {
        draw();
        addEvents();
    }

    private void draw() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx      = 0;
        gbc.gridy      = 0;
        gbc.gridwidth  = 4;
        add(searchInput, gbc);

        gbc.gridx      = 5;
        gbc.gridy      = 0;
        gbc.gridwidth  = 2;
        add(searchButton, gbc);

        gbc.gridx      = 0;
        gbc.gridy      = 1;
        gbc.gridwidth  = 3;
        add(addressListTable, gbc);
    }

    private void addEvents() {
        searchInput.addActionListener(this::handleSearchEvent);
        searchButton.addActionListener(this::handleSearchEvent);
    }

    private void handleSearchEvent(ActionEvent event) {
        searchInputValue = searchInput.getText();
        addressListTable.setVisible(true);
    }
}
