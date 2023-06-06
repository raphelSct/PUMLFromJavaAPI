package pumlFromJava;

import javax.lang.model.element.Element;

public class Structures_Class extends Structures {
    public Structures_Class(){

    }
    public void add_Class(Element classElement){
        String className = classElement.getSimpleName().toString();
        affiche.append("class " + className + " { \n");
        Methodes_Class mc = new Methodes_Class(classElement);
        mc.addElement();
        affiche.append("}\n");

    }

}

