package urlshortener.common.repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import urlshortener.common.domain.User;

import javax.annotation.PostConstruct;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final Logger log = LoggerFactory
        .getLogger(UserRepositoryImpl.class);

    private static final RowMapper<User> rowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(rs.getString("username"), rs.getString("pass"), rs.getString("email"),
                rs.getBoolean("admin"), rs.getDate("created"));
        }
    };

    @Autowired
    protected JdbcTemplate jdbc;

    public UserRepositoryImpl() {
    }

    public UserRepositoryImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public User findByName(String name) {
        try {
            return jdbc.queryForObject("SELECT * FROM users WHERE username=?",
                rowMapper, name);
        } catch (Exception e) {
            log.debug("When select for name " + name, e);
            return null;
        }
    }

    @Override
    public User save(User user) {
        try {
            jdbc.update("INSERT INTO users VALUES (?,?,?,?,?)",
                user.getName(), user.getPass(), user.getEmail(),
                user.getAdmin(), user.getCreated());
        } catch (DuplicateKeyException e) {
            log.debug("When insert for name " + user.getName(), e);
            return user;
        } catch (Exception e) {
            log.debug("When insert", e);
            return null;
        }
        return user;
    }


    @Override
    public void update(User user) {
        try {
            jdbc.update(
                "update users set pass=?, email=?, admin=?, created=? where username=?",
                user.getPass(), user.getEmail(), user.getAdmin(),
                user.getCreated(), user.getName());
        } catch (Exception e) {
            log.debug("When update for name " + user.getName(), e);
        }
    }

    @Override
    public void delete(String name) {
        try {
            jdbc.update("delete from users where username=?", name);
        } catch (Exception e) {
            log.debug("When delete for name " + name, e);
        }
    }

    @Override
    public Long count() {
        try {
            return jdbc.queryForObject("select count(*) from users",
                Long.class);
        } catch (Exception e) {
            log.debug("When counting", e);
        }
        return -1L;
    }

    @PostConstruct
    public void meterUsuario() {
        User u = new User("admin", "admin", "gmail", true, new Date(2000,12,12));
        save(u);
        System.out.println("Metido usuario admin");
    }

    @Override
    public List<User> list(Long limit, Long offset) {
        try {
            return jdbc.query("SELECT * FROM users LIMIT ? OFFSET ?",
                new Object[] { limit, offset }, rowMapper);
        } catch (Exception e) {
            log.debug("When select for limit " + limit + " and offset "
                + offset, e);
            return null;
        }
    }
}
