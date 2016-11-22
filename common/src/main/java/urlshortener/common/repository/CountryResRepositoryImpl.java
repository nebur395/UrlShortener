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

@Repository
public class CountryResRepositoryImpl implements CountryResRepository {

    private static final Logger log = LoggerFactory.getLogger(CountryResRepositoryImpl.class);

    private static final RowMapper<CountryRestriction> rowMapper = new RowMapper<CountryRestriction>() {
        @Override
        public CountryRestriction mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new CountryRestriction(rs.getString("country"), rs.getBoolean("accessAllow"));
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
        String[] countries = {"spain", "italy", "ireland", "japan"};
        CountryRestriction c;
        for(int i=0; i<countries.length; i++){
            c = new CountryRestriction(countries[i], true);
            save(c);
        }
        log.info("Initialized countryrestriction table");
    }

    public void save(CountryRestriction c) {
        log.info("add country: "+c.getCountry());
        try {
            jdbc.update("INSERT INTO countryrestriction VALUES (?,?)",
                c.getCountry(), c.isaccessAllowed());
        } catch (DuplicateKeyException e) {
            log.debug("When insert for key " + c.getCountry(), e);
        } catch (Exception e) {
            log.debug("When insert", e);
        }
    }

    public void restrictCountry(String country){
        try {
            log.info("country restricted: "+country);
            jdbc.update(
                "update countryrestriction set accessAllowed=false where country=?", country);
        } catch (Exception e) {
            log.debug("When update for country " + country, e);
        }
    }
}
