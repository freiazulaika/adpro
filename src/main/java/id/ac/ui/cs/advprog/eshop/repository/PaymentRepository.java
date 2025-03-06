package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PaymentRepository {
    private final List<Payment> paymentData = new ArrayList<>();
    private final Map<String, Order> paymentOrder = new HashMap<>();

    public Payment save(Order order, Payment payment) {
        return null;
    }

    public void update(Payment payment, String status) {
    }

    public Payment findById(String paymentId) {
        return null;
    }

    public List<Payment> findAll() {
        return null;
    }

    public Order getOrder(String paymentId) {
        return null;
    }
}