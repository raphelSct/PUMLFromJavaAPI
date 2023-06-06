package pumlFromJava;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;

public class Methodes_Enum extends  Structures{

    Element enumElement;
    public Methodes_Enum(Element element){
        this.enumElement=element;
    }
    public void addElement(){
        for (Element enclosedElement : this.enumElement.getEnclosedElements()) {
            if (enclosedElement.getKind() == ElementKind.ENUM_CONSTANT)
            {
                if(enclosedElement.getModifiers().contains(Modifier.STATIC)){
                    affiche.append("  " + getVisibility(enclosedElement) + "{static} "+ enclosedElement.getSimpleName() + "\n");
                }
                else{
                    affiche.append("  " + getVisibility(enclosedElement) + " "+ enclosedElement.getSimpleName() + "\n");
                }

            }
        }
    }
}
