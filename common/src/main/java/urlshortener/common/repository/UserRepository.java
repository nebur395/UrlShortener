package urlshortener.common.repository;

import java.util.List;

import urlshortener.common.domain.User;

public interface UserRepository {

    User findByName(String name);

    User save(User user);

    void update(User user);

    void delete(String name);

    Long count();

    List<User> list(Long limit, Long offset);

}
