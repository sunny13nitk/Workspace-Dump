package pp.springboot.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pp.springboot.dao.intf.ICustomerDAO;
import pp.springboot.entity.Customer;

@Repository
public class CustomerDAOJPAImpl implements ICustomerDAO
{
	
	@Autowired
	private EntityManager entMgr;
	
	@SuppressWarnings(
	    "unchecked"
	)
	public List<Customer> findAll(
	)
	{
		List<Customer> customers = null;
		
		// javax.persistence Query this time not hibernate query
		Query qCus = entMgr.createQuery("from Customer", Customer.class);
		if (qCus != null)
		{
			customers = qCus.getResultList();
		}
		
		return customers;
	}
	
	public Customer findbyId(
	        int id
	)
	{
		return entMgr.find(Customer.class, id);
	}
	
	public void save(
	        Customer newCustomer
	)
	{
		if (newCustomer != null && entMgr != null)
		{
			
			Customer dbCustomer = entMgr.merge(newCustomer);
			newCustomer.setId(dbCustomer.getId());
		}
		
	}
	
	public void deleteById(
	        int id
	)
	{
		Query qCus = entMgr.createQuery("delete from Customer where id =: id");
		if (qCus != null)
		{
			qCus.setParameter("id", id);
			qCus.executeUpdate();
		}
	}
	
}
