package pumlFromJava;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class PumlDiagram {
    private final List<String> classList;
    private final List<String> interfaceList;
    private final List<String> enumList;

    public PumlDiagram() {
        classList = new ArrayList<>();
        interfaceList = new ArrayList<>();
        enumList = new ArrayList<>();
    }

    public void addClass(String className) {
        classList.add(className);
    }

    public void addInterface(String interfaceName) {
        interfaceList.add(interfaceName);
    }

    public void addEnum(String enumName) {
        enumList.add(enumName);
    }

    public void generate(String fileName) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(new File(fileName));
        writer.println("@startuml");
        writer.println("skinparam style strictuml");

        for (String className : classList) {
            writer.println("class " + className);
        }

        for (String interfaceName : interfaceList) {
            writer.println("interface " + interfaceName + " <<interface>>");
        }

        for (String enumName : enumList) {
            writer.println("enum " + enumName + " <<enum>>");
        }

        writer.println("@enduml");
        writer.close();
    }
}
