package id.ac.ui.cs.advprog.eshop.model;

import enums.PaymentMethod;
import enums.PaymentStatus;
import lombok.Getter;

import java.util.Map;

@Getter
public class Payment {
    private String id;
    private String method;
    private String status;
    private Map<String, String> paymentData;

    public Payment(String id, String method, Map<String, String> paymentData) {
        if (!PaymentMethod.contains(method)) {
            throw new IllegalArgumentException("Unsupported payment method: " + method);
        }
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
        this.status = validatePayment();
    }

    private String validatePayment() {
        if (paymentData == null) {
            return PaymentStatus.REJECTED.getValue();
        }

        return switch (method) {
            case "BANK_TRANSFER" -> validateBankTransfer() ? PaymentStatus.SUCCESS.getValue() : PaymentStatus.REJECTED.getValue();
            case "VOUCHER_CODE" -> validateVoucherCode() ? PaymentStatus.SUCCESS.getValue() : PaymentStatus.REJECTED.getValue();
            default -> PaymentStatus.REJECTED.getValue();
        };
    }

    private boolean validateBankTransfer() {
        String bankName = paymentData.get("bankName");
        String referenceCode = paymentData.get("referenceCode");

        return isValid(bankName) && isValid(referenceCode);
    }

    private boolean validateVoucherCode() {
        String voucherCode = paymentData.get("voucherCode");
        return voucherCode != null && isVoucherFormatValid(voucherCode);
    }

    private boolean isValid(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private boolean isVoucherFormatValid(String voucherCode) {
        if (voucherCode.length() != 16 || !voucherCode.startsWith("ESHOP")) {
            return false;
        }

        long digitCount = voucherCode.substring(5).chars()
                .filter(Character::isDigit)
                .count();

        return digitCount == 8;
    }

    public void setStatus(String status) {
        if (!PaymentStatus.contains(status)) {
            throw new IllegalArgumentException("Invalid status change: " + status);
        }
        this.status = status;
    }
}