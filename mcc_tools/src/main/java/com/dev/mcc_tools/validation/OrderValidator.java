package com.dev.mcc_tools.validation;

import com.dev.mcc_tools.domain.Order;

import java.util.HashMap;
import java.util.Map;

public class OrderValidator extends MccValidator {


    public void checkOrderFormat(Order order) {
//        try {
//            checkStatus(order.getStatus());
//            Integer.parseInt(order.getInvoiceNumber());
//        } catch (NumberFormatException e) {
//            setErrors("invoiceNumber", "Invoice number must not contain any letters");
//        }
        checkStatus(order.getStatus());
        checkInvoice(order.getInvoiceNumber());
    }

    public Boolean checkStatus(String status) {
        if (!status.equals("pending") && !status.equals("arrived") && !status.equals("delivered")) {
            setErrors("status", "status must be pending, arrived, or delivered ");
            return false;
        }
        return true;
    }

    public Boolean checkInvoice(String invoiceNumber) {
        try {
            Integer.parseInt(invoiceNumber);
            return true;
        } catch (NumberFormatException e) {
            setErrors("invoiceNumber", "Invoice number must not contain any letters");
            return false;
        }
    }
}