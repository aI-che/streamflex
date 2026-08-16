package com.streamflex.domain.decorator;

import java.util.ArrayList;
import java.util.List;

/**
 * Temel abonelik planı (Decorator Pattern - Concrete Component).
 * Dekoratör zincirinin başlangıç noktasıdır.
 * Varsayılan özellikleri ve temel fiyatı barındırır.
 */
public class BasePlan implements SubscriptionComponent {

    private String name;
    private double baseCost;
    private List<String> baseFeatures;

    /**
     * Temel abonelik planı oluşturur.
     *
     * @param name     Plan adı (örn: "Standart Plan")
     * @param baseCost Temel aylık ücret (TL)
     */
    public BasePlan(String name, double baseCost) {
        this.name = name;
        this.baseCost = baseCost;
        this.baseFeatures = new ArrayList<>();
        this.baseFeatures.add("SD Çözünürlük");
        this.baseFeatures.add("Tek Ekran");
        this.baseFeatures.add("Reklamlı İzleme");
    }

    @Override
    public double getCost() {
        return baseCost;
    }

    @Override
    public List<String> getFeatures() {
        return new ArrayList<>(baseFeatures);
    }

    @Override
    public String getDescription() {
        return name + " (Temel Plan)";
    }

    @Override
    public String toString() {
        return String.format("%s — ₺%.2f | Özellikler: %s", name, baseCost, baseFeatures);
    }
}
