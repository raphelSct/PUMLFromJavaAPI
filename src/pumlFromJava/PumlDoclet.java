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

/**
 * Doclets : https://openjdk.org/groups/compiler/processing-code.html
 *
 * Doclets provide code that can be executed by the JDK javadoc tool.
 * Although the tool is primarily designed to support the ability to generate
 * API documentation from element declarations and documentation comments,
 * it is not limited to that purpose, and can run any user-supplied doclet,
 * which can use the Language Model API and Compiler Tree API to analyze the packages,
 * classes and files specified on the command line.
 */

/**
 * A minimal doclet that just prints out the names of the
 * selected elements.
 */
public class PumlDoclet implements Doclet {
    private final OptionOut out=new OptionOut();
    private final OptionD path = new OptionD();

    /**
     * A base class for declaring options.
     * Subtypes for specific options should implement
     * the  method
     * to handle instances of the option found on the
     * command line.
     */



    @Override
    public void init(Locale locale, Reporter reporter) {  }

    @Override
    public String getName() {
        // For this doclet, the name of the doclet is just the
        // simple name of the class. The name may be used in
        // messages related to this doclet, such as in command-line
        // help when doclet-specific options are provided.
        return getClass().getSimpleName();
    }

    @Override
    public Set<? extends Option> getSupportedOptions() {
        // This doclet does not support any options.
        return Set.of(out,path);
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        // This doclet supports all source versions.
        // More sophisticated doclets may use a more
        // specific version, to ensure that they do not
        // encounter more recent language features that
        // they may not be able to handle.
        return SourceVersion.latest();
    }

    @Override
    public boolean run(DocletEnvironment environment) {
        // This method is called to perform the work of the doclet.
        // In this case, it just prints out the names of the
        // elements specified on the command line.
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
        PumlDiagram diagram = new PumlDiagram();
        for (Element e : elements) {
            if (e.getKind() == ElementKind.CLASS) {
                diagram.addClass(e.getSimpleName().toString());
            } else if (e.getKind() == ElementKind.INTERFACE) {
                diagram.addInterface(e.getSimpleName().toString());
            } else if (e.getKind() == ElementKind.ENUM) {
                diagram.addEnum(e.getSimpleName().toString());
            }
        }

        // Écrire le diagramme UML dans un fichier de sortie.
        String filename = out.getNomFichier() + ".puml";
        filename= path.getChemin()+filename;
        try {
            diagram.generate(filename);
            System.out.println("Le diagramme UML a été écrit dans le fichier " + filename + ".");
        } catch (FileNotFoundException e) {
            System.err.println("Erreur lors de l'écriture du fichier " + filename + ".");
            e.printStackTrace();
        }

    }
}
