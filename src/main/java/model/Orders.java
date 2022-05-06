package model;

public class Orders {

    private int id;
    private int idClient;
    private int idproduct;
    private int quantity;
    private double price;

    public Orders(){

    }

    Orders(int id, int idcl, int idprod, int quan, double price){
        super();
        this.id = id;
        this.idClient = idcl;
        this.idproduct = idprod;
        this.quantity = quan;
        this.price = price;
    }

    public Orders(int idcl, int idprod, int quan, double price){
        super();
        this.idClient = idcl;
        this.idproduct = idprod;
        this.quantity = quan;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public int getIdClient() {
        return idClient;
    }

    public int getIdproduct() {
        return idproduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public void setIdproduct(int idproduct) {
        this.idproduct = idproduct;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

