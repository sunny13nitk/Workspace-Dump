package pp.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import pp.springboot.entity.Customer;

@RepositoryRestResource(
        path = "members"
)
public interface CustomerRepository extends JpaRepository<Customer, Integer>
{
	
}
