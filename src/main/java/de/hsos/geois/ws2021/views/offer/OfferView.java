package de.hsos.geois.ws2021.views.offer;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;


import de.hsos.geois.ws2021.data.entity.Offer;
import de.hsos.geois.ws2021.data.service.OfferDataService;
import de.hsos.geois.ws2021.views.MainView;

@Route(value = "offer", layout = MainView.class)
@PageTitle("MyDeviceManager")
@CssImport("./styles/views/mydevicemanager/my-device-manager-view.css")
@RouteAlias(value = "offer", layout = MainView.class)
public class OfferView extends Div {

    private static final long serialVersionUID = 4740201357551960590L;

    private Grid<Offer> grid;

    private TextField customerNr = new TextField();
    private TextField customerName = new TextField();
    private TextField customerAddresse = new TextField();
    private BigDecimalField purchasePrice = new BigDecimalField();
    private BigDecimalField salesPrice = new BigDecimalField();


    // TODO: Refactore these buttons in a separate (abstract) form class
    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private Binder<Offer> binder;

    private Offer currentOffer = new Offer();

    private OfferDataService offerService;

    public OfferView() {
        setId("my-device-manager-view");
        this.offerService = OfferDataService.getInstance();
        // Configure Grid
        grid = new Grid<>(Offer.class);
        grid.setColumns("offNr", "customerName", "customerNr");
        grid.setDataProvider(new OfferDataProvider());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                Offer offerFromBackend = offerService.getById(event.getValue().getId());
                // when a row is selected but the data is no longer available, refresh grid
                if (offerFromBackend != null) {
                    populateForm(offerFromBackend	);
                } else {
                    refreshGrid();
                }
            } else {
                clearForm();
            }
        });

        // Configure Form
        binder = new Binder<>(Offer.class);

        // Bind fields. This where you'd define e.g. validation rules
        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.currentOffer == null) {
                    this.currentOffer = new Offer();
                }
                binder.writeBean(this.currentOffer);
                offerService.update(this.currentOffer);
                clearForm();
                refreshGrid();
                Notification.show("Offer details stored.");
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the offer details.");
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
        addFormItem(editorDiv, formLayout, customerNr, "Customer Number");
        addFormItem(editorDiv, formLayout, customerName, "Customer name");
        addFormItem(editorDiv, formLayout, customerAddresse, "Customer Addresse");
        addFormItem(editorDiv, formLayout, purchasePrice, "Purchase price");
        addFormItem(editorDiv, formLayout, salesPrice, "Sales price");
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

    private void populateForm(Offer value) {
        this.currentOffer= value;
        binder.readBean(this.currentOffer);
    }
}
