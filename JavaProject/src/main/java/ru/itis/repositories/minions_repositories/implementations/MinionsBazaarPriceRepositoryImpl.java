package ru.itis.repositories.minions_repositories.implementations;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.itis.models.Item;
import ru.itis.repositories.SimpleJDBSTemplate;
import ru.itis.repositories.minions_repositories.interfaces.MinionsBazaarPriceRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Optional;

public class MinionsBazaarPriceRepositoryImpl implements MinionsBazaarPriceRepository {

    //language=SQL
    private static final String SQL_SELECT_BY_ITEM = "SELECT * FROM item_bazaar_price WHERE item_name=?";
    //language=SQL
    private static final String SQL_INSERT_ENTRY = "INSERT INTO item_bazaar_price(item_name, buy_price, sell_price) VALUE (?,?,?)";
    //language=SQL
    private static final String SQL_UPDATE = "UPDATE item_bazaar_price SET buy_price=?, sell_price=? where item_name=?";
    //language=SQL
    private static final String SQL_DELETE = "DELETE FROM item_bazaar_price WHERE item_name=?";
    //language=SQL
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM item_bazaar_price WHERE id=?";
    //language=SQL
    private static final String SQL_SELECT_ALL = "SELECT * FROM item_bazaar_price";



    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    private RowMapper<Item> bazaarPriceRowMapper = (row, i) -> Item.builder()
            .itemName(row.getString("item_name").trim())
            .buyBazaarPrice(row.getDouble("buy_price"))
            .sellBazaarPrice(row.getDouble("sell_price"))
            .build();

    public MinionsBazaarPriceRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Item> getEntryByItemName(String itemName) {
        return jdbcTemplate.query(SQL_SELECT_BY_ITEM, bazaarPriceRowMapper, itemName).stream().findAny();
    }

    @Override
    public void save(Item entity) {
        jdbcTemplate.update(SQL_INSERT_ENTRY, entity.getItemName(), entity.getBuyBazaarPrice(), entity.getSellBazaarPrice());
    }

    @Override
    public void update(Item entity) {
        jdbcTemplate.update(SQL_UPDATE, entity.getBuyBazaarPrice(), entity.getSellBazaarPrice(), entity.getItemName());
    }

    @Override
    public void delete(Item entity) {
        jdbcTemplate.update(SQL_DELETE, entity.getItemName());
    }

    @Override
    public Optional<Item> findById(Long id) {
        return jdbcTemplate.query(SQL_SELECT_BY_ID, bazaarPriceRowMapper, id).stream().findAny();
    }

    @Override
    public List<Item> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, bazaarPriceRowMapper);
    }
}
