package model;

public class Client {

    private int id;
    private String Name;
    private String Address;
    private String Email;
    private int Age;

    public Client(){

    }

    public Client(int idClient, String name, String address, String email, int age){
        super();
        this.id = idClient;
        this.Name = name;
        this.Address = address;
        this.Email = email;
        this.Age = age;
    }

    public Client(String name, String address, String email, int age) {
        super();
        this.Name = name;
        this.Address = address;
        this.Email = email;
        this.Age = age;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public String getAddress() {
        return Address;
    }

    public String getEmail() {
        return Email;
    }

    public int getAge() {
        return Age;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setAge(int age) {
        Age = age;
    }

    @Override
    public String toString(){
        return "Client : [id = " + id + ", name=" + Name + ", address=" + Address + ", email=" + Email + ", age=" + Age
                + "]";
    }
}

