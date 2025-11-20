public class Innkeeper extends Gaul{


    public Innkeeper(String name, Gender sex, double height, int age, int strength, int endurance) {
        super(name, sex, height, age, strength, endurance);
    }
    public void work() {
        System.out.println(this.name + " serves boar.");
    }
}