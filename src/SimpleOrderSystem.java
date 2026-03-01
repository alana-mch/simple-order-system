import java.util.ArrayList;

public class SimpleOrderSystem
{
  public static final int ADD_CUSTOMER = 1;
  public static final int ADD_ORDER = 2;
  public static final int ADD_PRODUCT = 3;
  public static final int LIST_CUSTOMERS = 4;
  public static final int ORDER_TOTAL = 5;
  public static final int DISPLAY_ORDERS_CONTAINING = 6;
  public static final int QUIT = 10;
  private Input in = new Input();
  private ArrayList<Customer> customers;
  private ArrayList<Product> products;

  public SimpleOrderSystem()
  {
    customers = new ArrayList<Customer>();
    products = new ArrayList<Product>();
  }

  public void run()
  {
    while(true)
    {
      displayMenu();
      int option = getMenuInput();
      if (option == QUIT)
      {
        break;
      }
      doOption(option);
    }
  }

  private void displayMenu()
  {
    System.out.println("Simple Order System Menu");
    System.out.println(ADD_CUSTOMER + ". Add Customer");
    System.out.println(ADD_ORDER + ". Add Order");
    System.out.println(ADD_PRODUCT + ". Add Product");
    System.out.println(LIST_CUSTOMERS + ". List Customers");
    System.out.println(ORDER_TOTAL + ". Orders Total");
    System.out.println(DISPLAY_ORDERS_CONTAINING + ". Display Orders Containing Product");

    System.out.println();
    System.out.println(QUIT + ". Quit");
  }
  
  private void doOption(int option)
  {
    switch (option)
    {
      case ADD_CUSTOMER:
        addCustomer();
        break;
      case ADD_ORDER:
         addOrder();
        break;
      case ADD_PRODUCT:
         addProduct();
         break;
      case LIST_CUSTOMERS:
        listCustomers();
        break;
      case ORDER_TOTAL:
        overallTotal();
        break;
      case DISPLAY_ORDERS_CONTAINING:
        displayOrdersContaining();
        break;
      default:
        System.out.println("Invalid option - try again");
    }
  }

  private int getMenuInput()
  {
    System.out.print("Enter menu selection: ");
    int option = in.nextInt();
    in.nextLine();
    return option;
  }

  private void addCustomer()
  {
    System.out.println("Add new customer");
    System.out.println("Enter first name:");
    String firstName = in.nextLine();
    System.out.println("Enter last name:");
    String lastName = in.nextLine();
    System.out.println("Enter address:");
    String address = in.nextLine();
    System.out.println("Enter postcode:");
    String postcode = in.nextLine();
    System.out.println("Enter phone number:");
    String phone = in.nextLine();
    System.out.println("Enter email address:");
    String email = in.nextLine();
    Customer customer = new Customer(firstName,lastName,address,postcode,phone,email);
    customers.add(customer);
  }

  private void addOrder()
  {
    Customer customer = findCustomer();
    if (customer == null)
    {
      System.out.println("Unable to add order");
      return;
    }
    Order order = new Order();
    addLineItems(order);
    if (order.getLineItemCount() == 0)
    {
      System.out.println("Cannot have an empty order");
      return;
    }
    customer.addOrder(order);
  }

  private Customer findCustomer()
  {
    System.out.print("Enter customer last name: ");
    String lastName = in.nextLine();
    System.out.print("Enter customer first name: ");
    String firstName = in.nextLine();
    return getCustomer(lastName, firstName);
  }

  private Customer getCustomer(String lastName, String firstName)
  {
    for (Customer customer : customers)
    {
      if (customer.getLastName().equals(lastName)
          && customer.getFirstName().equals(firstName))
      {
        return customer;
      }
    }
    return null;
  }

  private void addLineItems(Order order)
  {
    while (true)
    {
      System.out.print("Enter line item (y/n): ");
      String reply = in.nextLine();
      if (reply.startsWith("y"))
      {
        LineItem item = getLineItem();
        if (item != null)
        {
          order.add(item);
        }
      }
      else
      {
        break;
      }
    }
  }

  private LineItem getLineItem()
  {
    System.out.print("Enter product code: ");
    int code = in.nextInt();
    in.nextLine();
    Product product = getProduct(code);
    if (product == null)
    {
      System.out.println("Invalid product code");
      return null;
    }
    System.out.print("Enter quantity: ");
    int quantity = in.nextInt();
    in.nextLine();
    return new LineItem(quantity,product);
  }

  private Product getProduct(int code)
  {
    for (Product product : products)
    {
      if (product.getCode() == code)
      {
        return product;
      }
    }
    return null;
  }

  private void addProduct()
  {
    System.out.print("Enter product code: ");
    int code = in.nextInt();
    in.nextLine();
    if (!isAvailableProductCode(code))
    {
      return;
    }
    System.out.print("Enter product price: ");
    int price = in.nextInt();
    in.nextLine();

    if (code>20) //book
    {
      System.out.print("Enter Title: ");
      String title= in.nextLine();
      System.out.print("Enter Author: ");
      String author= in.nextLine();
      Product product = new Book(title, author, code,price);
      products.add(product);
    }
    else
    {
      System.out.print("Enter Description: ");
      String description= in.nextLine();
      Product product = new DogProduct(code,description,price);
      products.add(product);
    }

  }

  private boolean isAvailableProductCode(int code)
  {
    if (code < 1)
    {
      return false;
    }
    for (Product product : products)
    {
      if (product.getCode() == code)
      {
        return false;
      }
    }
    return true;
  }

  public void listCustomers()
  {
    System.out.println("List of customers");
    for (Customer customer : customers)
    {
      System.out.println("Name: " + customer.getLastName()
                                  + ", "
                                  + customer.getFirstName());
      System.out.println("Address: " + customer.getAddress());
      System.out.println("Phone: " + customer.getPhone());
      System.out.println("Email: " + customer.getEmail());
      System.out.println("Orders made: " + customer.getOrders().size());
      System.out.println("Total for all orders: " + customer.getTotalForAllOrders());
    }
  }

  public void overallTotal()
  {
    int total = 0;
    for (Customer customer : customers)
    {
      total += customer.getTotalForAllOrders();
    }
    System.out.println("Total for all orders: " + total);
  }

  public void displayOrdersContaining()
  {
    System.out.println("Enter Product Code");
    int code = in.nextInt();
    if (isAvailableProductCode(code))
    {
      System.out.println("Invalid Product code");
    }
    else
    {
      for (Customer customer : customers)
      {
        for (Order order : customer.getOrders())
        {
          for (LineItem lineItem : order.getLineItems())
          {
            if (lineItem.getProduct().getCode() == code)
            {
              System.out.println(order + " " + customer.getFirstName() + " " + customer.getLastName());
            }
          }
        }
      }
    }
  }

  public void addExampleData()
  {
    Customer customer1 = new Customer("Alana" , "McHugh", "125 Gower Street", "NW12 745", "07522430030", "alanamchugh77@gmail.com");
    Customer customer2 = new Customer("Jodie" , "Loaf", "32 Marsh Avenue", "BH3 456", "07885630777", "jodieloaf@gmail.com");
    Order order1 = new Order();
    Order order2 = new Order();
    Order order3 = new Order();
    Product product1 = new DogProduct(10, "Dog Toy", 8);
    Product product2 = new DogProduct(11, "Dog Treat", 2);
    Product product3 = new DogProduct(12, "Dog Coat", 15);
    Product product4 = new DogProduct(13, "Dog Bed", 11);
    Product product5 = new Book("The Seven Husbands of Evelyn Hugo", "Taylor Jenkins Reid", 25, 10);
    products.add(product1);
    products.add(product2);
    products.add(product3);
    products.add(product4);
    products.add(product5);

    order1.add(new LineItem(2, product2 ));
    order1.add(new LineItem(3, product1 ));
    order2.add(new LineItem(1, product3 ));
    order2.add(new LineItem(1, product5 ));
    order3.add(new LineItem(1, product4 ));
    customer1.addOrder(order1);
    customer2.addOrder(order2);
    customer2.addOrder(order3);
    customers.add(customer1);
    customers.add(customer2);

  }

  public static void main(String[] args)
  {
    SimpleOrderSystem orderSystem = new SimpleOrderSystem();
    orderSystem.addExampleData();
    orderSystem.run();
  }
}
