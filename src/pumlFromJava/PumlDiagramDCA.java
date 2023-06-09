package pumlFromJava;

import javax.lang.model.element.Element;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class PumlDiagramDCA {

    Structures fd=new Structures();
    protected DCAClasses Classes=new DCAClasses();
    protected DCAInterface Inter=new DCAInterface();
    protected  DCAEnum Enumer=new DCAEnum();
    protected Heritages link=new Heritages();
    private final List<String> classList;
    private final List<String> interfaceList;
    private final List<String> enumList;

    private String packageName;

    public PumlDiagramDCA() {
        this.classList = new ArrayList<>();
        this.interfaceList = new ArrayList<>();
        this.enumList = new ArrayList<>();
        this.packageName = "";
    }



    public void addClass(Element classElement) {
        Classes.addClass(classElement);
        this.classList.add(fd.affiche.toString());
        fd.affiche.delete(0,fd.affiche.length());

    }

    public void addInterface(Element interfaceElement) {
        Inter.addInterface(interfaceElement);
        this.interfaceList.add(fd.affiche.toString());
        fd.affiche.delete(0,fd.affiche.length());
    }


    public void addEnum(Element enumElement) {
        Enumer.addEnum(enumElement);
        this.enumList.add(fd.affiche.toString());
        fd.affiche.delete(0,fd.affiche.length());

    }
    public void addPackage(Element packElement){
        this.packageName = "package " + packElement.toString() + "{";
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
        for (String sube:fd.subElements){
            writer.println(sube);
        }
        fd.subElements.clear();



        writer.println("}");
        writer.println("@enduml");
        writer.close();
    }
}

