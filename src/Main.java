import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Ingrese el tamaño del arreglo de palabras: ");
        int n = sc.nextInt();
        sc.nextLine();

        String[] palabras = new String[n];

        System.out.println("\nIngrese las palabras:");
        for (int i = 0; i < n; i++) {
            System.out.print("Palabra " + (i + 1) + ": ");
            palabras[i] = sc.nextLine();
        }

        menuOrdenamiento(palabras);
        sc.close();
    }

    public static void menuOrdenamiento(String[] palabras) {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n=== MENÚ DE ORDENAMIENTO ===");
            System.out.println("1. Ordenamiento por Selección");
            System.out.println("2. Ordenamiento por Inserción");
            System.out.println("3. Ordenamiento por Burbuja");
            System.out.println("4. Ordenamiento por Mezcla (Merge Sort)");
            System.out.println("5. Ordenamiento Rápido (Quick Sort)");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> ordenarPorSeleccion(palabras);
                case 2 -> ordenarPorInsercion(palabras);
                case 3 -> ordenarPorBurbuja(palabras);
                case 4 -> ordenarPorMezcla(palabras, 0, palabras.length - 1);
                case 5 -> ordenarPorRapido(palabras, 0, palabras.length - 1);
                case 6 -> System.out.println("Saliendo del programa...");
                default -> System.out.println("Opción inválida. Intente de nuevo.");
            }

            if (opcion >= 1 && opcion <= 5) {
                System.out.println("\nArreglo ordenado:");
                for (String palabra : palabras) {
                    System.out.print(palabra + " ");
                }
                System.out.println("\n");

                System.out.print("¿Desea buscar una palabra? (s/n): ");
                String respuesta = sc.nextLine();
                if (respuesta.equalsIgnoreCase("s")) {
                    System.out.print("Ingrese la palabra a buscar: ");
                    String palabraBuscada = sc.nextLine();
                    int resultado = busquedaBinaria(palabras, palabraBuscada);

                    if (resultado != -1)
                        System.out.println("La palabra '" + palabraBuscada + "' se encuentra en la posición " + resultado);
                    else
                        System.out.println("La palabra '" + palabraBuscada + "' no fue encontrada.");
                }
            }

        } while (opcion != 6);
    }

    public static void ordenarPorSeleccion(String[] arr) {
        int n = arr.length;
        //recorre la lista hasta el penultimo elemento
        for (int i = 0; i < n - 1; i++) {
            int min = i; // Índice del elemento mínimo encontrado
            // Busca el menor elemento en el resto del arreglo
            for (int j = i + 1; j < n; j++) {
                if (arr[j].compareToIgnoreCase(arr[min]) < 0) {
                    min = j; // Actualiza el índice del nuevo mínimo
                }
            }
            // Intercambia el elemento actual con el mínimo encontrado
            String temp = arr[min];
            arr[min] = arr[i];
            arr[i] = temp;
        }
        // Complejidad temporal: O(n²)
    }

    public static void ordenarPorInsercion(String[] arr) {
        // Recorre el arreglo desde el segundo elemento
        for (int i = 1; i < arr.length; i++) {
            String clave = arr[i]; // Elemento que se va a insertar
            int j = i - 1;
            // Desplaza los elementos mayores que la clave una posición a la derecha
            while (j >= 0 && arr[j].compareToIgnoreCase(clave) > 0) {
                arr[j + 1] = arr[j];
                j--;
            }
            // Inserta la clave en la posición correcta
            arr[j + 1] = clave;
        }
        // Complejidad temporal: O(n²)
    }

    public static void ordenarPorBurbuja(String[] arr) {
        int n = arr.length;
        boolean intercambio;
        do {
            intercambio = false; // Indica si hubo intercambio en la pasada
            // Recorre los elementos adyacentes y los intercambia si están desordenados
            for (int i = 0; i < n - 1; i++) {
                if (arr[i].compareToIgnoreCase(arr[i + 1]) > 0) {
                    // Intercambio de posiciones
                    String temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    intercambio = true;
                }
            }
            // Si no hubo intercambios, el arreglo ya está ordenado
        } while (intercambio);
        // Complejidad temporal: O(n²)
    }

    public static void ordenarPorMezcla(String[] arr, int inicio, int fin) {
        // Divide el arreglo en mitades recursivamente hasta que quede de tamaño 1
        if (inicio < fin) {
            int medio = (inicio + fin) / 2;
            ordenarPorMezcla(arr, inicio, medio);       // Ordena la mitad izquierda
            ordenarPorMezcla(arr, medio + 1, fin);      // Ordena la mitad derecha
            merge(arr, inicio, medio, fin);              // Combina ambas mitades ordenadas
        }
        // Complejidad temporal: O(n log n)
    }

    public static void merge(String[] arr, int inicio, int medio, int fin) {
        // Calcula los tamaños de los subarreglos
        int n1 = medio - inicio + 1;
        int n2 = fin - medio;

        // Crea arreglos temporales
        String[] izquierda = new String[n1];
        String[] derecha = new String[n2];

        // Copia los datos a los subarreglos temporales
        for (int i = 0; i < n1; i++) izquierda[i] = arr[inicio + i];
        for (int j = 0; j < n2; j++) derecha[j] = arr[medio + 1 + j];

        int i = 0, j = 0, k = inicio;
        // Combina las dos mitades ordenadas comparando elemento por elemento
        while (i < n1 && j < n2) {
            if (izquierda[i].compareToIgnoreCase(derecha[j]) <= 0) {
                arr[k++] = izquierda[i++];
            } else {
                arr[k++] = derecha[j++];
            }
        }

        // Copia los elementos restantes de ambos lados (si los hay)
        while (i < n1) arr[k++] = izquierda[i++];
        while (j < n2) arr[k++] = derecha[j++];
    }

    public static void ordenarPorRapido(String[] arr, int inicio, int fin) {
        //Aplica el metodo de particion
        if (inicio < fin) {
            int pi = particionar(arr, inicio, fin);  // Divide el arreglo alrededor del pivote
            ordenarPorRapido(arr, inicio, pi - 1);   // Ordena la parte izquierda del pivote
            ordenarPorRapido(arr, pi + 1, fin);      // Ordena la parte derecha del pivote
        }
        // Complejidad temporal promedio: O(n log n)
        // Peor caso: O(n²)
    }

    public static int particionar(String[] arr, int inicio, int fin) {
        String pivote = arr[fin]; // Usa el último elemento como pivote
        int i = inicio - 1;
        // Coloca todos los elementos menores o iguales al pivote a su izquierda
        for (int j = inicio; j < fin; j++) {
            if (arr[j].compareToIgnoreCase(pivote) <= 0) {
                i++;
                // Intercambia arr[i] y arr[j]
                String temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        // Coloca el pivote en su posición final
        String temp = arr[i + 1];
        arr[i + 1] = arr[fin];
        arr[fin] = temp;
        return i + 1; // Devuelve la posición del pivote
    }

    public static int busquedaBinaria(String[] arr, String palabra) {
        int inicio = 0, fin = arr.length - 1;
        // Busca la palabra mientras el rango sea válido
        while (inicio <= fin) {
            int medio = (inicio + fin) / 2;
            int comparacion = palabra.compareToIgnoreCase(arr[medio]);

            if (comparacion == 0) {
                return medio; // Palabra encontrada
            } else if (comparacion < 0) {
                fin = medio - 1; // Buscar en la mitad izquierda
            } else {
                inicio = medio + 1; // Buscar en la mitad derecha
            }
        }
        // Si no se encuentra la palabra, devuelve -1
        // Complejidad temporal: O(log n)
        return -1;
    }

}
