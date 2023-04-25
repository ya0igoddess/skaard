package su.skaard.core.templates

import kotlinx.html.*
import kotlinx.html.stream.appendHTML

fun createHeader(): HEADER.() -> Unit = {
    h1 { +"Skaard" }
}
fun createFooter(): FOOTER.() -> Unit = { }

fun createCustomHTML(block: DIV.() -> Unit) = buildString {
    appendLine("<!DOCTYPE html>")
    appendHTML().html {
        head {
            styleLink("style.css")
        }
        body {
            header(block = createHeader())
            div("main") {
                id = "root"
                div(block = block)
            }
            footer(block = createFooter())
        }
    }
}
