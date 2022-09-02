package com.html.xml.editor.actions;

import com.html.xml.editor.View;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class UndoAction extends AbstractAction {
    private final View view;

    public UndoAction(View view) {
        super();
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        view.undo();
    }
}
