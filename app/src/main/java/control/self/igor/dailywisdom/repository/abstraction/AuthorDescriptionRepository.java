package control.self.igor.dailywisdom.repository.abstraction;

import org.springframework.data.repository.CrudRepository;

import control.self.igor.dailywisdom.entity.AuthorDescription;

public interface AuthorDescriptionRepository extends CrudRepository<AuthorDescription, Long> {
    AuthorDescription findByAuthorId(long authorId);
}
