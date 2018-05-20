package control.self.igor.dailywisdom.repository.abstraction;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import control.self.igor.dailywisdom.entity.User;
import control.self.igor.dailywisdom.entity.UserRole;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByName(String name);

    @Query("SELECT ur FROM UserRole ur WHERE ur.role = 'guest'")
    UserRole getGuest();

    @Query("SELECT ur.role FROM UserRole ur INNER JOIN User u ON u.name = :name WHERE u.userRole.id = ur.id")
    String getUserRoleByName(@Param("name") String name);

    boolean existsByNameIgnoreCase(String name);

}
