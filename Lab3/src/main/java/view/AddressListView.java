package view;

import lombok.SneakyThrows;
import models.Address;
import utils.JsonParser;

import javax.swing.*;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class AddressListView extends JPanel {

    public AddressListView() {
        draw();
    }

    private void draw() {
        Vector<String> addressesNameList = generateFakeData()
            .stream()
            .map(address -> address.name)
            .collect(Collectors.toCollection(Vector::new));
        JList<String> jAddressList = new JList<>(addressesNameList);
        jAddressList.setLayoutOrientation(JList.VERTICAL);
        add(jAddressList);
        setVisible(false);
    }

    @SneakyThrows
    public List<Address> generateFakeData() {
        InputStream stream = ClassLoader.getSystemResourceAsStream(
            "fakeAddressList.json"
        );
        assert stream != null;
        String fakeData = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        // return JsonParser.parse(fakeData, );
    }
}
