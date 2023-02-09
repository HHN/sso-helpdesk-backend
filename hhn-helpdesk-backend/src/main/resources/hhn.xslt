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
                <fo:simple-page-master master-name="simpleA4" page-height="29.7cm" page-width="21cm"
                                       margin-top="{$pageMargin}" margin-bottom="{$pageMargin}"
                                       margin-left="{$pageMargin}" margin-right="{$pageMargin}">
                    <fo:region-body/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            <fo:page-sequence master-reference="simpleA4">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block font-size="32pt" space-after="22mm">
                        Passwortbrief
                    </fo:block>
                    <fo:block font-size="6pt" space-after="5mm">
                        Rechenzentrum Hochschule Heilbronn - 74081 Heilbronn
                    </fo:block>
                    <fo:table table-layout="fixed" width="6.2cm" space-after="1.5cm">
                        <fo:table-column column-width="3cm"/>
                        <fo:table-column column-width="3.2cm"/>

                        <fo:table-body>
                            <fo:table-row background="black">
                                <fo:table-cell>
                                    <fo:block text-align="center" font-size="10pt">
                                        - persönlich -
                                    </fo:block>
                                    <fo:block text-align="center">
                                        <xsl:value-of select="hhn-seq-number"/>
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block>
                                        <fo:external-graphic content-width="5cm" content-height="5cm"
                                                             src="url({hhn-base64-code})" />
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                        </fo:table-body>
                    </fo:table>
                    <fo:block font-size="14pt" space-after="5mm">
                        Betrifft: Neues initiales Passwort für Ihren Nutzeraccount
                    </fo:block>

                    <fo:block>
                        Guten Tag,
                    </fo:block>
                    <fo:block space-after="10mm">
                        Ihr neues initiales Passwort lautet:
                    </fo:block>
                    <fo:block font-size="24pt">
                        <xsl:if test="normalize-space(hhn-password) != ''">
                            <fo:block margin-bottom="{$lineMargin}">
                                <fo:inline-container inline-progression-dimension="11cm" vertical-align="top">
                                    <fo:block>
                                        <xsl:value-of select="hhn-password"/>
                                    </fo:block>
                                </fo:inline-container>
                            </fo:block>
                        </xsl:if>
                    </fo:block>
                    <fo:block space-after="1cm">
                        Zum Ändern des initialen Passworts gehen Sie bitte auf login.hs-heilbronn.de und folgen Sie den dortigen Anweisungen. Verwenden Sie Ihren bekannten Benutzernamen und das oben abgedruckte Passwort für Ihren ersten Login.
                    </fo:block>
                    <fo:block>
                        Viele Grüße
                    </fo:block>
                    <fo:block>
                        Das Team der Benutzerverwaltung des Rechenzentrums
                    </fo:block>
                    <fo:block font-size="16pt">
                        <fo:block>

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