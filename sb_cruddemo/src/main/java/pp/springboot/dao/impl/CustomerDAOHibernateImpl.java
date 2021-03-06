package pp.springboot.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pp.springboot.dao.intf.ICustomerDAO;
import pp.springboot.entity.Customer;

@Repository
public class CustomerDAOHibernateImpl implements ICustomerDAO
{
	@Autowired
	private EntityManager entMgr;
	
	public List<Customer> findAll(
	)
	{
		List<Customer> customers = null;
		
		// Get Session handle
		
		Session sess = entMgr.unwrap(Session.class);
		if (sess != null)
		{
			// Create the Query - Traditional Hibernate Way
			Query<Customer> qCus = sess.createQuery("from Customer", Customer.class);
			if (qCus != null)
			{
				customers = qCus.getResultList();
			}
		}
		
		return customers;
	}
	
	public Customer findbyId(
	        int id
	)
	{
		Customer cust = null;
		
		Session sess = entMgr.unwrap(Session.class);
		if (sess != null)
		{
			// Create the Query - Traditional Hibernate Way
			cust = sess.get(Customer.class, id);
		}
		
		return cust;
	}
	
	public void save(
	        Customer newCustomer
	)
	{
		if (newCustomer != null)
		{
			Session sess = entMgr.unwrap(Session.class);
			if (sess != null)
			{
				sess.clear();
				// Create the Query - Traditional Hibernate Way
				sess.saveOrUpdate(newCustomer);
			}
		}
		
	}
	
	@SuppressWarnings(
	    "unchecked"
	)
	public void deleteById(
	        int id
	)
	{
		if (id > 0)
		{
			Session sess = entMgr.unwrap(Session.class);
			if (sess != null)
			{
				// Create the Query - Traditional Hibernate Way
				Query<Customer> qCus = sess.createQuery("delete from Customer where id =: id");
				if (qCus != null)
				{
					qCus.setParameter("id", id);
					qCus.executeUpdate();
				}
			}
		}
	}
	
}
