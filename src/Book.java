class Book extends Product {

    private String title;
    private String author;

    public Book(String title, String author, int code, int price)
    {
        super(code, price);
        this.title = title;
        this.author = author;
    }

    public String getDescription()
    {
        return title + " " + author;
    }

}
