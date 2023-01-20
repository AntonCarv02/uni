package cap13;

public class Pet {
    protected String name;
    protected char species;

    public Pet(char species, String name) {
        setSpecies(species);
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getSpecies() {
        return species;
    }

    public void setSpecies(char species) {
        this.species = species;
    }

    public String toString( ){
        return species + " " + name;
    }

}
