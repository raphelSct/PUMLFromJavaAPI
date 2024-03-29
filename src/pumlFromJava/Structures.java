package pumlFromJava;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Structures {
    protected static StringBuilder affiche=new StringBuilder();

    protected static List<String> subElements=new ArrayList<>();
    public Structures(){

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
}
