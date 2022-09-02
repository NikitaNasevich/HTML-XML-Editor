package com.html.xml.editor;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class HTMLFileFilter extends FileFilter {
    public HTMLFileFilter() {
        super();
    }

    @Override
    public boolean accept(File file) {
       return file.isDirectory() || file.getName().toLowerCase().endsWith(".html") || file.getName().toLowerCase().endsWith(".htm");
    }

    @Override
    public String getDescription() {
        return "HTML и HTM файлы";
    }
}
