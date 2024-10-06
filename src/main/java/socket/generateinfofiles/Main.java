/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package socket.generateinfofiles;

/**
 *
 * @author JHON SANCHEZ
 */
import java.io.*;
import java.util.*;

public class Main {

    // Method to read the products file and return a map of product ID to product name and price
    public static Map<Integer, Product> loadProducts(String fileName) {
        Map<Integer, Product> products = new HashMap<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                double price = Double.parseDouble(parts[2]);
                products.put(id, new Product(id, name, price));
            }
            
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de productos: " + e.getMessage());
        }
        
        return products;
    }

    // Method to read the salesmen info file and return a map of document ID to salesman name
    public static Map<Long, String> loadSalesmen(String fileName) {
        Map<Long, String> salesmen = new HashMap<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                long id = Long.parseLong(parts[1]);
                String name = parts[2] + " " + parts[3];
                salesmen.put(id, name);
            }
            
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de información de vendedores: " + e.getMessage());
        }
        
        return salesmen;
    }

    // Method to process sales files and generate a report of total sales per salesman
    public static Map<Long, Double> processSalesFiles(String folderPath, Map<Integer, Product> products) {
        Map<Long, Double> salesBySalesman = new HashMap<>();
        
        File folder = new File(folderPath);
        File[] salesFiles = folder.listFiles((dir, name) -> name.endsWith("_sales.txt"));
        
        if (salesFiles != null) {
            for (File salesFile : salesFiles) {
                try (BufferedReader reader = new BufferedReader(new FileReader(salesFile))) {
                    String[] header = reader.readLine().split(";");
                    long salesmanId = Long.parseLong(header[1]);
                    
                    String line;
                    double totalSales = 0.0;
                    
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(";");
                        int productId = Integer.parseInt(parts[0]);
                        int quantity = Integer.parseInt(parts[1]);
                        Product product = products.get(productId);
                        if (product != null) {
                            totalSales += product.price * quantity;
                        }
                    }
                    
                    salesBySalesman.put(salesmanId, salesBySalesman.getOrDefault(salesmanId, 0.0) + totalSales);
                    
                } catch (IOException e) {
                    System.err.println("Error al procesar archivo de ventas: " + e.getMessage());
                }
            }
        }
        
        return salesBySalesman;
    }

    // Method to generate a report of sales per salesman, ordered by total sales
    public static void generateSalesReport(String outputFileName, Map<Long, Double> salesBySalesman, Map<Long, String> salesmen) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
            salesBySalesman.entrySet().stream()
                .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue())) // Sort by total sales, descending
                .forEach(entry -> {
                    try {
                        writer.write(salesmen.get(entry.getKey()) + ";" + String.format("%.2f", entry.getValue()));
                        writer.newLine();
                    } catch (IOException e) {
                        System.err.println("Error al escribir el reporte de ventas: " + e.getMessage());
                    }
                });
            
            System.out.println("Reporte de ventas generado: " + outputFileName);
            
        } catch (IOException e) {
            System.err.println("Error al generar reporte de ventas: " + e.getMessage());
        }
    }

    // Method to generate a report of product sales, ordered by quantity sold
    public static void generateProductReport(String outputFileName, Map<Integer, Product> products, Map<Integer, Integer> productSales) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
            productSales.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue())) // Sort by quantity sold, descending
                .forEach(entry -> {
                    Product product = products.get(entry.getKey());
                    if (product != null) {
                        try {
                            writer.write(product.name + ";" + product.price + ";" + entry.getValue());
                            writer.newLine();
                        } catch (IOException e) {
                            System.err.println("Error al escribir el reporte de productos: " + e.getMessage());
                        }
                    }
                });
            
            System.out.println("Reporte de productos generado: " + outputFileName);
            
        } catch (IOException e) {
            System.err.println("Error al generar reporte de productos: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Load products and salesmen info
        Map<Integer, Product> products = loadProducts("products.txt");
        Map<Long, String> salesmen = loadSalesmen("salesmen_info.txt");
        
        // Process sales files and get total sales per salesman
        Map<Long, Double> salesBySalesman = processSalesFiles("sales_files_folder", products);
        
        // Generate sales report
        generateSalesReport("sales_report.txt", salesBySalesman, salesmen);
        
        // Generate product sales report
        Map<Integer, Integer> productSales = new HashMap<>(); // This would be filled by processing sales data
        generateProductReport("product_report.txt", products, productSales);
    }
}

// Class to represent a product with ID, name, and price
class Product {
    int id;
    String name;
    double price;

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
