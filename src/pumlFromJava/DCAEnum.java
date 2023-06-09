package pumlFromJava;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

public class DCAEnum extends Structures{
    public DCAEnum(){

    }
    public void addEnum(Element enumElement){
        String enumName = enumElement.toString();
        StringBuilder enumContent = new StringBuilder("enum " + enumName + " <<enumerate>> {\n");

        for (Element enclosedElement : enumElement.getEnclosedElements()) {
            if (enclosedElement.getKind() == ElementKind.ENUM_CONSTANT)
            {
                enumContent.append(enclosedElement + "\n");
            }
        }
        enumContent.append("}\n");
    }
}
