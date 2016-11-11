/**
 * �������ƣ������������Java��PC��
 * ��       �ߣ��ܲ���
 * ������ڣ�2016��11��03��
 * ��       ����V1.0
 */
package netassistpc.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import netassistpc.main.NetAssist;

public class UDPReceiveThread extends Thread {

	public static boolean flag = true;// �˳����ճ���ʱ��ı�־λ
	int port;// �˿�

	// ���췽��
	public UDPReceiveThread(int port) {
		this.port = port;
	}

	public static void receiveUDP() throws Exception {
		// ����UDP socket�������˵�
		DatagramSocket ds = new DatagramSocket(Integer.parseInt(NetAssist.jTextField_UDP_bdzjdk.getText())); // �����˿�

		// �������ݰ������ڴ洢����
		byte[] buf = new byte[1024];
		DatagramPacket dp = new DatagramPacket(buf, buf.length);
		System.out.println("���ݰ���ʼ����");
		ds.receive(dp);

		String ip = dp.getAddress().getHostAddress(); // ������ȡ
		String data = new String(dp.getData(), 0, dp.getLength());
		int port = dp.getPort();
		System.out.println(data + "." + port + "." + ip);
		ds.close();

		NetAssist.jTextArea_wbjsxs.append(data);// ����Ļд����ܵ�������
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
