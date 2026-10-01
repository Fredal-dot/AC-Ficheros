package AC_Ficheros;


import java.io.IOException;

public class Principal {
    public static void main() throws IOException {
        String Opcion = "";

        while (!Opcion.equals("4")) {
            System.out.println("\n=============== Menu Principal ===============");
            System.out.println("1 - Añadir usuarios ");
            System.out.println("2 - Mostrar usuarios");
            System.out.println("3 - Generar fichero de concordancia");
            System.out.println("4 - Salir");
            System.out.println("=============== Menu Principal ===============");

            Opcion = IO.readln("\nIngresa tu opcion: ");
            GestorArchivo gestor = new GestorArchivo("src\\AC_FICHEROS\\usuarios.txt");

            switch (Opcion) {
                case "1" -> {
                    String Datos = IO.readln("Ingrese el usuario seguido por las aficciones:\n");
                    gestor.agregarUsuario(Datos);
                }
                case "2" -> {
                    gestor.MostrarUsuarios();
                }
                case "3" -> {
                    gestor.Concordancia();
                }
                case "4" -> IO.println("Fin del programa");
                default -> IO.println("Opcion no valida");
            }
        }
    }
}
