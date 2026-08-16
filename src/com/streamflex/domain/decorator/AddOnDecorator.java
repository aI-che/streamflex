package com.streamflex.domain.decorator;

import java.util.List;

/**
 * Abonelik eklentilerinin soyut temel sınıfı (Decorator Pattern - Base Decorator).
 * SubscriptionComponent arayüzünü uygular ve aynı zamanda bir SubscriptionComponent'i sarar.
 * FourKResolution, AdFree ve OfflineMode bu sınıftan türer.
 * Özyinelemeli (recursive) getCost() çağrısı ile fiyat dıştan içe doğru hesaplanır.
 */
public abstract class AddOnDecorator implements SubscriptionComponent {

    protected SubscriptionComponent wrappedComponent;

    /**
     * Dekoratörün sarmalayacağı bileşeni ayarlar.
     *
     * @param component Sarmalanacak SubscriptionComponent (BasePlan veya başka bir Dekoratör)
     */
    public AddOnDecorator(SubscriptionComponent component) {
        this.wrappedComponent = component;
    }

    @Override
    public abstract double getCost();

    @Override
    public abstract List<String> getFeatures();

    @Override
    public abstract String getDescription();
}
