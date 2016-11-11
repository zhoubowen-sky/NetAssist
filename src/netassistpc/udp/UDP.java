/**
 * �������ƣ������������Java��PC��
 * ��       �ߣ��ܲ���
 * ������ڣ�2016��11��03��
 * ��       ����V1.0
 */

package netassistpc.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import netassistpc.main.*;

public class UDP {

	/**
	 * UDP��Ϣ���͵ķ���
	 * 
	 * @throws IOException
	 */
	public static void UDPsend(String ip, int port) throws IOException {
		System.out.println("UDPsend()����������");

		DatagramSocket ds = new DatagramSocket();
		byte[] buf = NetAssist.jTextArea_srfs.getText().getBytes(); // ��ȡ������е�����
		DatagramPacket dp = new DatagramPacket(buf, buf.length, InetAddress.getByName(ip /* "127.0.0.1" */),
				port /* 10000 */);// 10000Ϊ����Ķ˿�
		System.out.println("���ݰ���ʼ���͡�����");
		ds.send(dp);
		ds.close();
	}

	/**
	 * UDP��Ϣ����
	 * 
	 * @throws IOException
	 */
	public static void UDPReceive() throws IOException {
		// ����UDP socket�������˵�
		DatagramSocket ds = new DatagramSocket(10000); // ����10000�˿�

		// �������ݰ������ڴ洢����
		byte[] buf = new byte[1024];
		DatagramPacket dp = new DatagramPacket(buf, buf.length);
		System.out.println("���ݰ���ʼ����");
		ds.receive(dp);

		String ip = dp.getAddress().getHostAddress(); // ������ȡ
		String data = new String(dp.getData(), 0, dp.getLength());
		int port = dp.getPort();
		System.out.println(data + "." + port + ".." + ip);
		ds.close();
	}

}
