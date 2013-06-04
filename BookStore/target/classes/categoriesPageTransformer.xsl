<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : artistTransformer.xsl
    Created on : June 3, 2013, 7:53 PM
    Author     : Len
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    
    <xsl:output method="html"/>
    <xsl:template match="/">
        <html>
            <head>
                <title>Book Store: Categories</title>
            </head>
            <font size="8">Category page</font>
            <br/>
            <body>
                <table border="0">
                    <tr bgcolor="#9acd32">
                        <th>
                            <font size="6">Categories</font>
                        </th>
                    </tr>
                    
                    <xsl:for-each select="categories/category">
                    <tr>
                      <td>
                          <xsl:variable name="name">
                              <xsl:value-of select="name"/>
                          </xsl:variable>
                          <img src="img/categories/{$name}.png"></img>
                          <form name="{$name}" method="POST" action="{$name}">
                               <input type="submit" value="{$name}"/>
                          </form>
                      </td>
                    </tr>
                   </xsl:for-each>
                  </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
