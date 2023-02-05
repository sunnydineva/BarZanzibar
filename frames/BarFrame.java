package frames;

import models.Language;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BarFrame extends JFrame {

    public Language language;
    public BarRouter router;
    public BarLanguages currentLanguage;
    public BarDataProvider dataProvider;

    public BarFrame(){
        super("Bar ZanzibarS");
        setTitle(funTitle());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(1000, 700);
        ImageIcon img = new ImageIcon("icon_z.jpg");
        this.setIconImage(img.getImage());
        this.language = Language.BULGARIAN;
        dataProvider = new BarDataProvider();
        router = new BarRouter(this);
        currentLanguage = new BarLanguages(this);
        this.setLocationRelativeTo(null);
        router.showLogin(); //shows the first screen
    }

    String funTitle() {
        Random random = new Random();
        String funTitle1 = "Alcohol doesn't get you drunk. Bartenders do.";
        String funTitle2 = "Beach: Fun in the sun.";
        String funTitle3 = "Have a splashing good time at the beach.";
        String funTitle4 = " Life's a beach and I'm just playing in the sand.";
        String funTitle5 = "Make a Splash, Go to the beach.";
        String funTitle6 = "Relax I'm a bartender";
        String funTitle7 = "A bartender is just a pharmacist with a limited inventory";
        String funTitle8 = "At the beach time you enjoyed wasting, is not time wasted.";
        String funTitle9 = "Lifes a beach and then you move to one.";
        String funTitle10 = "My life is like a stroll on the beach...as near to the edge as I can go.";
        List<String> funTitles = Arrays.asList(funTitle1, funTitle2, funTitle3, funTitle4, funTitle5,
                funTitle6, funTitle7, funTitle8, funTitle9, funTitle10);
        int index = random.nextInt(funTitles.size());
        return funTitles.get(index);
    }
}
