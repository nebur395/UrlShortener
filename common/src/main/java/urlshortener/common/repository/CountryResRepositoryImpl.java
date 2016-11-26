package urlshortener.common.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import urlshortener.common.domain.CountryRestriction;
import javax.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CountryResRepositoryImpl implements CountryResRepository {

    private static final Logger log = LoggerFactory.getLogger(CountryResRepositoryImpl.class);

    private static final RowMapper<CountryRestriction> rowMapper = new RowMapper<CountryRestriction>() {
        @Override
        public CountryRestriction mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new CountryRestriction(rs.getString("country"), rs.getBoolean("accessAllowed"));
        }
    };

    @Autowired
    protected JdbcTemplate jdbc;

    public CountryResRepositoryImpl() {
    }

    public CountryResRepositoryImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * Initialize COUNTRYRESTRICTION table with different countries
     * allowing their requests
     */
    @PostConstruct
    public void initializeCountryTable(){
        String[] countries = {"Spain", "Italy", "Ireland", "Japan"};
        CountryRestriction c;
        for(int i=0; i<countries.length; i++){
            c = new CountryRestriction(countries[i], true);
            save(c);
        }
        log.info("Initialized countryrestriction table");
    }

    @Override
    public CountryRestriction save(CountryRestriction c) {
        log.info("add country: "+c.getCountry());
        try {
            jdbc.update("INSERT INTO countryrestriction VALUES (?,?)",
                c.getCountry(), c.isaccessAllowed());
        } catch (DuplicateKeyException e) {
            log.debug("When insert for key " + c.getCountry(), e);
            return null;
        } catch (Exception e) {
            log.debug("When insert", e);
            return null;
        }
        return c;
    }

    public List<String> listCountries(boolean access) {
        try {
            return jdbc.queryForList("SELECT country FROM countryrestriction where accessAllowed=?",
                String.class, access);
        } catch (Exception e) {
            log.debug("When select list depends on access ", e);
            return null;
        }
    }

    public boolean restrictCountry(String country){
        try {
            jdbc.update("UPDATE countryrestriction SET accessAllowed=false WHERE country=?", country);
            return true;
        } catch (Exception e) {
            log.debug("When update for country " + country, e);
            return false;
        }
    }

    public boolean unblockCountry(String country){
        try {
            jdbc.update(
                "update countryrestriction set accessAllowed=true where country=?", country);
            return true;
        } catch (Exception e) {
            log.debug("When update for country " + country, e);
            return false;
        }
    }

    public CountryRestriction findCountry(String country){
        try {
            return jdbc.queryForObject("SELECT * FROM countryrestriction WHERE country=?",
                rowMapper, country);
        } catch (Exception e) {
            log.debug("When select for key " + country, e);
            return null;
        }
    }

}
