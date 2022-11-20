package frames;


import models.Language;
import screens.BasePanel;
import screens.LoginPanel;

import javax.swing.*;

public class BarLanguages {
    public LoginPanel loginPanel;
    public BasePanel basePanel;
    public BarFrame frame;

    public BarLanguages(BarFrame frame) {
        this.frame = frame;

        System.out.println("Frame is in " + frame.language);
    }

    public void englishLanguageAction() {
        frame.language = Language.ENGLISH;
        System.out.println("button " + frame.language);
    }

    public void bulgarianLanguageActon() {
        frame.language = Language.BULGARIAN;
        System.out.println("button " + frame.language);

    }
}
