package pumlFromJava;

import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.Reporter;

import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class PumlDoclet implements Doclet {
    private final OptionOut out=new OptionOut();
    private final OptionD path = new OptionD();

    @Override
    public void init(Locale locale, Reporter reporter) {  }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public Set<? extends Option> getSupportedOptions() {
        return Set.of(out,path);
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    @Override
    public boolean run(DocletEnvironment environment) {
        System.out.println(this.getName());
        System.out.println(environment.getSpecifiedElements());
        System.out.println(environment.getIncludedElements());
        for (Element element : environment.getSpecifiedElements())
        {
            dumpElement(element);
        }
        return true;
    }


    private void dumpElement(Element element)
    {
        // Récupérer les classes, interfaces et énumérations inclus dans l'élément spécifié.
        List<Element> elements = new ArrayList<>();
        elements.add(element);
        elements.addAll(element.getEnclosedElements());

        // Créer un diagramme UML à partir des éléments spécifiés.
        PumlDiagramDCA diagram = new PumlDiagramDCA();

        for (Element e : elements) {
            if (e.getKind() == ElementKind.PACKAGE){
                diagram.addPackage(e);
            }
            if (e.getKind() == ElementKind.CLASS) {
                diagram.addClass(e);
            } else if (e.getKind() == ElementKind.INTERFACE) {
                diagram.addInterface(e);
            } else if (e.getKind() == ElementKind.ENUM) {
                diagram.addEnum(e);
            }
        }

        // Écrire le diagramme UML dans un fichier de sortie.
        String filename = out.getNomFichier() + ".puml";
        filename= path.getChemin()+filename;
        try {
            diagram.generate(filename);
            System.out.println("Le diagramme UML a été écrit dans le fichier " + filename + ".");
        }
        catch (FileNotFoundException e) {
            System.err.println("Erreur lors de l'écriture du fichier " + filename + ".");
            e.printStackTrace();
        }

    }
}
