package de.aice.act.xml;

import de.aice.act.Response;
import java.io.IOException;
import java.io.StringReader;
import javax.xml.transform.stream.StreamSource;
import org.junit.Test;

import static de.aice.act.Act.fixed;
import static de.aice.act.Headers.CONTENT_TYPE;
import static de.aice.act.Request.request;
import static de.aice.act.misc.Strings.joinOn;
import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public final class XmlTest {

	@Test
	public void convertsXmlToPlainText() throws IOException {
		String xml = joinOn(" ",
		                    "<?xml-stylesheet href='/x.xsl' type='text/xsl'?>",
		                    "<p><name>Jeffrey</name></p>");
		String xsl = joinOn(" ",
		                    "<stylesheet xmlns='http://www.w3.org/1999/XSL/Transform' ",
		                    "            xmlns:x='http://www.w3.org/1999/xhtml' version='2.0'>",
		                    "    <output method='text'/>",
		                    "    <template match='/'>Hey, <value-of select='/p/name'/>!</template>",
		                    "</stylesheet>");
		Response response = Xml.xslt(request -> Response.ok(xml),
		                             (href, base) -> new StreamSource(new StringReader(xsl)))
		                       .on(request("GET"));

		assertTrue(response.headers.has(CONTENT_TYPE, "application/xml"));
		assertTrue(response.body.endsWith("Hey, Jeffrey!"));
	}

	@Test
	public void resolvesInClasspath() throws IOException {
		checkXsl("/xsl/simple.xsl", "Dan");
		checkXsl("/xsl/simple.xsl?0", "Bobby");
		checkXsl("/xsl/includes.xsl", "Miranda");
	}

	@Test
	public void throwsExceptionIfStylesheetCanNotBeResolved() throws Exception {
		try {
			Xml.xslt(fixed(joinOn(" ",
			                      "<?xml-stylesheet href='/unknown.xsl' type='text/xsl'?>",
			                      "<p><name>Bob</name></p>")))
			   .on(request("GET"));
			fail();
		} catch (Exception e) {
			assertEquals("javax.xml.transform.TransformerException: '/unknown.xsl' not found in classpath, base='null'",
			             e.getMessage());
		}
	}

	@Test
	public void throwsExceptionIfNoStylesheetIsAssociated() throws Exception {
		try {
			Xml.xslt(fixed("<p><name>Dandy</name></p>"))
				.on(request("GET"));
			fail();
		} catch (Exception e) {
			assertEquals("No matching <?xml-stylesheet?> processing instruction found", e.getMessage());
		}

	}

	private static void checkXsl(String pathToXsl, String name) {
		Response response = Xml.xslt(fixed(joinOn(" ",
		                                          format("<?xml-stylesheet href='%s' type='text/xsl'?>", pathToXsl),
		                                          format("<p><name>%s</name></p>", name))))
		                       .on(request("GET"));
		assertTrue(response.body.endsWith(format("Hello, %s!", name)));
	}
}