package com.streamflex.domain.decorator;

import java.util.List;

/**
 * Abonelik bileşenlerinin temel sözleşmesi (Decorator Pattern - Component).
 * BasePlan ve AddOnDecorator bu arayüzü uygular.
 * getCost() özyinelemeli (recursive) çağrı ile toplam maliyeti hesaplar.
 */
public interface SubscriptionComponent {

    /**
     * Abonelik bileşeninin maliyetini döndürür.
     * Decorator zincirinde özyinelemeli olarak hesaplanır.
     *
     * @return Toplam maliyet (TL)
     */
    double getCost();

    /**
     * Abonelik bileşeninin özelliklerini döndürür.
     * Decorator zincirinde birikimli (cumulative) olarak toplanır.
     *
     * @return Özellik listesi
     */
    List<String> getFeatures();

    /**
     * Abonelik bileşeninin açıklamasını döndürür.
     *
     * @return Açıklama metni
     */
    String getDescription();
}
