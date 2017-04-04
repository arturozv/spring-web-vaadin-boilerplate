package com.shootr.web.admin.util;


import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ConfirmDialog extends Window {

    private Button yes;
    private Button cancel;

    public ConfirmDialog(String caption, String subCaption) {
        this.setCaption(caption);
        this.setModal(true);
        this.setResizable(false);
        this.setWidth(300, Unit.PIXELS);

        VerticalLayout subContent = new VerticalLayout();
        subContent.setMargin(true);
        this.setContent(subContent);

        // Put some components in it
        subContent.addComponent(new Label(subCaption));

        yes = new Button("yes");
        cancel = new Button("cancel");

        HorizontalLayout actions = new HorizontalLayout(cancel, yes);
        actions.setSizeFull();
        actions.setComponentAlignment(yes, Alignment.MIDDLE_RIGHT);
        actions.setSpacing(true);

        subContent.addComponent(actions);

        this.center();
    }

    public void setYesListener(Button.ClickListener clickListener) {
        yes.addClickListener(clickListener);
    }

    public void setCancelListener(Button.ClickListener clickListener) {
        cancel.addClickListener(clickListener);
    }

}