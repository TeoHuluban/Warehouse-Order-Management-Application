package DataAccess;

import Model.Bill;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.logging.Level;
import Connection.ConnectionFactory;
/**
 * This class extends the AbstractDAO class
 */
public class BillDAO extends AbstractDAO<Bill>{
    /**
     * This method overrides the insert method from AbstractDAO, such that the id will not be updated
     * @param t: the object to be stored
     * @return the object
     */
    @Override
    public Bill insert(Bill t) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionFactory.getConnection();

            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO ");
            sb.append(Bill.class.getSimpleName());
            sb.append(" (");

            Field[] fields = Bill.class.getDeclaredFields();
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
        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, Bill.class.getName() + "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return t;
    }
}
