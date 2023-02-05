package frames;

import models.Language;

public class BarLanguages {
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
