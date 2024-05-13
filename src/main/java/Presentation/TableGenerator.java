package Presentation;

import java.lang.reflect.Field;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * This class is a generic class that uses reflection to create a model for a table based on the fields of a class specified
 * through the generic parameter
 * @param <T> generic parameter that will represent a class
 */
public class TableGenerator<T> {
    private final Class<T> type;

    public TableGenerator(Class<T> type) {
        this.type = type;
    }

    /**
     * The main method of this class that creates the model for the table using reflection
     * @param objects the content of the table
     * @param table a reference to a table
     */
    public void generateTable(List<T> objects, JTable table) {
        DefaultTableModel model = new DefaultTableModel();

        Field[] fields = type.getDeclaredFields();
        for (Field field : fields) {
            model.addColumn(field.getName());
        }

        for (T obj : objects) {
            Object[] row = new Object[fields.length];
            int i = 0;
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    Object value = field.get(obj);
                    row[i++] = value;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            model.addRow(row);
        }

        table.setModel(model);
    }
}
