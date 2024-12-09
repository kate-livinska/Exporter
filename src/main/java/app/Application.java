package app;

import exporter.DataExporter;
import exporter.ExcelExporter;
import model.Book;
import model.BookBuilder;

import java.io.FileOutputStream;
import java.io.IOException;

public class Application {

    public static void main(String[] args) {
        Book book1 = new BookBuilder().id(1).title("Effective Java").author("Joshua Bloch")
                .description("\"A comprehensive guide to best practices in Java\"")
                .publisher("Addison-Wesley").category("Programming").type("Book").createBook();

        Book book2 = new BookBuilder().id(2).title("Clean Code").author("Robert C. Martin")
                .description("A Handbook of Agile Software Craftsmanship").publisher("Prentice Hall")
                .type("Book").createBook();

        Book book3 = new BookBuilder().id(3).title("The Pragmatic Programmer").author("Andrew Hunt, David Thomas")
                .description("Your Journey to Mastery").publisher("Addison-Wesley")
                .category("Programming").type("Book").createBook();

        Book book4 = new BookBuilder().id(4).title("Introduction to Algorithms")
                .author("Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest, Clifford Stein")
                .description("The standard book for algorithms").publisher("MIT Press")
                .category("Algorithms").type("Book").createBook();

        Book[] books = new Book[]{book1, book2, book3, book4};

        try (
                FileOutputStream fos = new FileOutputStream("output.xlsx")
                ) {
            DataExporter excelExporter = new ExcelExporter();

            excelExporter.exportData(books, fos);
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
