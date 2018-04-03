package control.self.igor.dailywisdom.service.abstraction;

import control.self.igor.dailywisdom.entity.Author;
import control.self.igor.dailywisdom.entity.AuthorDescription;

public interface AuthorService extends CrudAndSearchService<Author, String> {

    AuthorDescription getAuthorDescription(long id);

    byte[] getAuthorImage(long id);

}
