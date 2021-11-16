package view;

import api.PlaceService;
import models.Place;
import models.PlaceDescription;
import utils.ApiUtils;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class PlaceListView extends JPanel {

    private JList<String> jPlaceList;
    private List<Place> placeList;

    public void update(Place[] placeList) {
        this.placeList = Arrays.asList(placeList);
        Vector<String> placesNameList = Arrays.stream(placeList)
            .map(place -> place.name)
            .collect(Collectors.toCollection(Vector::new));
        jPlaceList = new JList<>(placesNameList);
        jPlaceList.setLayoutOrientation(JList.VERTICAL);
        removeAll();
        add(jPlaceList);
        addEvents();
    }

    private void addEvents() {
        jPlaceList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    int index = jPlaceList.locationToIndex(event.getPoint());
                    if (index >= 0) {
                        Object activeObject = jPlaceList.getModel().getElementAt(index);
                        Place activePlace = placeList
                            .stream()
                            .filter(place -> place.name.equals(activeObject.toString()))
                            .findAny()
                            .orElse(null);
                        ApiUtils.call(PlaceService.getPlaceDescription(activePlace.xid), PlaceDescription.class)
                            .thenAcceptAsync(data -> {
                                System.out.println("hey");
                                WindowView windowView = WindowView.getInstance();
                                JOptionPane.showMessageDialog(windowView, data.info.description);
                            });
                    }
                }
            }
        });
    }
}
