package de.hsos.geois.ws2021.views;

import java.util.stream.Stream;

import com.vaadin.flow.data.provider.Query;

import de.hsos.geois.ws2021.data.entity.Person;
import de.hsos.geois.ws2021.data.service.DataService;
import de.hsos.geois.ws2021.data.service.PersonDataService;

public class PersonDataProvider extends AbstractDataProvider<Person> {
    
	private static final long serialVersionUID = -110064154580521089L;

	@Override
	protected String getFilterBase(Person entity) {
		return entity.toString();
	}

	@Override
	protected DataService<Person> getDataService() {
		return PersonDataService.getInstance();
	}

	@Override
	protected Stream<Person> fetchFromBackEnd(Query<Person, String> query) {
		return PersonDataService.getInstance().fetchPersons(this.filterText, query.getLimit(), query.getOffset(),
		        query.getSortOrders()).stream();
	}

	@Override
	protected int sizeInBackEnd(Query<Person, String> query) {
		return PersonDataService.getInstance().countPersons(this.filterText);
	}
}
