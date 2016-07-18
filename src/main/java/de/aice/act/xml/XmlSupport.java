package de.aice.act.xml;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import static de.aice.act.misc.Exceptions.unchecked;
import static java.nio.charset.Charset.defaultCharset;

/**
 * Xml support.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
interface XmlSupport {

	/**
	 * Create {@link StreamSource} with given systemId and inputStream.
	 *
	 * @param systemId    systemId to set
	 * @param inputStream resource to load
	 * @return StreamSource
	 */
	static StreamSource streamSource(String systemId, InputStream inputStream) {
		StreamSource source = new StreamSource(new InputStreamReader(inputStream, defaultCharset()));
		source.setSystemId(systemId);
		return source;
	}

	/**
	 * Create transformerFactory with given {@link URIResolver}.
	 *
	 * @param resolver uri resolver to use.
	 * @return TransformerFactory
	 */
	static TransformerFactory transformerFactory(URIResolver resolver) {
		TransformerFactory factory = TransformerFactory.newInstance();
		factory.setURIResolver(resolver);
		return factory;
	}

	/**
	 * Transform xml with associated stylesheet.
	 *
	 * @param factory factory to use
	 * @param xml     xml with stylesheet
	 * @return transformed xml
	 */
	static String transform(TransformerFactory factory, String xml) {
		Source stylesheet = associatedStylesheet(factory, xml);
		StringWriter writer = new StringWriter();
		unchecked(() -> factory.newTransformer(stylesheet).transform(new StreamSource(new StringReader(xml)),
		                                                             new StreamResult(writer)));
		return writer.toString();
	}

	/**
	 * Load associated stylesheet.
	 *
	 * @param factory factory to use
	 * @param xml     xml with stylesheet
	 * @return stylesheet source
	 */
	static Source associatedStylesheet(TransformerFactory factory, String xml) {
		return unchecked(() -> factory.getAssociatedStylesheet(new StreamSource(new StringReader(xml)),
		                                                       null,
		                                                       null,
		                                                       null));
	}

}
