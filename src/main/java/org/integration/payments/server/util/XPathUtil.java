package org.integration.payments.server.util;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.InputSource;


public class XPathUtil {
    private static XPath cache = new CachingXPath();
    
    public static XPath getXpath() {
        return cache;
    }
    
    public static XPathExpression compile(String expression) throws XPathExpressionException {
        return getXpath().compile(expression);
    }

    public static Object evaluate(String expression, Object item, QName returnType) throws XPathExpressionException {
        return getXpath().evaluate(expression, item, returnType);
    }

    public static String evaluate(String expression, Object item) throws XPathExpressionException {
        return getXpath().evaluate(expression, item);
    }

    public static Object evaluate(String expression, InputSource source, QName returnType) throws XPathExpressionException {
        return getXpath().evaluate(expression, source, returnType);
    }

    public static String evaluate(String expression, InputSource source) throws XPathExpressionException {
        return getXpath().evaluate(expression, source);
    }
}
