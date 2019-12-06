package overun.utils;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @ClassName: HttpConnectionManager
 * @Description: httpclient���ӳ�
 * @author: ޲�׵δ�-����
 * @version: V1.0
 * @date: 2018��8��25�� ����2:16:21
 * @Copyright: 2018 www.yimidida.com Inc. All rights reserved.
 */
public class HttpConnectionManager {

	/**定义日志*/
	private static Logger logger = LoggerFactory.getLogger(HttpclientTools.class);

	static PoolingHttpClientConnectionManager cm = null;

	static {
		LayeredConnectionSocketFactory sslsf = null;
		try {
			sslsf = new SSLConnectionSocketFactory(SSLContext.getDefault());
		} catch (NoSuchAlgorithmException e) {
			logger.error("HttpConnectionManager异常：" + e.getMessage());
		}

		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("https", sslsf).register("http", new PlainConnectionSocketFactory()).build();
		cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		cm.setMaxTotal(200);
		cm.setDefaultMaxPerRoute(20);
	}

	/**
	 *
	 * @MethodName getHttpClient
	 * @Description ��ȡ��httpclient
	 * @return
	 */
	public static CloseableHttpClient getHttpClient() {
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
		return httpClient;
	}
}
