package ru.lamoda.jdo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "itemType", namespace = "http://lamoda.ru/xsd/wms/stock-state", propOrder = {
        "id",
        "sku",
        "count"
})
public class ItemType {

    @XmlElement(namespace = "http://lamoda.ru/xsd/wms/stock-state", required = true)
    protected Long id;
    @XmlElement(namespace = "http://lamoda.ru/xsd/wms/stock-state", required = true)
    protected String sku;
    @XmlElement(namespace = "http://lamoda.ru/xsd/wms/stock-state", required = true)
    protected Long count;

    public Long getId() {
        return id;
    }

    public void setId(Long value) {
        this.id = value;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String value) {
        this.sku = value;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long value) {
        this.count = value;
    }

    @Override
    public String toString() {
        return "StockStateItem{" +
                "id=" + id +
                ", sku='" + sku + '\'' +
                ", count=" + count +
                '}';
    }
}

