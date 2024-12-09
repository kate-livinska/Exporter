package model;

public class BookBuilder {
    private int id = 0;
    private String title = "";
    private String author = "";
    private String description = "";
    private String publisher = "";
    private String category = "";
    private String type = "";

    public BookBuilder id(int id) {
        this.id = id;
        return this;
    }

    public BookBuilder title(String title) {
        this.title = title;
        return this;
    }

    public BookBuilder author(String author) {
        this.author = author;
        return this;
    }

    public BookBuilder description(String description) {
        this.description = description;
        return this;
    }

    public BookBuilder publisher(String publisher) {
        this.publisher = publisher;
        return this;
    }

    public BookBuilder category(String category) {
        this.category = category;
        return this;
    }

    public BookBuilder type(String type) {
        this.type = type;
        return this;
    }

    public Book createBook() {
        return new Book(id, title, author, description, publisher, category, type);
    }
}