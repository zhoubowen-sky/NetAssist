package netassistpc.tcp;

import java.net.ServerSocket;
import java.net.Socket;

public class Server1 extends Thread{
	
	public static boolean flag_Server1 = false;
	int port;//端口
	
	//构造函数
	public Server1(int port){
		this.port = port;
	}
	
	public static void tcpServerReceive(int port) throws Exception {
		// 服务端在20006端口监听客户端请求的TCP连接
		ServerSocket server = new ServerSocket(port);//20006
		Socket client = null;
		boolean f = true;
		//while (f) {
			// 等待客户端的连接，如果没有获取连接
			client = server.accept();
			System.out.println("与客户端连接成功！");
			// 为每个客户端连接开启一个线程
			new Thread(new ServerThread(client)).start();
			//}
		server.close();
	}
	
	public void run() {
		while(true){
			if(flag_Server1 == false){break;}//跳出循环
			
			try {
			tcpServerReceive(port);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
	}
}
