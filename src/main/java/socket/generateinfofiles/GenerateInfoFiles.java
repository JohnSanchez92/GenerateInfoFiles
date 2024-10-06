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

    // Method to generate sales file of a seller
    public static void createSalesMenFile(int randomSalesCount, String name, long id) {
        String fileName = name.replace(" ", "_") + "_" + id + "_sales.txt";
        Random random = new Random();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("CC;" + id); // Example of Document Type and Seller Document Number
            writer.newLine();

            for (int i = 0; i < randomSalesCount; i++) {
                int productId = random.nextInt(100) + 1; // Pseudo-random product ID
                int quantity = random.nextInt(10) + 1;   // Pseudo-random quantity sold
                writer.write(productId + ";" + quantity);
                writer.newLine();
            }

            System.out.println("Archivo de ventas generado: " + fileName);

        } catch (IOException e) {
            System.err.println("Error al generar archivo de ventas: " + e.getMessage());
        }
    }

    // Method for generating product information file
    public static void createProductsFile(int productsCount) {
        String fileName = "products.txt";
        Random random = new Random();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < productsCount; i++) {
                int productId = i + 1;
                String productName = "Producto" + productId;
                double price = random.nextDouble() * 100; // Pseudo-random price

                writer.write(productId + ";" + productName + ";" + String.format("%.2f", price));
                writer.newLine();
            }

            System.out.println("Archivo de productos generado: " + fileName);

        } catch (IOException e) {
            System.err.println("Error al generar archivo de productos: " + e.getMessage());
        }
    }

    // Method for generating seller information file
    public static void createSalesManInfoFile(int salesmanCount) {
        String fileName = "salesmen_info.txt";
        String[] names = {"John", "Pablo", "Martha", "Maricela", "Juan"};
        String[] surnames = {"Sanchez", "Camargo", "Mera", "Lopez", "Tellez"};

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
        //Example of using file generation methods
        createSalesManInfoFile(5);        // Generate file with 5 sellers
        createProductsFile(10);           // Generate file with 10 products
        createSalesMenFile(5, "John Sanchez", 123456789L); // Generate sales file for a salesperson
    }
}
