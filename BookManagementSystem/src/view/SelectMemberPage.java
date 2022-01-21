package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import db.DBConnect;

public class SelectMemberPage implements ActionListener {
	DBConnect db = new DBConnect();
	private JFrame frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JTextField tf_SearchBar;
	private JComboBox cb_Search;
	private DefaultTableModel tablemd;
	private String[] tableheads;
	private String search = "";

	/**
	 * 어플리케이션 실행
	 */
	

	/**
	 * 어플리케이션 생성
	 */
	public SelectMemberPage(ArrayList<String[]> list) {
		initialize(list);
	}

	/**
	 * 프레임 내용 초기화
	 */
	private void initialize(ArrayList<String[]>list) {
		frame = new JFrame();
		frame.setResizable(false);	// 윈도우 사이즈 재설정 불가
		frame.setSize(1200, 800);		// 윈도우 사이즈 설정
		frame.setLocationRelativeTo(null);	// 윈도우 위치 설정
		frame.setTitle("대여할 회원 선택");	// 윈도우 창 이름 설정
		frame.getContentPane().setBackground(SystemColor.menu);	// 기본 배경색 설정
		frame.getContentPane().setLayout(null);	// 기본 레이아웃 설정
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// 윈도우 종료 버튼 설정
		frame.setVisible(true);
		
		// 환경설정
		db.getConnection();
		tableheads = new String[] {"아이디","비밀번호","이름","성별","생년월일","전화번호","등록일"};
		
		// 그래픽 UI
		JPanel p_Main = new JPanel();
		p_Main.setBounds(12, 93, 1170, 668);
		frame.getContentPane().add(p_Main);
		
		cb_Search = new JComboBox(tableheads);	// 검색 분류 콤보박스
		cb_Search.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		cb_Search.setBounds(178, 50, 137, 33);
		frame.getContentPane().add(cb_Search);
		
		tf_SearchBar = new JTextField();		// 검색바
		tf_SearchBar.setBounds(327, 50, 469, 33);
		tf_SearchBar.requestFocus();
		tf_SearchBar.setFocusable(true);
		frame.getContentPane().add(tf_SearchBar);
		tf_SearchBar.setColumns(50);
		p_Main.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 10, 1146, 605);
		p_Main.add(scrollPane);
		
		tablemd = new DefaultTableModel(db.getMemberList(cb_Search.getSelectedItem().toString(), ""),tableheads);
		table = new JTable(tablemd);
		table.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int col = table.getSelectedRow();
				TableModel model = table.getModel();
			}
		});
		scrollPane.setViewportView(table);
		
		JButton btn_Cencel = new JButton("취소");
		btn_Cencel.setToolTipText("진행을 취소합니다.");
		btn_Cencel.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		btn_Cencel.setFocusPainted(false);
		btn_Cencel.setBackground(Color.WHITE);
		btn_Cencel.setBounds(579, 625, 86, 33);
		p_Main.add(btn_Cencel);
		
		JButton btn_Select = new JButton("선택");
		btn_Select.setToolTipText("대여할 회원을 선택합니다.");
		btn_Select.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		btn_Select.setFocusPainted(false);
		btn_Select.setBackground(Color.WHITE);
		btn_Select.setBounds(481, 625, 86, 33);
		btn_Select.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int col = table.getSelectedRow();
				TableModel model = table.getModel();
				String id = model.getValueAt(col, 0).toString();
				String name = model.getValueAt(col, 2).toString();
				String tel = "";
				if (model.getValueAt(col, 5) != null) {
					tel = model.getValueAt(col, 5).toString();
				}
				int alarm = JOptionPane.showConfirmDialog(null,"해당 회원으로 선택하시겠습니까?","알람",JOptionPane.YES_NO_OPTION);
				if (alarm==0) {
					for (int i=0; i<list.size(); i++) {
						String[] books = list.get(i);
						String no_book = books[0];
						String title = books[1];
						
						db.rentalBook(no_book, title, id, name, tel);
//						System.out.println("Rental Book : " + no_book + "," +title+ "," +id+ "," +name+ "," +tel);
					}
					JOptionPane.showMessageDialog(null,id +" , " + name + "회원이 " + list.size() + " 개의 도서를 대여했습니다.");
					frame.setVisible(false);
					new BasketPage();
				}
			}
		});
		p_Main.add(btn_Select);
		
		JButton btn_Search = new JButton("검색");
		btn_Search.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		btn_Search.setFocusPainted(false);
		btn_Search.setBackground(Color.WHITE);
		btn_Search.setBounds(808, 50, 86, 33);
		frame.getContentPane().add(btn_Search);
		
		JButton btn_Clear = new JButton("지우기");
		btn_Clear.setToolTipText("검색창을 지웁니다.");
		btn_Clear.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		btn_Clear.setFocusPainted(false);
		btn_Clear.setBackground(Color.WHITE);
		btn_Clear.setBounds(906, 50, 86, 33);
		frame.getContentPane().add(btn_Clear);
		
		// 리스너
		btn_Cencel.addActionListener(this);
		btn_Search.addActionListener(this);
		btn_Clear.addActionListener(this);
		
	}
	
	public void refleshTable(String col, String search) {	//테이블을 새로고침하는 메소드
		Object[][] o = db.getMemberList(col, search);
		tablemd.setDataVector(o,tableheads);
	}
	
	//액션
	public void actionPerformed(ActionEvent e) {
		String com = e.getActionCommand();
		
		switch(com) {
		case "검색" :
			search = tf_SearchBar.getText();
			refleshTable(cb_Search.getSelectedItem().toString(), tf_SearchBar.getText());
			System.out.println("Search : " + cb_Search.getSelectedItem() + " , " + tf_SearchBar.getText());
			break;
			
		case "지우기" :
			tf_SearchBar.setText("");
			cb_Search.setSelectedIndex(0);
			System.out.println("SearchBar Clear");
			break;
			
		case "취소" :
			frame.setVisible(false);
			new BasketPage();
		
		}
	}
}
