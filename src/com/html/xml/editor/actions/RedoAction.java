package com.html.xml.editor.actions;

import com.html.xml.editor.View;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class RedoAction extends AbstractAction {
    private View view;

    public RedoAction(View view) {
        super();
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        view.redo();
    }
}
