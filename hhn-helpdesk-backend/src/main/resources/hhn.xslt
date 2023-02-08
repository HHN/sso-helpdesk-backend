<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:date="http://exslt.org/dates-and-times"
                exclude-result-prefixes="fo">
    <xsl:output method="xml" version="1.0" omit-xml-declaration="no" indent="yes"/>
    <xsl:param name="versionParam" select="'1.0'"/>
    <xsl:variable name="now" select="date:date-time()"/>
    <xsl:variable name="pageMargin" select="'1cm'"/>
    <xsl:variable name="lineMargin"
                  select="'4mm'"/>
    <xsl:template match="hhn-account">
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="simpleA4" page-height="15cm" page-width="20cm"
                                       margin-top="{$pageMargin}" margin-bottom="{$pageMargin}"
                                       margin-left="{$pageMargin}" margin-right="{$pageMargin}">
                    <fo:region-body/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            <fo:page-sequence master-reference="simpleA4">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block space-after="10mm">
                        <fo:inline-container inline-progression-dimension="5cm" vertical-align="middle">
                            <fo:block>
                                <fo:external-graphic content-width="5cm" content-height="5cm"
                                                     src="url({hhn-base64-code})"></fo:external-graphic>
                            </fo:block>
                        </fo:inline-container>
                    </fo:block>
                    <fo:block font-size="16pt">
                        <fo:block>
                            <xsl:if test="normalize-space(hnn-password) != ''">
                                <fo:block margin-bottom="{$lineMargin}">
                                    <fo:inline-container inline-progression-dimension="11cm" vertical-align="top">
                                        <fo:block>
                                            <xsl:value-of select="hnn-password"/>
                                        </fo:block>
                                    </fo:inline-container>
                                </fo:block>
                            </xsl:if>
                            <xsl:if test="normalize-space(hhn-seq-number) != ''">
                                <fo:block margin-bottom="{$lineMargin}">
                                    <fo:inline-container inline-progression-dimension="11cm" vertical-align="top">
                                        <fo:block>
                                            <xsl:value-of select="hhn-seq-number"/>
                                        </fo:block>
                                    </fo:inline-container>
                                </fo:block>
                            </xsl:if>

                        </fo:block>
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>