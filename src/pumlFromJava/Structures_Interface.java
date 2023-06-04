package pumlFromJava;

import javax.lang.model.element.Element;

public class Structures_Interface extends Structures{
    public Structures_Interface(){

    }
    public void addInterface(Element interfaceElement){
        String interfaceName = interfaceElement.getSimpleName().toString();
        StringBuilder interfaceContent = new StringBuilder("interface " + interfaceName + " <<interface>> {\n");
        Methodes_Interface mi = new Methodes_Interface(interfaceElement);
        mi.addInterface();
        interfaceContent.append("}\n");

    }
}
