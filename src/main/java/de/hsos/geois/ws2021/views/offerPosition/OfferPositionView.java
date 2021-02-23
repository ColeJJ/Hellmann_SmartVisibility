package de.hsos.geois.ws2021.views.offerPosition;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import de.hsos.geois.ws2021.data.entity.Offer;
import de.hsos.geois.ws2021.data.entity.OfferPosition;
import de.hsos.geois.ws2021.data.service.CustomerDataService;
import de.hsos.geois.ws2021.data.service.OfferDataService;
import de.hsos.geois.ws2021.data.service.OfferPositionDataService;
import de.hsos.geois.ws2021.views.MainView;

@Route(value = "offerposition", layout = MainView.class)
@PageTitle("MyDeviceManager")
@CssImport("./styles/views/mydevicemanager/my-device-manager-view.css")
@RouteAlias(value = "offerposition", layout = MainView.class)
public class OfferPositionView extends Div {

    private static final long serialVersionUID = 4740201357551960590L;

    private Grid<OfferPosition> grid;

    private TextField deviceTyp = new TextField();
    private TextField quantity = new TextField();
    private TextField price = new TextField();

    private ComboBox<Offer> offer = new ComboBox<Offer>();


    // TODO: Refactore these buttons in a separate (abstract) form class
    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private Binder<OfferPosition> binder;

    private OfferPosition currentOfferPosition = new OfferPosition();

    private OfferPositionDataService offerPositionService;

    public OfferPositionView() {
        setId("my-device-manager-view");
        this.offerPositionService = OfferPositionDataService.getInstance();
        // Configure Grid
        grid = new Grid<>(OfferPosition.class);
        grid.setColumns("deviceTyp", "quantity", "price");
        grid.setDataProvider(new OfferPositionDataProvider());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                OfferPosition offerPositionFromBackend = offerPositionService.getById(event.getValue().getId());
                // when a row is selected but the data is no longer available, refresh grid
                if (offerPositionFromBackend != null) {
                    populateForm(offerPositionFromBackend);
                } else {
                    refreshGrid();
                }
            } else {
                clearForm();
            }
        });

        // Configure Form
        binder = new Binder<>(OfferPosition.class);

        // Bind fields. This where you'd define e.g. validation rules
        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.currentOfferPosition == null) {
                    this.currentOfferPosition = new OfferPosition();
                }
                binder.writeBean(this.currentOfferPosition);
                offerPositionService.update(this.currentOfferPosition);
                clearForm();
                refreshGrid();
                Notification.show("Offerposition details stored.");
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the offerposition details.");
            }
        });
        
     // add offers to combobox offers
        offer.setItems(OfferDataService.getInstance().getAll());
        
        offer.addValueChangeListener(event -> {
        	if (event.isFromClient() && event.getValue()!=null) {
        		event.getValue().addOfferPosition(this.currentOfferPosition);
        		OfferDataService.getInstance().save(event.getValue());
        		this.currentOfferPosition.setOffer(event.getValue());
        		try {
					binder.writeBean(this.currentOfferPosition);
				} catch (ValidationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                this.currentOfferPosition = offerPositionService.update(this.currentOfferPosition);
        	}
        });

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setId("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setId("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        addFormItem(editorDiv, formLayout, deviceTyp, "Device Typ");
        addFormItem(editorDiv, formLayout, quantity, "Quantity");
        addFormItem(editorDiv, formLayout, price, "Price");
        addFormItem(editorDiv, formLayout, offer, "Offer");
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setId("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout.setSpacing(true);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void addFormItem(Div wrapper, FormLayout formLayout, AbstractField field, String fieldName) {
        formLayout.addFormItem(field, fieldName);
        wrapper.add(formLayout);
        field.getElement().getClassList().add("full-width");
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(OfferPosition value) {
        this.currentOfferPosition= value;
        binder.readBean(this.currentOfferPosition);
    }
}
