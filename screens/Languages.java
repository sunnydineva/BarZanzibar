package screens;

import models.Language;

public interface  Languages {
    void englishLanguage();
    void bulgarianLanguage();
     default void languageSwitch(Language language) {
        if (language == Language.BULGARIAN) {
            bulgarianLanguage();
        } else englishLanguage();
    }
}


