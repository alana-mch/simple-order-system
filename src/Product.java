abstract class Product
{
  private int code;
  private int price;

  public Product(int code, int price)
  {
    this.code = code;
    this.price = price;
  }

  public int getPrice()
  {
    return price;
  }

  abstract String getDescription();

  public int getCode()
  {
    return code;
  }
}
