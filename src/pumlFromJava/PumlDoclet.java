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
        PumlDiagram dcc = new PumlDiagram();
        PumlDiagramDCA dca = new PumlDiagramDCA();
        for (Element e : elements) {
            if (e.getKind() == ElementKind.PACKAGE){

                dca.addPackage(e);
            }
            if (e.getKind() == ElementKind.CLASS) {

                dca.addClass(e);
            } else if (e.getKind() == ElementKind.INTERFACE) {

                dca.addInterface(e);
            } else if (e.getKind() == ElementKind.ENUM) {

                dca.addEnum(e);
            }
            dca.addLink(e);


        }

        // Écrire le diagramme UML dans un fichier de sortie.
        String filenamedca = out.getNomFichier() + "DCA.puml";
        String filenamedcc = out.getNomFichier() + "DCC.puml";
        filenamedca= path.getChemin()+filenamedca;
        filenamedcc= path.getChemin()+filenamedcc;
        try {
            dca.generate((filenamedca));


            System.out.println("Le diagramme UML a été écrit dans le fichier " + filenamedca + ".");
        }
        catch (FileNotFoundException e) {
            System.err.println("Erreur lors de l'écriture du fichier " + filenamedca + ".");
            e.printStackTrace();
        }
        for (Element e : elements) {
            if (e.getKind() == ElementKind.PACKAGE){
                dcc.addPackage(e);

            }
            if (e.getKind() == ElementKind.CLASS) {
                dcc.addClass(e);

            } else if (e.getKind() == ElementKind.INTERFACE) {
                dcc.addInterface(e);

            } else if (e.getKind() == ElementKind.ENUM) {
                dcc.addEnum(e);

            }
            dca.addLink(e);


        }


        try {

            dcc.generate(filenamedcc);

            System.out.println("Le diagramme UML a été écrit dans le fichier " + filenamedcc + ".");
        }
        catch (FileNotFoundException e) {
            System.err.println("Erreur lors de l'écriture du fichier " + filenamedcc + ".");
            e.printStackTrace();
        }

    }
}
