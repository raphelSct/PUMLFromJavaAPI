package western;

import java.util.ArrayList;
import java.util.List;

public class Sherif extends Cowboy{
    private Cowboy cowboy;
    private List<HorsLaLoi> wanted=new ArrayList<>();
    public Sherif(String nom){
        super(nom, getBoissonParDefaut());
        this.cowboy = new Cowboy(super.getNom());
    }
    public Sherif(String nom,Boisson boisson){
        super(nom,boisson);
        this.cowboy = new Cowboy(super.getNom(),boisson);
    }
    public String getNom(){
        return "Shérif "+super.getNom();
    }
    public void sePresenter(){
        super.sePresenter();
        this.dire("Je recherche "+getWanted());
    }
    public void capturer(HorsLaLoi horsLaLoi){
        this.dire("Au nom de la loi, "+horsLaLoi.getPseudo()+", je t'arrête !");
        super.capturer(horsLaLoi);
        List<HorsLaLoi> l=getWanted();
        l.remove(horsLaLoi);

    }
    public List<HorsLaLoi> getWanted(){
        return this.wanted;
    }
    public void rechercher(HorsLaLoi horsLaLoi){
        this.wanted.add(horsLaLoi);
    }
    public Boolean isWanted(HorsLaLoi horsLaLoi){
        List<HorsLaLoi> l =getWanted();
        for (HorsLaLoi h:l) {
            if(horsLaLoi.getPseudo().equals(h.getPseudo())){
                return true;
            }
        }
        return false;
    }
}

