package AC_Ficheros;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class GestorArchivo {

    //variables destinadas a las funciones de agregar usuarios y mostrarlos
    BufferedWriter writer;
    BufferedReader reader;
    File Fichero;
    ArrayList <String> usuarios = new ArrayList<>();
    ArrayList <String[]> informacion =  new ArrayList<>();
    int NumUsuarios = 0;
    int CantidadUsuarios;

    //variables destinadas a la concordancia del archivo
    Path UbicacionArchivo = Paths.get("src\\AC_FICHEROS\\concordancia.txt");
    File ArchivoConcordancia = new File(UbicacionArchivo.toString());
    BufferedWriter EscritorConcordancia = new BufferedWriter(new FileWriter(ArchivoConcordancia));



    public GestorArchivo(String fichero) throws IOException {

        Fichero = new File(fichero);
        if ( Fichero.exists()) writer  = new BufferedWriter(new FileWriter(fichero,true));
        else writer  = new BufferedWriter(new FileWriter(fichero));

        Incializar();

    }

    public void agregarUsuario(String Datos) throws IOException {

            String[] Informacion = Datos.split(" ");

            if (!ComprobarUsuario(Informacion[0])){
                Informacion[0] = "U" + ( 100 + CantidadUsuarios );
            }
                for (String usuario : usuarios) {
                    if (usuario.equals(Informacion[0])) {
                    System.out.println("Usuario existe");
                    break;
                    }
                }

            usuarios.add(Informacion[0]);

            informacion.add(Arrays.copyOfRange(Informacion,1,Informacion.length));

            writer.write("\n"+Arrays.toString(Informacion)
                    .replace("[","")
                    .replace("]","")
                    .replace(",","")
                    );
            writer.flush();

    }

    public void Incializar() throws IOException {
        reader = new BufferedReader(new FileReader(Fichero));

        CantidadUsuarios = (int) Files.lines(Fichero.toPath()).count();

        while (reader.ready()){

            String[] linea = reader.readLine().split(" ");
            usuarios.add(linea[0]);
            informacion.add(Arrays.copyOfRange(linea,1,linea.length));

            NumUsuarios++;

        }
    }

    public void MostrarUsuarios(){

        for (int Usuarios = 0; Usuarios < NumUsuarios; Usuarios++){

            System.out.print("\n" + usuarios.get(Usuarios) + " ");

            for (int Datos = 0; Datos < informacion.get(Usuarios).length ; Datos++){
                System.out.print( informacion.get(Usuarios)[Datos] + " ");
            }

        }

    }

    private Boolean ComprobarUsuario(String Usuario){

        int Identificador = Integer.parseInt(Usuario.substring(1));
        int IdentificadorCorrecto = 100+CantidadUsuarios;

        if (Identificador < 100) throw new RuntimeException("El usuario ha puesto un identificador menor al original");
            else if (Identificador != IdentificadorCorrecto){
                IO.println("El identificador de usuario es diferente a la cantidad de usuarios existentes, se le cambia el identificador a U" + IdentificadorCorrecto);
                return false;
            }
        else return true;

    }

    public void Concordancia() throws IOException{

        StringBuilder LineaConcordancia = new StringBuilder();

        for (int usuarioActual = 0; usuarioActual < usuarios.size(); usuarioActual++) {


            for (int Numusuario = 0; Numusuario < usuarios.size(); Numusuario++){
                LineaConcordancia = new StringBuilder();
                int concordancia = 0;
            if (Numusuario == usuarioActual) {Numusuario++;
            }
            else {
                LineaConcordancia
                        .append(usuarios.get(usuarioActual))
                        .append(" " + usuarios.get(Numusuario) + " ");
                for (String datosUser1 : informacion.get(usuarioActual)) {


                    for (String datosUser2 : informacion.get(Numusuario)) {

                        if (datosUser1.equals(datosUser2)) {
                            concordancia++;
                            LineaConcordancia.append(datosUser1 + " ");
                        }
                    }

                }
                if (concordancia == 0) {
                    LineaConcordancia.delete(0, LineaConcordancia.length());
                } else {
                    EscritorConcordancia.write(LineaConcordancia.toString());
                    EscritorConcordancia.write("\n");
                    EscritorConcordancia.flush();
                }
             }
            }
        }
        BorrarLineasDuplicadas();
    }

    public void BorrarLineasDuplicadas() throws FileNotFoundException,IOException {
        StringBuilder LineasCorrectas = new StringBuilder();
        List<String> LineasArchivo = Files.readAllLines(UbicacionArchivo);
        EscritorConcordancia = new BufferedWriter(new FileWriter(ArchivoConcordancia));

        Set<String> lineasUnicas = new LinkedHashSet<>();
        for (String linea : LineasArchivo){
            String[] Datos = linea.split(" ");
            String Usuario1 = Datos[0];
            String Usuario2 = Datos[1];
            if (Usuario1.compareTo(Usuario2) > 0){
                Datos[0] = Usuario2;
                Datos[1] = Usuario1;
            }

            // me explico, al ser el valor distinto a 0 indica que usuario2 es en valores alfabeticos mayor a usuario1.
            // asi que de esa manera hago que se ordenen cambiando el orden para ver si usando LinkedHashSet esa linea ya esta en la lista,
            // aprovechando que esa variable cuenta con una funcion para no agregar duplicados

            if (lineasUnicas.add(Arrays.toString(Datos))){
                LineasCorrectas.append(linea+"\n");
            }
        }
        EscritorConcordancia.write(LineasCorrectas.toString());
        EscritorConcordancia.flush();
    }


}