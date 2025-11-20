public class Druid extends Gaul{
    public Druid(String name, Gender sex, double height, int age, int strength, int endurance) {
        super(name, sex, height, age, strength, endurance);
    }

    public void concoctPotion() {
        System.out.println(this.name + " is stirring the magic potion in the cauldron.");
    }

    public void command() {
        System.out.println(this.name + " advices the village with wisdom.");
    }

    public void work() {
        System.out.println(this.name + " gathers herbs in the forest.");
    }
}