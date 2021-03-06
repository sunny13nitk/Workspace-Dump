package pp.springboot.dao.intf;

import java.util.List;

import pp.springboot.entity.Customer;

public interface ICustomerDAO
{
	public List<Customer> findAll(
	);
	
	public Customer findbyId(
	        int id
	);
	
	public void save(
	        Customer newCustomer
	);
	
	public void deleteById(
	        int id
	);
}
