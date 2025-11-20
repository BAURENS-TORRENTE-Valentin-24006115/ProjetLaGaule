public class Prefect extends Roman {


    public Prefect(String name, Gender sex, double height, int age, int strength, int endurance) {
        super(name, sex, height, age, strength, endurance);
    }


    public void command() {
        System.out.println(this.name + " issues administrative orders.");
    }
}