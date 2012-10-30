/**
 * EasySSLSocketFactory.java
 *
 */
package br.org.yacamim.http.ssl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;

import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.LayeredSocketFactory;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.util.Log;

/**
 * 
 * Class EasySSLSocketFactory TODO
 * 
 * @author Community
 * @version 1.0
 * @since 1.0
 */
public class EasySSLSocketFactory implements LayeredSocketFactory {

	private SSLContext sslcontext = null;

	/**
	 * 
	 */
	public EasySSLSocketFactory() {
		super();
	}
	
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	private static SSLContext createEasySSLContext() throws IOException {
		try {
			SSLContext context = SSLContext.getInstance("TLS");
			context.init(null, new TrustManager[] { new EasyX509TrustManager(
					null) }, null);
			return context;
		} catch (Exception _e) {
			Log.e("EasySSLSocketFactory.createEasySSLContext", _e.getMessage());
			throw new IOException(_e.getMessage());
		}
	}

	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	private SSLContext getSSLContext() throws IOException {
		if (this.sslcontext == null) {
			this.sslcontext = createEasySSLContext();
		}
		return this.sslcontext;
	}

	/**
	 * @see org.apache.http.conn.scheme.SocketFactory#connectSocket(java.net.Socket,
	 *      java.lang.String, int, java.net.InetAddress, int,
	 *      org.apache.http.params.HttpParams)
	 */
	public Socket connectSocket(Socket sock, String host, int port,
			InetAddress localAddress, int localPort, HttpParams params)
			throws IOException, UnknownHostException, ConnectTimeoutException {
		
		final int connTimeout = HttpConnectionParams.getConnectionTimeout(params);
		final int soTimeout = HttpConnectionParams.getSoTimeout(params);

		final InetSocketAddress remoteAddress = new InetSocketAddress(host, port);
		final SSLSocket sslsock = (SSLSocket) ((sock != null) ? sock : createSocket());

		if ((localAddress != null) || (localPort > 0)) {
			// é necessário fazer o bind explícitamente
			if (localPort < 0) {
				localPort = 0; // indica "qualquer um"
			}
			InetSocketAddress isa = new InetSocketAddress(localAddress,
					localPort);
			sslsock.bind(isa);
		}

		sslsock.connect(remoteAddress, connTimeout);
		sslsock.setSoTimeout(soTimeout);
		return sslsock;

	}

	/**
	 * @see org.apache.http.conn.scheme.SocketFactory#createSocket()
	 */
	public Socket createSocket() throws IOException {
		return getSSLContext().getSocketFactory().createSocket();
	}

	/**
	 * @see org.apache.http.conn.scheme.SocketFactory#isSecure(java.net.Socket)
	 */
	public boolean isSecure(Socket socket) throws IllegalArgumentException {
		return true;
	}

	/**
	 * @see org.apache.http.conn.scheme.LayeredSocketFactory#createSocket(java.net.Socket,
	 *      java.lang.String, int, boolean)
	 */
	public Socket createSocket(Socket socket, String host, int port,
			boolean autoClose) throws IOException, UnknownHostException {
        return getSSLContext().getSocketFactory().createSocket(socket, host, port, autoClose);
	}

	/**
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		return ((obj != null) && obj.getClass().equals(
				EasySSLSocketFactory.class));
	}

	/**
	 *
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return EasySSLSocketFactory.class.hashCode();
	}

}
