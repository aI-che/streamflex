package com.streamflex.domain.strategy;

/**
 * Kripto para ile ödeme stratejisi (Strategy Pattern - Concrete Strategy).
 * Blockchain tabanlı ödeme geçidini simüle eder.
 */
public class CryptoPayment implements PaymentStrategy {

    private final String walletAddress;

    /**
     * Kripto para ödeme yöntemi oluşturur.
     *
     * @param walletAddress Kripto cüzdan adresi
     * @throws IllegalArgumentException Cüzdan adresi null veya boş ise
     */
    public CryptoPayment(String walletAddress) {
        if (walletAddress == null || walletAddress.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Cüzdan adresi boş olamaz.");
        }
        this.walletAddress = walletAddress.trim();
    }

    /**
     * Kripto para ile ödeme işlemini simüle eder.
     * Cüzdan adresi "invalid" ile başlıyorsa ödeme reddedilir (test senaryosu).
     *
     * @param amount Ödenecek tutar (TL)
     * @return Ödeme başarılıysa true, reddedildiyse false
     */
    @Override
    public boolean pay(double amount) {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("🪙 Kripto Para Ödeme İşlemi Başlatıldı");
        System.out.println("   Cüzdan      : " + walletAddress.substring(0, Math.min(8, walletAddress.length())) + "...");
        System.out.printf("   Tutar       : ₺%.2f%n", amount);

        // Simülasyon: "invalid" ile başlayan cüzdanlar reddedilir
        if (walletAddress.toLowerCase().startsWith("invalid")) {
            System.out.println("   Sonuç       : ❌ REDDEDILDI (Geçersiz cüzdan adresi)");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            return false;
        }

        System.out.println("   Blok Onayı  : 3/3 ✓");
        System.out.println("   Sonuç       : ✅ ONAYLANDI");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        return true;
    }

    @Override
    public String getPaymentMethodName() {
        return "Kripto Para";
    }

    @Override
    public String toString() {
        return "CryptoPayment [" + walletAddress.substring(0, Math.min(8, walletAddress.length())) + "...]";
    }
}
