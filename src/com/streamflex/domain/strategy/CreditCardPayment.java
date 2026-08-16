package com.streamflex.domain.strategy;

/**
 * Kredi kartı ile ödeme stratejisi (Strategy Pattern - Concrete Strategy).
 * Harici ödeme geçidini (Payment Gateway) simüle eder.
 * Kart numarası maskelenerek saklanır (PCI-DSS uyumluluğu).
 */
public class CreditCardPayment implements PaymentStrategy {

    private final String fullCardNumber;
    private final String maskedCardNumber;
    private final String cardHolder;

    /**
     * Kredi kartı ödeme yöntemi oluşturur.
     *
     * @param cardNumber Kart numarası (en az 4 karakter)
     * @param cardHolder Kart sahibinin adı
     * @throws IllegalArgumentException Kart numarası null veya 4 karakterden kısa ise
     */
    public CreditCardPayment(String cardNumber, String cardHolder) {
        if (cardNumber == null || cardNumber.trim().length() < 4) {
            throw new IllegalArgumentException(
                    "Kart numarası en az 4 karakter olmalıdır.");
        }
        if (cardHolder == null || cardHolder.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Kart sahibi adı boş olamaz.");
        }
        this.fullCardNumber = cardNumber.trim();
        this.maskedCardNumber = "**** **** **** " 
                + fullCardNumber.substring(fullCardNumber.length() - 4);
        this.cardHolder = cardHolder.trim();
    }

    /**
     * Kredi kartı ile ödeme işlemini simüle eder.
     * Kart numarası "0000" ile başlıyorsa ödeme reddedilir (test senaryosu).
     *
     * @param amount Ödenecek tutar (TL)
     * @return Ödeme başarılıysa true, reddedildiyse false
     */
    @Override
    public boolean pay(double amount) {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("💳 Kredi Kartı Ödeme İşlemi Başlatıldı");
        System.out.println("   Kart Sahibi : " + cardHolder);
        System.out.println("   Kart No     : " + maskedCardNumber);
        System.out.printf("   Tutar       : ₺%.2f%n", amount);

        // Simülasyon: "0000" ile başlayan kartlar reddedilir
        if (fullCardNumber.startsWith("0000")) {
            System.out.println("   Sonuç       : ❌ REDDEDILDI (Yetersiz bakiye)");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            return false;
        }

        System.out.println("   Sonuç       : ✅ ONAYLANDI");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        return true;
    }

    @Override
    public String getPaymentMethodName() {
        return "Kredi Kartı";
    }

    @Override
    public String toString() {
        // Hassas veri asla toString() içinde tam olarak gösterilmez
        return "CreditCardPayment [" + cardHolder + " - " + maskedCardNumber + "]";
    }
}
