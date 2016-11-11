package netassistpc.tcp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import netassistpc.main.NetAssist;

public class ServerThread extends Thread /*implements Runnable */{

	private Socket client = null;

	public ServerThread(Socket client) {
		this.client = client;
	}

	@Override
	public void run() {
		try {
			// ��ȡSocket���������������ͻ��˷�������
			PrintStream out = new PrintStream(client.getOutputStream());
			// ��ȡSocket�����������������մӿͻ��˷��͹���������
			BufferedReader buf = new BufferedReader(new InputStreamReader(client.getInputStream()));
			boolean flag = true;
			while (flag) {
				// ���մӿͻ��˷��͹���������
				String str = buf.readLine();
				if (str == null || "".equals(str)) {
					flag = false;
				} else {
					if ("bye".equals(str)) {
						flag = false;
					} else {
						// �����յ����ַ���ǰ�����echo�����͵���Ӧ�Ŀͻ���
						out.println("echo:" + str);
						NetAssist.jTextArea_wbjsxs.append(str);//���ı���ʾ��������ʾ����
					}
				}
			}
			out.close();
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
