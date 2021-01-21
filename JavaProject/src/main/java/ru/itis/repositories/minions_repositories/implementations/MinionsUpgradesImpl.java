package ru.itis.repositories.minions_repositories.implementations;

import javafx.util.Pair;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.itis.models.Upgrade;
import ru.itis.repositories.SimpleJDBSTemplate;
import ru.itis.repositories.minions_repositories.interfaces.MinionsUpgrades;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class MinionsUpgradesImpl implements MinionsUpgrades {

    //language=SQL
    private static final String SQL_INSERT_ENTRY = "INSERT INTO upgrades(name, upgrade_group)  " +
            "VALUE (?,?)";
    //language=SQL
    private static final String SQL_UPDATE = "UPDATE upgrades SET upgrade_group=? WHERE name=?";
    //language=SQL
    private static final String SQL_DELETE = "DELETE FROM upgrades WHERE name=?";
    //language=SQL
    private static final String SQL_SELECT_ALL = "SELECT * FROM upgrades";



    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    private RowMapper<Upgrade> upgradesRowMapper = (row, i) -> Upgrade.builder()
            .name(row.getString("name").trim())
            .groupName(row.getString("upgrade_group").trim())
            .build();

    public MinionsUpgradesImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Upgrade entity) {
        jdbcTemplate.update(SQL_INSERT_ENTRY, entity.getName(), entity.getGroupName());
    }

    @Override
    public void update(Upgrade entity) {
        jdbcTemplate.update(SQL_UPDATE, entity.getGroupName(), entity.getName());
    }

    @Override
    public void delete(Upgrade entity) {
        jdbcTemplate.update(SQL_DELETE, entity.getName());
    }

    @Override
    public Optional<Upgrade> findById(Long id) {
        return Optional.empty(); //not actually method for this database.
    }

    @Override
    public List<Upgrade> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, upgradesRowMapper);
    }
}
