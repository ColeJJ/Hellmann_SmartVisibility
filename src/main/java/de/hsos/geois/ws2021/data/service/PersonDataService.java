package de.hsos.geois.ws2021.data.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;

import de.hsos.geois.ws2021.data.EntityManagerHandler;
import de.hsos.geois.ws2021.data.entity.Person;

@Service
public class PersonDataService extends DataService<Person> {

	private static final long serialVersionUID = 5072749097789090163L;

	private static PersonDataService INSTANCE;
    
    public static final String SORT_ON_ID = "p.id";
	public static final String SORT_ON_FIRSTNAME = "p.firstName";
	public static final String SORT_ON_LASTNAME = "p.lastName";
	
	private PersonDataService() {
		super();
	}

	/**
	 * @return a reference to an example facade for Person objects.
	 */
	public static PersonDataService getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new PersonDataService();
		}
		return INSTANCE;
	}
	
	@Override
	public Person newEntity() {
		return new Person();
	}

	@Override
	protected String getByIdQuery() {
		return "SELECT p FROM Person p WHERE p.id = :id";
	}

	@Override
	protected String getAllQuery() {
		return "SELECT p FROM Person p ORDER BY p.lastName";
	}

	@Override
	protected Class<Person> getEntityClass() {
		return Person.class;
	}
	
	public int countPersons(String filter) {
		String queryString = "SELECT count(p) FROM Person p WHERE (CONCAT(p.id, '') LIKE :filter "
				+ "OR LOWER(p.firstName) LIKE :filter "
				+ "OR LOWER(p.lastName) LIKE :filter)";
		return super.count(queryString, filter);
	}
	
	public Collection<Person> fetchPersons(String filter, int limit, int offset, List<QuerySortOrder> sortOrders) {
		
		final String preparedFilter = prepareFilter(filter);
		
		// By default sort on StartDate
		if (sortOrders == null || sortOrders.isEmpty()) {
			sortOrders = new ArrayList<>();
		    sortOrders.add(new QuerySortOrder(SORT_ON_LASTNAME, SortDirection.DESCENDING));
		}
		
		String sortString = getSortingString(sortOrders);
		
		String queryString = "SELECT p FROM Person p WHERE (CONCAT(p.id, '') LIKE :filter "
				+ "OR LOWER(p.firstName) LIKE :filter "
				+ "OR LOWER(p.lastName) LIKE :filter)" + sortString;
		
		return EntityManagerHandler.runInTransaction(em -> em.createQuery(queryString, Person.class)
				 .setParameter("filter", preparedFilter)
				 .setFirstResult(offset)
			     .setMaxResults(limit)
				 .getResultList());
	}



}
