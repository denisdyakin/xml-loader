package ru.lamoda.dao;

import java.util.List;

public interface LoaderDao {

    void saveBatchOfItems(List<ru.lamoda.jdo.LoadingStockStateResponseType> items);

}
