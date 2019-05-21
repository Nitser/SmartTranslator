package com.example.admin.smarttranslator.Entities;

/**
 * Language - an enum of language codes supported by the Yandex API
 */
public enum Language {
    AFRICAN("af"),
    ALBANIAN("sq"),
    AMHARIC("am"),
    ARABIC("ar"),
    ARMENIAN("hy"),
    AZERBAIJANI("az"),
    BASHKIR("ba"),
    BASQUE("eu"),
    BELARUSIAN("be"),
    BENGALI("bn"),
    BOSNIAN("bs"),
    BULGARIAN("bg"),
    BURMESE("my"),
    CATALAN("ca"),
    CHINESE("zh"),
    CROATIAN("hr"),
    CZECH("cs"),
    DANISH("da"),
    DUTCH("nl"),
    ENGLISH("en"),
    ESPERANTO("eo"),
    ESTONIAN("et"),
    FINNISH("fi"),
    FRENCH("fr"),
    GALICIAN("gl"),
    GERMAN("de"),
    GEORGIAN("ka"),
    GREEK("el"),
    HAITIAN("ht"),
    HEBREW("he"),
    HINDI("hi"),
    HUNGARIAN("hu"),
    INDONESIAN("id"),
    ICELANDIC("is"),
    IRISH("ga"),
    ITALIAN("it"),
    JAPANESE("ja"),
    KANNAD("kn"),
    KAZAKH("kk"),
    KHMER("km"),
    KOREAN("ko"),
    KYRGYZ("ky"),
    LAO("lo"),
    LATIN("la"),
    LATVIAN("lv"),
    LITHUANIAN("lt"),
    LUXEMBOURG("lb"),
    MACEDONIAN("mk"),
    MALAGASY("mg"),
    MALAY("ms"),
    MALTESE("mt"),
    MAORI("mi"),
    MARI("mhr"),
    MONGOLIAN("mn"),
    NEPALI("ne"),
    NORWEGIAN("no"),
    PAPIAMETO("pap"),
    PERSIAN("fa"),
    POLISH("pl"),
    PORTUGUESE("pt"),
    ROMANIAN("ro"),
    RUSSIAN("ru"),
    SCOTTISH("gd"),
    SERBIAN("sr"),
    SIHALESE("si"),
    SLOVAK("sk"),
    SLOVENIAN("sl"),
    SPANISH("es"),
    SUNDANESE("su"),
    SWAHILI("sw"),
    SWEDISH("sv"),
    TAGALOG("tl"),
    TAJIK("tg"),
    THAI("th"),
    TATAR("tt"),
    TURKISH("tr"),
    UDMURT("udm"),
    UKRAINIAN("uk"),
    URDU("ur"),
    UZBEK("uz"),
    VIETNAMESE("vi"),
    WELSH("cy");

    /**
     * String representation of this language.
     */
    private final String language;

    /**
     * Enum constructor.
     * @param pLanguage The language identifier.
     */
    private Language(final String pLanguage) {
        language = pLanguage;
    }

    public static Language fromString(final String pLanguage) {
        for (Language l : values()) {
            if (l.toString().equals(pLanguage)) {
                return l;
            }
        }
        return null;
    }

    /**
     * Returns the String representation of this language.
     * @return The String representation of this language.
     */
    @Override
    public String toString() {
        return language;
    }


}