package pp.springboot.service.intf;

import java.util.List;

import pp.springboot.entity.Customer;

public interface ICustomerSrv
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
