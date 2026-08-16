package com.streamflex.domain.strategy;

/**
 * Ödeme algoritmalarının soyutlanması (Strategy Pattern - Strategy).
 * CreditCardPayment, CryptoPayment gibi somut sınıflar bu arayüzü uygular.
 * Ödeme yöntemi çalışma zamanında (runtime) dinamik olarak değiştirilebilir.
 */
public interface PaymentStrategy {

    /**
     * Belirtilen tutarda ödeme işlemini gerçekleştirir.
     *
     * @param amount Ödenecek tutar (TL)
     * @return Ödeme başarılıysa true, reddedildiyse false
     */
    boolean pay(double amount);

    /**
     * Ödeme yönteminin adını döndürür.
     *
     * @return Ödeme yöntemi adı (örn: "Kredi Kartı", "Kripto Para")
     */
    String getPaymentMethodName();
}
