package de.hsos.geois.ws2021.views.offerPosition;

import java.util.stream.Stream;

import com.vaadin.flow.data.provider.Query;

import de.hsos.geois.ws2021.data.entity.OfferPosition;
import de.hsos.geois.ws2021.data.service.DataService;
import de.hsos.geois.ws2021.data.service.OfferPositionDataService;
import de.hsos.geois.ws2021.views.AbstractDataProvider;

public class OfferPositionDataProvider extends AbstractDataProvider<OfferPosition> {

	private static final long serialVersionUID = 2819892218407623706L;

	@Override
	protected String getFilterBase(OfferPosition entity) {
		return entity.toString();
	}

	@Override
	protected DataService<OfferPosition> getDataService() {
		return OfferPositionDataService.getInstance();
	}

	@Override
	protected Stream<OfferPosition> fetchFromBackEnd(Query<OfferPosition, String> query) {
		return OfferPositionDataService.getInstance().fetchOfferPositions(this.filterText, query.getLimit(), query.getOffset(),
		        query.getSortOrders()).stream();
	}

	@Override
	protected int sizeInBackEnd(Query<OfferPosition, String> query) {
		return OfferPositionDataService.getInstance().countOfferPositions(this.filterText);
	}
}
