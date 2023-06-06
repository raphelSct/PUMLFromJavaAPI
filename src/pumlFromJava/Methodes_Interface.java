package pumlFromJava;

import javax.lang.model.element.*;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

public class Methodes_Interface extends Structures{

    Element interfaceElement;
    public Methodes_Interface(Element element){
        this.interfaceElement=element;
    }
    public void addInterface(){
        for (Element enclosedElement : interfaceElement.getEnclosedElements()) {
            if (enclosedElement.getKind() == ElementKind.METHOD) {
                if(enclosedElement.getModifiers().contains(Modifier.STATIC)) {

                    affiche.append("  " + getVisibility(enclosedElement) + "{static} " + enclosedElement.getSimpleName() + "(" );
                    Element_Methodes em =new Element_Methodes(enclosedElement);
                    em.addTypes();

                }
                else{
                    affiche.append("  " + getVisibility(enclosedElement) + "  " + enclosedElement.getSimpleName() + "(");
                    Element_Methodes em =new Element_Methodes(enclosedElement);
                    em.addTypes();

                }
            }
        }
    }

}
