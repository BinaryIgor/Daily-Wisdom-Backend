package control.self.igor.dailywisdom.repository.abstraction;

import org.springframework.data.repository.CrudRepository;

import control.self.igor.dailywisdom.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);

}
