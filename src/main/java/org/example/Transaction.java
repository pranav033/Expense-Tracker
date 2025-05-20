package org.example;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

class Transaction {
    LocalDate date;
    String type; // INCOME or EXPENSE
    String category;
    double amount;
    String description;

    public Transaction(LocalDate date, String type, String category, double amount, String description) {
        this.date = date;
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.description = description;
    }

    public String toString() {
        return date + " | " + type + " | " + category + " | ₹" + amount + " | " + description;
    }
}
