class DogProduct extends Product
{
    private String description;

    public DogProduct(int code, String description, int price)
    {
        super(code,price);
        this.description = description + "For Dogs";
    }
    public String getDescription()
    {
        return description;
    }

}
