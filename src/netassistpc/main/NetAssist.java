/**
 * 程序名称：网络调试助手Java版PC端
 * 作       者：周博文
 * 完成日期：2016年11月03日
 * 版       本：V1.0
 */

package netassistpc.main;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;

import netassistpc.tcp.Client1;
import netassistpc.tcp.Server1;
import netassistpc.udp.UDP;
import netassistpc.udp.UDPReceiveThread;

public class NetAssist extends JFrame{
	
	//构造函数
	public NetAssist(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static final int winWidth = 550;//窗口宽度
	public static final int winHeight = 550;//窗口高度
	public static final int winLeft = 600;//距离屏幕左边缘距离
	public static final int winRight = 100;//距离屏幕上面边缘距离
	
	/*以下是UI界面的相关组件*/
	public static JTextField jTextField_UDP_bdzjdz = new JTextField("本地主机地址",15);//UDP本地主机地址编辑框
	public static JTextField jTextField_UDP_bdzjdk = new JTextField("本地主机端口",15);//UDP本地主机端口编辑框
	//public static JTextField jTextField_TCPClient_bdzjdz = new JTextField("本地主机地址",15);//TCP Client本地主机地址编辑框
	//public static JTextField jTextField_TCPClient_bdzjdk = new JTextField("远程主机地址",15);//TCP Client本地主机端口编辑框
	public static JTextField jTextField_TCPServer_bdzjdz = new JTextField("本地主机地址",15);//TCP Server本地主机地址编辑框
	public static JTextField jTextField_TCPServer_bdzjdk = new JTextField("本地主机端口",15);//TCP Server本地主机端口编辑框
	public static JButton jButton_UDP_start = new JButton("开启UDP服务");//开启UDP服务器的按钮
	public static JButton jButton_UDP_stop = new JButton("关闭UDP服务");//关闭UDP服务器的按钮
	public static JButton jButton_TCPServer_start = new JButton("开启TCP Server服务");//开启TCP Server服务的按钮
	public static JButton jButton_TCPServer_stop = new JButton("关闭TCP Server服务");//关闭TCP Server服务的按钮
	
	public static JTextArea jTextArea_srfs = new JTextArea(5,10);//输入并发送的文本框
	public static JTextArea jTextArea_wbjsxs = new JTextArea(10,10);//接收显示文本框
	
	public static int SEND_STATE = 1;//"点击发送"按钮对应的状态变量，0为向局域网所有设备发送UDP广播，1为发送UDP，2为发送TCP
	
	public void go(){
		JFrame win = new JFrame("网络调试助手Java版 V1.0");    //程序窗口
		win.setBounds(winLeft, winRight, winWidth, winHeight);   //程序窗口默认的位置以及大小
		
		Container container = win.getContentPane();  //创建Container隐性层，组件都是放在这个隐性层上面
		container.setLayout(new GridLayout(1,2));   //布局管理,Grid 表示1行2列
		
		JPanel jpleft = new JPanel();//左边布局
		JPanel jpright = new JPanel();//右边布局
		jpleft.setLayout(new GridLayout(3, 1));  //表格布局
		
		JPanel jpleft_UDP = new JPanel();
		JPanel jpleft_TCPClient = new JPanel();
		JPanel jpleft_TCPServer = new JPanel();
		
		JPanel jpright_wbjsxs = new JPanel();//右边文本接收显示
		JPanel jpright_srfs = new JPanel();//右边输入发送
		
		jpleft.add(jpleft_UDP);
		jpleft.add(jpleft_TCPClient);
		jpleft.add(jpleft_TCPServer);
		
		jpright.add(jpright_wbjsxs);
		jpright.add(jpright_srfs);
		
		win.add(jpleft);
		win.add(jpright);
		win.setVisible(true);   //定义窗口可见性
		
		//左边UDP协议栏中的相关组件
		JLabel jludp = new JLabel("这里是UDP协议相关的内容");
		//JButton jButton_UDP_start = new JButton("开启UDP服务");
		        jButton_UDP_start.setActionCommand("jButton_UDP_start");
		//JButton jButton_UDP_stop = new JButton("关闭UDP服务");
		        jButton_UDP_stop.setActionCommand("jButton_UDP_stop");
		jpleft_UDP.add(jludp);
		jpleft_UDP.add(jTextField_UDP_bdzjdz);
		jpleft_UDP.add(jTextField_UDP_bdzjdk);
		jpleft_UDP.add(jButton_UDP_start);
		jpleft_UDP.add(jButton_UDP_stop);
		
		//左边TCP Client协议栏中的相关组件
		//JLabel jltcpclient = new JLabel("这里是TCP Client协议相关的内容");
		//JButton jButton_TCPClient_start = new JButton("开启TCP Client服务");
		//JButton jButton_TCPClient_stop = new JButton("关闭TCP Client服务");
		//jpleft_TCPClient.add(jltcpclient);
		//jpleft_TCPClient.add(jTextField_TCPClient_bdzjdz);
		//jpleft_TCPClient.add(jTextField_TCPClient_bdzjdk);
		//jpleft_TCPClient.add(jButton_TCPClient_start);
		//jpleft_TCPClient.add(jButton_TCPClient_stop);
		
		//左边TCP Server协议栏中的相关组件
		JLabel jltcpserver = new JLabel("这里是TCP Server协议相关的内容");
		//JButton jButton_TCPServer_start = new JButton("开启TCP Server服务");
		        jButton_TCPServer_start.setActionCommand("jButton_TCPServer_start");
		//JButton jButton_TCPServer_stop = new JButton("关闭TCP Server服务");
		        jButton_TCPServer_stop.setActionCommand("jButton_TCPServer_stop");
		jpleft_TCPServer.add(jltcpserver);
		jpleft_TCPServer.add(jTextField_TCPServer_bdzjdz);
		jpleft_TCPServer.add(jTextField_TCPServer_bdzjdk);
		jpleft_TCPServer.add(jButton_TCPServer_start);//开启的按钮
		jpleft_TCPServer.add(jButton_TCPServer_stop);
		
		
		JButton jbsend = new JButton("点击发送");
		        jbsend.setActionCommand("jbsend");
		
		
		JLabel jLabel_wbjsxs = new JLabel("接收文本");
		
		
		
		
		jpright_wbjsxs.add(jTextArea_wbjsxs);
		jpright_wbjsxs.add(jLabel_wbjsxs);
		jpright_srfs.add(jTextArea_srfs);
		jpright_srfs.add(jbsend);
		
		
		
		
		
		
		//给按钮添加事件监听器
		ActionListener ac = new ActionHandler();
		jbsend.addActionListener(ac);
		jButton_TCPServer_start.addActionListener(ac);
		
		jButton_UDP_start.addActionListener(ac);
		jButton_UDP_stop.addActionListener(ac);
		
		
		
		//给窗口添加监听器
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		WindowListener wl = new WindowHandler();
		addWindowListener(wl);
		
		
		
		
		
		
	}
	
	
	/**
	 * 定义事件监听类
	 * @param arg
	 */
	class ActionHandler implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			      if(e.getActionCommand() == "jbsend"){
			    	//“点击发送”按钮，判断以下此时此刻是处于何种状态，就调用不同的方法获取不同的参数来发送数据
			    	if(SEND_STATE == 0){
			    		  System.out.println("事件0响应，向局域网所有设备发送UDP广播");
			    		  try {
			    			  
							UDP.UDPsend(jTextField_UDP_bdzjdz.getText(),Integer.parseInt(jTextField_UDP_bdzjdk.getText()));
							
						} catch (IOException e1) {
							System.out.println("事件0响应失败，向局域网所有设备发送UDP广播失败");
							e1.printStackTrace();
						}
			    		  
			    	}else if (SEND_STATE == 1) {
			    		  System.out.println("事件1响应，开启TCP Server服务，发送按钮发送TCP消息");
			    		  try {
			    			  //TCP消息发送
							Client1.tcpClientSend(jTextField_TCPServer_bdzjdz.getText(), Integer.parseInt(jTextField_TCPServer_bdzjdk.getText()));
						} catch (NumberFormatException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			    		  
					}else if (SEND_STATE == 2) {
						System.out.println("事件2响应");
						
					}else if (SEND_STATE == 3) {
						System.out.println("事件3响应");
						
					}else if (SEND_STATE != 0 && SEND_STATE != 1 && SEND_STATE != 2 && SEND_STATE != 3) {
						System.out.println("事件else响应");
						
					}
				
			}else if (e.getActionCommand() == "jButton_TCPServer_start") {
				//点击开启TCP Server服务的按钮
				SEND_STATE = 1;//将发送按钮 SEND_STATE 状态改成1
				/*try {
	    			  //开启TCP Server服务
					Server1.tcpServerReceive(Integer.parseInt(jTextField_TCPServer_bdzjdk.getText()));
					
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}*/
				
				//开启TCPServer的线程
				Server1.flag_Server1 = true;
				Server1 server1 = new Server1(Integer.parseInt(jTextField_TCPServer_bdzjdk.getText()));
				server1.start();
				
				
			}else if (e.getActionCommand() == "jButton_TCPServer_stop") {
				//关闭TCP Server服务的按钮
				Server1.flag_Server1 = false;//改变标志位的值，让循环自动停止
				
				
				
			} else if (e.getActionCommand() == "jButton_UDP_start") {
				//判断填写的，要监听的端口号的编辑框是否填入了正确的端口号
				if(isPort(jTextField_UDP_bdzjdk.getText())){
					//开启UDP服务
					SEND_STATE = 0;//将发送按钮 SEND_STATE 状态改成0
					jTextArea_wbjsxs.append("UDP消息接收线程已经启动...\n");//添加启动成功的提示
					UDPReceiveThread.flag = true;//恢复标志位的原本属性，让下一次UDP接收线程可以正常开启
					UDPReceiveThread udpReceiveThread = new UDPReceiveThread(Integer.parseInt(jTextField_UDP_bdzjdk.getText())/*, "127.0.0.1"*/);
					udpReceiveThread.start();//启动接收线程
					
					jButton_UDP_start.setEnabled(false);//设置点击后按钮不可点
				}else {
					//弹出提示框提示输入的端口号不正确
			        JOptionPane.showMessageDialog(null, "请填写正确的端口号1-65535","提示窗口", JOptionPane.INFORMATION_MESSAGE);
				}
				
				
			}else if (e.getActionCommand() == "jButton_UDP_stop") {
				//关闭UDP服务
				jButton_UDP_start.setEnabled(true);//设置点击后恢复开启按钮为可点
				UDPReceiveThread.flag = false;//改变标志位，推出循环接收的线程
				
				/**UDP接收线程阻塞的问题还没有解决*//********************************************************************/
			}
			
		}
		
		
		
	}
	
	
	/**
	 * 定义窗口监听类
	 * @param arg
	 */
	class WindowHandler implements WindowListener{
		@Override
		public void windowActivated(WindowEvent arg0) {}
		@Override
		public void windowClosed(WindowEvent arg0) {
			System.exit(0);
		}
		@Override
		public void windowClosing(WindowEvent arg0) {}
		@Override
		public void windowDeactivated(WindowEvent arg0) {}
		@Override
		public void windowDeiconified(WindowEvent arg0) {}
		@Override
		public void windowIconified(WindowEvent arg0) {}
		@Override
		public void windowOpened(WindowEvent arg0) {}
		
	}
	
	/**
	 * 判断是否为端口号的方法
	 * @param port
	 * @return
	 */
	public static boolean isPort(String port) {
		boolean flag ;
		if(isNumeric(port) && 0 < Integer.parseInt(port) && 65536 > Integer.parseInt(port)){
			flag = true;
		}else {
			flag = false;
		}
		return flag;
	}
	/**
	 * 判断是否为数字的方法，用的是正则表达式
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){
		   Pattern pattern = Pattern.compile("[0-9]*"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true; 
		}

	
	
	
	public static void main(String arg[]) {
		//JFrame类的对象，并调用go()方法
		NetAssist fe = new NetAssist();
		fe.go();
	}
}
