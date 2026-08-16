package com.streamflex.domain.model;

/**
 * Sistemdeki bir içerik nesnesini (Film, Belgesel vb.) temsil eder.
 * Tüm alanlar private ve final olup, yalnızca getter metotları ile erişilebilir (NFR-3: Kapsülleme).
 * Sınıf final olarak işaretlenmiştir — istenmeyen miras (inheritance) engellenir.
 */
public final class Content {

    private final String id;
    private final String title;
    private final String genre;
    private final int ageRestriction;
    private final boolean isPremium;

    /**
     * Yeni bir Content nesnesi oluşturur.
     *
     * @param id             Benzersiz içerik kimliği
     * @param title          İçerik başlığı
     * @param genre          İçerik türü (Aksiyon, Belgesel vb.)
     * @param ageRestriction Yaş sınırı (örn: 13, 18)
     * @param isPremium      Premium içerik olup olmadığı
     * @throws IllegalArgumentException id veya title null/boş ise
     */
    public Content(String id, String title, String genre, int ageRestriction, boolean isPremium) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("İçerik ID boş olamaz.");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("İçerik başlığı boş olamaz.");
        }
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.ageRestriction = ageRestriction;
        this.isPremium = isPremium;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getAgeRestriction() {
        return ageRestriction;
    }

    public boolean isPremium() {
        return isPremium;
    }

    /**
     * İçeriğin metadata bilgilerini okunabilir formatta döndürür.
     *
     * @return İçerik bilgilerini içeren String
     */
    public String getMetadata() {
        return String.format("[%s] %s | Tür: %s | Yaş Sınırı: %d+ | %s",
                id, title, genre, ageRestriction,
                isPremium ? "★ Premium" : "Standart");
    }

    @Override
    public String toString() {
        return getMetadata();
    }
}
