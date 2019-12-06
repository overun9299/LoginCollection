package overun.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * http请求工具类，提供get,post 等方法
 * @author admin
 * @date 2018-7-7
 * @since 1.0
 * */
public class HttpclientTools {
	/**定义日志*/
	private static Logger logger = LoggerFactory.getLogger(HttpclientTools.class);
	private static RequestConfig requestConfig = null;

	/***
	 * get 方法实现
	 * @param url 请求的url
	 * @param paramMap 参数集合,get 请求参数跟在连接后面
	 * @param token
	 * @return String
	 * */
	public static String doGet(String url, Map<String, String> paramMap, String token) {
		CloseableHttpClient httpClient = HttpConnectionManager.getHttpClient();
		String result = null;
		CloseableHttpResponse response = null;
		try {
			URIBuilder builder = new URIBuilder(url);
			if (paramMap != null && paramMap.size() > 0) {
				for (String key : paramMap.keySet()) {
					builder.addParameter(key, paramMap.get(key));
				}
			}
			URI uri = builder.build();
			HttpGet httpGet = new HttpGet(uri);
			httpGet.setConfig(requestConfig);
			if (StringUtils.isNotBlank(token))
				httpGet.setHeader("token", token);
			response = httpClient.execute(httpGet);
			/**接口调用返回为200表示成功*/
			logger.info("请求服务处理状态 status = " + response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = EntityUtils.toString(response.getEntity(), Constant.CHARSET);
			}
		} catch (Exception e) {
			logger.error("get 请求出现异常：" + e.getMessage());

		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					logger.error("finally出现异常：" + e.getMessage());
				}
			}
		}
		return result;
	}

	/***
	 * get 方法实现
	 * @param url 请求的url
	 * @return String
	 * */
	public static String doGet(String url) {
		return doGet(url, null, null);
	}

	/***
	 * post 方法实现
	 * @param url 请求的url
	 * @return String
	 * */
	public static String doPostMap(String url) {
		return doPostMap(url, null, null);
	}

	/***
	 * post 方法实现
	 * @param url 请求url
	 * @param paramMap 请求参数
	 * @return String
	 * */
	public static String doPostMap(String url, Map<String, String> paramMap, String contentType) {
		CloseableHttpClient httpClient = HttpConnectionManager.getHttpClient();
		String result = null;
		CloseableHttpResponse response = null;
		try {
			HttpPost httpPost = new HttpPost(url);
			if (paramMap != null && paramMap.size() > 0) {
				List<NameValuePair> params = new ArrayList<>();
				for (String key : paramMap.keySet()) {
					params.add(new BasicNameValuePair(key, paramMap.get(key)));
				}
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params);
				httpPost.setEntity(entity);
			}
			/**设置Content-type*/
			httpPost.setHeader("Content-type",
					StringUtils.isBlank(contentType) ? Constant.DEFAULT_CONTENT_TYPE : contentType);
			/**设置配置*/
			httpPost.setConfig(requestConfig);
			response = httpClient.execute(httpPost);
			logger.info("请求服务处理状态 status = " + response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(response.getEntity().getContent(), Constant.CHARSET));
				StringBuffer sb = new StringBuffer();
				String readLine = null;
				while ((readLine = reader.readLine()) != null) {
					sb.append(readLine);
				}
				result = sb.toString();
			}
		} catch (Exception e) {
			logger.error("post 请求出现异常：" + e.getMessage());
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					logger.error("finally出现异常：" + e.getMessage());
				}
			}
		}
		return result;
	}

	/***
	 * post 方法实现
	 * @param url 请求的url
	 * @return String
	 * */
	public static String doPostJson(String url) {
		return doPostJson(url, null, null);
	}

	/***
	 * post 方法实现
	 * @param url 请求url
	 * @param paramMap 请求参数
	 * @param contentType 传输文本类型，默认application/json; charset=utf-8
	 * @return String
	 * */
	public static String doPostJson(String url, String paramJson, String contentType) {
		CloseableHttpClient httpClient = HttpConnectionManager.getHttpClient();
		CloseableHttpResponse response = null;
		String result = null;
		HttpPost httpPost = new HttpPost(url);
		StringEntity entity = new StringEntity(paramJson, Constant.CHARSET);
		try {
			httpPost.setConfig(requestConfig);
			/**以json 格式传递需要设置Content-type*/
			httpPost.setHeader("Content-type",
					StringUtils.isBlank(contentType) ? Constant.DEFAULT_CONTENT_TYPE : contentType);
			httpPost.setEntity(entity);
			response = httpClient.execute(httpPost);
			logger.info("请求服务处理状态 status = " + response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(response.getEntity().getContent(), Constant.CHARSET));
				StringBuffer sb = new StringBuffer();
				String readLine = null;
				while ((readLine = reader.readLine()) != null) {
					sb.append(readLine);
				}
				result = sb.toString();
			}
		} catch (Exception e) {
			logger.error("post 请求出现异常：" + e.getMessage());
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					logger.error("finally出现异常：" + e.getMessage());
				}
			}
		}
		return result;
	}

	/**
	 * 关闭开启的资源
	 * @param httpClient http 连接对象
	 * @param response 返回对象
	 * */
	private static void close(CloseableHttpClient httpClient, CloseableHttpResponse response) {
		try {
			if (response != null) {
				response.close();
			}
			if (httpClient != null) {
				httpClient.close();
			}
		} catch (IOException e) {
			logger.error("关闭资源出现异常：" + e.getMessage());
		}
	}
}
