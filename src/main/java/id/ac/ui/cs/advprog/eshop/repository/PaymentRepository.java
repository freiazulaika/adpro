package id.ac.ui.cs.advprog.eshop.repository;

import enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PaymentRepository {
    private final List<Payment> paymentData = new ArrayList<>();
    private final Map<String, Order> paymentOrder = new HashMap<>();

    public Payment save(Order order, Payment payment) {
        if (order == null || payment == null) {
            throw new IllegalArgumentException("Order and Payment cannot be null");
        }

        Payment existingPayment = findById(payment.getId());
        if (existingPayment != null) {
            paymentData.remove(existingPayment);
        }

        paymentData.add(payment);
        paymentOrder.put(payment.getId(), order);
        return payment;
    }

    public void update(Payment payment, String status) {
        if (payment == null || status == null || !PaymentStatus.contains(status)) {
            throw new IllegalArgumentException("Invalid payment or status");
        }

        for (Payment p : paymentData) {
            if (p.getId().equals(payment.getId())) {
                p.setStatus(status);

                Order order = paymentOrder.get(payment.getId());
                if (order != null) {
                    if (status.equals(PaymentStatus.SUCCESS.getValue())) {
                        order.setStatus("SUCCESS");
                    } else if (status.equals(PaymentStatus.REJECTED.getValue())) {
                        order.setStatus("FAILED");
                    }
                }
                return;
            }
        }
    }

    public Payment findById(String paymentId) {
        if (paymentId == null) return null;
        return paymentData.stream()
                .filter(payment -> payment.getId().equals(paymentId))
                .findFirst()
                .orElse(null);
    }

    public List<Payment> findAll() {
        return new ArrayList<>(paymentData);
    }

    public Order getOrder(String paymentId) {
        return paymentOrder.get(paymentId);
    }
}