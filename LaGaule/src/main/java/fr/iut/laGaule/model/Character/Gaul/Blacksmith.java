public class Blacksmith extends Gaul{

    public Blacksmith(String name, Gender sex, double height, int age, int strength, int endurance) {
        super(name, sex, height, age, strength, endurance);
    }


    public void work() {
        System.out.println(this.name + " forges swords and shields.");
    }
}