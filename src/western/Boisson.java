package western;

import java.util.HashMap;
import java.util.Map;

public class Boisson
{
    private final Substantif substantif;

    public static final Boisson EAU = new Boisson("eau", Genre.FEMININ);

    private static Map<String, Boisson> boissons = new HashMap<>();

    public static Boisson of(String nom, Genre genre)
    {
        Boisson boisson;
        //+ boissons.put("eau",EAU);
        boolean contientNom = boissons.containsKey(nom);
        if (contientNom)
            boisson = boissons.get(nom);
        else
        {
            boisson = new Boisson(nom, genre);
            boissons.put(nom, boisson);
        }
        return boisson;
    }

    private Boisson(String nom, Genre genre)
    {
        this.substantif = new Substantif(nom, genre);
    }

    public String getNom()
    {
        return this.substantif.getNom();
    }

    public Genre getGenre()
    {
        return this.substantif.getGenre();
    }

    public String getNomAvecArticleIndefini()
    {
        return substantif.avecArticleIndefini();
    }

    public String getNomAvecArticlePartitif()
    {
        return substantif.avecArticlePartitif();
    }

    public String getNomAvecArticleDefini()
    {
        return substantif.avecArticleDefini();
    }

    public String getNomAvecPreposition(String preposition)
    {
        return substantif.avecPreposition(preposition);
    }

}