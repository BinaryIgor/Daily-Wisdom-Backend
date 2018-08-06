package control.self.igor.dailywisdom.service.crud;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import control.self.igor.dailywisdom.entity.Quote;
import control.self.igor.dailywisdom.repository.QuoteRepository;

@Service
@Transactional
public class QuoteCrudService extends CrudService<Quote> {

    public QuoteCrudService(QuoteRepository repository) {
	super(repository, "id");
    }
}
