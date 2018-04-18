package control.self.igor.dailywisdom.repository.abstraction;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import control.self.igor.dailywisdom.entity.Category;

public interface CategoryRepository
	extends PagingAndSortingRepository<Category, Long>, JpaSpecificationExecutor<Category> {

}
