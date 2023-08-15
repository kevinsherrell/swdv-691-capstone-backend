package com.dev.mcc_tools.validation;

import com.dev.mcc_tools.domain.Order;

import java.util.HashMap;
import java.util.Map;

public class OrderValidator extends MccValidator {


    public void checkOrderFormat(Order order) {
        try {
            if (!order.getStatus().equals("pending") && !order.getStatus().equals("arrived") && !order.getStatus().equals("delivered")) {
                setErrors("status", "status must be pending, arrived, or delivered ");
            }
            Integer.parseInt(order.getInvoiceNumber());
        } catch (NumberFormatException e) {
            setErrors("invoiceNumber", "Invoice number must not contain any letters");
        }
    }

}