package com.distribuidos.inOutObjects;

public class OpenAccountOutput {
    private String document_id;
    private int cantAccounts;

    public OpenAccountOutput() {
    }

    public OpenAccountOutput(String document_id, int cantAccounts) {
        this.document_id = document_id;
        this.cantAccounts = cantAccounts;
    }

    public String getDocument_id() {
        return document_id;
    }

    public void setDocument_id(String document_id) {
        this.document_id = document_id;
    }

    public int getCantAccounts() {
        return cantAccounts;
    }

    public void setCantAccounts(int cantAccounts) {
        this.cantAccounts = cantAccounts;
    }
}
