package com.example.fellow_traveller.SearchAndBook;

public class PaymentItem {
    private String paymentMethod;
    private int selectMethod;

    public PaymentItem(String aPayment, int aMethod){

        this.paymentMethod = aPayment;
        this.selectMethod = aMethod;
    }


    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public int getSelectMethod() {
        return selectMethod;
    }

    public void setSelectMethod(int selectMethod) {
        this.selectMethod = selectMethod;
    }
}
