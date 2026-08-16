package com.streamflex.domain.decorator;

import java.util.ArrayList;
import java.util.List;

/**
 * 4K Ultra HD çözünürlük eklentisi (Decorator Pattern - Concrete Decorator).
 * Mevcut planın üzerine 4K özelliğini ve ek maliyetini ekler.
 */
public class FourKResolution extends AddOnDecorator {

    private static final double ADDON_COST = 20.0;
    private static final String FEATURE_NAME = "4K Ultra HD";

    public FourKResolution(SubscriptionComponent component) {
        super(component);
    }

    /**
     * Özyinelemeli olarak maliyet hesaplar.
     * Sarmalanan bileşenin maliyetine 4K ek ücretini ekler.
     *
     * @return Toplam maliyet (sarmalanan + 20 TL)
     */
    @Override
    public double getCost() {
        return wrappedComponent.getCost() + ADDON_COST;
    }

    /**
     * Sarmalanan bileşenin özelliklerine 4K Ultra HD ekler.
     *
     * @return Güncellenmiş özellik listesi
     */
    @Override
    public List<String> getFeatures() {
        // Savunmacı kopya: wrappedComponent'ın iç listesini değiştirmez
        List<String> features = new ArrayList<>(wrappedComponent.getFeatures());
        features.add(FEATURE_NAME);
        return features;
    }

    @Override
    public String getDescription() {
        return wrappedComponent.getDescription() + " + " + FEATURE_NAME;
    }

    @Override
    public String toString() {
        return String.format("%s + %s (+₺%.2f)", wrappedComponent.getDescription(), FEATURE_NAME, ADDON_COST);
    }
}
