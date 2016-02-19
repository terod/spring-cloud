package eureka_application_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Date;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryManager;

public class ExampleEurekaClient {
	private static final DynamicPropertyFactory configInstance = com.netflix.config.DynamicPropertyFactory
			.getInstance();

	public void sendRequestToServiceUsingEureka() {
		// initialize the client
		DiscoveryManager.getInstance().initComponent(
				new MyDataCenterInstanceConfig(),
				new DefaultEurekaClientConfig());

		// this is the vip address for the example service to talk to as defined
		// in conf/sample-eureka-service.properties
		String vipAddress = "eureka.mydomain.net";

		InstanceInfo nextServerInfo = null;
		try {
			nextServerInfo = DiscoveryManager.getInstance().getEurekaClient()
					.getNextServerFromEureka(vipAddress, false);
		} catch (Exception e) {
			System.err
					.println("Cannot get an instance of example service to talk to from eureka");
			System.exit(-1);
		}

		System.out
				.println("Found an instance of example service to talk to from eureka: "
						+ nextServerInfo.getVIPAddress()
						+ ":"
						+ nextServerInfo.getPort());

		System.out.println("healthCheckUrl: "
				+ nextServerInfo.getHealthCheckUrl());
		System.out.println("override: " + nextServerInfo.getOverriddenStatus());

		Socket s = new Socket();
		int serverPort = nextServerInfo.getPort();
		try {
			s.connect(new InetSocketAddress(nextServerInfo.getHostName(),
					serverPort));
		} catch (IOException e) {
			System.err.println("Could not connect to the server :"
					+ nextServerInfo.getHostName() + " at port " + serverPort);
		} catch (Exception e) {
			System.err.println("Could not connect to the server :"
					+ nextServerInfo.getHostName() + " at port " + serverPort
					+ "due to Exception " + e);
		}
		try {
			String request = "FOO " + new Date();
			System.out
					.println("Connected to server. Sending a sample request: "
							+ request);

			PrintStream out = new PrintStream(s.getOutputStream());
			out.println(request);

			System.out.println("Waiting for server response..");
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					s.getInputStream()));
			String str = rd.readLine();
			if (str != null) {
				System.out.println("Received response from server: " + str);
				System.out.println("Exiting the client. Demo over..");
			}
			rd.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// finally shutdown
		DiscoveryManager.getInstance().getEurekaClient().shutdown();
	}

	public static void main(String[] args) {
		ExampleEurekaClient sampleClient = new ExampleEurekaClient();
		sampleClient.sendRequestToServiceUsingEureka();
	}

}
