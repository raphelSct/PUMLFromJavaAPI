package pumlFromJava;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

public class Methodes_Class extends Structures{
    Element classElem;
    public Methodes_Class(Element classElement) {
        this.classElem = classElement;
    }

    public void addElement() {
        for (Element enclosedElement : classElem.getEnclosedElements()) {
            if (enclosedElement.getKind() == ElementKind.METHOD) {
                affiche.append("  " + super.getVisibility(enclosedElement) + " " + enclosedElement.getSimpleName() + "():"+"\n");
            }
        }
    }
}
