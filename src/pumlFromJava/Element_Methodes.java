package pumlFromJava;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.util.Objects;

public class Element_Methodes extends Structures{
    Element classElem;
    public Element_Methodes(Element classElement) {
        this.classElem = classElement;
    }

    public void addTypes(){
        StringBuilder parameters = new StringBuilder();
        String retour="";
        TypeMirror typeretour=((ExecutableElement)classElem).getReturnType();
        Element ElementRetour=getTypeElement(typeretour);
        if (Objects.equals(typeretour.toString(), "boolean")){
            retour="Boolean";
        } else if (Objects.equals(typeretour.toString(), "int")) {
            retour="Integer";
        }
        
        if(ElementRetour!=null){
            retour=": "+ElementRetour.getSimpleName().toString();
        }
        for(VariableElement tpe :((ExecutableElement) classElem).getParameters()) {

            TypeMirror typeMirror= tpe.asType();

            Element typeElement = getTypeElement(typeMirror);
            if(typeElement!=null){
                parameters.append(tpe + " : " + (typeElement.getSimpleName() + " "));
            }
        }
        affiche.append(parameters+") "+retour+"\n");
    }
    private static Element getTypeElement(TypeMirror typeMirror) {
        if (typeMirror instanceof DeclaredType) {
            DeclaredType declaredType = (DeclaredType) typeMirror;
            Element enclosingElement = declaredType.asElement();
            if (enclosingElement instanceof TypeElement) {
                return (TypeElement) enclosingElement;
            }
        } else if (typeMirror instanceof ArrayType) {
            ArrayType arrayType = (ArrayType) typeMirror;
            return getTypeElement(arrayType.getComponentType());
        }

        return null;
    }
}
