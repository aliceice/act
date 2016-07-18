package de.aice.act.xml;

import de.aice.act.Act;
import de.aice.act.Headers;
import de.aice.act.Response;
import java.net.URI;
import java.util.Optional;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;

import static de.aice.act.misc.Exceptions.unchecked;
import static de.aice.act.xml.XmlSupport.streamSource;
import static de.aice.act.xml.XmlSupport.transform;
import static de.aice.act.xml.XmlSupport.transformerFactory;
import static java.lang.String.format;

/**
 * Xml.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public interface Xml {

	/**
	 * Act with XSL transformation of response body.
	 * Uses a custom {@link URIResolver} which resolves resources in classpath.
	 *
	 * @param act origin. must provide xml with stylesheet.
	 * @return Act with XSL transformation.
	 */
	static Act xslt(Act act) {
		return xslt(act, (href, base) -> {
			String path = Optional.ofNullable(base)
			                      .map(s -> URI.create(s).resolve(href))
			                      .orElseGet(() -> URI.create(href))
			                      .getPath();
			return Optional.ofNullable(System.class.getResourceAsStream(path))
			               .map(inputStream -> streamSource(path, inputStream))
			               .orElseThrow(() -> new TransformerException(format("'%s' not found in classpath, base='%s'",
			                                                                  href,
			                                                                  base)));
		});
	}

	/**
	 * Act with XSL transformation of response body.
	 *
	 * @param act      origin. must provide xml with stylesheet.
	 * @param resolver uri resolver to resolve stylesheets in xml.
	 * @return Act with XSL transformation.
	 */
	static Act xslt(Act act, URIResolver resolver) {
		return request -> unchecked(() -> {
			Response response = act.on(request);
			return response.body(transform(transformerFactory(resolver), response.body))
			               .header(Headers.CONTENT_TYPE, "application/xml");
		});
	}

}
