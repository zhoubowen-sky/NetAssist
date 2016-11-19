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

public class NetAssist extends JFrame {

	// 构造函数
	public NetAssist() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/* 程序窗口的大小 */
	public static final int winWidth = 550;// 窗口宽度
	public static final int winHeight = 500;// 窗口高度
	public static final int winLeft = 600;// 距离屏幕左边缘距离
	public static final int winRight = 100;// 距离屏幕上面边缘距离

	/* 文本框组件的大小 */
	public static final int jTextArea_srfs_a = 5;
	public static final int jTextArea_srfs_b = 10;
	public static final int jTextArea_wbjsxs_a = 5;// 高
	public static final int jTextArea_wbjsxs_b = 10;// 宽

	/* 以下是UI界面的相关组件 */
	public static JTextField jTextField_UDP_bdzjdz = new JTextField("本地主机地址", 15);// UDP本地主机地址编辑框
	public static JTextField jTextField_UDP_bdzjdk = new JTextField("本地主机端口", 15);// UDP本地主机端口编辑框
	public static JTextField jTextField_TCPServer_bdzjdz = new JTextField("本地主机地址", 15);// TCP
																						// Server本地主机地址编辑框
	public static JTextField jTextField_TCPServer_bdzjdk = new JTextField("本地主机端口", 15);// TCP
																						// Server本地主机端口编辑框
	public static JButton jButton_UDP_start = new JButton("开启UDP服务");// 开启UDP服务器的按钮
	public static JButton jButton_UDP_stop = new JButton("关闭UDP服务");// 关闭UDP服务器的按钮
	public static JButton jButton_TCPServer_start = new JButton("开启TCP Server服务");// 开启TCP
																					// Server服务的按钮
	public static JButton jButton_TCPServer_stop = new JButton("关闭TCP Server服务");// 关闭TCP
																					// Server服务的按钮

	public static JTextArea jTextArea_srfs = new JTextArea(jTextArea_srfs_a, jTextArea_srfs_b);// 输入并发送的文本框
	public static JTextArea jTextArea_wbjsxs = new JTextArea(jTextArea_wbjsxs_a, jTextArea_wbjsxs_b);// 接收显示文本框

	public void go() {
		JFrame win = new JFrame("网络调试助手Java版PC端 V1.0"); // 程序窗口
		win.setBounds(winLeft, winRight, winWidth, winHeight); // 程序窗口默认的位置以及大小

		Container container = win.getContentPane(); // 创建Container隐性层，组件都是放在这个隐性层上面
		container.setLayout(new GridLayout(1, 2)); // 布局管理,Grid 表示1行2列

		JPanel jpleft = new JPanel();// 左边布局
		JPanel jpright = new JPanel();// 右边布局
		jpleft.setLayout(new GridLayout(2, 1)); // 表格布局 两行一列

		JPanel jpleft_UDP = new JPanel();
		JPanel jpleft_TCPServer = new JPanel();

		JPanel jpright_wbjsxs = new JPanel();// 右边文本接收显示
		JPanel jpright_srfs = new JPanel();// 右边输入发送

		jpleft.add(jpleft_UDP);
		jpleft.add(jpleft_TCPServer);

		jpright.add(jpright_wbjsxs);
		jpright.add(jpright_srfs);

		win.add(jpleft);
		win.add(jpright);
		win.setVisible(true); // 定义窗口可见性为真

		// 左边UDP协议栏中的相关组件
		JLabel jludp = new JLabel("这里是UDP协议相关的内容");
		// JButton jButton_UDP_start = new JButton("开启UDP服务");
		jButton_UDP_start.setActionCommand("jButton_UDP_start");
		// JButton jButton_UDP_stop = new JButton("关闭UDP服务");
		jButton_UDP_stop.setActionCommand("jButton_UDP_stop");
		JButton jButton_UDP_send = new JButton("发送UDP消息");
		jButton_UDP_send.setActionCommand("jButton_UDP_send");
		jpleft_UDP.add(jludp);
		jpleft_UDP.add(jTextField_UDP_bdzjdz);
		jpleft_UDP.add(jTextField_UDP_bdzjdk);
		jpleft_UDP.add(jButton_UDP_start);
		jpleft_UDP.add(jButton_UDP_stop);
		jpleft_UDP.add(jButton_UDP_send);

		// 左边TCP Server协议栏中的相关组件
		JLabel jltcpserver = new JLabel("这里是TCP Server协议相关的内容");
		// JButton jButton_TCPServer_start = new JButton("开启TCP Server服务");
		jButton_TCPServer_start.setActionCommand("jButton_TCPServer_start");
		// JButton jButton_TCPServer_stop = new JButton("关闭TCP Server服务");
		jButton_TCPServer_stop.setActionCommand("jButton_TCPServer_stop");
		JButton jButton_TCPServer_send = new JButton("发送TCP消息");
		jButton_TCPServer_send.setActionCommand("jButton_TCPServer_send");
		jpleft_TCPServer.add(jltcpserver);
		jpleft_TCPServer.add(jTextField_TCPServer_bdzjdz);
		jpleft_TCPServer.add(jTextField_TCPServer_bdzjdk);
		jpleft_TCPServer.add(jButton_TCPServer_start);// 开启TCPserver的按钮
		jpleft_TCPServer.add(jButton_TCPServer_stop);
		jpleft_TCPServer.add(jButton_TCPServer_send);

		JLabel jbsend = new JLabel("要发送的文本");
		JLabel jLabel_wbjsxs = new JLabel("接收的文本");

		jpright_wbjsxs.add(jTextArea_wbjsxs);
		jpright_wbjsxs.add(jLabel_wbjsxs);
		jpright_srfs.add(jTextArea_srfs);
		jpright_srfs.add(jbsend);

		// 给按钮添加事件监听器
		ActionListener ac = new ActionHandler();
		jButton_TCPServer_start.addActionListener(ac);
		jButton_TCPServer_stop.addActionListener(ac);
		jButton_UDP_start.addActionListener(ac);
		jButton_UDP_stop.addActionListener(ac);
		jButton_TCPServer_send.addActionListener(ac);
		jButton_UDP_send.addActionListener(ac);

		// 给窗口添加监听器
		// setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		WindowListener wl = new WindowHandler();
		addWindowListener(wl);
	}

	/**
	 * 定义事件监听类
	 * 
	 * @param arg
	 */
	class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() == "jButton_UDP_send") {
				// 判断端口号处填入的是否是正确的端口数字
				if (isPort(jTextField_UDP_bdzjdk.getText())) {
					// 点击发送UDP信息的按钮，向局域网所有设备发送UDP广播
					System.out.println("开始向局域网所有设备发送UDP广播");
					try {
						UDP.UDPsend(jTextField_UDP_bdzjdz.getText(), Integer.parseInt(jTextField_UDP_bdzjdk.getText()));

					} catch (IOException e1) {
						System.out.println("事件响应失败，向局域网所有设备发送UDP广播失败");
						e1.printStackTrace();
					}
				} else {
					// 如果填入的端口号不符合要求，弹出提示框
					JOptionPane.showMessageDialog(null, "请填写正确的端口号1-65535", "提示窗口", JOptionPane.INFORMATION_MESSAGE);
				}

			} else if (e.getActionCommand() == "jButton_TCPServer_send") {
				if (isPort(jTextField_TCPServer_bdzjdk.getText())) {
					// 发送TCP消息
					System.out.println("TCP Server服务，发送按钮发送TCP消息");
					try {
						// TCP消息发送
						Client1.tcpClientSend(jTextField_TCPServer_bdzjdz.getText(),
								Integer.parseInt(jTextField_TCPServer_bdzjdk.getText()));

					} catch (NumberFormatException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} else {
					// 如果填入的端口号不符合要求，弹出提示框
					JOptionPane.showMessageDialog(null, "请填写正确的端口号1-65535", "提示窗口", JOptionPane.INFORMATION_MESSAGE);
				}

			} else if (e.getActionCommand() == "jButton_TCPServer_start") {

				// 点击开启TCP Server服务的按钮,开启TCPServer的线程
				jTextArea_wbjsxs.append("TCP消息接收线程已经启动...\n");// 添加启动成功的提示
				Server1.flag_Server1 = true;
				Server1 server1 = new Server1(Integer.parseInt(jTextField_TCPServer_bdzjdk.getText()));
				server1.start();

				jButton_TCPServer_start.setEnabled(false);// 设置按钮点击后不可再次点击

			} else if (e.getActionCommand() == "jButton_TCPServer_stop") {
				// 关闭TCP Server服务的按钮
				Server1.flag_Server1 = false;// 改变标志位的值，让循环自动停止

				jButton_TCPServer_start.setEnabled(true);// 恢复按钮为可点击状态

			} else if (e.getActionCommand() == "jButton_UDP_start") {
				// 判断填写的，要监听的端口号的编辑框是否填入了正确的端口号
				if (isPort(jTextField_UDP_bdzjdk.getText())) {
					// 开启UDP服务
					jTextArea_wbjsxs.append("UDP消息接收线程已经启动...\n");// 添加启动成功的提示
					UDPReceiveThread.flag = true;// 恢复标志位的原本属性，让下一次UDP接收线程可以正常开启
					UDPReceiveThread udpReceiveThread = new UDPReceiveThread(
							Integer.parseInt(jTextField_UDP_bdzjdk.getText())/* , "127.0.0.1" */);
					udpReceiveThread.start();// 启动接收线程

					jButton_UDP_start.setEnabled(false);// 设置点击后按钮不可点
				} else {
					// 弹出提示框提示输入的端口号不正确
					JOptionPane.showMessageDialog(null, "请填写正确的端口号1-65535", "提示窗口", JOptionPane.INFORMATION_MESSAGE);
				}

			} else if (e.getActionCommand() == "jButton_UDP_stop") {
				// 关闭UDP服务
				jButton_UDP_start.setEnabled(true);// 设置点击后恢复开启按钮为可点
				UDPReceiveThread.flag = false;// 改变标志位，推出循环接收的线程

				/** UDP接收线程阻塞的问题还没有解决 *//********************************************************************/
			}

		}

	}

	/**
	 * 定义窗口监听类
	 * 
	 * @param arg
	 */
	class WindowHandler implements WindowListener {
		@Override
		public void windowActivated(WindowEvent arg0) {
		}

		@Override
		public void windowClosed(WindowEvent arg0) {
			System.exit(0);
		}

		@Override
		public void windowClosing(WindowEvent arg0) {
		}

		@Override
		public void windowDeactivated(WindowEvent arg0) {
		}

		@Override
		public void windowDeiconified(WindowEvent arg0) {
		}

		@Override
		public void windowIconified(WindowEvent arg0) {
		}

		@Override
		public void windowOpened(WindowEvent arg0) {
		}

	}

	/**
	 * 判断是否为端口号的方法
	 * 
	 * @param port
	 * @return
	 */
	public static boolean isPort(String port) {
		boolean flag;
		if (isNumeric(port) && 0 < Integer.parseInt(port) && 65536 > Integer.parseInt(port)) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}

	/**
	 * 判断是否为数字的方法，用的是正则表达式
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 程序的入口
	 * 
	 * @param arg
	 */
	public static void main(String arg[]) {
		// JFrame类的对象，并调用go()方法
		NetAssist fe = new NetAssist();
		fe.go();
	}
}
