package Clases;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class gestionPersonas {

    public void injertar(Person persono){

        try(RandomAccessFile accesoFichero = new RandomAccessFile(new File("src/data/dataPerson.txt"), "rws")){

            //accesoFichero.writeBytes(persono.imprimir());

            //nos colocamos al final del fichero

            accesoFichero.seek(accesoFichero.length());

            //escribimos el dni

            accesoFichero.write(String.format("%1$-" + 9 + "s", (persono.getDni())).getBytes(), 0, 9);

            accesoFichero.write((" ").getBytes(), 0, 1);

            //escribimos el nombre

            accesoFichero.write(String.format("%1$-" + 22 + "s", (persono.getNombre())).getBytes(), 0, 22);

            accesoFichero.write((" ").getBytes(), 0, 1);

            //escribimos la edad

            accesoFichero.write(String.format("%1$-" + 3 + "s", (persono.getEdad())).getBytes(), 0, 3);

            //nos colocamos al final del fichero

            accesoFichero.seek(accesoFichero.length());

            //saltamos de línea

            accesoFichero.write("\n".getBytes(), 0, 1);

            //accesoFichero.seek(5);
            //accesoFichero.seek(5);

            //System.out.println(Character.toString(accesoFichero.read()));

            accesoFichero.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public Optional<Person> obtenerPersona(String dni){

        Person res = null;

        try(RandomAccessFile accesoFichero = new RandomAccessFile(new File("src/data/dataPerson.txt"), "rws")){

            accesoFichero.seek(0);

            while (accesoFichero.getFilePointer() != accesoFichero.length()){

                String regisPers = accesoFichero.readLine();

                regisPers = regisPers.trim().replaceAll(" +", " ");

                String[] partesPersona = regisPers.split(" ");

                Person persono = new Person(partesPersona[1], partesPersona[0], Integer.parseInt(partesPersona[2]));

                if (persono.getDni().equalsIgnoreCase(dni)){
                    res = persono;

                    res.imprimir();
                    break;
                }

                accesoFichero.seek(accesoFichero.getFilePointer());
            }

        }catch (Exception e){

            e.printStackTrace();
        }

        /*if(res == null){
            throw new Exception("No se ha encontrado a la persona");
        }*/

        return Optional.ofNullable(res);
    }

    public void eliminar(String dni){

        ArrayList<Person> personos = new ArrayList<>();
        ArrayList<Person> personosLimpio = new ArrayList<>();

        File dataPerson = new File("src/data/dataPerson.txt");

        try{

            FileChannel archivo = new FileInputStream(dataPerson).getChannel();
            FileChannel copia = new FileOutputStream(new File("src/data/dataPersonCopy.txt")).getChannel();
            copia.transferFrom(archivo, 0, archivo.size());
        }catch (Exception e){
            e.printStackTrace();
        }

        try(RandomAccessFile accesoFichero = new RandomAccessFile(new File("src/data/dataPerson.txt"), "rws")){

            accesoFichero.seek(0);


            while (accesoFichero.getFilePointer() != accesoFichero.length()){

                String regisPers = accesoFichero.readLine();

                regisPers = regisPers.trim().replaceAll(" +", " ");

                String[] partesPersona = regisPers.split(" ");

                Person persono = new Person(partesPersona[1], partesPersona[0], Integer.parseInt(partesPersona[2]));

                accesoFichero.seek(accesoFichero.getFilePointer());

                personos.add(persono);
            }

            PrintWriter writer = new PrintWriter(dataPerson);

            writer.print("");
            writer.close();

            personosLimpio.addAll(personos.stream().filter(perchon -> !perchon.getDni().equalsIgnoreCase(dni)).collect(Collectors.toList()));

            personosLimpio.forEach((person) -> injertar(person));

        }catch (Exception e){

            e.printStackTrace();
        }

    }
}
