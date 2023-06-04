package pumlFromJava;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

public class Structures_Enum extends Structures{

    //String enumName;
    public Structures_Enum(){

    }
    public void addEnum(Element enumElement){
        String enumName = enumElement.getSimpleName().toString();
        affiche.append("enum " + enumName + " <<enumerate>> {\n");
        Methodes_Enum me=new Methodes_Enum(enumElement);
        me.addElement();
        affiche.append("}\n");

    }
}
