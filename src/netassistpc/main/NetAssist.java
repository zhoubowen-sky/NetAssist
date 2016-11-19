/**
 * �������ƣ������������Java��PC��
 * ��       �ߣ��ܲ���
 * ������ڣ�2016��11��03��
 * ��       ����V1.0
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
	
	//���캯��
	public NetAssist(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static final int winWidth = 550;//���ڿ��
	public static final int winHeight = 550;//���ڸ߶�
	public static final int winLeft = 600;//������Ļ���Ե����
	public static final int winRight = 100;//������Ļ�����Ե����
	
	/*������UI�����������*/
	public static JTextField jTextField_UDP_bdzjdz = new JTextField("����������ַ",15);//UDP����������ַ�༭��
	public static JTextField jTextField_UDP_bdzjdk = new JTextField("���������˿�",15);//UDP���������˿ڱ༭��
	//public static JTextField jTextField_TCPClient_bdzjdz = new JTextField("����������ַ",15);//TCP Client����������ַ�༭��
	//public static JTextField jTextField_TCPClient_bdzjdk = new JTextField("Զ��������ַ",15);//TCP Client���������˿ڱ༭��
	public static JTextField jTextField_TCPServer_bdzjdz = new JTextField("����������ַ",15);//TCP Server����������ַ�༭��
	public static JTextField jTextField_TCPServer_bdzjdk = new JTextField("���������˿�",15);//TCP Server���������˿ڱ༭��
	public static JButton jButton_UDP_start = new JButton("����UDP����");//����UDP�������İ�ť
	public static JButton jButton_UDP_stop = new JButton("�ر�UDP����");//�ر�UDP�������İ�ť
	public static JButton jButton_TCPServer_start = new JButton("����TCP Server����");//����TCP Server����İ�ť
	public static JButton jButton_TCPServer_stop = new JButton("�ر�TCP Server����");//�ر�TCP Server����İ�ť
	
	public static JTextArea jTextArea_srfs = new JTextArea(5,10);//���벢���͵��ı���
	public static JTextArea jTextArea_wbjsxs = new JTextArea(10,10);//������ʾ�ı���
	
	public static int SEND_STATE = 1;//"�������"��ť��Ӧ��״̬������0Ϊ������������豸����UDP�㲥��1Ϊ����UDP��2Ϊ����TCP
	
	public void go(){
		JFrame win = new JFrame("�����������Java�� V1.0");    //���򴰿�
		win.setBounds(winLeft, winRight, winWidth, winHeight);   //���򴰿�Ĭ�ϵ�λ���Լ���С
		
		Container container = win.getContentPane();  //����Container���Բ㣬������Ƿ���������Բ�����
		container.setLayout(new GridLayout(1,2));   //���ֹ���,Grid ��ʾ1��2��
		
		JPanel jpleft = new JPanel();//��߲���
		JPanel jpright = new JPanel();//�ұ߲���
		jpleft.setLayout(new GridLayout(3, 1));  //��񲼾�
		
		JPanel jpleft_UDP = new JPanel();
		JPanel jpleft_TCPClient = new JPanel();
		JPanel jpleft_TCPServer = new JPanel();
		
		JPanel jpright_wbjsxs = new JPanel();//�ұ��ı�������ʾ
		JPanel jpright_srfs = new JPanel();//�ұ����뷢��
		
		jpleft.add(jpleft_UDP);
		jpleft.add(jpleft_TCPClient);
		jpleft.add(jpleft_TCPServer);
		
		jpright.add(jpright_wbjsxs);
		jpright.add(jpright_srfs);
		
		win.add(jpleft);
		win.add(jpright);
		win.setVisible(true);   //���崰�ڿɼ���
		
		//���UDPЭ�����е�������
		JLabel jludp = new JLabel("������UDPЭ����ص�����");
		//JButton jButton_UDP_start = new JButton("����UDP����");
		        jButton_UDP_start.setActionCommand("jButton_UDP_start");
		//JButton jButton_UDP_stop = new JButton("�ر�UDP����");
		        jButton_UDP_stop.setActionCommand("jButton_UDP_stop");
		jpleft_UDP.add(jludp);
		jpleft_UDP.add(jTextField_UDP_bdzjdz);
		jpleft_UDP.add(jTextField_UDP_bdzjdk);
		jpleft_UDP.add(jButton_UDP_start);
		jpleft_UDP.add(jButton_UDP_stop);
		
		//���TCP ClientЭ�����е�������
		//JLabel jltcpclient = new JLabel("������TCP ClientЭ����ص�����");
		//JButton jButton_TCPClient_start = new JButton("����TCP Client����");
		//JButton jButton_TCPClient_stop = new JButton("�ر�TCP Client����");
		//jpleft_TCPClient.add(jltcpclient);
		//jpleft_TCPClient.add(jTextField_TCPClient_bdzjdz);
		//jpleft_TCPClient.add(jTextField_TCPClient_bdzjdk);
		//jpleft_TCPClient.add(jButton_TCPClient_start);
		//jpleft_TCPClient.add(jButton_TCPClient_stop);
		
		//���TCP ServerЭ�����е�������
		JLabel jltcpserver = new JLabel("������TCP ServerЭ����ص�����");
		//JButton jButton_TCPServer_start = new JButton("����TCP Server����");
		        jButton_TCPServer_start.setActionCommand("jButton_TCPServer_start");
		//JButton jButton_TCPServer_stop = new JButton("�ر�TCP Server����");
		        jButton_TCPServer_stop.setActionCommand("jButton_TCPServer_stop");
		jpleft_TCPServer.add(jltcpserver);
		jpleft_TCPServer.add(jTextField_TCPServer_bdzjdz);
		jpleft_TCPServer.add(jTextField_TCPServer_bdzjdk);
		jpleft_TCPServer.add(jButton_TCPServer_start);//�����İ�ť
		jpleft_TCPServer.add(jButton_TCPServer_stop);
		
		
		JButton jbsend = new JButton("�������");
		        jbsend.setActionCommand("jbsend");
		
		
		JLabel jLabel_wbjsxs = new JLabel("�����ı�");
		
		
		
		
		jpright_wbjsxs.add(jTextArea_wbjsxs);
		jpright_wbjsxs.add(jLabel_wbjsxs);
		jpright_srfs.add(jTextArea_srfs);
		jpright_srfs.add(jbsend);
		
		
		
		
		
		
		//����ť����¼�������
		ActionListener ac = new ActionHandler();
		jbsend.addActionListener(ac);
		jButton_TCPServer_start.addActionListener(ac);
		
		jButton_UDP_start.addActionListener(ac);
		jButton_UDP_stop.addActionListener(ac);
		
		
		
		//��������Ӽ�����
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		WindowListener wl = new WindowHandler();
		addWindowListener(wl);
		
		
		
		
		
		
	}
	
	
	/**
	 * �����¼�������
	 * @param arg
	 */
	class ActionHandler implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			      if(e.getActionCommand() == "jbsend"){
			    	//��������͡���ť���ж����´�ʱ�˿��Ǵ��ں���״̬���͵��ò�ͬ�ķ�����ȡ��ͬ�Ĳ�������������
			    	if(SEND_STATE == 0){
			    		  System.out.println("�¼�0��Ӧ��������������豸����UDP�㲥");
			    		  try {
			    			  
							UDP.UDPsend(jTextField_UDP_bdzjdz.getText(),Integer.parseInt(jTextField_UDP_bdzjdk.getText()));
							
						} catch (IOException e1) {
							System.out.println("�¼�0��Ӧʧ�ܣ�������������豸����UDP�㲥ʧ��");
							e1.printStackTrace();
						}
			    		  
			    	}else if (SEND_STATE == 1) {
			    		  System.out.println("�¼�1��Ӧ������TCP Server���񣬷��Ͱ�ť����TCP��Ϣ");
			    		  try {
			    			  //TCP��Ϣ����
							Client1.tcpClientSend(jTextField_TCPServer_bdzjdz.getText(), Integer.parseInt(jTextField_TCPServer_bdzjdk.getText()));
						} catch (NumberFormatException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			    		  
					}else if (SEND_STATE == 2) {
						System.out.println("�¼�2��Ӧ");
						
					}else if (SEND_STATE == 3) {
						System.out.println("�¼�3��Ӧ");
						
					}else if (SEND_STATE != 0 && SEND_STATE != 1 && SEND_STATE != 2 && SEND_STATE != 3) {
						System.out.println("�¼�else��Ӧ");
						
					}
				
			}else if (e.getActionCommand() == "jButton_TCPServer_start") {
				//�������TCP Server����İ�ť
				SEND_STATE = 1;//�����Ͱ�ť SEND_STATE ״̬�ĳ�1
				/*try {
	    			  //����TCP Server����
					Server1.tcpServerReceive(Integer.parseInt(jTextField_TCPServer_bdzjdk.getText()));
					
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}*/
				
				//����TCPServer���߳�
				Server1.flag_Server1 = true;
				Server1 server1 = new Server1(Integer.parseInt(jTextField_TCPServer_bdzjdk.getText()));
				server1.start();
				
				
			}else if (e.getActionCommand() == "jButton_TCPServer_stop") {
				//�ر�TCP Server����İ�ť
				Server1.flag_Server1 = false;//�ı��־λ��ֵ����ѭ���Զ�ֹͣ
				
				
				
			} else if (e.getActionCommand() == "jButton_UDP_start") {
				//�ж���д�ģ�Ҫ�����Ķ˿ںŵı༭���Ƿ���������ȷ�Ķ˿ں�
				if(isPort(jTextField_UDP_bdzjdk.getText())){
					//����UDP����
					SEND_STATE = 0;//�����Ͱ�ť SEND_STATE ״̬�ĳ�0
					jTextArea_wbjsxs.append("UDP��Ϣ�����߳��Ѿ�����...\n");//��������ɹ�����ʾ
					UDPReceiveThread.flag = true;//�ָ���־λ��ԭ�����ԣ�����һ��UDP�����߳̿�����������
					UDPReceiveThread udpReceiveThread = new UDPReceiveThread(Integer.parseInt(jTextField_UDP_bdzjdk.getText())/*, "127.0.0.1"*/);
					udpReceiveThread.start();//���������߳�
					
					jButton_UDP_start.setEnabled(false);//���õ����ť���ɵ�
				}else {
					//������ʾ����ʾ����Ķ˿ںŲ���ȷ
			        JOptionPane.showMessageDialog(null, "����д��ȷ�Ķ˿ں�1-65535","��ʾ����", JOptionPane.INFORMATION_MESSAGE);
				}
				
				
			}else if (e.getActionCommand() == "jButton_UDP_stop") {
				//�ر�UDP����
				jButton_UDP_start.setEnabled(true);//���õ����ָ�������ťΪ�ɵ�
				UDPReceiveThread.flag = false;//�ı��־λ���Ƴ�ѭ�����յ��߳�
				
				/**UDP�����߳����������⻹û�н��*//********************************************************************/
			}
			
		}
		
		
		
	}
	
	
	/**
	 * ���崰�ڼ�����
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
	 * �ж��Ƿ�Ϊ�˿ںŵķ���
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
	 * �ж��Ƿ�Ϊ���ֵķ������õ���������ʽ
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
		//JFrame��Ķ��󣬲�����go()����
		NetAssist fe = new NetAssist();
		fe.go();
	}
}
