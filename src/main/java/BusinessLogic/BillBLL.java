package BusinessLogic;

import DataAccess.BillDAO;
import Model.Bill;

/**
 * This class manages and calls the methods from the data access class
 */
public class BillBLL {
    private final BillDAO billDAO;

    public BillBLL() {
        billDAO = new BillDAO();
    }

    public void insertBill(Bill c) {
        billDAO.insert(c);
    }

}
