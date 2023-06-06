package pumlFromJava;

import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.type.ArrayType;

import javax.lang.model.util.Types;
import java.lang.reflect.Type;

public class Methodes_Class extends Structures{
    Element classElem;
    public Methodes_Class(Element classElement) {
        this.classElem = classElement;
    }

    public void addElement() {


        for (Element enclosedElement : classElem.getEnclosedElements()) {

            if (enclosedElement.getKind() == ElementKind.METHOD) {
                if(enclosedElement.getModifiers().contains(Modifier.STATIC)){

                    affiche.append("  " + getVisibility(enclosedElement) + "{static} " + enclosedElement.getSimpleName() + "(" );
                    Element_Methodes em =new Element_Methodes(enclosedElement);
                    em.addTypes();

                }
                else{

                    affiche.append("  " + getVisibility(enclosedElement) + " " + enclosedElement.getSimpleName() + "(" );
                    Element_Methodes em =new Element_Methodes(enclosedElement);
                    em.addTypes();

                }

            }
            else if (enclosedElement.getKind() == ElementKind.FIELD) {
                if(enclosedElement.getModifiers().contains(Modifier.STATIC)){
                    affiche.append("  " + getVisibility(enclosedElement) + "{static} "+ enclosedElement.getSimpleName() + "\n");
                }
                else{
                    affiche.append("  " + getVisibility(enclosedElement) + " "+ enclosedElement.getSimpleName() + "\n");
                }
            }
            else if (enclosedElement.getKind() == ElementKind.CONSTRUCTOR) {
                if(enclosedElement.getModifiers().contains(Modifier.STATIC)){

                    affiche.append("  " + getVisibility(enclosedElement) + "{static} <<create>>"+ enclosedElement.getEnclosingElement().getSimpleName() +"(");
                    Element_Methodes em =new Element_Methodes(enclosedElement);
                    em.addTypes();

                }
                else{
                    affiche.append("  " + getVisibility(enclosedElement) + " <<create>> " + enclosedElement.getEnclosingElement().getSimpleName() +"(");
                    Element_Methodes em =new Element_Methodes(enclosedElement);
                    em.addTypes();

                }
            }
        }
    }

}
