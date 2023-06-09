package pumlFromJava;

import javax.lang.model.element.*;
import javax.lang.model.type.*;
import javax.lang.model.util.Elements;

import javax.lang.model.util.Types;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Methodes_Class extends Structures{
    Element classElem;
    List<Element> Agregs=new ArrayList<>();
    public Methodes_Class(Element classElement) {
        this.classElem = classElement;
    }

    public void addElement() {


        for (Element enclosedElement : classElem.getEnclosedElements()) {


            if (enclosedElement.getKind() == ElementKind.METHOD) {
                if(enclosedElement.getModifiers().contains(Modifier.STATIC)){

                    affiche.append("  " + getVisibility(enclosedElement) + "{static} " + enclosedElement.getSimpleName() + "(" );


                }
                else{

                    affiche.append("  " + getVisibility(enclosedElement) + " " + enclosedElement.getSimpleName() + "(" );


                }
                Element_Methodes em =new Element_Methodes(enclosedElement);
                em.addTypes();



            }
            else if (enclosedElement.getKind() == ElementKind.FIELD) {
                if(!isAgreg(this.classElem,enclosedElement)) {
                    if (enclosedElement.getModifiers().contains(Modifier.STATIC)) {
                        affiche.append("  " + getVisibility(enclosedElement) + "{static} " + enclosedElement.getSimpleName() + "\n");
                    } else if (enclosedElement.getModifiers().contains(Modifier.FINAL)) {
                        affiche.append("  " + getVisibility(enclosedElement) + enclosedElement.getSimpleName() + " {ReadOnly}\n");
                    } else if (enclosedElement.getModifiers().contains(Modifier.FINAL) && enclosedElement.getModifiers().contains(Modifier.STATIC)) {
                        affiche.append("  " + getVisibility(enclosedElement) + "{static} " + enclosedElement.getSimpleName() + " {ReadOnly}\n");

                    } else {
                        affiche.append("  " + getVisibility(enclosedElement) + " " + enclosedElement.getSimpleName() + "\n");
                    }
                }
                else {
                    String link="";

                    if (enclosedElement.asType().getKind() == TypeKind.ARRAY) {
                        if(getTypeElement(enclosedElement.asType())!=null){
                        link = this.classElem + " o-> \"* " + getVisibility(enclosedElement) + enclosedElement.getSimpleName() + "\" " + getTypeElement(enclosedElement.asType()).toString().replaceFirst("\\[\\]", "") + "\n";
                        }
                    } else {
                        link = this.classElem + " o-> \"1 " + getVisibility(enclosedElement) + enclosedElement.getSimpleName() + "\" " + getTypeElement(enclosedElement.asType()) + "\n";
                    }

                    if (!subElements.contains(link)) {
                        subElements.add(link);
                    }

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
    public boolean isAgreg(Element element,Element enclosedElement){
        int packsize = element.toString().length()-element.getSimpleName().toString().length();
        String test=element.toString().substring(0,packsize);
        if (enclosedElement.asType().toString().length()>packsize ) {

            String res=enclosedElement.asType().toString().substring(0,packsize);
            return test.equals(res);
        }
        return false;

    }
    private  Element getTypeElement(TypeMirror typeMirror) {
        if (typeMirror.getKind() == TypeKind.ARRAY) {
            ArrayType arrayType = (ArrayType) typeMirror;


            return getTypeElement(arrayType.getComponentType());
        }
        else if (typeMirror instanceof DeclaredType) {
            DeclaredType declaredType = (DeclaredType) typeMirror;
            Element element = declaredType.asElement();
            if (element instanceof TypeElement) {
                TypeElement typeElement = (TypeElement) element;
                if (typeElement.getQualifiedName().toString().equals("java.util.List")) {
                    // Récupérer les arguments de type du type paramétré
                    List<? extends TypeMirror> typeArguments = declaredType.getTypeArguments();
                    if (!typeArguments.isEmpty()) {
                        // Retourner le premier argument de type

                        return getTypeElement(typeArguments.get(0))  ;
                    }
                }
                else {
                    return element;
                }
            }
        }
        else if (typeMirror instanceof WildcardType) {
            WildcardType wildcardType = (WildcardType) typeMirror;
            TypeMirror extendsBound = wildcardType.getExtendsBound();
            if (extendsBound != null) {
                return getTypeElement(extendsBound);
            } else {
                System.out.println("Le wildcard n'a pas de borne supérieure (extends).");
            }
        }


        return null;
    }

}
