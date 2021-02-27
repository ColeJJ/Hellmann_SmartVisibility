package de.hsos.geois.ws2021.data.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;

import de.hsos.geois.ws2021.data.EntityManagerHandler;
import de.hsos.geois.ws2021.data.entity.Offer;

@Service
public class OfferDataService extends DataService<Offer> {

	private static final long serialVersionUID = -6353052725237675682L;

	private static OfferDataService INSTANCE;
    
    public static final String SORT_ON_ID = "o.id";
	public static final String SORT_ON_OFFNR = "o.offNr";
	public static final String SORT_ON_CUSTOMERFIRSTNAME = "o.customerFirstName";
	public static final String SORT_ON_CUSTOMERLASTNAME = "o.customerLastName";
	public static final String SORT_ON_COMPANYNAME = "o.companyName";
	
	private OfferDataService() {
		super();
	}

	/**
	 * @return a reference to an example facade for Offer objects.
	 */
	public static OfferDataService getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new OfferDataService();
		}
		return INSTANCE;
	}
	
	@Override
	public Offer newEntity() {
		return new Offer();
	}

	@Override
	protected String getByIdQuery() {
		return "SELECT o FROM Offer o WHERE o.id = :id";
	}

	@Override
	protected String getAllQuery() {
		return "SELECT o FROM Offer o ORDER BY o.offNr";
	}

	@Override
	protected Class<Offer> getEntityClass() {
		return Offer.class;
	}
	
	public int countOffers(String filter) {
		String queryString = "SELECT count(o) FROM Offer o WHERE (CONCAT(o.id, '') LIKE :filter "
				+ "OR LOWER(o.offNr) LIKE :filter "
				+ "OR LOWER(o.companyName) LIKE :filter "
				+ "OR LOWER(o.customerFirstName) LIKE :filter "
				+ "OR LOWER(o.customerLastName) LIKE :filter)";
		return super.count(queryString, filter);
	}
	
	public Collection<Offer> fetchOffers(String filter, int limit, int offset, List<QuerySortOrder> sortOrders) {
		
		final String preparedFilter = prepareFilter(filter);
		
		// By default sort on name
		if (sortOrders == null || sortOrders.isEmpty()) {
			sortOrders = new ArrayList<>();
		    sortOrders.add(new QuerySortOrder(SORT_ON_OFFNR, SortDirection.ASCENDING));
		}
		
		String sortString = getSortingString(sortOrders);
		
		String queryString = "SELECT o FROM Offer o WHERE (CONCAT(o.id, '') LIKE :filter "
				+ "OR LOWER(o.offNr) LIKE :filter "
				+ "OR LOWER(o.companyName) LIKE :filter "
				+ "OR LOWER(o.customerFirstName) LIKE :filter "
				+ "OR LOWER(o.customerLastName) LIKE :filter)" + sortString;
		
		return EntityManagerHandler.runInTransaction(em -> em.createQuery(queryString, Offer.class)
				 .setParameter("filter", preparedFilter)
				 .setFirstResult(offset)
			     .setMaxResults(limit)
				 .getResultList());
	}
}
