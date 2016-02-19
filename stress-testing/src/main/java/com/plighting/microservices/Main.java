package com.plighting.microservices;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public class Main {
	private static int count = 0;

	public static void main(String... args) {
		HttpGet get = new HttpGet("http://localhost:9090/bookmark/");
		while (true) {
			try {
				Thread.sleep(200);

				HttpClient httpClient = HttpClientBuilder.create().build();

				HttpResponse httpResponse = httpClient.execute(get);
				if (httpResponse.getHeaders("xcorg").length != 0)
					System.out.println("Message" + count++ + ":"
							+ httpResponse.getHeaders("xcorg")[0].getValue());
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();

			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
