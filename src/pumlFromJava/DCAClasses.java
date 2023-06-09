package pumlFromJava;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.*;
import java.util.List;
import java.util.Objects;

public class DCAClasses extends Structures{
    public DCAClasses(){}
    public void addClass(Element classElement){
        String className = classElement.toString();
        affiche.append("class " + className + " {\n");
        int packsize = classElement.toString().length()-classElement.getSimpleName().toString().length();
        String test=classElement.toString().substring(0,packsize);
        // On ajoute le contenu de chaque classe (méthodes et variables)
        for (Element enclosedElement : classElement.getEnclosedElements()) {
            if (enclosedElement.asType().getKind().isPrimitive() || Objects.equals(enclosedElement.asType().toString(), "java.lang.String")) {
                affiche.append(enclosedElement + "\n");
            }
            else if (enclosedElement.asType().toString().length()>packsize ) {

                String res=enclosedElement.asType().toString().substring(0,packsize);
                if(test.equals(res)) {
                    subElements.add(classElement + " -- " + enclosedElement.asType());

                }
            }
        }
        affiche.append("}\n");

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
