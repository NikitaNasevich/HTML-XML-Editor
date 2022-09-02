package com.html.xml.editor;

import com.html.xml.editor.listeners.FrameListener;
import com.html.xml.editor.listeners.TabbedPaneChangeListener;
import com.html.xml.editor.listeners.UndoListener;

import javax.swing.*;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame implements ActionListener {
    private Controller controller;
    private final JTabbedPane tabbedPane = new JTabbedPane();
    private final JTextPane htmlTextPane = new JTextPane();
    private final JEditorPane plainTextPane = new JEditorPane();
    private final UndoManager undoManager = new UndoManager();
    private final UndoListener undoListener = new UndoListener(undoManager);

    public View() {
        try {
            UIManager.setLookAndFeel("Default");
        } catch (ClassNotFoundException e) {
            ExceptionHandler.log(e);
        } catch (InstantiationException e) {
            ExceptionHandler.log(e);
        } catch (IllegalAccessException e) {
            ExceptionHandler.log(e);
        } catch (UnsupportedLookAndFeelException e) {
            ExceptionHandler.log(e);
        }
    }

    public UndoListener getUndoListener() {
        return undoListener;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void init() {
        initGui();
        FrameListener listener = new FrameListener(this);
        this.addWindowListener(listener);
        this.setVisible(true);
    }

    public void resetUndo() {
        undoManager.discardAllEdits();
    }

    public void undo() {
        try {
            undoManager.undo();
        } catch (Exception e) {
            ExceptionHandler.log(e);
        }
    }

    public void redo() {
        try {
            undoManager.redo();
        } catch (Exception e) {
            ExceptionHandler.log(e);
        }
    }

    public boolean canUndo() {
        return undoManager.canUndo();
    }

    public boolean canRedo() {
        return undoManager.canRedo();
    }

    public boolean isHtmlTabSelected() {
        return tabbedPane.getSelectedIndex() == 0;
    }

    public void selectedTabChanged() {
        if (tabbedPane.getSelectedIndex() == 0) {
            controller.setPlainText(plainTextPane.getText());
        } else {
            plainTextPane.setText(controller.getPlainText());
        }
        resetUndo();
    }

    public void selectHtmlTab() {
        tabbedPane.setSelectedIndex(0);
        resetUndo();
    }

    public void update() {
        htmlTextPane.setDocument(controller.getDocument());
    }

    public void showAbout() {
        JOptionPane.showMessageDialog(getContentPane(), "Version 1.0.0", " dev. - Nikita Nasevich", JOptionPane.INFORMATION_MESSAGE);
    }

    public void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        MenuHelper.initFileMenu(this, menuBar);
        MenuHelper.initEditMenu(this, menuBar);
        MenuHelper.initStyleMenu(this, menuBar);
        MenuHelper.initAlignMenu(this, menuBar);
        MenuHelper.initColorMenu(this, menuBar);
        MenuHelper.initFontMenu(this, menuBar);
        MenuHelper.initHelpMenu(this, menuBar);
        this.getContentPane().add(menuBar, BorderLayout.NORTH);
    }

    public void initEditor() {
        htmlTextPane.setContentType("text/html");
        JScrollPane jScrollPane = new JScrollPane(htmlTextPane);
        tabbedPane.addTab("HTML", jScrollPane);
        jScrollPane = new JScrollPane(plainTextPane);
        tabbedPane.addTab("Текст", jScrollPane);
        tabbedPane.setPreferredSize(new Dimension());
        TabbedPaneChangeListener listener = new TabbedPaneChangeListener(this);
        tabbedPane.addChangeListener(listener);
        this.getContentPane().add(tabbedPane, BorderLayout.CENTER);
    }

    public void initGui() {
        initMenuBar();
        initEditor();
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();
        switch (command) {
            case "Новый":
                controller.createNewDocument();
                break;
            case "Открыть":
                controller.openDocument();
                break;
            case "Сохранить":
                controller.saveDocument();
                break;
            case "Сохранить как...":
                controller.saveDocumentAs();
                break;
            case "Выход":
                controller.exit();
                break;
            case "О программе":
                showAbout();
                break;
        }
    }

    public void exit() {
        controller.exit();
    }
}
