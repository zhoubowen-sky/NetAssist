package netassistpc.tcp;

import java.net.ServerSocket;
import java.net.Socket;

public class Server1 extends Thread{
	
	public static boolean flag_Server1 = false;
	int port;//�˿�
	
	//���캯��
	public Server1(int port){
		this.port = port;
	}
	
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
	
	public void run() {
		while(true){
			if(flag_Server1 == false){break;}//����ѭ��
			
			try {
			tcpServerReceive(port);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
	}
}
