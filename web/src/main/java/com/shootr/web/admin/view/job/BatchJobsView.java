package com.shootr.web.admin.view.job;

import com.shootr.web.admin.util.ConfirmDialog;
import com.shootr.web.batch.core.CustomJobLauncher;
import com.shootr.web.batch.core.JobResult;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@SpringView(name = BatchJobsView.VIEW_NAME)
public class BatchJobsView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "BatchJobsView";
    private static final Logger logger = LoggerFactory.getLogger(BatchJobsView.class);

    @Autowired
    Collection<Job> jobs;

    @Autowired
    private CustomJobLauncher jobLauncher;

    private Grid<Job> grid;

    private Button executeJobButton;

    private Label label;

    private ExecutorService jobExecutorService = Executors.newFixedThreadPool(2);

    private ConfirmDialog confirmationDialog;

    @PostConstruct
    void init() {
        logger.debug("I'm being created: {}", VIEW_NAME);
        setMargin(true);
        setSpacing(true);
        this.grid = new Grid<>();
        this.executeJobButton = new Button("Execute", FontAwesome.BOLT);
        this.label = new Label();
        label.setContentMode(ContentMode.TEXT);

        // build layout
        HorizontalLayout actions = new HorizontalLayout(executeJobButton);
        VerticalLayout mainLayout = new VerticalLayout(grid, actions, label);
        addComponent(mainLayout);

        // Configure layouts and components
        actions.setSpacing(true);
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);

        grid.setHeight(200, Unit.PIXELS);
        grid.setWidth(100, Unit.PERCENTAGE);
        grid.addColumn(Job::getName).setCaption("Name");
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        // Hook logic to components

        confirmationDialog = new ConfirmDialog("", "");

        executeJobButton.addClickListener(e -> {
            if (grid.getSelectedItems() != null && !grid.getSelectedItems().isEmpty()) {
                Job job = grid.getSelectedItems().iterator().next();
                confirmationDialog.setCaption("<p>Execute <b>" + job.getName() + "</b>?</p>");
                confirmationDialog.setCaptionAsHtml(true);
                getUI().addWindow(confirmationDialog);
            }
        });

        confirmationDialog.setYesListener(e -> {
            confirmationDialog.close();
            this.executeJobButton.setEnabled(false);
            Job job = grid.getSelectedItems().iterator().next();
            this.executeJob(job);
            label.setValue(String.format("Executing job %s...", job.getName()));
        });

        confirmationDialog.setCancelListener(e -> confirmationDialog.close());

        // Initialize grid
        listJobs();
    }

    private void executeJob(Job job) {
        jobExecutorService.submit(() -> {
            logger.info("call to execute " + job);
            JobResult result = jobLauncher.executeJob(job);

            logger.info("JobResult:" + result);

            getUI().access(new Runnable() {
                public void run() {

                    label.setValue(getLabelValue(result));
                    executeJobButton.setEnabled(true);
                }

                private String getLabelValue(JobResult result) {
                    StringBuilder val = new StringBuilder();
                    val.append(String.format("Job %s %s in %ds", result.getName(), result.getStatus(), TimeUnit.MILLISECONDS.toSeconds(result.getDuration())));
                    if (result.getExitDescription() != null) {
                        val.append(String.format(":\n %s", result.getExitDescription()));
                    }
                    return val.toString();
                }
            });
        });
    }

    private void listJobs() {
        grid.setItems(jobs);
    }

    @PreDestroy
    void destroy() {
        logger.debug("I'm being destroyed: {}", VIEW_NAME);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

}
