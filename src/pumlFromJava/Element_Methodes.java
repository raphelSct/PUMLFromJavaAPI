package pumlFromJava;

import javax.lang.model.element.*;
import javax.lang.model.type.*;
import java.util.List;
import java.util.Objects;

public class Element_Methodes extends Structures{
    Element classElem;
    StringBuilder nomarg=new StringBuilder();
    public Element_Methodes(Element classElement) {
        this.classElem = classElement;
    }

    public void addTypes(){
        StringBuilder parameters = new StringBuilder();
        String retour="";
        String typeparam="";


        for(VariableElement tpe :((ExecutableElement) classElem).getParameters()) {

            TypeMirror typeMirror= tpe.asType();

            Element typeElement = getTypeElement(typeMirror);
            switch (typeMirror.toString()) {
                case "boolean":
                    typeparam = "Boolean";
                    break;
                case "int":
                case "short":
                case "byte":
                case "long":
                    typeparam = "Integer";
                    break;
                case "double":
                case "float":
                    typeparam = "Real";
                    break;
                default:

                    break;
            }
            if(typeElement!=null){
                typeparam=typeElement.getSimpleName().toString();

            }
            parameters.append(tpe + " : " + ( typeparam+ " "));
        }
        affiche.append(parameters);
        affiche.append(nomarg);
        nomarg.delete(0,nomarg.length());
        TypeMirror typeretour=((ExecutableElement)classElem).getReturnType();
        Element ElementRetour=getTypeElement(typeretour);


        switch (typeretour.toString()) {
            case "boolean":
                retour = "Boolean";
                break;
            case "int":
            case "short":
            case "byte":
            case "long":
                retour = "Integer";
                break;
            case "double":
            case "float":
                retour = "Real";
                break;
            default:

                break;
        }
        if(ElementRetour!=null){

            retour=": "+ElementRetour.getSimpleName().toString();
        }
        if(classElem.getModifiers().contains(Modifier.FINAL)){
            affiche.append(") " + retour + nomarg + " {ReadOnly}\n");
        }
        else {
            affiche.append(") " + retour + nomarg + "\n");
        }
    }
    private  Element getTypeElement(TypeMirror typeMirror) {
        if (typeMirror.getKind() == TypeKind.ARRAY) {
            ArrayType arrayType = (ArrayType) typeMirror;
            if(arrayType.toString().endsWith("[]"))
                if(nomarg.length()==0) {
                    nomarg.append("[*]");
                }
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
                        if(nomarg.length()==0) {
                            nomarg.append("[*]");
                        }
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
