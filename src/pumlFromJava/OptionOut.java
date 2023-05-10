package pumlFromJava;

import jdk.javadoc.doclet.Doclet;

import java.util.List;

public class OptionOut implements Doclet.Option{
    String nomFichier;

    @Override
    public int getArgumentCount() {
        return 1;
    }

    @Override
    public String getDescription() {
        return "Fixer le nom du fichier";
    }

    @Override
    public Kind getKind() {
        return Kind.STANDARD;
    }

    @Override
    public List<String> getNames() {
        return List.of("-out");
    }

    @Override
    public String getParameters() {
        return "<String>";
    }

    @Override
    public boolean process(String option, List<String> arguments) {
        this.nomFichier = arguments.get(0);
        return true;
    }

    public String getNomFichier()
    {
        return this.nomFichier;
    }
}
