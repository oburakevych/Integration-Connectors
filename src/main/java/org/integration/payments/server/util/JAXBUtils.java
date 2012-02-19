package org.integration.payments.server.util;

import org.apache.commons.collections.MapUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;
import org.springframework.util.ReflectionUtils.FieldFilter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.bind.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class JAXBUtils {
	private static Map<Class<?>, JAXBContext> contexts = new ConcurrentHashMap<Class<?>, JAXBContext>();
	
	private static Map<Class<?>, ThreadLocal<Unmarshaller>> unmarshallers = new ConcurrentHashMap<Class<?>, ThreadLocal<Unmarshaller>>();
	private static Map<Class<?>, ThreadLocal<Marshaller>> marshallers = new ConcurrentHashMap<Class<?>, ThreadLocal<Marshaller>>();
	
	public static JAXBContext getContext(Class<?> type) {
		JAXBContext ctx = contexts.get(type);
		if (ctx == null) {
			try {
				ctx = JAXBContext.newInstance(type);
				contexts.put(type, ctx);
			} catch (JAXBException e) {
				throw new RuntimeException("Unable to create context for " + type, e);
			}
		}
		return ctx;
	}
	
	public static byte[] marshall(Object input) {
		return marshall(input, true);
	}
	
	public static byte[] marshall(final Object input, boolean fragment) {
		return marshall(input, fragment, null);
	}
	public static byte[] marshall(final Object input, boolean fragment, Map<String, String> namespaces) {
		if (input == null) return null;
		
		Object obj = input;
		try {
			
			ThreadLocal<Marshaller> marshaller = marshallers.get(obj.getClass());
			if (marshaller == null) {
				marshaller = new ThreadLocal<Marshaller>() {
					protected Marshaller initialValue() {
						JAXBContext ctx = getContext(input.getClass());
						try {
						Marshaller marshaller = ctx.createMarshaller();
						marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
						return marshaller;
						} catch (JAXBException e) {
							throw new RuntimeException(e);
						}
					};
				};
				marshallers.put(obj.getClass(), marshaller);
			}
			
			if (!AnnotationUtils.isAnnotationInherited(XmlRootElement.class, obj.getClass())) {
				String factoryName = obj.getClass().getPackage().getName() + ".ObjectFactory";
				try {
					Class<?> factory = Class.forName(factoryName);
					Method[] ms = ReflectionUtils.getAllDeclaredMethods(factory);
					for (Method method : ms) {
						if (method.getReturnType().equals(JAXBElement.class) && method.getGenericReturnType() instanceof ParameterizedType) {
							ParameterizedType pt = (ParameterizedType) method.getGenericReturnType();
							Class<?> type = (Class<?>) pt.getActualTypeArguments()[0];
							if (type.equals(obj.getClass())) {
								Object f = factory.newInstance();
								obj = method.invoke(f, obj);
							}
						}
					}
					
				} catch (Exception e) {
					
				}
			}

			byte[] doc;
			if(MapUtils.isNotEmpty(namespaces)){
				Document wrapperDoc = buildWrapperDocument(namespaces);
				Marshaller m = marshaller.get();
				m.setProperty(Marshaller.JAXB_FRAGMENT, fragment);
				Element wrapperElement = wrapperDoc.getDocumentElement();
				m.marshal(obj, wrapperElement);
				Element firstChild = (Element)wrapperElement.getFirstChild();
				setAttributeNs(firstChild, namespaces);
				firstChild.setPrefix("");
				doc = XmlUtil.toByteArray(firstChild);
			}else{
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				Marshaller m = marshaller.get();
				m.setProperty(Marshaller.JAXB_FRAGMENT, fragment);
				m.marshal(obj, os);
				doc = os.toByteArray();
			}

			return doc;
		} catch (JAXBException e) {
			throw new RuntimeException("Unable to marshall " + obj, e);
		}
	}

	private static Document buildWrapperDocument(Map<String, String> commonNamespaces) {
		StringBuilder sb = new StringBuilder();
		sb.append("<Wrapper");
		buildNamespace(commonNamespaces, sb);
		sb.append("></Wrapper>");
		return XmlUtil.parse(sb.toString().getBytes());
	}

	private static void buildNamespace(Map<String, String> commonNamespaces, StringBuilder sb) {
		for (Map.Entry<String, String> prefix : commonNamespaces.entrySet()) {
			sb.append(" xmlns:");sb.append(prefix.getKey());
			sb.append("=\"");sb.append(prefix.getValue());sb.append("\"");
		}
	}

	private static void setAttributeNs(Element element, Map<String, String> commonNamespaces) {
		for (Map.Entry<String, String> prefix : commonNamespaces.entrySet()) {
			element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + prefix.getKey(), prefix.getValue());
		}
	}

	public static <T> T unmarshall(XMLStreamReader reader, final Class<T> type) {
		try {
			ThreadLocal<Unmarshaller> unmarshaller = getUnmarshaller(type);
		
			return unmarshaller.get().unmarshal(reader, type).getValue();
		} catch (JAXBException e) {
			throw new RuntimeException("Unable to unmarshall to " + type, e);
		}
		
	}
	
	public static <T> T unmarshall(byte[] data, final Class<T> type) {
		if (data == null) return null;
		
		try {
			ThreadLocal<Unmarshaller> unmarshaller = getUnmarshaller(type);
		
			JAXBElement<T> elm = unmarshaller.get().unmarshal(new StreamSource(new ByteArrayInputStream(data)), type);
			if (elm == null) return null;
			return elm.getValue();
		} catch (JAXBException e) {
			throw new RuntimeException("Unable to unmarshall to " + type + ", from: " + new String(data), e);
		}
	}

	private static <T> ThreadLocal<Unmarshaller> getUnmarshaller(final Class<T> type) {
		ThreadLocal<Unmarshaller> unmarshaller = unmarshallers.get(type);
		if (unmarshaller == null) {
			unmarshaller = new ThreadLocal<Unmarshaller>() {
				@Override
				protected Unmarshaller initialValue() {
					JAXBContext ctx = getContext(type);
					try {
						return ctx.createUnmarshaller();
					} catch (JAXBException e) {
						throw new RuntimeException(e);
					}
				}
			};
			
			unmarshallers.put(type, unmarshaller);
		}
		return unmarshaller;
	}
	
	public static Map<String, ?> toMap(final Object o) {
		final Map<String, Object> res = new HashMap<String, Object>();
		
		toMap("", res, o);
		
		return res;
	}
	
	private static void toMap(final String prefix, final Map<String, Object> res, final Object o) {
		if (o == null) return;
		
		ReflectionUtils.doWithFields(o.getClass(), new FieldCallback() {
			@Override
			public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
				field.setAccessible(true);
				try {
					XmlElement xe = field.getAnnotation(XmlElement.class);
					Object value = field.get(o);
					res.put(prefix + xe.name(), value);

					toMap(prefix + xe.name() + ".", res, value);
				} finally {
					field.setAccessible(false);
				}
			}
		}, new FieldFilter() {
			@Override
			public boolean matches(Field field) {
				field.setAccessible(true);
				try {
					return field.getAnnotation(XmlElement.class) != null;
				} finally {
					field.setAccessible(false);
				}
			}
		});
		
	}
}
