package model.Database;

import java.util.Date;

public class Payment {
    private int paymentID;
    private Order order;
    private String payment_method;
    private Date payment_Date;
    private double amount;
    private String status;
}
