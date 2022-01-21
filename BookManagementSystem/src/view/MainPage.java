package view;

// import
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import db.DBConnect;

import javax.swing.JScrollPane;

public class MainPage implements ActionListener {
	DBConnect db = new DBConnect();
	private JFrame frame;
	private JPanel p_Input;
	private JTextField tf_SearchBar, tf_NoBook, tf_Title, tf_Author, tf_Publisher, tf_PBYear, tf_Price;
	private JTable table;
	private JScrollPane scrollPane;
	private String[] tableheads, divisions, langs, categorys;
	private JLabel lb_NoBook, lb_Title, lb_Author, lb_Publisher, lb_PBYear, lb_Price, lb_Division, lb_Lang, lb_Category;
	private JButton btn_Insert, btn_Delete, btn_Update, btn_tfClear;
	private JComboBox cb_Search, cb_Division, cb_Lang, cb_Category;
	private DefaultTableModel tablemd;
	private JLabel lb_DateTime;
	private JLabel lblNewLabel;
	/**
	 * 어플리케이션 실행
	 */
	

	/**
	 * 어플리케이션 생성
	 */
	public MainPage() {
		initialize();	
	}

	/**
	 * 프레임 내용 초기화
	 */
	private void initialize() {
		// 윈도우 설정
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
		divisions = new String[] {"국내도서","외국도서"};
		langs = new String[] {"독일어","러시아어", "스페인어","영어","일본어","중국어","프랑스어","한국어"};
		categorys = new String[] {"소설","시","언어","교재","사회","정치","자격증","에세이","여행","역사","예술","인문","인물","자기계발","자연과학","컴퓨터 IT","잡지","종교"};
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
		btn_BookList.setBackground(SystemColor.activeCaption);
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
		btn_Basket.setBackground(SystemColor.window);
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
		btn_Refresh.setToolTipText("도서 목록 테이블을 새로고침 합니다");
		btn_Refresh.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		btn_Refresh.setBounds(1483, 50, 86, 33);
		btn_Refresh.setBackground(SystemColor.window);
		btn_Refresh.setFocusPainted(false);
		frame.getContentPane().add(btn_Refresh);
		p_Main.setLayout(null);
		
		p_Input = new JPanel();		// 정보 입력 패널
		p_Input.setBounds(0, 10, 413, 705);
		p_Input.setBorder(new LineBorder(new Color(0, 0, 0)));
		p_Input.setBackground(SystemColor.info);
		p_Main.add(p_Input);
		p_Input.setLayout(null);
		
		tf_NoBook = new JTextField();		// 도서번호 텍스트 필드
		tf_NoBook.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		tf_NoBook.setHorizontalAlignment(SwingConstants.CENTER);
		tf_NoBook.setToolTipText("도서번호");
		tf_NoBook.setEditable(false);
		tf_NoBook.setBounds(12, 24, 389, 35);
		p_Input.add(tf_NoBook);
		tf_NoBook.setColumns(10);
		
		tf_Title = new JTextField();		// 도서명 텍스트 필드
		tf_Title.setToolTipText("도서명");
		tf_Title.setHorizontalAlignment(SwingConstants.CENTER);
		tf_Title.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		tf_Title.setColumns(50);
		tf_Title.setBounds(12, 83, 389, 35);
		p_Input.add(tf_Title);
		
		tf_Author = new JTextField();		// 저자 텍스트 필드
		tf_Author.setToolTipText("저자");
		tf_Author.setHorizontalAlignment(SwingConstants.CENTER);
		tf_Author.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		tf_Author.setColumns(50);
		tf_Author.setBounds(12, 142, 389, 35);
		p_Input.add(tf_Author);
		
		tf_Publisher = new JTextField();		// 출판사 텍스트 필드
		tf_Publisher.setToolTipText("출판사");
		tf_Publisher.setHorizontalAlignment(SwingConstants.CENTER);
		tf_Publisher.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		tf_Publisher.setColumns(50);
		tf_Publisher.setBounds(12, 201, 389, 35);
		p_Input.add(tf_Publisher);
		
		tf_PBYear = new JTextField();		// 출판연도 텍스트 필드
		tf_PBYear.setToolTipText("출판연도");
		tf_PBYear.setHorizontalAlignment(SwingConstants.CENTER);
		tf_PBYear.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		tf_PBYear.setColumns(50);
		tf_PBYear.setBounds(12, 260, 389, 35);
		p_Input.add(tf_PBYear);
		
		tf_Price = new JTextField();		// 가격 텍스트 필드
		tf_Price.setToolTipText("가격");
		tf_Price.setHorizontalAlignment(SwingConstants.CENTER);
		tf_Price.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		tf_Price.setColumns(50);
		tf_Price.setBounds(12, 320, 389, 35);
		p_Input.add(tf_Price);
		
		lb_NoBook = new JLabel("도서번호");
		lb_NoBook.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		lb_NoBook.setBounds(12, 10, 57, 15);
		p_Input.add(lb_NoBook);
		
		lb_Title = new JLabel("도서명");
		lb_Title.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		lb_Title.setBounds(12, 69, 57, 15);
		p_Input.add(lb_Title);
		
		lb_Author = new JLabel("저자");
		lb_Author.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		lb_Author.setBounds(12, 128, 57, 15);
		p_Input.add(lb_Author);
		
		lb_Publisher = new JLabel("출판사");
		lb_Publisher.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		lb_Publisher.setBounds(12, 187, 57, 15);
		p_Input.add(lb_Publisher);
		
		lb_PBYear = new JLabel("출판연도");
		lb_PBYear.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		lb_PBYear.setBounds(12, 246, 57, 15);
		p_Input.add(lb_PBYear);
		
		lb_Price = new JLabel("가격");
		lb_Price.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		lb_Price.setBounds(12, 305, 57, 15);
		p_Input.add(lb_Price);
		
		lb_Division = new JLabel("구분");
		lb_Division.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		lb_Division.setBounds(12, 365, 57, 15);
		p_Input.add(lb_Division);
		
		lb_Lang = new JLabel("언어");
		lb_Lang.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		lb_Lang.setBounds(12, 425, 57, 15);
		p_Input.add(lb_Lang);
		
		lb_Category = new JLabel("분야");
		lb_Category.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		lb_Category.setBounds(12, 485, 57, 15);
		p_Input.add(lb_Category);
		
		btn_Insert = new JButton("추가");
		btn_Insert.setToolTipText("기입된 도서 정보를 바탕으로 도서를 추가합니다. 도서번호는 자동으로 생성됩니다.");
		btn_Insert.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		btn_Insert.setFocusPainted(false);
		btn_Insert.setBackground(Color.WHITE);
		btn_Insert.setBounds(12, 619, 180, 33);
		p_Input.add(btn_Insert);
		
		btn_Delete = new JButton("삭제");
		btn_Delete.setToolTipText("해당 도서를 목록에서 삭제합니다.");
		btn_Delete.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		btn_Delete.setFocusPainted(false);
		btn_Delete.setBackground(Color.WHITE);
		btn_Delete.setBounds(221, 619, 180, 33);
		p_Input.add(btn_Delete);
		
		btn_Update = new JButton("수정");
		btn_Update.setToolTipText("기입된 내용으로 도서 정보를 수정합니다.");
		btn_Update.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		btn_Update.setFocusPainted(false);
		btn_Update.setBackground(Color.WHITE);
		btn_Update.setBounds(12, 662, 180, 33);
		p_Input.add(btn_Update);
		
		btn_tfClear = new JButton("초기화");
		btn_tfClear.setToolTipText("모든 정보 입력 칸을 초기화합니다.");
		btn_tfClear.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		btn_tfClear.setFocusPainted(false);
		btn_tfClear.setBackground(Color.WHITE);
		btn_tfClear.setBounds(221, 662, 180, 33);
		p_Input.add(btn_tfClear);
		
		cb_Division = new JComboBox(divisions);
		cb_Division.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		cb_Division.setBounds(12, 380, 389, 35);
		p_Input.add(cb_Division);
		
		cb_Lang = new JComboBox(langs);
		cb_Lang.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		cb_Lang.setBounds(12, 440, 389, 35);
		p_Input.add(cb_Lang);

		cb_Category = new JComboBox(categorys);
		cb_Category.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		cb_Category.setBounds(12, 500, 389, 35);
		p_Input.add(cb_Category);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(425, 10, 1133, 705);
		p_Main.add(scrollPane);
		
		tablemd = new DefaultTableModel(db.getBookList(),tableheads);
		table = new JTable(tablemd);
		table.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int[] cols = table.getSelectedRows();
				TableModel model = table.getModel();
				int no_book = Integer.parseInt(model.getValueAt(cols[cols.length-1], 0).toString());
				Object[][] info = db.getBookList(no_book);
				
				tf_NoBook.setText(info[0][0].toString());
				tf_Title.setText(info[0][1].toString());
				tf_Author.setText(info[0][2].toString());
				tf_Publisher.setText(info[0][3].toString());
				tf_PBYear.setText(info[0][4].toString());
				tf_Price.setText(info[0][5].toString());
				cb_Division.setSelectedItem(info[0][6].toString());
				cb_Lang.setSelectedItem(info[0][7].toString());
				cb_Category.setSelectedItem(info[0][8].toString());
			}
		});
		scrollPane.setViewportView(table);
		
		JButton btn_Put = new JButton("담기");	// 도서를 책 바구니에 담는 '담기' 버튼
		btn_Put.setToolTipText("선택한 모든 도서를 책 바구니에 담습니다.");
		btn_Put.setBounds(1472, 725, 86, 33);
		btn_Put.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		btn_Put.setBackground(SystemColor.window);
		btn_Put.setFocusPainted(false);
		p_Main.add(btn_Put);
		
		lb_DateTime = new JLabel(today);
		lb_DateTime.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 20));
		lb_DateTime.setBounds(10, 725, 202, 33);
		p_Main.add(lb_DateTime);
		
		lblNewLabel = new JLabel("made by 신재현");
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
		btn_Insert.addActionListener(this);
		btn_Delete.addActionListener(this);
		btn_Update.addActionListener(this);
		btn_tfClear.addActionListener(this);
		btn_Put.addActionListener(this);
		
		
	}
	
	public void clearInfo() {		// 도서 정보 입력칸을 전부 비우는 메소드
		tf_NoBook.setText("");
		tf_Title.setText("");
		tf_Author.setText("");
		tf_Publisher.setText("");
		tf_PBYear.setText("");
		tf_Price.setText("");
		cb_Division.setSelectedIndex(0);
		cb_Lang.setSelectedIndex(0);
		cb_Category.setSelectedIndex(0);
	}
	
	public void refleshTable(String col, String search) {	//테이블을 새로고침하는 메소드
		Object[][] o = db.getBookList("book",col, search);
		tablemd.setDataVector(o,tableheads);
	}
	
	// 액션
	@Override
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
			refleshTable(cb_Search.getSelectedItem().toString(), tf_SearchBar.getText());
			System.out.println("Search : " + cb_Search.getSelectedItem() + " , " + tf_SearchBar.getText());
			break;
			
		case "지우기" :
			tf_SearchBar.setText("");
			cb_Search.setSelectedIndex(0);
			System.out.println("SearchBar Clear");
			break;
			
		case "새로고침" :
			refleshTable(cb_Search.getSelectedItem().toString(), tf_SearchBar.getText());
			System.out.println("Reflesh the Table");
			break;
			
		case "추가" :
			if (! (tf_Title.getText().equals("") && tf_Author.getText().equals("") && tf_Publisher.getText().equals("") && 
					tf_PBYear.getText().equals("") && tf_Price.getText().equals(""))) {
				// insert 메소드
				db.insertBook(tf_Title.getText(), tf_Author.getText(), tf_Publisher.getText(), tf_PBYear.getText(), Integer.parseInt(tf_Price.getText()), 
						cb_Division.getSelectedItem().toString(), cb_Lang.getSelectedItem().toString(), cb_Category.getSelectedItem().toString());	
				
				refleshTable(cb_Search.getSelectedItem().toString(), tf_SearchBar.getText());
				clearInfo();
			} else {
				JOptionPane.showMessageDialog(null, "도서 정보 입력 칸이 비어있습니다. 전부 입력해주세요.");
			}
			break;
			
		case "삭제" :
			// delete 메소드
			int alarm = JOptionPane.showConfirmDialog(null,"정말 삭제하시겠습니까?","경고",JOptionPane.YES_NO_OPTION);
			if (alarm == 0) {
				db.deleteBook(Integer.parseInt(tf_NoBook.getText()));
				refleshTable(cb_Search.getSelectedItem().toString(), tf_SearchBar.getText());
				clearInfo();
			}
			
			break;
			
		case "수정" :
			if (! (tf_Title.getText().equals("") && tf_Author.getText().equals("") && tf_Publisher.getText().equals("") && 
					tf_PBYear.getText().equals("") && tf_Price.getText().equals(""))) {
				// update 메소드
				db.updateBook(Integer.parseInt(tf_NoBook.getText()),tf_Title.getText(), tf_Author.getText(), tf_Publisher.getText(), tf_PBYear.getText(), Integer.parseInt(tf_Price.getText()), 
						cb_Division.getSelectedItem().toString(), cb_Lang.getSelectedItem().toString(), cb_Category.getSelectedItem().toString());	
				
				refleshTable(cb_Search.getSelectedItem().toString(), tf_SearchBar.getText());
				clearInfo();
			} else {
				JOptionPane.showMessageDialog(null, "도서 정보 입력 칸이 비어있습니다. 전부 입력해주세요.");
			}
			break;
			
		case "초기화" :
			clearInfo();
			System.out.println("Initialize Input Fields");
			break;
			
		case "담기" :
			int[] cols = table.getSelectedRows();
			TableModel model = table.getModel();
			
			if (tf_NoBook.getText().equals("")) {
				JOptionPane.showMessageDialog(null, " 도서를 선택해 주세요.");
			}else {
				int cnt = 0;
				for (int i=0; i<cols.length; i++) {
					String no_book = model.getValueAt(cols[i], 0).toString();
					String status = model.getValueAt(cols[i], 9).toString();
					if(status.equals("보유")) {
						if(db.checkBasket(no_book) == false) {
							db.putInBasket(no_book);
							cnt++;
						}else {
							JOptionPane.showMessageDialog(null, "도서번호 : " + no_book + "번 도서는 이미 바구니에 있습니다.");
//							System.out.println("no_book : " + no_book + " exist in basket table");
						}
					}else {
						JOptionPane.showMessageDialog(null, "대여 중인 도서는 바구니에 담을 수 없습니다.");
					}
					
					
				}
				JOptionPane.showMessageDialog(null, cnt + "개의 도서를 바구니에 담았습니다.");
				System.out.println("put " + cnt + " books in basket table");
			}
			break;
		}
	}
	
}
