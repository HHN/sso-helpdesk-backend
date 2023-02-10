<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:date="http://exslt.org/dates-and-times"
                exclude-result-prefixes="fo">
    <xsl:output method="xml" version="1.0" omit-xml-declaration="no" indent="yes"/>
    <xsl:param name="versionParam" select="'1.0'"/>
    <xsl:variable name="now" select="date:date-time()"/>
    <xsl:variable name="pageMargin" select="'2cm'"/>
    <xsl:variable name="pageMarginTop" select="'1cm'"/>
    <xsl:variable name="lineMargin"
                  select="'4mm'"/>
    <xsl:template match="hhn-account">
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="simpleA4" page-height="29.7cm" page-width="21cm"
                                       margin-top="{$pageMarginTop}" margin-bottom="{$pageMargin}"
                                       margin-right="{$pageMargin}">

                    <fo:region-body margin-left="{$pageMargin}" />

                    <fo:region-start region-name="Inhalt-rechts-start" extent="{$pageMargin}"/>

                </fo:simple-page-master>
            </fo:layout-master-set>
            <xsl:for-each select="hhn-account-element">

                <fo:page-sequence master-reference="simpleA4">

                    <fo:static-content flow-name="Inhalt-rechts-start">

                        <!-- DIN 5008 Type B -->
                        <fo:block space-after="85mm"/>
                        <fo:block space-after="105mm">
                            <fo:leader leader-pattern="rule" leader-length="25%" rule-style="solid" rule-thickness="1pt"/>
                        </fo:block>
                        <fo:block space-after="105mm">
                            <fo:leader leader-pattern="rule" leader-length="25%" rule-style="solid" rule-thickness="1pt"/>
                        </fo:block>
                    </fo:static-content>

                    <fo:flow flow-name="xsl-region-body">

                        <fo:block font-family="Arial" font-size="10pt" font-style="normal" font-weight="normal"
                                  line-height="14pt" text-decoration="none">
                            <fo:table table-layout="fixed" space-before.optimum="1cm" space-after.optimum="2pt">
                                <fo:table-column column-width="13.7cm"/>
                                <fo:table-column column-width="4cm"/>
                                <fo:table-body>
                                    <fo:table-row>
                                        <fo:table-cell number-columns-spanned="1" padding-top="0.0pt"
                                                       padding-bottom="8pt"
                                                       display-align="center">
                                            <fo:block span="none" font-family="Arial" text-align="left">
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell number-columns-spanned="1" padding-top="4.0pt"
                                                       padding-bottom="8pt">
                                            <fo:block>
                                                <fo:external-graphic content-height="scale-to-fit"
                                                                     src="classpath:img/hhn_logo.png"
                                                                     content-width="4cm"/>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                </fo:table-body>
                            </fo:table>
                        </fo:block>
                        <fo:block space-after="6mm">
                        </fo:block>
                        <fo:block font-size="6pt" space-after="5mm">
                            Rechenzentrum - Hochschule Heilbronn - 74081 Heilbronn
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
                                                                 src="url({hhn-base64-code})"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                            </fo:table-body>
                        </fo:table>
                        <fo:block font-size="14pt" space-after="5mm">
                            Betrifft: Passwort für Ihren Hochschulaccount
                        </fo:block>

                        <fo:block space-after="0.5cm">
                            Hallo,
                        </fo:block>
                        <fo:block space-after="0.5cm" text-align="justify">
                            wir freuen uns, Ihnen mitteilen zu können, dass wir das Passwort für Ihren Hochschulaccount
                            erfolgreich zurückgesetzt haben.
                            Bitte bewahren Sie dieses Passwort sicher auf und ändern Sie es zeitnah unter
                            https://login.hs-heilbronn.de

                            Ihr neues Passwort lautet:

                        </fo:block>
                        <fo:block font-size="24pt" text-align="center" space-after="0.5cm">
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
                        <fo:block space-after="0.5cm" text-align="justify">

                            Bitte besuchen Sie https://login.hs-heilbronn.de und melden Sie sich mit Ihrem Benutzernamen
                            und dem initialen Passwort an.
                            Sobald Sie angemeldet sind, werden Sie aufgefordert, ein neues, sicheres Passwort zu
                            erstellen.
                        </fo:block>

                        <fo:block space-after="0.5cm" text-align="justify">
                            Wir empfehlen Ihnen, ein Passwort zu wählen, das mindestens zwölf Zeichen lang ist und
                            sowohl Buchstaben als auch Zahlen sowie Sonderzeichen enthält.
                            Verwenden Sie keine einfachen Wörter oder Informationen, die einfach zu erraten sind (z. B.
                            Ihren Namen oder Geburtstag).
                        </fo:block>

                        <fo:block space-after="1.5cm" text-align="justify">
                            Wenn Sie Fragen oder Probleme beim Zugriff auf Ihr Konto haben, wenden Sie sich bitte an
                            benutzerverwaltung@hs-heilbronn.de
                        </fo:block>
                        <fo:block>
                            Viele Grüße
                        </fo:block>
                        <fo:block>
                            Ihre Benutzerverwaltung des Rechenzentrums
                        </fo:block>
                    </fo:flow>
                </fo:page-sequence>

            </xsl:for-each>

        </fo:root>
    </xsl:template>
</xsl:stylesheet>