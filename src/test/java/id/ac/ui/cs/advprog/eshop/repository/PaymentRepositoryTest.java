package id.ac.ui.cs.advprog.eshop.repository;

import enums.PaymentMethod;
import enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {
    private PaymentRepository paymentRepository;
    private List<Payment> paymentList;
    private Map<String, String> paymentData;
    private Order order;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
        paymentList = new ArrayList<>();
        paymentData = new HashMap<>();

        List<Product> products = new ArrayList<>();
        Product sampleProduct = new Product();
        sampleProduct.setProductId("product-001");
        sampleProduct.setProductName("Sample Product");
        sampleProduct.setProductQuantity(1);
        products.add(sampleProduct);

        order = new Order("order-001", products, 120120120L, "Author Test");

        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment firstPayment = new Payment("id-001", PaymentMethod.VOUCHER_CODE.getValue(), paymentData);
        paymentList.add(firstPayment);

        Map<String, String> bankData = new HashMap<>();
        bankData.put("bankName", "Bank ABC");
        bankData.put("referenceCode", "ESHOP87654321");

        Payment secondPayment = new Payment("id-002", PaymentMethod.BANK_TRANSFER.getValue(), bankData);
        paymentList.add(secondPayment);
    }

    @Test
    void shouldSaveNewPaymentSuccessfully() {
        Payment payment = paymentList.get(0);
        Payment savedPayment = paymentRepository.save(order, payment);

        Payment retrievedPayment = paymentRepository.findById(payment.getId());
        assertEquals(payment.getId(), savedPayment.getId());
        assertEquals(payment.getMethod(), retrievedPayment.getMethod());
        assertEquals(payment.getStatus(), retrievedPayment.getStatus());
        assertSame(payment.getPaymentData(), retrievedPayment.getPaymentData());
    }

    @Test
    void shouldFindPaymentByIdIfExists() {
        for (Payment payment : paymentList) {
            paymentRepository.save(order, payment);
        }

        Payment retrievedPayment = paymentRepository.findById(paymentList.get(0).getId());
        assertNotNull(retrievedPayment);
        assertEquals(paymentList.get(0).getId(), retrievedPayment.getId());
    }

    @Test
    void shouldUpdatePaymentStatusAndChangeOrderStatus() {
        Payment payment = paymentList.get(1);
        paymentRepository.save(order, payment);

        paymentRepository.update(payment, PaymentStatus.REJECTED.getValue());
        Payment updatedPayment = paymentRepository.findById(payment.getId());

        assertEquals(PaymentStatus.REJECTED.getValue(), updatedPayment.getStatus());
        assertEquals("FAILED", order.getStatus());
    }

    @Test
    void shouldFindAllPayments() {
        for (Payment payment : paymentList) {
            paymentRepository.save(order, payment);
        }

        List<Payment> retrievedPayments = paymentRepository.findAll();
        assertEquals(2, retrievedPayments.size());
    }
}