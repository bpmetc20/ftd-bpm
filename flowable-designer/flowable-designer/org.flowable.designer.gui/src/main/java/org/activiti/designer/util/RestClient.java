package org.activiti.designer.util;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RestClient {

	private static CredentialsProvider provider;
	private static SSLConnectionSocketFactory sslsf;
	private static String ftdProxyRsPrefix = "https://165.227.16.142.nip.io:8443/ftdproxyrs/";
	private static String user = "rest";
	private static String password = "test";
	
	static {
		try {
			SSLContextBuilder builder = new SSLContextBuilder();
			builder.loadTrustMaterial(null, createTrustAllStrategy());
			sslsf = new SSLConnectionSocketFactory(builder.build());
			provider = new BasicCredentialsProvider();
			provider.setCredentials(AuthScope.ANY, 
					new UsernamePasswordCredentials(user, password));

		} catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Map<String, String> getForms() {
		return getCollection(ftdProxyRsPrefix + "forms");
	}

	public static Map<String, String> getUsers() {
		return getCollection(ftdProxyRsPrefix + "users");
	}
	
	public static Map<String, String> getGroups() {
		return getCollection(ftdProxyRsPrefix + "groups");
	}
	
	@SuppressWarnings("unchecked")
	private static Map<String, String> getCollection(String url) {
		HttpGet request = new HttpGet(url);
		Map<String, String> collection;
		try (CloseableHttpClient httpClient = HttpClients.custom()
				.setSSLSocketFactory(sslsf)
				.setHostnameVerifier(createHostnameVerifier())
				.setDefaultCredentialsProvider(provider)
				.build();
				CloseableHttpResponse response = httpClient.execute(request)) {
			
			request.addHeader("Accept", "application/json");
			request.addHeader("Content-Type", "application/json");

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new Exception("couldn't invoke url ");
			}
			collection = new ObjectMapper().readValue(response.getEntity().getContent(), Map.class);
			
		} catch (Exception ex) {
			// TODO bring error pop-up window
			collection = new HashMap<>();
		}
		
		return collection;
	}

	private static TrustStrategy createTrustAllStrategy() {
		return new TrustStrategy() {
			public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				return true;
			}
		};
	}

	private static X509HostnameVerifier createHostnameVerifier() {
		return new X509HostnameVerifier() {
			@Override
			public void verify(String host, SSLSocket ssl) throws IOException {
			}

			@Override
			public void verify(String host, X509Certificate cert) throws SSLException {
			}

			@Override
			public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
			}

			@Override
			public boolean verify(String s, SSLSession sslSession) {
				return true;
			}
		};
	}

}
