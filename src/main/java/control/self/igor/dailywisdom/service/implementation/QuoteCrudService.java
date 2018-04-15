package control.self.igor.dailywisdom.service.implementation;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import control.self.igor.dailywisdom.entity.Quote;
import control.self.igor.dailywisdom.repository.abstraction.QuoteRepository;
import control.self.igor.dailywisdom.service.abstraction.AbstractCrudService;

@Service
@Transactional
public class QuoteCrudService extends AbstractCrudService<Quote> {

    @Autowired
    public QuoteCrudService(QuoteRepository repository) {
	super(repository);
    }
}
