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
import java.util.regex.Pattern;

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
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import db.DBConnect;

public class MemberPage implements ActionListener {
	DBConnect db = new DBConnect();
	private JFrame frame;
	private JPanel p_Input;
	private JTable table;
	private JScrollPane scrollPane;
	private JTextField tf_SearchBar, tf_Id, tf_Password, tf_Name, tf_Birth, tf_Tel;
	private JLabel lb_Id, lb_Password, lb_Name, lb_Gender, lb_Birth, lb_Tel;
	private JButton btn_Insert, btn_Delete, btn_Update, btn_tfClear;
	private JComboBox cb_Search, cb_Gender;
	private DefaultTableModel tablemd;
	private String[] tableheads, genders;
	
	/**
	 * 어플리케이션 실행
	 */
	

	/**
	 * 어플리케이션 생성
	 */
	public MemberPage() {
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
		tableheads = new String[] {"아이디","비밀번호","이름","성별","생년월일","전화번호","등록일"};
		genders = new String[] {"남자","여자"};
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
		btn_Member.setBackground(SystemColor.activeCaption);
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
		btn_Refresh.setToolTipText("회원 목록 테이블을 새로고침 합니다");
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
		
		tf_Id = new JTextField();		// 아이디 텍스트 필드
		tf_Id.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		tf_Id.setHorizontalAlignment(SwingConstants.CENTER);
		tf_Id.setToolTipText("아이디");
		tf_Id.setBounds(12, 24, 389, 35);
		p_Input.add(tf_Id);
		tf_Id.setColumns(10);
		
		tf_Password = new JTextField();		// 비밀번호 텍스트 필드
		tf_Password.setToolTipText("비밀번호");
		tf_Password.setHorizontalAlignment(SwingConstants.CENTER);
		tf_Password.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		tf_Password.setColumns(50);
		tf_Password.setBounds(12, 83, 389, 35);
		p_Input.add(tf_Password);
		
		tf_Name = new JTextField();		// 이름 텍스트 필드
		tf_Name.setToolTipText("이름");
		tf_Name.setHorizontalAlignment(SwingConstants.CENTER);
		tf_Name.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		tf_Name.setColumns(50);
		tf_Name.setBounds(12, 142, 389, 35);
		p_Input.add(tf_Name);
		
		tf_Birth = new JTextField();		// 생년월일 텍스트 필드
		tf_Birth.setToolTipText("생년월일");
		tf_Birth.setHorizontalAlignment(SwingConstants.CENTER);
		tf_Birth.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		tf_Birth.setColumns(50);
		tf_Birth.setBounds(12, 260, 389, 35);
		p_Input.add(tf_Birth);
		
		tf_Tel = new JTextField();		// 전화번호 텍스트 필드
		tf_Tel.setToolTipText("전화번호");
		tf_Tel.setHorizontalAlignment(SwingConstants.CENTER);
		tf_Tel.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		tf_Tel.setColumns(50);
		tf_Tel.setBounds(12, 320, 389, 35);
		p_Input.add(tf_Tel);
		
		lb_Id = new JLabel("아이디");
		lb_Id.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		lb_Id.setBounds(12, 10, 57, 15);
		p_Input.add(lb_Id);
		
		lb_Password = new JLabel("비밀번호");
		lb_Password.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		lb_Password.setBounds(12, 69, 57, 15);
		p_Input.add(lb_Password);
		
		lb_Name = new JLabel("이름");
		lb_Name.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		lb_Name.setBounds(12, 128, 57, 15);
		p_Input.add(lb_Name);
		
		lb_Gender = new JLabel("성별");
		lb_Gender.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		lb_Gender.setBounds(12, 187, 57, 15);
		p_Input.add(lb_Gender);
		
		lb_Birth = new JLabel("생년월일");
		lb_Birth.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		lb_Birth.setBounds(12, 246, 57, 15);
		p_Input.add(lb_Birth);
		
		lb_Tel = new JLabel("전화번호");
		lb_Tel.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		lb_Tel.setBounds(12, 305, 57, 15);
		p_Input.add(lb_Tel);
		
		btn_Insert = new JButton("추가");
		btn_Insert.setToolTipText("기입된 정보를 바탕으로 회원을 추가합니다.");
		btn_Insert.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		btn_Insert.setFocusPainted(false);
		btn_Insert.setBackground(Color.WHITE);
		btn_Insert.setBounds(12, 619, 180, 33);
		p_Input.add(btn_Insert);
		
		btn_Delete = new JButton("삭제");
		btn_Delete.setToolTipText("해당 회원을 목록에서 삭제합니다.");
		btn_Delete.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		btn_Delete.setFocusPainted(false);
		btn_Delete.setBackground(Color.WHITE);
		btn_Delete.setBounds(221, 619, 180, 33);
		p_Input.add(btn_Delete);
		
		btn_Update = new JButton("수정");
		btn_Update.setToolTipText("기입된 내용으로 회원 정보를 수정합니다.");
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
		
		cb_Gender = new JComboBox(genders);
		cb_Gender.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		cb_Gender.setBounds(12, 201, 389, 35);
		p_Input.add(cb_Gender);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(425, 10, 1133, 705);
		p_Main.add(scrollPane);
		
		tablemd = new DefaultTableModel(db.getMemberList(cb_Search.getSelectedItem().toString(), ""),tableheads);
		table = new JTable(tablemd);
		table.setFont(new Font("Tmon몬소리 Black", Font.PLAIN, 12));
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int col = table.getSelectedRow();
				TableModel model = table.getModel();
				
				tf_Id.setText(model.getValueAt(col, 0).toString());
				tf_Password.setText(model.getValueAt(col, 1).toString());
				tf_Name.setText(model.getValueAt(col, 2).toString());
				cb_Gender.setSelectedItem(model.getValueAt(col, 3).toString());
				tf_Birth.setText(model.getValueAt(col, 4).toString());
				tf_Tel.setText(model.getValueAt(col, 5).toString());
			}
		});
		scrollPane.setViewportView(table);
		
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
		btn_Insert.addActionListener(this);
		btn_Delete.addActionListener(this);
		btn_Update.addActionListener(this);
		btn_tfClear.addActionListener(this);
		
		
	}
	
	public void clearInfo() {		// 정보 입력칸을 전부 비우는 메소드
		tf_Id.setText("");
		tf_Password.setText("");
		tf_Name.setText("");
		tf_Birth.setText("");
		tf_Tel.setText("");
		cb_Gender.setSelectedIndex(0);
	}
	
	public void refleshTable(String col, String search) {	//테이블을 새로고침하는 메소드
		Object[][] o = db.getMemberList(col, search);
		tablemd.setDataVector(o,tableheads);
	}
	
	public Boolean checkInput(String col ,String info) {
		Boolean chk = false;
		String pattern = "";
		if (col.equals("생년월일")) {
			pattern = "([0-9]{2}(0[1-9]|1[0-2])(0[1-9]|[1,2][0-9]|3[0,1]))"; 	//숫자 6자리
			chk = Pattern.matches(pattern, info);
		}else if(col.equals("전화번호")) {
			pattern = "^\\d{2,3}-\\d{3,4}-\\d{4}$";
			chk = Pattern.matches(pattern, info);
		}else {
			JOptionPane.showMessageDialog(null, col + " 은 checkInput 메소드가 지원하지 않습니다.");
		}
		return chk;
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
			if (! (tf_Id.getText().equals("") || tf_Password.getText().equals(""))) {		//아이디와 비밀번호가 공백이 아닐경우
				// insert 메소드
				Object[] info = new Object[]{tf_Id.getText(), tf_Password.getText(), tf_Name.getText(), cb_Gender.getSelectedItem().toString(), tf_Birth.getText(), tf_Tel.getText()};
				if (db.checkID(tf_Id.getText()) == true) {
					JOptionPane.showMessageDialog(null,"해당 아이디가 존재합니다. 다른 아이디를 입력하세요.");
				}else {
					if(tf_Birth.getText().equals("") && tf_Tel.getText().equals("")) {
						// 생년월일과 전화번호가 공백인 경우
						db.insertMember(info);
						refleshTable(cb_Search.getSelectedItem().toString(), tf_SearchBar.getText());
						clearInfo();
					}else if(tf_Birth.getText().equals("") && !(tf_Tel.getText().equals(""))){
						// 생년월일만 공백인 경우
						if(checkInput("전화번호",tf_Tel.getText()) == true) {
							// 전화번호가 형식에 맞을 경우
							db.insertMember(info);
							refleshTable(cb_Search.getSelectedItem().toString(), tf_SearchBar.getText());
							clearInfo();
						}else {
							JOptionPane.showMessageDialog(null,"전화번호가 (0)00-(0)000-0000 형식에 맞지 않습니다.");
							System.out.println("Failed insert member table. ");
						}
					}else if(!(tf_Birth.getText().equals("")) && tf_Tel.getText().equals("")) {
						// 전화번호만 공백인 경우
						if(checkInput("생년월일",tf_Birth.getText()) == true) {
							// 생년월일이 형식에 맞을 경우
							db.insertMember(info);
							refleshTable(cb_Search.getSelectedItem().toString(), tf_SearchBar.getText());
							clearInfo();
						}else {
							JOptionPane.showMessageDialog(null,"생년월일 6글자에 맞지 않습니다.");
							System.out.println("Failed insert member table. ");
						}
					}else {
						//둘다 공백이 아닌 경우
						if(checkInput("생년월일",tf_Birth.getText())==true && checkInput("전화번호",tf_Tel.getText()) == true) {
							//둘다 형식에 맞는 경우
							db.insertMember(info);
							refleshTable(cb_Search.getSelectedItem().toString(), tf_SearchBar.getText());
							clearInfo();
						}else {
							JOptionPane.showMessageDialog(null,"생년월일(6글자)과 전화번호(000-0000-0000)가 형식에 맞지 않습니다.");
							System.out.println("Failed insert member table. ");
						}
					}
				}
			} else { // 아이디와 비밀번호가 공백일 경우
				JOptionPane.showMessageDialog(null, "아이디와 비밀번호가 비어있습니다. 다시 입력해주세요.");
			}
			break;
			
		case "삭제" :
			// delete 메소드
			int alarm = JOptionPane.showConfirmDialog(null,"정말 삭제하시겠습니까?","경고",JOptionPane.YES_NO_OPTION);
			if (alarm == 0) {
				// delete 메소드
				if (db.checkID(tf_Id.getText()) == true) {
					db.deleteMember(tf_Id.getText());
					refleshTable(cb_Search.getSelectedItem().toString(), tf_SearchBar.getText());
					clearInfo();
				}
				}else {
					JOptionPane.showMessageDialog(null,"해당 아이디가 존재하지 않습니다. 다시 확인해주세요.");
					System.out.println("Failed delete member table");
				}
			break;
			
		case "수정" :
			if (! (tf_Id.getText().equals("") || tf_Password.getText().equals(""))) {
				// update 메소드 
				Object[] info = new Object[]{tf_Id.getText(), tf_Password.getText(), tf_Name.getText(), cb_Gender.getSelectedItem().toString(), tf_Birth.getText(), tf_Tel.getText()};
				if (db.checkID(tf_Id.getText()) == true) {
					if(tf_Birth.getText().equals("") && tf_Tel.getText().equals("")) {
						// 생년월일과 전화번호가 공백인 경우
						db.updateMember(info);
						refleshTable(cb_Search.getSelectedItem().toString(), tf_SearchBar.getText());
						clearInfo();
					}else if(tf_Birth.getText().equals("") && !(tf_Tel.getText().equals(""))){
						// 생년월일만 공백인 경우
						if(checkInput("전화번호",tf_Tel.getText()) == true) {
							// 전화번호가 형식에 맞을 경우
							db.updateMember(info);
							refleshTable(cb_Search.getSelectedItem().toString(), tf_SearchBar.getText());
							clearInfo();
						}else {
							JOptionPane.showMessageDialog(null,"전화번호가 (0)00-(0)000-0000 형식에 맞지 않습니다.");
							System.out.println("Failed update member table. ");
						}
					}else if(!(tf_Birth.getText().equals("")) && tf_Tel.getText().equals("")) {
						// 전화번호만 공백인 경우
						if(checkInput("생년월일",tf_Birth.getText()) == true) {
							// 생년월일이 형식에 맞을 경우
							db.updateMember(info);
							refleshTable(cb_Search.getSelectedItem().toString(), tf_SearchBar.getText());
							clearInfo();
						}else {
							JOptionPane.showMessageDialog(null,"생년월일 6글자에 맞지 않습니다.");
							System.out.println("Failed update member table. ");
						}
					}else {
						//둘다 공백이 아닌 경우
						if(checkInput("생년월일",tf_Birth.getText())==true && checkInput("전화번호",tf_Tel.getText()) == true) {
							//둘다 형식에 맞는 경우
							db.updateMember(info);
							refleshTable(cb_Search.getSelectedItem().toString(), tf_SearchBar.getText());
							clearInfo();
						}else {
							JOptionPane.showMessageDialog(null,"생년월일과 전화번호가 형식에 맞지 않습니다.");
							System.out.println("Failed update member table. ");
						}
					}
				}else {
					JOptionPane.showMessageDialog(null,"해당 아이디가 존재하지 않습니다. 다시 확인해주세요.");
					System.out.println("Failed update member table");
				}
			} else {
				JOptionPane.showMessageDialog(null, "아이디와 비밀번호가 비어있습니다. 다시 입력해주세요.");
			}
			break;
			
		case "초기화" :
			clearInfo();
			System.out.println("Initialize Input Fields");
			break;	
		
		}
	}
	

}
