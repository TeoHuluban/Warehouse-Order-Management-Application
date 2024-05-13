package Model;

import java.time.LocalDateTime;
/**
 * This immutable class models the bill that has an id, date, idOrder, and totalPrice.
 */
public record Bill(int id, LocalDateTime date, int idOrder, int totalPrice) {
    public Bill(int id, LocalDateTime date, int idOrder, int totalPrice) {
        this.id = id;
        this.date = date;
        this.idOrder = idOrder;
        this.totalPrice = totalPrice;
    }
}
