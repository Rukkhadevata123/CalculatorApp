public class Bird {
    boolean isHungry = true;
    String species;
    String color;

    public Bird(String species, String color) {
        this.species = species;
        this.color = color;
        System.out.println("A " + color + " " + species + " bird is created.");
    }

    public void eat() {
        if (isHungry) {
            System.out.println("The " + species + " bird is hungry.");
            isHungry = false;
        } else {
            System.out.println("The " + species + " bird is not hungry.");
        }
    }

    public void fly() {
        System.out.println("The " + species + " bird is flying.");
    }

    public void sing() {
        System.out.println("The " + species + " bird is singing.");
    }

    public static void main(String[] args) {
        var bird1 = new Bird("sparrow", "brown");
        bird1.eat();
        bird1.eat();
        bird1.fly();
        bird1.sing();
    }
}
