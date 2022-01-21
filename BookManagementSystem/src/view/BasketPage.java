package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import db.DBConnect;

public class BasketPage implements ActionListener {
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
	public BasketPage() {
		initialize();
	}

	/**
	 * 프레임 내용 초기화
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);	// 윈도우 사이즈 재설정 불가
		frame.setSize(1600, 900);		// 윈도우 사이즈 설정
		frame.setLocationRelativeTo(null);	// 윈도우 위치 설정
		frame.setTitle("도서 대여 시스템");	// 윈도우 창 이름 설정
		frame.getContentPane().setBackground(SystemColor.menu);	// 기본 배경색 설정
		frame.getContentPane().setLayout(null);	// 기본 레이아웃 설정
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// 윈도우 종료 버튼 설정
		frame.setVisible(true);
		
		// 환경설정
		db.getConnection();
		tableheads = new String[] {"도서번호","도서명","저자","출판사","출판연도","가격","구분","언어","분야","상태","등록일"};
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
		LocalDate now = LocalDate.now();
		String today = now.format(dateTimeFormatter);
		
		// 그래픽 UI
		JPanel p_Main = new JPanel();
		p_Main.setBounds(12, 93, 1570, 768);
		frame.getContentPane().add(p_Main);
		
		JButton btn_BookList = new JButton("도서 목록");		// 도서 목록 버튼
		btn_BookList.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 15));
		btn_BookList.setBounds(12, 10, 97, 73);
		btn_BookList.setBackground(SystemColor.window);
		btn_BookList.setFocusPainted(false);
		frame.getContentPane().add(btn_BookList);
		
		JButton btn_RentalCondition = new JButton("대여 현황");	// 대여 현황 버튼
		btn_RentalCondition.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 15));
		btn_RentalCondition.setBounds(121, 10, 97, 73);
		btn_RentalCondition.setBackground(SystemColor.window);
		btn_RentalCondition.setFocusPainted(false);
		frame.getContentPane().add(btn_RentalCondition);
		
		JButton btn_Member = new JButton("회원 관리");		// 회원 관리 버튼
		btn_Member.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 15));
		btn_Member.setBounds(230, 10, 97, 73);
		btn_Member.setBackground(SystemColor.window);
		btn_Member.setFocusPainted(false);
		frame.getContentPane().add(btn_Member);
		
		JButton btn_Basket = new JButton("책 바구니");		// 책 바구니 버튼
		btn_Basket.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 15));
		btn_Basket.setBounds(339, 10, 97, 73);
		btn_Basket.setBackground(SystemColor.activeCaption);
		btn_Basket.setFocusPainted(false);
		frame.getContentPane().add(btn_Basket);
		
		JLabel lb_Slogan = new JLabel("책으로 여는 미래, 미래를 여는 문");	// 도서관 슬로건 라벨
		lb_Slogan.setForeground(new Color(30, 144, 255));
		lb_Slogan.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		lb_Slogan.setBounds(448, 23, 170, 33);
		frame.getContentPane().add(lb_Slogan);
		
		JLabel lb_Library = new JLabel("중랑구립면목정보도서관");		// 도서관 이름 라벨
		lb_Library.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 20));
		lb_Library.setBounds(448, 50, 228, 33);
		frame.getContentPane().add(lb_Library);
		
		cb_Search = new JComboBox(tableheads);	// 검색 분류 콤보박스
		cb_Search.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		cb_Search.setBounds(661, 49, 137, 33);
		frame.getContentPane().add(cb_Search);
		
		tf_SearchBar = new JTextField();		// 검색바
		tf_SearchBar.setBounds(810, 50, 465, 33);
		tf_SearchBar.requestFocus();
		tf_SearchBar.setFocusable(true);
		frame.getContentPane().add(tf_SearchBar);
		tf_SearchBar.setColumns(50);
		
		JButton btn_Search = new JButton("검색");	// 검색 버튼
		btn_Search.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		btn_Search.setBounds(1287, 50, 86, 33);
		btn_Search.setBackground(SystemColor.window);
		btn_Search.setFocusPainted(false);
		frame.getContentPane().add(btn_Search);
		
		JButton btn_Clear = new JButton("지우기");	// 검색창 지우기 버튼
		btn_Clear.setToolTipText("검색 바를 비웁니다.");
		btn_Clear.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		btn_Clear.setBounds(1385, 50, 86, 33);
		btn_Clear.setBackground(SystemColor.window);
		btn_Clear.setFocusPainted(false);
		frame.getContentPane().add(btn_Clear);
		
		JButton btn_Refresh = new JButton("새로고침");		// 새로고침 버튼
		btn_Refresh.setToolTipText("회원 목록 테이블을 새로고침 합니다");
		btn_Refresh.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		btn_Refresh.setBounds(1483, 50, 86, 33);
		btn_Refresh.setBackground(SystemColor.window);
		btn_Refresh.setFocusPainted(false);
		frame.getContentPane().add(btn_Refresh);
		p_Main.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 10, 1546, 705);
		p_Main.add(scrollPane);
		
		tablemd = new DefaultTableModel(db.getBookList("basket",cb_Search.getSelectedItem().toString(),""),tableheads);
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
		
		JButton btn_Rental = new JButton("대여");	// 빌린 도서를 반납합니다
		btn_Rental.setToolTipText("선택한 도서를 대여합니다.");
		btn_Rental.setBounds(1472, 725, 86, 33);
		btn_Rental.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		btn_Rental.setBackground(SystemColor.window);
		btn_Rental.setFocusPainted(false);
		p_Main.add(btn_Rental);
		
		JButton btn_Delete = new JButton("삭제");
		btn_Delete.setToolTipText("선택된 도서를 바구니에서 삭제합니다.");
		btn_Delete.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		btn_Delete.setFocusPainted(false);
		btn_Delete.setBackground(Color.WHITE);
		btn_Delete.setBounds(1374, 725, 86, 33);
		p_Main.add(btn_Delete);
		
		JButton btn_DeleteAll = new JButton("비우기");
		btn_DeleteAll.setToolTipText("바구니를 전부 비웁니다.");
		btn_DeleteAll.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		btn_DeleteAll.setFocusPainted(false);
		btn_DeleteAll.setBackground(Color.WHITE);
		btn_DeleteAll.setBounds(1276, 725, 86, 33);
		p_Main.add(btn_DeleteAll);
		
		JLabel lb_DateTime = new JLabel(today);
		lb_DateTime.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 20));
		lb_DateTime.setBounds(10, 725, 403, 33);
		p_Main.add(lb_DateTime);
		
		JLabel lblNewLabel = new JLabel("made by 신재현");
		lblNewLabel.setFont(new Font("tvN 즐거운이야기 Medium", Font.PLAIN, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel.setBounds(296, 736, 117, 22);
		p_Main.add(lblNewLabel);
		
		//리스너
		btn_BookList.addActionListener(this);
		btn_RentalCondition.addActionListener(this);
		btn_Member.addActionListener(this);
		btn_Basket.addActionListener(this);
		btn_Search.addActionListener(this);
		btn_Clear.addActionListener(this);
		btn_Refresh.addActionListener(this);
		btn_Rental.addActionListener(this);
		btn_Delete.addActionListener(this);
		btn_DeleteAll.addActionListener(this);

		
	}
	
	public void refleshTable(String col, String search) {	//테이블을 새로고침하는 메소드
		Object[][] o = db.getBookList("basket",col, search);
		tablemd.setDataVector(o,tableheads);
	}
	
	//액션
	public void actionPerformed(ActionEvent e) {
		String com = e.getActionCommand();
		switch(com) {
		case "도서 목록" : 
			frame.setVisible(false);
			new MainPage();
			System.out.println("move to BookList");
			break;
			
		case "대여 현황" :
			frame.setVisible(false);
			new RentalPage();
			System.out.println("move to RentalCondition");
			break;
			
		case "회원 관리" :
			frame.setVisible(false);
			new MemberPage();
			System.out.println("move to MemberManagement");
			break;
			
		case "책 바구니" :
			frame.setVisible(false);
			new BasketPage();
			System.out.println("move to BookBasket");
			break;
			
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
			
		case "새로고침" :
			refleshTable(cb_Search.getSelectedItem().toString(), search);
			System.out.println("Reflesh the Table");
			break;
			
		case "대여" :
			int[] cols = table.getSelectedRows();
			TableModel model = table.getModel();
			ArrayList<String[]> list = new ArrayList<String[]>();
			for(int i=0; i<cols.length; i++) {
				String no_book = model.getValueAt(cols[i], 0).toString();
				String title = model.getValueAt(cols[i], 1).toString();
				list.add(new String[] {no_book,title});
			}
			new SelectMemberPage(list);
			frame.setVisible(false);
			break;
			
		case "삭제" :
			int alarm = JOptionPane.showConfirmDialog(null,"선택된 도서들을 삭제하시겠습니까?","경고",JOptionPane.YES_NO_OPTION);
			if (alarm == 0) {
				int[] cols1 = table.getSelectedRows();
				TableModel model1 = table.getModel();
				int cnt1 = 0;
				for(int i=0; i<cols1.length; i++) {
					String no_book = model1.getValueAt(cols1[i], 0).toString();
					db.deleteBasket(no_book);
					cnt1++;
				}
				JOptionPane.showMessageDialog(null, cnt1 + "개의 도서를 목록에서 삭제했습니다.");
				refleshTable(cb_Search.getSelectedItem().toString(), tf_SearchBar.getText());
			}
			break;
			
		case "비우기" :
			int alarm1 = JOptionPane.showConfirmDialog(null,"바구니를 전부 비우시겠습니까?","경고",JOptionPane.YES_NO_OPTION);
			if (alarm1 == 0) {
				db.deleteBasket("0");
				JOptionPane.showMessageDialog(null, "목록을 전부 삭제했습니다.");
				refleshTable(cb_Search.getSelectedItem().toString(), tf_SearchBar.getText());
			}
		}
		
		
	}

}
