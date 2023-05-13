package pumlFromJava;

import jdk.javadoc.doclet.Doclet;

import java.util.List;

public class OptionD implements Doclet.Option {
    private String chemin;
    @Override
    public int getArgumentCount() {
        return 1;
    }

    @Override
    public String getDescription() {
        return "Fixer le répertoire";
    }

    @Override
    public Kind getKind() {
        return Kind.STANDARD;
    }

    @Override
    public List<String> getNames() {
        return List.of("-d");
    }

    @Override
    public String getParameters() {
        return "<String>";
    }

    @Override
    public boolean process(String option, List<String> arguments) {
        this.chemin = arguments.get(0);
        return true;
    }


    public String getChemin()
    {
        return this.chemin;
    }
}
