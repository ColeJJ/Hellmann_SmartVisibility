package de.hsos.geois.ws2021.views.offerPosition;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import de.hsos.geois.ws2021.data.entity.Offer;
import de.hsos.geois.ws2021.data.entity.OfferPosition;
import de.hsos.geois.ws2021.data.service.OfferDataService;
import de.hsos.geois.ws2021.data.service.OfferPositionDataService;
import de.hsos.geois.ws2021.views.MainView;

@Route(value = "offerposition", layout = MainView.class)
@PageTitle("Offerposition")
@CssImport("./styles/views/mydevicemanager/my-device-manager-view.css")
@RouteAlias(value = "offerposition", layout = MainView.class)
public class OfferPositionView extends Div {

    private static final long serialVersionUID = 4740201357551960590L;

    private Grid<OfferPosition> grid;

    private TextField deviceTyp = new TextField();
    private IntegerField quantity = new IntegerField();
    private BigDecimalField price = new BigDecimalField();
    private BigDecimalField totalPrice = new BigDecimalField();

    private ComboBox<Offer> offer = new ComboBox<Offer>();


    // TODO: Refactore these buttons in a separate (abstract) form class
    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private Binder<OfferPosition> binder;

    private OfferPosition currentOfferPosition = new OfferPosition();

    private OfferPositionDataService offerPositionService;

    private boolean givenObject = false;


    public OfferPositionView() {
        setId("my-device-manager-view");
        this.offerPositionService = OfferPositionDataService.getInstance();
        // Configure Grid
        grid = new Grid<>(OfferPosition.class);
        grid.setColumns("offer", "deviceTyp", "quantity", "price", "totalPrice");
        grid.setDataProvider(new OfferPositionDataProvider());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                OfferPosition offerPositionFromBackend = offerPositionService.getById(event.getValue().getId());
                this.givenObject = true;
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
            	//update total price when price or quantity changed
            	totalPrice.setValue(new BigDecimal(quantity.getValue()).multiply(price.getValue()));
            	
                binder.writeBean(this.currentOfferPosition);
                //binding those objects creates and saves the object as well
                this.connectWithOffer();          
                clearForm();
                refreshGrid();
                Notification.show("Offerposition details stored.");
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the offerposition details.");
            }
            catch(NullPointerException npE) {
            	Notification.show("Offerpositon could not be saved. Please choose an Offer!");
            }
        });
        
     // add offers to combobox offers
        offer.setItems(OfferDataService.getInstance().getAll());
        
        //Listener only takes effect when currenOfferPostion is already given.. for new OfferPostions function connectWithOffer is used
        offer.addValueChangeListener(event -> {
        	if (event.isFromClient() && event.getValue()!=null) {
                if (this.currentOfferPosition == null) { this.currentOfferPosition = new OfferPosition(); }
        		this.currentOfferPosition.setOffer(event.getValue());
        	}
        }); 
        
        //calculate total price      
        price.addValueChangeListener(e -> {
        	BigDecimal total;
        	if (e.getValue() == null) {
                total = BigDecimal.ZERO;
            } else {
                total = e.getValue().multiply(new BigDecimal(quantity.getValue()));
            }
            totalPrice.setValue(total);
        });
        
        //Preis soll sich auch direkt ändern, wenn nur Quantity geändert wird. Funktioniert so leider noch nicht.
//        quantity.addValueChangeListener(e -> {
//        	BigDecimal total;
//        	if (e.getValue() == null) {
//                total = BigDecimal.ZERO;
//            } else {
//                total = new BigDecimal(quantity.getValue()).multiply(price.getValue());
//                
//            }
//            totalPrice.setValue(total);
//        });

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
        
        offer.setRequired(true);
        offer.setErrorMessage("Please choose Offer!");
        quantity.setHasControls(true);
        quantity.setMin(1);
        price.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
        price.setPrefixComponent(new Icon(VaadinIcon.EURO));
        totalPrice.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
        totalPrice.setPrefixComponent(new Icon(VaadinIcon.EURO));
        totalPrice.setReadOnly(true);
        


        FormLayout formLayout = new FormLayout();
        addFormItem(editorDiv, formLayout, offer, "Offer");
        addFormItem(editorDiv, formLayout, deviceTyp, "Device Typ");
        addFormItem(editorDiv, formLayout, quantity, "Quantity");
        addFormItem(editorDiv, formLayout, price, "Price");
        addFormItem(editorDiv, formLayout, totalPrice, "Total Price");
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

    private void connectWithOffer() {
        if (this.givenObject) {
            //remove OfferPosition from Collection -> this.currentOfferPosition.getID()
            boolean ok = this.currentOfferPosition.getOffer().addOfferPosition(this.currentOfferPosition);
            //remove If: https://www.baeldung.com/java-collection-remove-elements
            boolean ok = this.currenOfferPostion.getOffer().getOfferpositions().removeIf(e -> e.getID(this.currentOfferPosition.getID()));
            boolean ok = this.currentOfferPosition.getOffer().addOfferPosition(this.currentOfferPosition);
            //replace: https://howtodoinjava.com/java/collections/arraylist/replace-element-arraylist/
            boolean ok = this.currenOfferPostion.getOffer().getOfferpositions().set(this.currenOfferPostion, this.currenOfferPostion);
        } else {
            boolean ok = this.currentOfferPosition.getOffer().addOfferPosition(this.currentOfferPosition);
        }
        if (ok) { OfferDataService.getInstance().update(this.currentOfferPosition.getOffer()); }
    }
}
