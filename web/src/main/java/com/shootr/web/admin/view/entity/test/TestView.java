package com.shootr.web.admin.view.entity.test;

import com.shootr.web.admin.util.NotificationBuilder;
import com.shootr.web.core.dao.domain.Test;
import com.shootr.web.core.service.TestService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@SpringView(name = TestView.VIEW_NAME)
public class TestView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "TestView";
    private static final Logger logger = LoggerFactory.getLogger(TestView.class);

    @Autowired
    private TestService service;

    private TextField searchField;

    private TextField newWordField;
    private Button addNewWordButton;
    private Button removeWordButton;
    private Grid<Test> grid;


    @PostConstruct
    void init() {
        logger.debug("I'm being created: {}", VIEW_NAME);
        setMargin(true);
        setSpacing(true);

        this.searchField = new TextField();
        this.newWordField = new TextField();
        this.addNewWordButton = new Button("Add", FontAwesome.PLUS);
        this.removeWordButton = new Button("Remove", FontAwesome.MINUS);
        this.grid = new Grid<>();

        // build layout
        HorizontalLayout searchLayout = new HorizontalLayout(searchField);
        HorizontalLayout newWordLayout = new HorizontalLayout(newWordField, addNewWordButton, removeWordButton);
        VerticalLayout mainLayout = new VerticalLayout(searchLayout, grid, newWordLayout);
        addComponent(mainLayout);

        // Configure layouts and components
        searchLayout.setSpacing(true);
        newWordLayout.setSpacing(true);
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);

        grid.setHeight(400, Unit.PIXELS);
        grid.setWidth(100, Unit.PERCENTAGE);
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.addColumn(Test::getId).setCaption("id");
        grid.addColumn(Test::getName).setCaption("Name");


        searchField.setPlaceholder("Search");
        newWordField.setPlaceholder("Enter new");

        // Hook logic to components

        // Replace listing with filtered content when user changes searchField
        searchField.addValueChangeListener(e -> reloadGrid(e.getValue()));

        removeWordButton.addClickListener(e -> {
            if (grid.getSelectedItems() != null && !grid.getSelectedItems().isEmpty()) {
                Test test = grid.getSelectedItems().iterator().next();
                service.delete(test.getId());
                reloadGrid(null);
            }
        });

        // Instantiate and edit new User the new button is clicked
        addNewWordButton.addClickListener(e -> {
            if (!newWordField.isEmpty()) {
                String name = newWordField.getValue();
                Test test = Test.builder().name(name).build();
                test = service.save(test);
                if (test.getId() != null) {
                    newWordField.clear();
                    reloadGrid(null);
                } else {
                    NotificationBuilder.aErrorNotification("Error");
                }
            }
        });

        // Initialize listing
        reloadGrid(null);
    }

    private void reloadGrid(String name) {
        List<Test> content = (StringUtils.isEmpty(name)) ?
                service.findAll() :
                service.findByName(name);
        logger.debug("reload grid found {} entities", content.size());
        grid.setItems(content);
    }

    @PreDestroy
    void destroy() {
        logger.debug("I'm being destroyed: {}", VIEW_NAME);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
