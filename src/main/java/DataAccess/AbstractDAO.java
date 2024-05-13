package DataAccess;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import Connection.ConnectionFactory;

/**
 * This class is a generic class which creates CRUD queries to manipulate the database
 */
public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * This method creates a string representing a SELECT query.
     * @param field: the field for the WHERE clause
     * @return the SELECT query as a string
     */
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

    /**
     * This method extracts all records from a table in the database
     * @return the records found
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM " + type.getSimpleName();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * This method searches the record with the specified id
     * @param id: represents the id of the record
     * @return the record found
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
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

    /**
     * This method creates objects from a query result
     * @param resultSet: the result of a query
     * @return a list of created objects
     */
    private List<T> createObjects(ResultSet resultSet) {
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

    /**
     * This method creates a string representing a DELETE query.
     * @param field: the field for the WHERE clause
     * @return the DELETE query as a string
     */
    private String createDeleteQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

    /**
     * This method searches and deletes the record with the specified id
     * @param id: represents the id of the record
     * @return true if the deletion was successful, and false otherwise
     */
    public boolean deleteById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();

            String query = createDeleteQuery("id");
            statement = connection.prepareStatement(query);

            statement.setLong(1, id);

            int nr = statement.executeUpdate();
            return nr > 0;

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:delete " + e.getMessage());
            return false;
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * This method stores an object in the database
     * @param t: the object to be stored
     * @return the object
     */
    public T insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionFactory.getConnection();

            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO ");
            sb.append(type.getSimpleName());
            sb.append(" (");

            Field[] fields = type.getDeclaredFields();
            for (Field f: fields) {
                sb.append(f.getName());
                sb.append(", ");
            }

            sb.delete(sb.length() - 2, sb.length());
            sb.append(") VALUES (");

            for (Field f: fields) {
                sb.append("?");
                sb.append(", ");
            }

            sb.delete(sb.length() - 2, sb.length());
            sb.append(")");

            statement = connection.prepareStatement(sb.toString(), Statement.RETURN_GENERATED_KEYS);

            int i = 1;                           //indexul parametrului (field-ului)
            for (Field f : fields) {
                f.setAccessible(true);           //setez field-ul sa fie modificabil
                Object value = f.get(t);         //iau valoarea din field-ul curent
                statement.setObject(i++, value); //adaug valoarea in statement
            }

            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                int generatedId = resultSet.getInt(1);
                Field idField = type.getDeclaredField("id");
                idField.setAccessible(true);
                idField.set(t, generatedId);
            }

        } catch (SQLException | IllegalAccessException | NoSuchFieldException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return t;
    }

    /**
     * This method updates the information about an object stored in the database
     * @param t the object with updated fields
     * @return the object
     */
    public T update(T t) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();

            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE ");
            sb.append(type.getSimpleName());
            sb.append(" SET ");

            Field[] fields = type.getDeclaredFields();
            for (Field f : fields) {
                if (!f.getName().equals("id")) {
                    sb.append(f.getName());
                    sb.append(" = ?, ");
                }
            }

            sb.delete(sb.length() - 2, sb.length());
            sb.append(" WHERE id = ?");

            statement = connection.prepareStatement(sb.toString());

            int i = 1;
            for (Field f : fields) {
                if (!f.getName().equals("id")) {
                    f.setAccessible(true);
                    Object value = f.get(t);
                    statement.setObject(i++, value);
                }
            }

            Field idField = type.getDeclaredField("id");
            idField.setAccessible(true);
            Object idValue = idField.get(t);
            statement.setObject(i, idValue);

            statement.executeUpdate();

        } catch (SQLException | IllegalAccessException | NoSuchFieldException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return t;
    }

}
