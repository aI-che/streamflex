package com.streamflex.data;

import com.streamflex.domain.model.Content;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * İçerik envanterini yöneten Singleton sınıf.
 * HashMap&lt;String, Content&gt; kullanarak O(1) erişim garantisi sağlar (NFR-2: Performans).
 * Sistemde yalnızca tek bir örneği (instance) bulunur.
 * Thread-safe Singleton: Bill Pugh Idiom (Initialization-on-demand holder) kullanılır.
 */
public class ContentInventory {

    private final Map<String, Content> contentMap;

    /**
     * Private constructor — dışarıdan nesne oluşturulmasını engeller.
     */
    private ContentInventory() {
        this.contentMap = new HashMap<>();
    }

    /**
     * Inner static class, JVM tarafından yalnızca ilk erişimde yüklenir.
     * Bu sayede hem lazy initialization hem de thread safety garanti edilir.
     * volatile veya synchronized anahtar kelimesine gerek kalmaz.
     */
    private static class Holder {
        private static final ContentInventory INSTANCE = new ContentInventory();
    }

    /**
     * Singleton örneğini döndürür. Thread-safe ve lazy-loaded.
     *
     * @return ContentInventory'nin tek örneği
     */
    public static ContentInventory getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * Envantere yeni bir içerik ekler.
     *
     * @param content Eklenecek içerik nesnesi
     */
    public void addContent(Content content) {
        contentMap.put(content.getId(), content);
    }

    /**
     * ID ile içerik arar. O(1) zaman karmaşıklığı.
     *
     * @param id Aranan içeriğin benzersiz kimliği
     * @return Bulunan Content nesnesi veya null
     */
    public Content getContent(String id) {
        return contentMap.get(id);
    }

    /**
     * Envanterdeki tüm içeriklerin değiştirilemez görünümünü döndürür.
     *
     * @return Değiştirilemez içerik koleksiyonu
     */
    public Collection<Content> getAllContents() {
        return Collections.unmodifiableCollection(contentMap.values());
    }

    /**
     * Belirtilen türdeki içerikleri filtreler.
     *
     * @param genre Aranacak tür (örn: "Aksiyon", "Belgesel")
     * @return Filtrelenmiş içerik listesi
     */
    public List<Content> searchByGenre(String genre) {
        return contentMap.values().stream()
                .filter(c -> c.getGenre().equalsIgnoreCase(genre))
                .collect(Collectors.toList());
    }

    /**
     * Envanterdeki toplam içerik sayısını döndürür.
     *
     * @return İçerik sayısı
     */
    public int getContentCount() {
        return contentMap.size();
    }
}
