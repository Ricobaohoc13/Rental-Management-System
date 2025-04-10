package Data.Model;

public class Property {
    private double price;
    private String address;

    public void setPrice(double price)
    {
        this.price = price;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }
    public double  getPrice()
    {
        return price;
    }
    public String getAddress()
    {
        return address;
    }
}
