package org.example;
import org.example.model.Animal;
import org.example.model.Barrel;
import org.example.model.Person;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        Animal animal = new Animal.Builder()
                .species("Cat")
                .eyeColor("Black")
                .hasWool(true)
                .build();
        System.out.println(animal);

        Person person = new Person.Builder()
                .gender("Male")
                .age(25)
                .lastName("Smith")
                .build();
        System.out.println(person);

        Barrel barrel = new Barrel.Builder()
                .volume(100)
                .storedMaterial("Oil")
                .material("Steel")
                .build();
        System.out.println(barrel);
    }

}
