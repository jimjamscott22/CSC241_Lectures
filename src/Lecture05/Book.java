package Lecture05;

interface Book {
    String publisher = "SUNY Oswego Press";

    void showPublisher();
    void showTitle();
    void showISBN();
    void showNumOfPages();
}

class Textbook implements Book {
    private String title;
    private String author;
    private String isbn;
    private int numOfPages;

    Textbook(String title, String author, String isbn, int numOfPages) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.numOfPages = numOfPages;
    }

    public void showPublisher() { System.out.println("Publisher:\t" + publisher); }

    public void showTitle() { System.out.println("Title:\t\t" + title); }

    public void showISBN(){ System.out.println("ISBN:\t\t" + isbn); }

    public void showNumOfPages() { System.out.println("Pages:\t\t" + numOfPages); }

    public void setNumOfPages(int numOfPages) { this.numOfPages = numOfPages;  }
}