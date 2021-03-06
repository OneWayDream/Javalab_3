package ru.itis.repositories.minions_repositories.implementations;

import javafx.util.Pair;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.itis.repositories.SimpleJDBSTemplate;
import ru.itis.repositories.minions_repositories.interfaces.MinionsUpgradeGroupsRepository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MinionsUpgradeGroupsRepositoryImpl implements MinionsUpgradeGroupsRepository {


    //language=SQL
    private static final String SQL_SELECT_BY_MINION_NAME = "SELECT * FROM minion_upgrade_group WHERE minion_name=?";
    //language=SQL
    private static final String SQL_INSERT_ENTRY = "INSERT INTO minion_upgrade_group(minion_name, upgrade_name)  " +
            "VALUE (?,?)";
    //language=SQL
    private static final String SQL_DELETE = "DELETE FROM minion_upgrade_group WHERE minion_name=? AND upgrade_name=?";
    //language=SQL
    private static final String SQL_SELECT_ALL = "SELECT * FROM minion_upgrade_group";



    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    private RowMapper<Pair<String, String>> upgradesRowMapper = (row, i) -> new Pair<>(row.getString("minion_name").trim(),
            row.getString("upgrade_name").trim());

    public MinionsUpgradeGroupsRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<String> getUpgradesMyMinionName(String minion_name) {
        return jdbcTemplate.query(SQL_SELECT_BY_MINION_NAME, upgradesRowMapper, minion_name).stream().map(Pair::getValue).collect(Collectors.toList());
    }

    @Override
    public void save(Pair<String, String> entity) {
        jdbcTemplate.update(SQL_INSERT_ENTRY, entity.getKey(), entity.getValue());
    }

    @Override
    public void update(Pair<String, String> entity) {
        //not actually method for this database.
    }

    @Override
    public void delete(Pair<String, String> entity) {
        jdbcTemplate.update(SQL_DELETE, entity.getKey(), entity.getValue());
    }

    @Override
    public Optional<Pair<String, String>> findById(Long id) {
        return Optional.empty(); //not actually method for this database.
    }

    @Override
    public List<Pair<String, String>> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, upgradesRowMapper);
    }
}
