/**
 * 程序名称：网络调试助手Java版PC端
 * 作       者：周博文
 * 完成日期：2016年11月03日
 * 版       本：V1.0
 */

package netassistpc.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import netassistpc.main.*;

public class UDP {

	/**
	 * UDP消息发送的方法
	 * 
	 * @throws IOException
	 */
	public static void UDPsend(String ip, int port) throws IOException {
		System.out.println("UDPsend()方法启动。");

		DatagramSocket ds = new DatagramSocket();
		byte[] buf = NetAssist.jTextArea_srfs.getText().getBytes(); // 获取输入框中的内容
		DatagramPacket dp = new DatagramPacket(buf, buf.length, InetAddress.getByName(ip /* "127.0.0.1" */),
				port /* 10000 */);// 10000为定义的端口
		System.out.println("数据包开始发送。。。");
		ds.send(dp);
		ds.close();
	}

	/**
	 * UDP消息接收
	 * 
	 * @throws IOException
	 */
	public static void UDPReceive() throws IOException {
		// 创建UDP socket，建立端点
		DatagramSocket ds = new DatagramSocket(10000); // 监听10000端口

		// 定义数据包，用于存储数据
		byte[] buf = new byte[1024];
		DatagramPacket dp = new DatagramPacket(buf, buf.length);
		System.out.println("数据包开始接收");
		ds.receive(dp);

		String ip = dp.getAddress().getHostAddress(); // 数据提取
		String data = new String(dp.getData(), 0, dp.getLength());
		int port = dp.getPort();
		System.out.println(data + "." + port + ".." + ip);
		ds.close();
	}

}
