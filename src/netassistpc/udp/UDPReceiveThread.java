/**
 * 程序名称：网络调试助手Java版PC端
 * 作       者：周博文
 * 完成日期：2016年11月03日
 * 版       本：V1.0
 */
package netassistpc.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import netassistpc.main.NetAssist;

public class UDPReceiveThread extends Thread {

	public static boolean flag = true;// 退出接收程序时候的标志位
	int port;// 端口

	// 构造方法
	public UDPReceiveThread(int port) {
		this.port = port;
	}

	public static void receiveUDP() throws Exception {
		// 创建UDP socket，建立端点
		DatagramSocket ds = new DatagramSocket(Integer.parseInt(NetAssist.jTextField_UDP_bdzjdk.getText())); // 监听端口

		// 定义数据包，用于存储数据
		byte[] buf = new byte[1024];
		DatagramPacket dp = new DatagramPacket(buf, buf.length);
		System.out.println("数据包开始接收");
		ds.receive(dp);

		String ip = dp.getAddress().getHostAddress(); // 数据提取
		String data = new String(dp.getData(), 0, dp.getLength());
		int port = dp.getPort();
		System.out.println(data + "." + port + "." + ip);
		ds.close();

		NetAssist.jTextArea_wbjsxs.append(data);// 向屏幕写入接受到的数据
	}

	public void run() {
		while (true) {
			if (flag == false) {
				break;
			}

			try {
				receiveUDP();
			} catch (Exception e) {

				e.printStackTrace();
			}

		}
	}
}
