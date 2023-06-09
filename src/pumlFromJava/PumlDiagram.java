package pumlFromJava;

import javax.lang.model.element.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PumlDiagram {

    Structures el=new Structures();
    protected Heritages link=new Heritages();
    protected Structures_Class Classes=new Structures_Class();
    protected Structures_Interface Inter=new Structures_Interface();
    protected  Structures_Enum Enumer=new Structures_Enum();
    private final List<String> classList;
    private final List<String> interfaceList;
    private final List<String> enumList;

    private String packageName;

    public PumlDiagram() {
        classList = new ArrayList<>();
        interfaceList = new ArrayList<>();
        enumList = new ArrayList<>();
    }


    public void addClass(Element classElement) {
        Classes.add_Class(classElement);
        this.classList.add(el.affiche.toString());
        el.affiche.delete(0,el.affiche.length());
    }

    public void addInterface(Element interfaceElement) {
        Inter.addInterface(interfaceElement);
        this.interfaceList.add(el.affiche.toString());
        el.affiche.delete(0,el.affiche.length());
    }


    public void addEnum(Element enumElement) {
        Enumer.addEnum(enumElement);
        this.enumList.add(el.affiche.toString());
        el.affiche.delete(0,el.affiche.length());
    }
    public void addPackage(Element packElement){
        this.packageName = "package " + packElement.getSimpleName().toString() + "{";
    }
    public void addLink(Element element){
        link.addLink(element);

    }



    public void generate(String fileName) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(new File(fileName));
        writer.println("@startuml");
        writer.println("skinparam style strictuml");
        writer.println("skinparam classFontStyle Bold");
        writer.println("skinparam classAttributeIconSize 0");
        writer.println(packageName);
        for (String className : classList) {
            writer.println(className);
        }

        for (String interfaceName : interfaceList) {
            writer.println(interfaceName);
        }

        for (String enumName : enumList) {
            writer.println(enumName );
        }
        for(String liens : el.subElements){
            writer.println(liens);
        }
        el.subElements.clear();


        writer.println("}");
        writer.println("@enduml");
        writer.close();
    }
}
