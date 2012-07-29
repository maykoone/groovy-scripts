/*
 * Groovy script for perform web scraping on a web page
 * written by Mayko B. Oliveira
 */

package com.maykoone.webscraping

import org.ccil.cowan.tagsoup.Parser
import javax.imageio.ImageIO
import java.awt.Graphics2D
import java.awt.image.BufferedImage

/*
* Web Scraping 1: extract all images from page and resize images to 80x80 and then
* write on disk
*/
def address = "http://www.iconarchive.com/search?q=web+page&page=13"
def url = new URL(address)

@Grapes( @Grab("org.ccil.cowan.tagsoup:tagsoup:1.2.1") ) 
def slurper = new XmlSlurper(new Parser() )

url.withReader { reader -> 
    html = slurper.parse(reader) 
    html."**".findAll { it.name() == "img" }.each { 
        def imgSrc = it.@src.text() 
        println imgSrc
        
        try {
            BufferedImage bf = ImageIO.read(new URL(imgSrc))
            println "height : ${bf.getHeight()}, width: ${bf.getWidth()}"
            //Resize image, only images above 80x80
            if(bf.getWidth() >= 80 && bf.getHeight() >= 80){
                BufferedImage resizedImage = new BufferedImage(80, 80, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = resizedImage.createGraphics();
                g.drawImage(bf, 0, 0, 80, 80, null);
                g.dispose();
                ImageIO.write(resizedImage, "png", new File("${imgSrc.tokenize('/')[-1]}.png"))
            }
        } catch (Exception e){
            println e.getMessage()
        }
        
    }
}
