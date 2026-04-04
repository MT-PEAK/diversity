package com.peak.diversityCore.features.registrant;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;

/*
* Generic helper for storing and registering objects with identifiers
*
* @param <T> the type being registered
*/
public class Registrant<T> {
    protected final String namespace;

    private final Map<T, Identifier> entries = new LinkedHashMap<>();
    private final BiConsumer<T, Identifier> registerer;

    /*
     * @param namespace identifier namespace
     * @param registerer function used to register entities
    */
    public Registrant(String namespace, BiConsumer<T, Identifier> registerer) {
        this.namespace = Objects.requireNonNull(namespace, "Namespace cannot be null");
        this.registerer = Objects.requireNonNull(registerer, "Registerer cannot be null");
    }

    /*
    * Creates and stores an entry
    *
    * @param name identifier path
    * @param object object to store
    * @return that same object
    */
    public <M extends T> M create(String name, M object) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(object);

        Identifier id = Identifier.of(namespace, name);

        if (entries.containsKey(object)) {
            throw new IllegalStateException("Duplicate object: " + id);
        }

        if (entries.containsValue(id)) {
            throw new IllegalStateException("Duplicate identifier: " + id);
        }

        entries.put(object, id);
        return object;
    }

    /* Registers all stored entries */
    public void registerEntries() {
        entries.forEach(registerer);
    }

    /* @return unmodifiable view of entries */
    public Map<T, Identifier> getEntries() {
        return Collections.unmodifiableMap(entries);
    }

    /* Generate language entries */
    public void generateLang(RegistryWrapper.WrapperLookup lookup, FabricLanguageProvider.TranslationBuilder builder) {
        entries.forEach((object, id) ->
                builder.add(id, getTranslation(object, id))
        );
    }

    /* @return translation string for given object */
    protected String getTranslation(T object, Identifier id) {
        return formatValueString(id.getPath());
    }

    private static String formatValueString(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        StringBuilder converted = new StringBuilder();
        boolean convertNext = true;

        for (char ch : text.toCharArray()) {
            if (ch == '_') {
                ch = ' ';
            }

            if (Character.isWhitespace(ch)) {
                convertNext = true;
            } else if (convertNext) {
                ch = Character.toTitleCase(ch);
                convertNext = false;
            } else {
                ch = Character.toLowerCase(ch);
            }

            converted.append(ch);
        }

        return converted.toString();
    }
}