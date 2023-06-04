package pumlFromJava;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

public class Methodes_Interface extends Structures{

    Element interfaceElement;
    public Methodes_Interface(Element element){
        this.interfaceElement=element;
    }
    public void addInterface(){
        for (Element enclosedElement : interfaceElement.getEnclosedElements()) {
            if (enclosedElement.getKind() == ElementKind.METHOD) {
                affiche.append("  " + getVisibility(enclosedElement) + " " + enclosedElement.getSimpleName() +"()\n");
            }
        }
    }
}
