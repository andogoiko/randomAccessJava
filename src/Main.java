import Clases.Person;
import Clases.gestionPersonas;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Main {

    private static Object Consumer;

    public static void main(String[] args){

        gestionPersonas gestionar = new gestionPersonas();

        ArrayList<Person> personitas = new ArrayList<>();

        Person persono = new Person("Paco", "48544761R", 32);
        Person persono2 = new Person("Julian", "58746847D", 3);
        Person persono3 = new Person("Almudena", "42578965F", 18);
        Person persono4 = new Person("Pancracio", "42589657T", 101);

        personitas.add(persono);
        personitas.add(persono2);
        personitas.add(persono3);
        personitas.add(persono4);

        personitas.forEach((person) -> {
            gestionar.injertar(person);
        });

        gestionar.obtenerPersona("58746847D").ifPresent( (s) -> System.out.println(s.imprimir()));

        gestionar.eliminar("42589657T");

    }

}
