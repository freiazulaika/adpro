package id.ac.ui.cs.advprog.eshop.service;

import enums.PaymentMethod;
import enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Order testOrder;
    private Payment testPayment;
    private Map<String, String> validVoucherData;
    private Map<String, String> validBankTransferData;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product testProduct = new Product();
        testProduct.setProductId("product-001");
        testProduct.setProductName("Test Product");
        testProduct.setProductQuantity(2);
        products.add(testProduct);

        testOrder = new Order("order-001", products, 120120120L, "Test User");

        validVoucherData = new HashMap<>();
        validVoucherData.put("voucherCode", "ESHOP1234ABC5678");

        validBankTransferData = new HashMap<>();
        validBankTransferData.put("bankName", "Bank ABC");
        validBankTransferData.put("referenceCode", "REFCODE12345678");

        testPayment = new Payment("payment-001", PaymentMethod.VOUCHER_CODE.getValue(), validVoucherData);
    }

    @Test
    void testAddPayment_WithValidData_ShouldCreateAndSavePayment() {
        when(paymentRepository.save(any(Order.class), any(Payment.class))).thenReturn(testPayment);

        Payment result = paymentService.addPayment(testOrder, PaymentMethod.VOUCHER_CODE.getValue(), validVoucherData);

        assertNotNull(result);
        assertEquals(testPayment.getId(), result.getId());
        assertEquals(testPayment.getMethod(), result.getMethod());
        assertEquals(testPayment.getStatus(), result.getStatus());
        verify(paymentRepository, times(1)).save(eq(testOrder), any(Payment.class));
    }

    @Test
    void testAddPayment_WithInvalidMethod_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                paymentService.addPayment(testOrder, "INVALID_METHOD", validVoucherData)
        );
        verify(paymentRepository, never()).save(any(), any());
    }

    @Test
    void testAddPayment_WithNullOrder_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                paymentService.addPayment(null, PaymentMethod.VOUCHER_CODE.getValue(), validVoucherData)
        );
        verify(paymentRepository, never()).save(any(), any());
    }

    @Test
    void testSetStatus_ToSuccess_ShouldUpdatePaymentAndOrderStatus() {
        paymentService.setStatus(testPayment, PaymentStatus.SUCCESS.getValue());

        verify(paymentRepository, times(1)).update(testPayment, PaymentStatus.SUCCESS.getValue());
    }

    @Test
    void testSetStatus_ToRejected_ShouldUpdatePaymentAndOrderStatus() {
        paymentService.setStatus(testPayment, PaymentStatus.REJECTED.getValue());

        verify(paymentRepository, times(1)).update(testPayment, PaymentStatus.REJECTED.getValue());
    }

    @Test
    void testSetStatus_WithInvalidStatus_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                paymentService.setStatus(testPayment, "INVALID_STATUS")
        );
        verify(paymentRepository, never()).update(any(), any());
    }

    @Test
    void testSetStatus_WithNullPayment_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                paymentService.setStatus(null, PaymentStatus.SUCCESS.getValue())
        );
        verify(paymentRepository, never()).update(any(), any());
    }

    @Test
    void testGetPayment_WithValidId_ShouldReturnPayment() {
        when(paymentRepository.findById("payment-001")).thenReturn(testPayment);

        Payment result = paymentService.getPayment("payment-001");

        assertNotNull(result);
        assertEquals(testPayment, result);
        verify(paymentRepository, times(1)).findById("payment-001");
    }

    @Test
    void testGetPayment_WithInvalidId_ShouldReturnNull() {
        when(paymentRepository.findById("invalid-id")).thenReturn(null);

        Payment result = paymentService.getPayment("invalid-id");

        assertNull(result);
        verify(paymentRepository, times(1)).findById("invalid-id");
    }

    @Test
    void testGetAllPayments_ShouldReturnAllPayments() {
        List<Payment> expectedPayments = new ArrayList<>();
        expectedPayments.add(testPayment);

        Payment bankPayment = new Payment("payment-002", PaymentMethod.BANK_TRANSFER.getValue(), validBankTransferData);
        expectedPayments.add(bankPayment);

        when(paymentRepository.findAll()).thenReturn(expectedPayments);

        List<Payment> result = paymentService.getAllPayments();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(testPayment));
        assertTrue(result.contains(bankPayment));
        verify(paymentRepository, times(1)).findAll();
    }

    @Test
    void testGetAllPayments_WhenEmpty_ShouldReturnEmptyList() {
        when(paymentRepository.findAll()).thenReturn(new ArrayList<>());

        List<Payment> result = paymentService.getAllPayments();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(paymentRepository, times(1)).findAll();
    }
}