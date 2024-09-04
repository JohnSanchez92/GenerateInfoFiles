package socket.generateinfofiles;

/**
 *
 * @author JHON SANCHEZ
 */
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GenerateInfoFiles {

    // Método para generar archivo de ventas de un vendedor
    public static void createSalesMenFile(int randomSalesCount, String name, long id) {
        String fileName = name.replace(" ", "_") + "_" + id + "_sales.txt";
        Random random = new Random();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("CC;" + id); // Ejemplo de TipoDocumento y NúmeroDocumento del vendedor
            writer.newLine();

            for (int i = 0; i < randomSalesCount; i++) {
                int productId = random.nextInt(100) + 1; // ID de producto pseudoaleatorio
                int quantity = random.nextInt(10) + 1;   // Cantidad vendida pseudoaleatoria
                writer.write(productId + ";" + quantity);
                writer.newLine();
            }

            System.out.println("Archivo de ventas generado: " + fileName);

        } catch (IOException e) {
            System.err.println("Error al generar archivo de ventas: " + e.getMessage());
        }
    }

    // Método para generar archivo de información de productos
    public static void createProductsFile(int productsCount) {
        String fileName = "products.txt";
        Random random = new Random();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < productsCount; i++) {
                int productId = i + 1;
                String productName = "Producto" + productId;
                double price = random.nextDouble() * 100; // Precio pseudoaleatorio

                writer.write(productId + ";" + productName + ";" + String.format("%.2f", price));
                writer.newLine();
            }

            System.out.println("Archivo de productos generado: " + fileName);

        } catch (IOException e) {
            System.err.println("Error al generar archivo de productos: " + e.getMessage());
        }
    }

    // Método para generar archivo de información de vendedores
    public static void createSalesManInfoFile(int salesmanCount) {
        String fileName = "salesmen_info.txt";
        String[] names = {"Juan", "Carlos", "Maria", "Ana", "Luis", "Pedro"};
        String[] surnames = {"Perez", "Gomez", "Rodriguez", "Lopez", "Martinez"};

        Random random = new Random();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < salesmanCount; i++) {
                String name = names[random.nextInt(names.length)];
                String surname = surnames[random.nextInt(surnames.length)];
                long id = 1000000L + random.nextInt(9000000);

                writer.write("CC;" + id + ";" + name + ";" + surname);
                writer.newLine();
            }

            System.out.println("Archivo de información de vendedores generado: " + fileName);

        } catch (IOException e) {
            System.err.println("Error al generar archivo de información de vendedores: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Ejemplo de uso de los métodos de generación de archivos
        createSalesManInfoFile(5);        // Generar archivo con 5 vendedores
        createProductsFile(10);           // Generar archivo con 10 productos
        createSalesMenFile(5, "Juan Perez", 123456789L); // Generar archivo de ventas para un vendedor
    }
}
