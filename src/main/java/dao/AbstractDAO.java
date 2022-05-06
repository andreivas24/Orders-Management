package dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;
import model.Client;


public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }

    private String createSelectQuery(){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        return sb.toString();
    }

    public String createSelectWhereQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

    private String createInsertQuery(ArrayList<String> field){
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO "+type.getSimpleName()+ " (");
        for(int i = 0;i < field.size();i++){
            if(i == 0){
                continue;
            }
            if(i == field.size()-1){
                sb.append(field.get(i)+") ");
                break;
            }
            sb.append(field.get(i)+",");
        }
        sb.append(" VALUES (");
        for(int i = 0;i < field.size();i++){
            if(i == 0){
                continue;
            }
            if(i == field.size()-1){
                sb.append("?);");
                break;
            }
            sb.append("?,");
        }
        return sb.toString();
    }

    private String createUpdateQuery(ArrayList<String> fields){
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE " + type.getSimpleName());
        sb.append(" SET ");
        for(int i = 0;i < fields.size();i++){
            if(i == fields.size()-1){
                sb.append(fields.get(i)+" = ? ");
                break;
            }
            sb.append(fields.get(i)+" = ?, ");
        }
        sb.append("WHERE id = ?;");
        return sb.toString();
    }

    private String createDeleteQuery(String field){
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE ");
        sb.append(field);
        sb.append(" = ?;");
        return sb.toString();
    }

    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            return createObjects(resultSet);
        } catch (SQLException throwables) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + throwables.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectWhereQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    public T findByName(String name) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectWhereQuery("name");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:findByName " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    public List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T)ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    private void getValuesFromT(T t,ArrayList<Object> values,ArrayList<String> fields){
        for(Field field:t.getClass().getDeclaredFields()){
            field.setAccessible(true);
            fields.add(field.getName());
            Object value;
            try {
                value = field.get(t);
                values.add(value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public void insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        ArrayList<Object> values = new ArrayList<>();
        ArrayList<String> fields = new ArrayList<String>();
        getValuesFromT(t,values,fields);
        String query = createInsertQuery(fields);
        System.out.println(query);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            for(int i=1;i<fields.size();i++){
                if(values.get(i) instanceof Integer){
                    statement.setInt(i,((Integer) values.get(i)).intValue());
                }
                if(values.get(i) instanceof String){
                    statement.setString(i,values.get(i).toString());
                }
                if(values.get(i) instanceof Double){
                    statement.setDouble(i,((Double) values.get(i)).doubleValue());
                }
            }
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            ConnectionFactory.close(connection);
            ConnectionFactory.close(statement);
        }
    }

    public void update(T t, int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ArrayList<Object> values = new ArrayList<>();
        ArrayList<String> fields = new ArrayList<String>();
        getValuesFromT(t,values,fields);
        String query = createUpdateQuery(fields);
        try {
            int i;
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            for(i=0;i<fields.size();i++){
                if(values.get(i) instanceof Integer){
                    statement.setInt(i+1,((Integer) values.get(i)).intValue());
                }
                if(values.get(i) instanceof String){
                    statement.setString(i+1,values.get(i).toString());
                }
                if(values.get(i) instanceof Double){
                    statement.setDouble(i+1,((Double) values.get(i)).doubleValue());
                }
            }
            statement.setInt(i+1, id);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            ConnectionFactory.close(connection);
            ConnectionFactory.close(statement);
        }
    }

    public void delete(int id){
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createDeleteQuery("id");
        T t = findById(id);
        try {
            int i;
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            ConnectionFactory.close(connection);
            ConnectionFactory.close(statement);
        }

    }
}
