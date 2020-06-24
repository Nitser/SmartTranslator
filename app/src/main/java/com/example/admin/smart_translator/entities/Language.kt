package com.example.admin.smart_translator.entities

// В котлине для энтити используется data class (https://kotlinlang.org/docs/reference/data-classes.html)
// У них есть плюшки в виде автогенерации toString, hash
// И в энтити (или data class) обычно используется val вместо val. Так можно обезопасить себя от странных багов из-за изменения состояния
// (у меня часто были такие проблемы, особенно, при использовании таких классов в recycler'e)
class Language {
    var langCode: LanguageEnum = LanguageEnum.RUSSIAN
    var lanIconPath: Int = 0
    var isChecked: Boolean = false
}
