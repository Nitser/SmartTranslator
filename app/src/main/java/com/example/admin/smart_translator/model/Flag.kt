package com.example.admin.smart_translator.model

import kotlin.properties.Delegates

class Flag {
    lateinit var langCode: LanguageEnum
    var flagIconPath by Delegates.notNull<Int>()
    var isChecked by Delegates.notNull<Boolean>()
}
