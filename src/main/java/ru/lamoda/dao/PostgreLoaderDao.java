package ru.lamoda.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lamoda.jdo.ItemType;
import ru.lamoda.jdo.LoadingStockStateResponseType;
import ru.lamoda.utils.SqlScriptLoader;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostgreLoaderDao implements LoaderDao {
    private static final Logger log = LoggerFactory.getLogger(PostgreLoaderDao.class);

    private static String LAND_RESPONSES = SqlScriptLoader.loadScript("sql/landResponses.sql");

    private JdbcTemplate jdbcTemplate;

    public PostgreLoaderDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    @Override
    public void saveBatchOfItems(List<LoadingStockStateResponseType> responses) {
        if (responses == null || responses.size() == 0) return;

        log.debug("Save loading responses - {}", responses.size());
        List<ItemType> itemsOfResponses  = responses
                        .parallelStream()
                        .flatMap((response) -> response.getItem().stream())
                        .collect(Collectors.toList());

        log.debug("Save loading items - {}", itemsOfResponses.size());
        jdbcTemplate.batchUpdate(LAND_RESPONSES, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                ItemType itemType = itemsOfResponses.get(i);
                preparedStatement.setLong(1, itemType.getId());
                preparedStatement.setString(2, itemType.getSku());
                preparedStatement.setLong(3, itemType.getCount());
            }

            @Override
            public int getBatchSize() {
                return itemsOfResponses.size();
            }
        });
    }
}
