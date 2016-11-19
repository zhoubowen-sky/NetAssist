package netassistpc.tcp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import netassistpc.main.NetAssist;

public class ServerThread extends Thread /* implements Runnable */ {

	public static Socket client = null;
	public boolean flag = true;

	public ServerThread(Socket client) {
		this.client = client;
	}

	// TCPServer 消息接收
	public static void receiveTCP() {
		try {
			// 获取Socket的输出流，用来向客户端发送数据
			PrintStream out = new PrintStream(client.getOutputStream());
			// 获取Socket的输入流，用来接收从客户端发送过来的数据
			BufferedReader buf = new BufferedReader(new InputStreamReader(client.getInputStream()));
			// boolean flag = true;
			// while (flag) {
			// 接收从客户端发送过来的数据
			String str = buf.readLine();
			if (str == null || "".equals(str)) {
				// flag = false;
			} else {
				if ("bye".equals(str)) {
					// flag = false;
				} else {
					// 将接收到的字符串前面加上echo，发送到对应的客户端
					// out.println("echo:" + str);
					// NetAssist.jTextArea_wbjsxs.append(str);//在文本显示框里面显示文字
				}
			}

			out.println("echo:" + str);
			NetAssist.jTextArea_wbjsxs.append(str);// 在文本显示框里面显示文字

			// }
			out.close();
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

		// while(true){
		// if(flag == false){break;}//跳出循环
		// 开启TCP消息的接收线程
		receiveTCP();
		// }

	}

}
