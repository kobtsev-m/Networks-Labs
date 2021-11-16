package view;

import models.Address;
import models.AddressList;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import java.util.stream.Collectors;

public class AddressListView extends JPanel {

    private final ContainerView containerView;
    private JList<String> jAddressList = new JList<>();;
    private AddressList addressList;

    public AddressListView(ContainerView containerView) {
        this.containerView = containerView;
    }

    public void update(AddressList addressList) {
        this.addressList = addressList;
        Vector<String> addressesNameList = addressList.hits
            .stream()
            .map(address -> address.name)
            .collect(Collectors.toCollection(Vector::new));
        jAddressList = new JList<>(addressesNameList);
        jAddressList.setLayoutOrientation(JList.VERTICAL);
        removeAll();
        add(jAddressList);
        addEvents();
    }

    private void addEvents() {
        jAddressList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    int index = jAddressList.locationToIndex(event.getPoint());
                    if (index >= 0) {
                        Object activeObject = jAddressList.getModel().getElementAt(index);
                        Address activeAddress = addressList.hits
                            .stream()
                            .filter(address -> address.name.equals(activeObject.toString()))
                            .findAny()
                            .orElse(null);
                        containerView.updatePlaceList(activeAddress.coordinates);
                    }
                }
            }
        });
    }
}
