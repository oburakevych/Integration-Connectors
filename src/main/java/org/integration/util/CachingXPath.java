package org.integration.util;

import java.util.Map;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFunctionResolver;
import javax.xml.xpath.XPathVariableResolver;

import org.apache.commons.collections.map.LRUMap;
import org.integration.connectors.tradeshift.document.ubl.UblNamespaceContext;
import org.xml.sax.InputSource;


/**
 * Caches known XPath expressions, by default in a HashMap (so don't keep creating new expressions.
 * 
 * This implementation is not thread-safe, create one per thread (just like XPath).
 */
public class CachingXPath implements XPath {
    private static final int CACHE_SIZE = 100;
    private XPath delegate;
    private Map<String,XPathExpression> cache = createCache(CACHE_SIZE);
    
    public CachingXPath () {
        this(null);
    }

    public CachingXPath (XPath delegate) {
        if (delegate == null) {
            delegate = createDelegate();
        }
        
        this.delegate = delegate;
    }
    
    /**
     * Create a new XPath instance to be used in a thread. By default, creates it by creating a new XPathFactory
     * and then a new XPath, wrapping it in a CachingXPath.
     */
    protected XPath createDelegate() {
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
        xpath.setNamespaceContext(new UblNamespaceContext());
        
        return xpath;
    }
    
    @SuppressWarnings("unchecked")
    protected Map<String, XPathExpression> createCache(int size) {
        return new LRUMap(size);
    }

    public void reset() {
        delegate.reset();
        cache.clear();
    }

    public void setXPathVariableResolver(XPathVariableResolver resolver) {
        delegate.setXPathVariableResolver(resolver);
    }

    public XPathVariableResolver getXPathVariableResolver() {
        return delegate.getXPathVariableResolver();
    }

    public void setXPathFunctionResolver(XPathFunctionResolver resolver) {
        delegate.setXPathFunctionResolver(resolver);
    }

    public XPathFunctionResolver getXPathFunctionResolver() {
        return delegate.getXPathFunctionResolver();
    }

    public void setNamespaceContext(NamespaceContext nsContext) {
        delegate.setNamespaceContext(nsContext);
    }

    public NamespaceContext getNamespaceContext() {
        return delegate.getNamespaceContext();
    }

    private XPathExpression lookup (String expression) throws XPathExpressionException {
        XPathExpression xPathExpression = cache.get(expression);
        if (xPathExpression == null) {
            xPathExpression = delegate.compile(expression);
            cache.put (expression, xPathExpression);
        }
        return xPathExpression;
    }
    
    public XPathExpression compile(String expression) throws XPathExpressionException {
        return lookup (expression);
    }

    public Object evaluate(String expression, Object item, QName returnType) throws XPathExpressionException {
        return lookup (expression).evaluate(item, returnType);
    }

    public String evaluate(String expression, Object item) throws XPathExpressionException {
        return lookup (expression).evaluate(item);
    }

    public Object evaluate(String expression, InputSource source, QName returnType) throws XPathExpressionException {
        return lookup (expression).evaluate(source, returnType);
    }

    public String evaluate(String expression, InputSource source) throws XPathExpressionException {
        return lookup (expression).evaluate(source);
    }
}
