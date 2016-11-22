package urlshortener.common.repository;

import urlshortener.common.domain.CountryRestriction;

public interface CountryResRepository {

    void save(CountryRestriction c);

    void restrictCountry(String country);
}
