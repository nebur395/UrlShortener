package urlshortener.common.repository;

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

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final Logger log = LoggerFactory
        .getLogger(ShortURLRepositoryImpl.class);

    private static final RowMapper<User> rowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(rs.getString("name"), rs.getString("pass"), rs.getString("email"),
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
            return jdbc.queryForObject("SELECT * FROM user WHERE name=?",
                rowMapper, name);
        } catch (Exception e) {
            log.debug("When select for name " + name, e);
            return null;
        }
    }

    @Override
    public User save(User user) {
        try {
            jdbc.update("INSERT INTO user VALUES (?,?,?,?,?)",
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
                "update user set pass=?, email=?, admin=?, created=? where name=?",
                user.getPass(), user.getEmail(), user.getAdmin(),
                user.getCreated(), user.getName());
        } catch (Exception e) {
            log.debug("When update for name " + user.getName(), e);
        }
    }

    @Override
    public void delete(String name) {
        try {
            jdbc.update("delete from user where name=?", name);
        } catch (Exception e) {
            log.debug("When delete for name " + name, e);
        }
    }

    @Override
    public Long count() {
        try {
            return jdbc.queryForObject("select count(*) from user",
                Long.class);
        } catch (Exception e) {
            log.debug("When counting", e);
        }
        return -1L;
    }

    @Override
    public List<User> list(Long limit, Long offset) {
        try {
            return jdbc.query("SELECT * FROM user LIMIT ? OFFSET ?",
                new Object[] { limit, offset }, rowMapper);
        } catch (Exception e) {
            log.debug("When select for limit " + limit + " and offset "
                + offset, e);
            return null;
        }
    }
}
