package framework.automation.driver.web;

import java.io.IOException;

import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.ProxyServer;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

//TODO - migrate to beta-10 version because of https do not support 
// https://github.com/lightbody/browsermob-proxy/issues/82
// https://github.com/lightbody/browsermob-proxy/issues/129
/**
 * Class that represent Proxy instance for webdriver, ability to run webdriver
 * with started proxy to get the har
 * 
 * @author Taras.Lytvyn
 *
 */
public class ProxyServerForBrowser {

	private ProxyServer server = null;
	private static ProxyServerForBrowser proxyForBrowser;
	private boolean serverStarted = false;

	private ProxyServerForBrowser() {
		server = new ProxyServer();
	}

	/**
	 * singleton to get Proxy Server instance 
	 * @return instance of proxy server
	 */
	public static ProxyServerForBrowser getServerInstance() {
		if (proxyForBrowser == null) {
			proxyForBrowser = new ProxyServerForBrowser();
		}
		return proxyForBrowser;
	}

	/**
	 * starts the proxy server
	 * @param capabilities	desired capabilities of webdriver 
	 * @param port	proxy server port
	 * @throws Exception	if it is already running on the same port
	 */
	public void startProxyServer(DesiredCapabilities capabilities, int port)
			throws Exception {
		if (!serverStarted) {
			String PROXY = "localhost:" + port;
			server.setPort(port);
			server.start();
			Proxy proxy = new Proxy();
			proxy.setHttpProxy(PROXY).setFtpProxy(PROXY).setSslProxy(PROXY);
			capabilities.setCapability(CapabilityType.PROXY, proxy);
			serverStarted = true;
		} else {
			String errorMessage = "Another server instance is currently running on port: "
					+ port + ", ";
			throw new Exception(errorMessage);
		}
	}

	/**
	 * stops proxy server
	 * @throws Exception if it is currently stopped
	 */
	public void stopProxyServer() throws Exception {
		if (serverStarted) {
			server.cleanup();
			server.stop();
			serverStarted = false;
		}
	}

	/**
	 * starts Http Archive capturing
	 * @param url 	url to start HAR capturing
	 */
	public void startHarCapturing(String url) {
		server.newHar(url);
	}

	/**
	 * @return written Har object during HAR capturing
	 * @throws IOException
	 */
	public Har getWrittenHar() {
		return server.getHar();
	}

}
