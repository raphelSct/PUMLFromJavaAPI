package pumlFromJava;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PumlDiagram {
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
        String className = classElement.getSimpleName().toString();
        StringBuilder classContent = new StringBuilder("class " + className + " {\n");
        // On ajoute le contenu de chaque classe (méthodes et variables)
        for (Element enclosedElement : classElement.getEnclosedElements()) {
            if (enclosedElement.getKind() == ElementKind.METHOD) {
                classContent.append("  " + getVisibility(enclosedElement) + " " + enclosedElement.getSimpleName() + "()\n");
            }
            else if (enclosedElement.getKind() == ElementKind.FIELD) {
                classContent.append("  " + getVisibility(enclosedElement) + " " + enclosedElement.getSimpleName() + "\n");
            }
        }
        classContent.append("}\n");
        classList.add(classContent.toString());
    }

    public void addInterface(Element interfaceElement) {
        String interfaceName = interfaceElement.getSimpleName().toString();
        StringBuilder interfaceContent = new StringBuilder("interface " + interfaceName + " <<interface>> {\n");

        for (Element enclosedElement : interfaceElement.getEnclosedElements()) {
            if (enclosedElement.getKind() == ElementKind.METHOD) {
                interfaceContent.append("  " + getVisibility(enclosedElement) + " " + enclosedElement.getSimpleName() +"()\n");
            }
        }
        interfaceContent.append("}\n");
        interfaceList.add(interfaceContent.toString());
    }


    public void addEnum(Element enumElement) {
        String enumName = enumElement.getSimpleName().toString();
        StringBuilder enumContent = new StringBuilder("enum " + enumName + " <<enumerate>> {\n");

        for (Element enclosedElement : enumElement.getEnclosedElements()) {
            if (enclosedElement.getKind() == ElementKind.ENUM_CONSTANT)
            {
                enumContent.append("  " + getVisibility(enclosedElement) + " " + enclosedElement.getSimpleName() + "\n");
            }
        }

        enumContent.append("}\n");
        enumList.add(enumContent.toString());
    }
    public void addPackage(Element packElement){
        this.packageName = "package " + packElement.getSimpleName().toString() + "{";
    }
    public String getVisibility(Element element)
    {
        Set<Modifier> modifiers = element.getModifiers();

        if (modifiers.contains(Modifier.PUBLIC))
        {
            return "+";
        }
        else if (modifiers.contains(Modifier.PRIVATE))
        {
            return "-";
        }
        else if (modifiers.contains(Modifier.PROTECTED))
        {
            return "#";
        }
        else
        {
            return "~";
        }
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
        writer.println("}");
        writer.println("@enduml");
        writer.close();
    }
}
