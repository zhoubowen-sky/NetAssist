package netassistpc.tcp;

import java.net.ServerSocket;
import java.net.Socket;

public class Server1 {
	public static void tcpServerReceive(int port) throws Exception {
		// �������20006�˿ڼ����ͻ��������TCP����
		ServerSocket server = new ServerSocket(port);//20006
		Socket client = null;
		boolean f = true;
		//while (f) {
			// �ȴ��ͻ��˵����ӣ����û�л�ȡ����
			client = server.accept();
			System.out.println("��ͻ������ӳɹ���");
			// Ϊÿ���ͻ������ӿ���һ���߳�
			new Thread(new ServerThread(client)).start();
			//}
		server.close();
	}
}
