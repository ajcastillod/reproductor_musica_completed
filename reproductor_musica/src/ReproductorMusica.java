import java.util.Scanner;

class Cancion {
    String titulo;
    String artista;
    int duracion;
    Cancion siguiente;

    public Cancion(String titulo, String artista, int duracion) {
        this.titulo = titulo;
        this.artista = artista;
        this.duracion = duracion;
        this.siguiente = null;
    }
}

class Playlist {
    private String nombre;
    private Cancion inicio;

    public Playlist(String nombre) {
        this.nombre = nombre;
        this.inicio = null;
    }

    public String getNombre() {
        return nombre;
    }

    public void agregarCancion(String titulo, String artista, int duracion) {
        if (titulo == null || titulo.trim().isEmpty() ||
            artista == null || artista.trim().isEmpty() ||
            duracion <= 0) {
            System.out.println(" Datos inválidos. Verifica el título, artista y duración.");
            return;
        }

        Cancion nueva = new Cancion(titulo, artista, duracion);
        if (inicio == null) {
            inicio = nueva;
        } else {
            Cancion actual = inicio;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nueva;
        }
        System.out.println(" Canción agregada correctamente.");
    }

    public void mostrarCanciones() {
        if (inicio == null) {
            System.out.println(" La playlist está vacía.");
            return;
        }

        System.out.println(" Canciones en la playlist '" + nombre + "':");
        Cancion actual = inicio;
        while (actual != null) {
            System.out.println("- " + actual.titulo + " | " + actual.artista + " | " + actual.duracion + " seg");
            actual = actual.siguiente;
        }
    }

    public void buscarCancion(String titulo) {
        Cancion actual = inicio;
        while (actual != null) {
            if (actual.titulo.equalsIgnoreCase(titulo)) {
                System.out.println(" Canción encontrada:");
                System.out.println("- Título: " + actual.titulo);
                System.out.println("- Artista: " + actual.artista);
                System.out.println("- Duración: " + actual.duracion + " segundos");
                return;
            }
            actual = actual.siguiente;
        }
        System.out.println(" Canción no encontrada.");
    }

    public void eliminarCancion(String titulo) {
        if (inicio == null) {
            System.out.println(" No hay canciones en la playlist.");
            return;
        }

        if (inicio.titulo.equalsIgnoreCase(titulo)) {
            inicio = inicio.siguiente;
            System.out.println(" Canción eliminada correctamente.");
            return;
        }

        Cancion actual = inicio;
        while (actual.siguiente != null) {
            if (actual.siguiente.titulo.equalsIgnoreCase(titulo)) {
                actual.siguiente = actual.siguiente.siguiente;
                System.out.println(" Canción eliminada correctamente.");
                return;
            }
            actual = actual.siguiente;
        }

        System.out.println(" Canción no encontrada.");
    }
}

public class ReproductorMusica {

    public static void limpiarConsola() {
        try {
            if (System.getProperty("os.name").startsWith("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println(" No se pudo limpiar la consola.");
        }
    }

    public static void pausar(Scanner scanner) {
        System.out.println("\nPresiona ENTER para continuar...");
        scanner.nextLine();
        limpiarConsola();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Playlist playlist = null;
        int opcion;

        do {
            limpiarConsola();

            System.out.println(" MENU DEL REPRODUCTOR DE MUSICA ");
            System.out.println("1. Crear nueva playlist");
            System.out.println("2. Agregar canción");
            System.out.println("3. Mostrar canciones");
            System.out.println("4. Buscar canción por título");
            System.out.println("5. Eliminar canción por título");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                limpiarConsola();

                switch (opcion) {
                    case 1:
                        System.out.print("Ingrese el nombre de la nueva playlist: ");
                        String nombre = scanner.nextLine();
                        if (nombre.trim().isEmpty()) {
                            System.out.println(" El nombre de la playlist no puede estar vacío.");
                        } else {
                            playlist = new Playlist(nombre);
                            System.out.println(" Playlist creada: " + nombre);
                        }
                        break;

                    case 2:
                        if (playlist == null) {
                            System.out.println(" Primero debe crear una playlist.");
                            break;
                        }

                        System.out.print("Título de la canción: ");
                        String titulo = scanner.nextLine();
                        System.out.print("Artista: ");
                        String artista = scanner.nextLine();
                        System.out.print("Duración en segundos: ");
                        String duracionStr = scanner.nextLine();

                        try {
                            int duracion = Integer.parseInt(duracionStr);
                            playlist.agregarCancion(titulo, artista, duracion);
                        } catch (NumberFormatException e) {
                            System.out.println(" La duración debe ser un número entero positivo.");
                        }
                        break;

                    case 3:
                        if (playlist != null) {
                            playlist.mostrarCanciones();
                        } else {
                            System.out.println(" No hay playlist creada.");
                        }
                        break;

                    case 4:
                        if (playlist == null) {
                            System.out.println(" Primero debe crear una playlist.");
                            break;
                        }
                        System.out.print("Ingrese el título de la canción a buscar: ");
                        String buscarTitulo = scanner.nextLine();
                        playlist.buscarCancion(buscarTitulo);
                        break;

                    case 5:
                        if (playlist == null) {
                            System.out.println(" Primero debe crear una playlist.");
                            break;
                        }
                        System.out.print("Ingrese el título de la canción a eliminar: ");
                        String eliminarTitulo = scanner.nextLine();
                        playlist.eliminarCancion(eliminarTitulo);
                        break;

                    case 6:
                        System.out.println(" Gracias por usar el reproductor. ¡Hasta pronto!");
                        break;

                    default:
                        System.out.println(" Opción no válida. Intente nuevamente.");
                }

                if (opcion != 6) {
                    pausar(scanner);
                }

            } catch (NumberFormatException e) {
                System.out.println(" Error: debe ingresar un número válido.");
                pausar(scanner);
                opcion = 0;
            }

        } while (opcion != 6);

        scanner.close();
    }
}
