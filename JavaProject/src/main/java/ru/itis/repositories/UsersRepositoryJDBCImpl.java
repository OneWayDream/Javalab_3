package ru.itis.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.itis.models.User;
import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJDBCImpl implements UsersRepository {

    //language=SQL
    private static final String SQL_FIND_ALL = "select * from users";
    //language=SQL
    private static final String SQL_INSERT_USER = "INSERT INTO users (login, email, password, first_name, gender, country, nickname, facebook, vk, role) VALUES (?,?,?,?,?::integer,?,?,?,?,?)";
    //language=SQL
    protected static final String SQL_FIND_USER_BY_EMAIL = "SELECT * FROM users WHERE email=?";
    //language=SQL
    protected static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login=?";
    //language=SQL
    private static final String SQL_UPDATE = "UPDATE users SET login=?, email=?, password=?, first_name=?, nickname=?, gender=?::integer , country=?, vk=?, facebook=? where id=?";
    //language=SQL
    private static final String SQL_UPDATE_PASSWORD = "UPDATE users SET password=? where id=?";
    //language=SQL
    private static final String SQL_DELETE = "DELETE FROM users WHERE id=?";
    //language=SQL
    protected static final String SQL_FIND_USER_BY_ID = "SELECT * from users where id=?";

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    public UsersRepositoryJDBCImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    //RowMapper and JdbcTemplate from Spring

    private RowMapper<User> userRowMapper = (row, i) -> User.builder()
            .id(row.getLong("id"))
            .login(row.getString("login").trim())
            .email(row.getString("email").trim())
            .password(row.getString("password").trim())
            .firstName(row.getString("first_name").trim())
            .gender(row.getInt("gender"))
            .country(row.getString("country").trim())
            .minecraftNickname(row.getString("nickname").trim())
            .facebook(row.getString("facebook").trim())
            .vk(row.getString("vk").trim())
            .role(row.getString("role").trim())
            .build();

    @Override
    public void save(User entity) {
        jdbcTemplate.update(SQL_INSERT_USER, entity.getLogin(), entity.getEmail(), entity.getPassword(),
                entity.getFirstName(), entity.getGender(), entity.getCountry(), entity.getMinecraftNickname(),
                entity.getFacebook(), entity.getVk(), entity.getRole());
    }

    @Override
    public void update(User entity) {
        jdbcTemplate.update(SQL_UPDATE, entity.getLogin(), entity.getEmail(), entity.getPassword(),
                entity.getFirstName(), entity.getMinecraftNickname(), entity.getGender(), entity.getCountry(),
                entity.getVk(), entity.getFacebook(), entity.getId());
    }

    @Override
    public void delete(User entity) {
        jdbcTemplate.update(SQL_DELETE, entity.getId());
    }

    @Override
    public Optional<User> findById(Long id) {
        return jdbcTemplate.query(SQL_FIND_USER_BY_ID, userRowMapper, id).stream().findAny();
    }

    @Override
    public Optional<User> findByEMail(String email) {
        return jdbcTemplate.query(SQL_FIND_USER_BY_EMAIL, userRowMapper, email).stream().findAny();
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return jdbcTemplate.query(SQL_FIND_USER_BY_LOGIN, userRowMapper, login).stream().findAny();
    }

    @Override
    public void updateUserPassword(User user) {
        jdbcTemplate.update(SQL_UPDATE_PASSWORD, user.getPassword(), user.getId());
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, userRowMapper);
    }

}
