package de.hsos.geois.ws2021.data.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;

import de.hsos.geois.ws2021.data.EntityManagerHandler;
import de.hsos.geois.ws2021.data.entity.OfferPosition;

@Service
public class OfferPositionDataService extends DataService<OfferPosition> {


	private static final long serialVersionUID = 2353312583773322709L;

	private static OfferPositionDataService INSTANCE;
    
    public static final String SORT_ON_ID = "op.id";
	public static final String SORT_ON_OFFPONR = "op.offPoNr";
	public static final String SORT_ON_OFFERPONAME = "op.offerPoName";
	public static final String SORT_ON_QUANTITY = "op.quantity";
	
	private OfferPositionDataService() {
		super();
	}

	/**
	 * @return a reference to an example facade for OfferPosition objects.
	 */
	public static OfferPositionDataService getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new OfferPositionDataService();
		}
		return INSTANCE;
	}
	
	@Override
	public OfferPosition newEntity() {
		return new OfferPosition();
	}

	@Override
	protected String getByIdQuery() {
		return "SELECT op FROM OfferPosition op WHERE op.id = :id";
	}

	@Override
	protected String getAllQuery() {
		return "SELECT op FROM OfferPosition op ORDER BY op.offPoNr";
	}

	@Override
	protected Class<OfferPosition> getEntityClass() {
		return OfferPosition.class;
	}
	
	public int countOfferPositions(String filter) {
		String queryString = "SELECT count(op) FROM OfferPosition op WHERE (CONCAT(op.id, '') LIKE :filter "
				+ "OR LOWER(op.offPoNr) LIKE :filter "
				+ "OR LOWER(op.offerPoName) LIKE :filter "
				+ "OR LOWER(op.quantity) LIKE :filter)";
		return super.count(queryString, filter);
	}
	
	public Collection<OfferPosition> fetchOfferPositions(String filter, int limit, int offset, List<QuerySortOrder> sortOrders) {
		
		final String preparedFilter = prepareFilter(filter);
		
		// By default sort on name
		if (sortOrders == null || sortOrders.isEmpty()) {
			sortOrders = new ArrayList<>();
		    sortOrders.add(new QuerySortOrder(SORT_ON_OFFERPONAME, SortDirection.ASCENDING));
		}
		
		String sortString = getSortingString(sortOrders);
		
		String queryString = "SELECT op FROM OfferPosition op WHERE (CONCAT(op.id, '') LIKE :filter "
				+ "OR LOWER(op.offPoNr) LIKE :filter "
				+ "OR LOWER(op.offerPoName) LIKE :filter "
				+ "OR LOWER(op.quantity) LIKE :filter)" + sortString;
		
		return EntityManagerHandler.runInTransaction(em -> em.createQuery(queryString, OfferPosition.class)
				 .setParameter("filter", preparedFilter)
				 .setFirstResult(offset)
			     .setMaxResults(limit)
				 .getResultList());
	}
}
