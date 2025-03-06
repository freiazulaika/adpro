package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        paymentData = new HashMap<>();
    }

    @Test
    void testCreatePaymentWithInvalidMethod() {
        paymentData.put("voucherCode", "ESHOP12345678ABC");
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("payment-001", "INVALID_METHOD", paymentData);
        });
    }

    @Test
    void testValidVoucherCode() {
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("payment-002", "VOUCHER_CODE", paymentData);

        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testVoucherCodeTooShort() {
        paymentData.put("voucherCode", "ESHOP1234ABC");
        Payment payment = new Payment("payment-003", "VOUCHER_CODE", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testVoucherCodeNotStartingWithESHOP() {
        paymentData.put("voucherCode", "ADPRO1234ABC5678");
        Payment payment = new Payment("payment-004", "VOUCHER_CODE", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testVoucherCodeWithoutEightNumbers() {
        paymentData.put("voucherCode", "ESHOPABCDEFG5678");
        Payment payment = new Payment("payment-005", "VOUCHER_CODE", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testVoucherCodeIsNull() {
        paymentData.put("voucherCode", null);
        Payment payment = new Payment("payment-007", "VOUCHER_CODE", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testVoucherCodeIsEmpty() {
        paymentData.put("voucherCode", "");
        Payment payment = new Payment("payment-020", "VOUCHER_CODE", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testValidBankTransfer() {
        paymentData.put("bankName", "Bank ABC");
        paymentData.put("referenceCode", "REFCODE12345678");
        Payment payment = new Payment("payment-008", "BANK_TRANSFER", paymentData);

        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testBankTransferWithEmptyBankName() {
        paymentData.put("bankName", "");
        paymentData.put("referenceCode", "REFCODE12345678");
        Payment payment = new Payment("payment-009", "BANK_TRANSFER", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testBankTransferWithNullBankName() {
        paymentData.put("bankName", null);
        paymentData.put("referenceCode", "REFCODE12345678");
        Payment payment = new Payment("payment-010", "BANK_TRANSFER", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testBankTransferWithEmptyReferenceCode() {
        paymentData.put("bankName", "Bank ABC");
        paymentData.put("referenceCode", "");
        Payment payment = new Payment("payment-011", "BANK_TRANSFER", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testBankTransferWithNullReferenceCode() {
        paymentData.put("bankName", "Bank ABC");
        paymentData.put("referenceCode", null);
        Payment payment = new Payment("payment-012", "BANK_TRANSFER", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testRejectSettingInvalidStatus() {
        Payment payment = new Payment("payment-014", "BANK_TRANSFER", paymentData);
        assertThrows(IllegalArgumentException.class, () -> payment.setStatus("INVALID_STATUS"));
    }

    @Test
    void testSetStatusToSuccess() {
        paymentData.put("bankName", "Bank ABC");
        paymentData.put("referenceCode", "ESHOP1234XYZ5678");
        Payment payment = new Payment("payment-015", "BANK_TRANSFER", paymentData);
        payment.setStatus("SUCCESS");

        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testSetStatusToRejected() {
        paymentData.put("bankName", "Bank ABC");
        paymentData.put("referenceCode", "INVALID12345");
        Payment payment = new Payment("payment-016", "BANK_TRANSFER", paymentData);
        payment.setStatus("REJECTED");

        assertEquals("REJECTED", payment.getStatus());
    }
}