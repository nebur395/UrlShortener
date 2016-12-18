package urlshortener.common.repository;

import urlshortener.common.domain.CountryRestriction;

import java.util.List;

public interface CountryResRepository {

    CountryRestriction save(CountryRestriction c);

    boolean restrictCountry(String country);

    boolean unblockCountry(String country);

    List<String> listCountries(boolean access);

    CountryRestriction findCountry(String country);

    boolean updateFrequency(String country, Integer req, Integer time);
}
