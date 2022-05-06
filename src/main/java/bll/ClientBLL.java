package bll;

import bll.validators.EmailValidator;
import bll.validators.Validator;
import dao.ClientDAO;
import model.Client;
import model.Student;

import java.util.List;
import java.util.NoSuchElementException;

public class ClientBLL {

    private Validator validator;
    private ClientDAO clientDAO;

    public ClientBLL(){
        validator = new EmailValidator();
        clientDAO = new ClientDAO();
    }

    public Client findClientById(int id) {
        Client cl = clientDAO.findById(id);
        if (cl == null) {
            throw new NoSuchElementException("The student with id =" + id + " was not found!");
        }
        return cl;
    }

    public Client findClientByName(String name) {
        Client cl = clientDAO.findByName(name);
        if (cl == null) {
            throw new NoSuchElementException("The student with name =" + name + " was not found!");
        }
        return cl;
    }

    public void insertClient(Client cl){
        validator.validate(cl);
        clientDAO.insert(cl);
    }

    public List<Client> findAllClients(){
        List<Client> list = clientDAO.findAll();
        if(list == null){
            throw new NoSuchElementException("There are no clients");
        }
        return list;
    }

    public void updateClientById(Client cl, int id){
        validator.validate(cl);
        clientDAO.update(cl,id);
    }

    public void deleteClient(String name)
    {
        Client c = clientDAO.findByName(name);
        clientDAO.delete(c.getId());
    }

}