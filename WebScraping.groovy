/*
 * Groovy script for perform web scraping on a web page
 * written by Mayko B. Oliveira
 */

import org.ccil.cowan.tagsoup.Parser


def address = "http://www.iconarchive.com/search?q=web+page&page=13"
def url = new URL(address)

@Grapes( @Grab("org.ccil.cowan.tagsoup:tagsoup:1.2.1") ) 
def slurper = new XmlSlurper(new Parser() )


url.withReader { reader -> 
    html = slurper.parse(reader) 
    html."**".findAll { it.name() == "img" }.each { println it.@src.text() }
}
