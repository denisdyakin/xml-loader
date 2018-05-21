package ru.lamoda.jdo;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LoadingStockStateResponseType", namespace = "http://lamoda.ru/xsd/wms/stock-state", propOrder = {
        "item"
})
public class LoadingStockStateResponseType {

    @XmlElement(namespace = "http://lamoda.ru/xsd/wms/stock-state")
    protected List<ItemType> item;

    public List<ItemType> getItem() {
        if (item == null) {
            item = new ArrayList<ItemType>();
        }
        return this.item;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (ItemType itemType : item)
            str.append(itemType.toString() + " ");
        return str.toString();
    }
}
