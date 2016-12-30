package urlshortener.aeternum;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import urlshortener.aeternum.web.QrGenerator;

import java.net.URI;
import java.nio.charset.Charset;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.boot.Banner.Mode.LOG;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= RANDOM_PORT)
@DirtiesContext
public class SystemTests {
    private static final Logger LOG = LoggerFactory
        .getLogger(SystemTests.class);

	@Value("${local.server.port}")
	private int port = 0;

	@Test
	public void testHome() throws Exception {
		ResponseEntity<String> entity = new TestRestTemplate().getForEntity(
				"http://localhost:" + this.port, String.class);
		assertThat(entity.getStatusCode(), is(HttpStatus.OK));
		assertTrue(entity.getHeaders().getContentType().isCompatibleWith(new MediaType("text", "html")));
		assertThat(entity.getBody(), containsString("<title>URL"));
	}

	@Test
	public void testCss() throws Exception {
		ResponseEntity<String> entity = new TestRestTemplate().getForEntity(
				"http://localhost:" + this.port
						+ "/webjars/bootstrap/3.3.5/css/bootstrap.min.css", String.class);
		assertThat(entity.getStatusCode(), is(HttpStatus.OK));
		assertThat(entity.getHeaders().getContentType(), is(MediaType.valueOf("text/css")));
		assertThat(entity.getBody(), containsString("body"));
	}

	@Test
	public void testCreateLink() throws Exception {
		ResponseEntity<String> entity = postLink("http://example.com/");
		assertThat(entity.getStatusCode(), is(HttpStatus.CREATED));
		assertThat(entity.getHeaders().getLocation(), is(new URI("http://localhost:"+ this.port+"/f684a3c4")));
		assertThat(entity.getHeaders().getContentType(), is(new MediaType("application", "json", Charset.forName("UTF-8"))));
		ReadContext rc = JsonPath.parse(entity.getBody());
		assertThat(rc.read("$.hash"), is("f684a3c4"));
		assertThat(rc.read("$.uri"), is("http://localhost:"+ this.port+"/f684a3c4"));
		assertThat(rc.read("$.target"), is("http://example.com/"));
		assertThat(rc.read("$.sponsor"), is(nullValue()));
	}

	@Test
	public void testRedirection() throws Exception {
		postLink("http://example.com/");
		ResponseEntity<String> entity = new TestRestTemplate().getForEntity(
				"http://localhost:" + this.port
						+ "/f684a3c4", String.class);
		assertThat(entity.getStatusCode().toString(), is(HttpStatus.TEMPORARY_REDIRECT.toString()));
		assertThat(entity.getHeaders().getLocation().toString(), is("http://example.com/"));
	}

	private ResponseEntity<String> postLink(String url) {
		MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
		parts.add("url", url);
		return new TestRestTemplate().postForEntity(
				"http://localhost:" + this.port+"/link", parts, String.class);
	}



}
