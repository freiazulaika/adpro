package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;

import java.util.Map;

@Getter
public class Payment {
    private final String id;
    private final String method;
    private String status;
    private final Map<String, String> paymentData;

    private static final String[] VALID_METHODS = {"BANK_TRANSFER", "VOUCHER_CODE"};

    public Payment(String id, String method, Map<String, String> paymentData) {
        this.id = id;
        this.method = validateMethod(method);
        this.paymentData = paymentData;
        this.status = validatePayment();
    }

    private String validateMethod(String method) {
        if (java.util.Arrays.stream(VALID_METHODS).noneMatch(valid -> valid.equals(method))) {
            throw new IllegalArgumentException("Unsupported payment method: " + method);
        }
        return method;
    }

    private String validatePayment() {
        if (paymentData == null) {
            return "REJECTED";
        }

        return switch (method) {
            case "BANK_TRANSFER" -> validateBankTransfer() ? "SUCCESS" : "REJECTED";
            case "VOUCHER_CODE" -> validateVoucherCode() ? "SUCCESS" : "REJECTED";
            default -> "REJECTED";
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
        if (!"SUCCESS".equals(status) && !"REJECTED".equals(status)) {
            throw new IllegalArgumentException("Invalid status change: " + status);
        }
        this.status = status;
    }
}