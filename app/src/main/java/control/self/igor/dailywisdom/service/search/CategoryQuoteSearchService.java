package control.self.igor.dailywisdom.service.search;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import control.self.igor.dailywisdom.entity.Category;
import control.self.igor.dailywisdom.repository.EntityQuoteRepository;

@Service
@Transactional
public class CategoryQuoteSearchService extends EntityQuoteSearchService<Category> {

    @Autowired
    public CategoryQuoteSearchService(EntityQuoteRepository entityQuoteRepository) {
	super(Category.class, entityQuoteRepository);
    }

}
