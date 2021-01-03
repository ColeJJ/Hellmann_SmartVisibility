package de.hsos.geois.ws2021.views.offer;

import java.util.stream.Stream;

import com.vaadin.flow.data.provider.Query;

import de.hsos.geois.ws2021.data.entity.Offer;
import de.hsos.geois.ws2021.data.service.DataService;
import de.hsos.geois.ws2021.data.service.OfferDataService;
import de.hsos.geois.ws2021.views.AbstractDataProvider;

public class OfferDataProvider extends AbstractDataProvider<Offer> {

	private static final long serialVersionUID = 6475084191083177928L;

	@Override
	protected String getFilterBase(Offer entity) {
		return entity.toString();
	}

	@Override
	protected DataService<Offer> getDataService() {
		return OfferDataService.getInstance();
	}

	@Override
	protected Stream<Offer> fetchFromBackEnd(Query<Offer, String> query) {
		return OfferDataService.getInstance().fetchOffers(this.filterText, query.getLimit(), query.getOffset(),
		        query.getSortOrders()).stream();
	}

	@Override
	protected int sizeInBackEnd(Query<Offer, String> query) {
		return OfferDataService.getInstance().countOffers(this.filterText);
	}
}
