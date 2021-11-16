package view;

import api.AddressService;
import api.PlaceService;
import models.Address;
import models.AddressList;
import models.Place;
import utils.ApiUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ContainerView extends JPanel {

    JTextField searchInput = new JTextField(50);
    JButton searchButton = new JButton("Search");

    AddressListView addressListView = new AddressListView(this);
    PlaceListView placeListView = new PlaceListView();

    public ContainerView() {
        draw();
        addEvents();
    }

    private void draw() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        add(searchInput, gbc);

        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(searchButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        add(addressListView, gbc);

        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        add(placeListView, gbc);
    }

    private void addEvents() {
        searchInput.addActionListener(this::handleSearchEvent);
        searchButton.addActionListener(this::handleSearchEvent);
    }

    private void handleSearchEvent(ActionEvent event) {
        updateAddressList(searchInput.getText());
    }

    public void updateAddressList(String query) {
        ApiUtils.call(AddressService.getAddressList(query), AddressList.class)
            .thenAcceptAsync(data -> addressListView.update(data));
    }

    public void updatePlaceList(Address.Coordinates coordinates) {
        ApiUtils.call(PlaceService.getPlaceList(coordinates, 1000), Place[].class)
            .thenAcceptAsync(data -> placeListView.update(data));
    }
}
