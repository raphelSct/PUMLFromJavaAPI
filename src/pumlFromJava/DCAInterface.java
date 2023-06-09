package pumlFromJava;

import javax.lang.model.element.Element;

public class DCAInterface extends Structures{
    public DCAInterface(){

    }
    public void addInterface(Element interfaceElement){
        String interfaceName = interfaceElement.toString();
        affiche.append("interface " + interfaceName + " <<interface>> {\n");
        affiche.append("}\n");

    }
}
