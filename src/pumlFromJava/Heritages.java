package pumlFromJava;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.List;
import java.util.Objects;

public class Heritages extends Structures{


    public Heritages(){

    }
    public void addLink(Element element){
        String result;
        List<? extends TypeMirror> interfaces;

        if (element.getKind()== ElementKind.CLASS){
            result=((TypeElement)element).getSuperclass().toString();
            interfaces =((TypeElement) element).getInterfaces();
            for(TypeMirror e : interfaces){
                subElements.add(e.toString()+" <|.. " +element);
            }
            if(!Objects.equals(result, "none") && !Objects.equals(result, "java.lang.Object")){
                subElements.add(result+" <|-- "+element );
            }
        }
    }

}
