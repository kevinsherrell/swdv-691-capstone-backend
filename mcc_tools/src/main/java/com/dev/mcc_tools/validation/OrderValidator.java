package com.dev.mcc_tools.validation;

import com.dev.mcc_tools.domain.Order;

public class OrderValidator extends MccValidator {


    public void initiateOrderChecks(Order order) throws Exception {
//        try {
//            checkStatus(order.getStatus());
//            Integer.parseInt(order.getInvoiceNumber());
//        } catch (NumberFormatException e) {
//            setErrors("invoiceNumber", "Invoice number must not contain any letters");
//        }
        checkForNullFields(order);
        checkStatus(order.getStatus());
        checkInvoice(order.getInvoiceNumber());

    }

    public void checkStatus(String status) throws Exception {
        if (!status.equals("pending") && !status.equals("arrived") && !status.equals("delivered")) {
            setErrors("status", "status must be pending, arrived, or delivered ");
        }
        if (!errors.isEmpty()) {
            throw new Exception();
        }
    }

    public void checkInvoice(String invoiceNumber) throws Exception {
        try {
            Integer.parseInt(invoiceNumber);
        } catch (NumberFormatException e) {
            setErrors("invoiceNumber", "Invoice number must not contain any letters");
        }

        if (!errors.isEmpty()) {
            throw new Exception();
        }
    }

    public void checkForNullFields(Order order) throws Exception {
        if (order.getInvoiceNumber() == null | order.getInvoiceNumber().isEmpty()) {
            setErrors("invoiceNumber", "Invoice number must not be empty");
        }
        if (order.getStatus() == null | order.getStatus().isEmpty()) {
            setErrors("status", "status must not be empty");
        }
        if (order.getLocation() == null | order.getLocation().isEmpty()) {
            setErrors("location", "location must not be empty");
        }

        if(!errors.isEmpty()){
            throw new Exception();
        }
    }
}