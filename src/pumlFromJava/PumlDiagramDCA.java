package pumlFromJava;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.NullType;
import javax.lang.model.type.TypeMirror;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class PumlDiagramDCA {
    private final List<String> classList;
    private final List<String> interfaceList;
    private final List<String> enumList;
    private final List<String> subInterfaces;
    private final List<String> superClasses;
    private final List<String> subElements;
    private String packageName;

    public PumlDiagramDCA() {
        this.classList = new ArrayList<>();
        this.interfaceList = new ArrayList<>();
        this.enumList = new ArrayList<>();
        this.subInterfaces = new ArrayList<>();
        this.superClasses = new ArrayList<>();
        this.subElements = new ArrayList<>();
        this.packageName = "";
    }



    public void addClass(Element classElement) {
        String className = classElement.toString();
        StringBuilder classContent = new StringBuilder("class " + className + " {\n");
        int packsize = classElement.toString().length()-classElement.getSimpleName().toString().length();
        String test=classElement.toString().substring(0,packsize);
        // On ajoute le contenu de chaque classe (méthodes et variables)
        for (Element enclosedElement : classElement.getEnclosedElements()) {
            if (enclosedElement.asType().getKind().isPrimitive() || Objects.equals(enclosedElement.asType().toString(), "java.lang.String")) {
                classContent.append(enclosedElement + "\n");
            }
            else if (enclosedElement.asType().toString().length()>packsize) {
                String res=enclosedElement.asType().toString().substring(0,packsize);
                if(test.equals(res)){
                    this.subElements.add(classElement+" -- "+enclosedElement.asType());
                    System.out.println("Bonjour");
                }
            }
        }
        classContent.append("}\n");
        this.classList.add(classContent.toString());

    }

    public void addInterface(Element interfaceElement) {
        String interfaceName = interfaceElement.toString();
        StringBuilder interfaceContent = new StringBuilder("interface " + interfaceName + " <<interface>> {\n");
        interfaceContent.append("}\n");
        this.interfaceList.add(interfaceContent.toString());
    }


    public void addEnum(Element enumElement) {
        String enumName = enumElement.toString();
        StringBuilder enumContent = new StringBuilder("enum " + enumName + " <<enumerate>> {\n");

        for (Element enclosedElement : enumElement.getEnclosedElements()) {
            if (enclosedElement.getKind() == ElementKind.ENUM_CONSTANT)
            {
                enumContent.append(enclosedElement + "\n");
            }
        }

        enumContent.append("}\n");
        this.enumList.add(enumContent.toString());
    }
    public void addPackage(Element packElement){
        this.packageName = "package " + packElement.toString() + "{";
    }
    public void addLink(Element element){
        String result;
        List<? extends TypeMirror> interfaces;

        if (element.getKind()==ElementKind.CLASS){
            result=((TypeElement)element).getSuperclass().toString();
            interfaces =((TypeElement) element).getInterfaces();
            for(TypeMirror e : interfaces){
                this.subInterfaces.add(e.toString()+" <|.. " +element);
            }
            if(!Objects.equals(result, "none") && !Objects.equals(result, "java.lang.Object")){
                this.superClasses.add(result+" <|-- "+element );
            }
        }

    }
    /*public String getVisibility(Element element)
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
    }*/

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
        for (String subInt:subInterfaces) {
            writer.println(subInt);
        }
        for (String sup:superClasses) {
            writer.println(sup);
        }
        for (String sube:subElements){
            writer.println(sube);
        }
        writer.println("}");
        writer.println("@enduml");
        writer.close();
    }
}

