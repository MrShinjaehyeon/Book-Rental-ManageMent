package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JOptionPane;

// 오라클 SQL 연결
public class DBConnect{
	private Connection con;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	public String data;	
	private String id=null, name=null, tel=null;
	private Boolean check = false;
	
	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	LocalDate now = LocalDate.now();
	String today = now.format(dateTimeFormatter);
	LocalDate rtdt = now.plusDays(10);
	String rtDate = rtdt.format(dateTimeFormatter);
	
	public DBConnect() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); 	// 오라클 드라이버를 로드합니다.
			System.out.println("Driver Registretion Completed");
		}catch(ClassNotFoundException e) {		// 로드 실패 시 예외 캐치
			e.printStackTrace();
		}
	}
	
	public void getConnection() {
		String url = "jdbc:oracle:thin:@localhost:1521:xe"; 	// 오라클 url
		String id = "shin";	// 오라클 데이터베이스 아이디
		String passwd = "shin";	// 오라클 데이터베이스 비밀번호
		try {
			con = DriverManager.getConnection(url, id, passwd);		// 드라이버와 연결
			System.out.println("Successful Connection");
		}catch(SQLException e) {	// 드라이버 연결 실패시 예외 캐치
			e.printStackTrace();
		}
	}
	
	// SQL 메소드
	public Object[][] getBookList(){	//도서 목록을 전부 가져옵니다.
		String sql = "select * from book order by no_book";
		try {
			stmt = con.createStatement();	// 데이터베이스로 SQL 문을 보내기 위한 개체를 만듭니다.
			rs = stmt.executeQuery(sql);	// 만들어진 개체를 ResultSet으로 반환합니다.
			ArrayList<Object[]> list = new ArrayList<Object[]>();	// 1차원 배열을 담는 배열리스트 선언 합니다.
			while(rs.next()) {	// rs 안에 행이 다 끝날 때까지 반복문 작업을 합니다.
				list.add(new Object[]{ // 리스트에 오브젝트 배열을 추가합니다.
					rs.getInt("no_book"),
					rs.getString("title"),
					rs.getString("author"),
					rs.getString("publisher"),
					rs.getString("pb_year"),
					rs.getInt("price"),
					rs.getString("division"),
					rs.getString("lang"),
					rs.getString("category"),
					rs.getString("status"),
					rs.getString("regdate")
				});
			}
			Object[][] arr = new Object[list.size()][11];	// 행과 열의 수를 맞춰 2차원 배열을 만들어줍니다.
			return list.toArray(arr);	// 리스트를 방금 만든 2차원 배열 형태로 형변환 시키고 반환합니다.
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}finally {
			try {
				if(stmt != null)stmt.close();		// statement를 더 이상 사용하지 않으면 닫아줍니다.
				if(rs != null)rs.close();	// ResultSet을 더 이상 사용하지 않으면 닫아줍니다.
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 도서번호로 도서 목록 가져오기
	public Object[][] getBookList(int no_book){	//해당 도서번호의 정보를 가져옵니다.
		String sql = "select * from book where no_book = '"+no_book+"' order by no_book";
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			ArrayList<Object[]> list = new ArrayList<Object[]>();
			while(rs.next()) {
				list.add(new Object[]{ 
					rs.getInt("no_book"),
					rs.getString("title"),
					rs.getString("author"),
					rs.getString("publisher"),
					rs.getString("pb_year"),
					rs.getInt("price"),
					rs.getString("division"),
					rs.getString("lang"),
					rs.getString("category"),
					rs.getString("status"),
					rs.getString("regdate")
				});
			}
			Object[][] arr = new Object[list.size()][11];
			return list.toArray(arr);
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}finally {
			try {
				if(stmt != null)stmt.close();	
				if(rs != null)rs.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 도서 목록 검색
	public Object[][] getBookList(String table, String col, String search){	// 도서 목록과 책 바구니를 선택해, 해당 컬럼의 검색어와 일치하는 목록을 가져옵니다.
		String sql = "select * from " + table + " order by no_book";
		String c = "";
		switch (col) {
		
		case "도서번호" : c = "no_book"; break;
		case "도서명" : c = "title"; break;
		case "저자" : c = "author"; break;
		case "출판사" : c = "publisher"; break;
		case "출판연도" : c = "pb_year"; break;
		case "가격" : c = "price"; break;
		case "구분" : c = "division"; break;
		case "언어" : c = "lang"; break;
		case "분야" : c = "category"; break;
		case "상태" : c = "status"; break;
		case "등록일" : c = "regdate";
		}
		if (! search.equals("")) {
			if(c.equals("no_book") || c.equals("pb_year") || c.equals("price") || c.equals("status")) {
				sql = "select * from " +table+ " where " + c +" = '"+search+"' order by no_book";
				try {
					stmt = con.createStatement();
					rs = stmt.executeQuery(sql);
					ArrayList<Object[]> list = new ArrayList<Object[]>();	
					while(rs.next()) {
						list.add(new Object[]{
							rs.getInt("no_book"),
							rs.getString("title"),
							rs.getString("author"),
							rs.getString("publisher"),
							rs.getString("pb_year"),
							rs.getInt("price"),
							rs.getString("division"),
							rs.getString("lang"),
							rs.getString("category"),
							rs.getString("status"),
							rs.getString("regdate")
						});
					}
					Object[][] arr = new Object[list.size()][11];
					return list.toArray(arr);
				}catch(SQLException e) {
					e.printStackTrace();
					return null;
				}finally {
					try {
						if(stmt != null)stmt.close();
						if(rs != null)rs.close();
					}catch(SQLException e) {
						e.printStackTrace();
					}
				}
			}else {
				sql = "select * from " +table+ " where " +c+ " like ? order by no_book";
				try {
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, "%"+search+"%");
					rs = pstmt.executeQuery();
					ArrayList<Object[]> list = new ArrayList<Object[]>();	
					while(rs.next()) {
						list.add(new Object[]{
							rs.getInt("no_book"),
							rs.getString("title"),
							rs.getString("author"),
							rs.getString("publisher"),
							rs.getString("pb_year"),
							rs.getInt("price"),
							rs.getString("division"),
							rs.getString("lang"),
							rs.getString("category"),
							rs.getString("status"),
							rs.getString("regdate")
						});
					}
					Object[][] arr = new Object[list.size()][11];
					return list.toArray(arr);
				}catch(SQLException e) {
					e.printStackTrace();
					return null;
				}finally {
					try {
						if(pstmt != null)pstmt.close();
						if(rs != null)rs.close();
					}catch(SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		System.out.println(c + " , " + search + " : " + sql);
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			ArrayList<Object[]> list = new ArrayList<Object[]>();
			while(rs.next()) {
				list.add(new Object[]{
					rs.getInt("no_book"),
					rs.getString("title"),
					rs.getString("author"),
					rs.getString("publisher"),
					rs.getString("pb_year"),
					rs.getInt("price"),
					rs.getString("division"),
					rs.getString("lang"),
					rs.getString("category"),
					rs.getString("status"),
					rs.getString("regdate")
				});
			}
			Object[][] arr = new Object[list.size()][11];
			return list.toArray(arr);
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}finally {
			try {
				if(stmt != null)stmt.close();
				if(rs != null)rs.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 도서 목록 추가 
	public void insertBook(String title, String author, String publisher, String pb_year, int price, 
			String division, String lang, String category) {
		String sql = "insert into book values(seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, author);
			pstmt.setString(3, publisher);
			pstmt.setString(4, pb_year);
			pstmt.setInt(5, price);
			pstmt.setString(6, division);
			pstmt.setString(7, lang);
			pstmt.setString(8, category);
			pstmt.setString(9, "보유");
			pstmt.setString(10, today);
			rs = pstmt.executeQuery();
			
			System.out.println("Insert to Book table : " + title + "," + author + "," + publisher + "," + pb_year + "," + price + "," + division + "," + lang + "," + category + "," + "보유" + "," + today);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null)pstmt.close();
				if(rs != null)rs.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 도서 목록 수정
	public void updateBook(int no_book, String title, String author, String publisher, String pb_year, int price,
			String division, String lang, String category) {
		String sql = "update book set title = ?, author = ?, publisher = ?, pb_year = ?, price = ?, division = ?, lang = ?, category = ? where no_book = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, author);
			pstmt.setString(3, publisher);
			pstmt.setString(4, pb_year);
			pstmt.setInt(5, price);
			pstmt.setString(6, division);
			pstmt.setString(7, lang);
			pstmt.setString(8, category);
			pstmt.setInt(9, no_book);
			rs = pstmt.executeQuery();
			
			System.out.println("Update to Book table : " + no_book + "," + title + "," + author + "," + publisher + "," + pb_year + "," + price + "," + division + "," + lang + "," + category);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null)pstmt.close();
				if(rs != null)rs.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 도서 목록 삭제
	public void deleteBook(int no_book) {
		String sql = "delete from book where no_book = '"+no_book+"'";
		try {
			stmt = con.createStatement();
			int rs = stmt.executeUpdate(sql);
			
			System.out.println("no_book : " +no_book+ ", delete from book table" );
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(stmt != null)stmt.close();
				if(rs != null)rs.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 바구니 테이블 목록 추가
	public void insertBasket(int no_book, String title, String author, String publisher, String pb_year, int price, 
			String division, String lang, String category, String status) {
		String sql = "insert into basket values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, no_book);
			pstmt.setString(2, title);
			pstmt.setString(3, author);
			pstmt.setString(4, publisher);
			pstmt.setString(5, pb_year);
			pstmt.setInt(6, price);
			pstmt.setString(7, division);
			pstmt.setString(8, lang);
			pstmt.setString(9, category);
			pstmt.setString(10, status);
			pstmt.setString(11, today);
			rs = pstmt.executeQuery();
			
			System.out.println("Insert to Basket table : " + no_book + "," + title + "," + author + "," + publisher + "," + pb_year + "," + price + "," + division + "," + lang + "," + category + "," + "보유" + "," + today);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null)pstmt.close();
				if(rs != null)rs.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 바구니 테이블 목록 제거
	public void deleteBasket(String no_book) {
		String sql = "delete from basket where no_book = '"+no_book+"'";
		if (no_book.equals("0")) {sql = "delete from basket"; System.out.println("delete from basket table all");}
		System.out.println("no_book : " +no_book+ ", delete from basket table" );
		try {
			stmt = con.createStatement();
			int rs = stmt.executeUpdate(sql);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(stmt != null)stmt.close();
				if(rs != null)rs.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 책 바구니에 담기
	public void putInBasket(String selected){
		String sql = "select * from book where no_book = ?";
		Integer no_book = null,price = null;
		String title = null,author=null,publisher=null,pb_year=null,division=null,lang=null,category=null,status=null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, selected);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				no_book = rs.getInt("no_book");
				title = rs.getString("title");
				author = rs.getString("author");
				publisher = rs.getString("publisher");
				pb_year = rs.getString("pb_year");
				price = rs.getInt("price");
				division = rs.getString("division");
				lang = rs.getString("lang");
				category = rs.getString("category");
				status = rs.getString("status");
			}
			insertBasket(no_book, title, author, publisher, pb_year, price, division, lang, category, status);
			System.out.println("Insert Basket Table : " + no_book + "," + title + "," + author + "," + publisher + "," + pb_year + "," + price + "," + division + "," + lang + "," + category + "," + status);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null)pstmt.close();
				if(rs != null)rs.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 책 바구니 중복 확인
	public Boolean checkBasket(String selected) {
		String sql = "select * from basket where no_book = ?";
		boolean bcheck = false;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, selected);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				System.out.println(selected + " exist in basket table");
				bcheck = true;
			}else {
				System.out.println(selected + " does not exist in basket table");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null)pstmt.close();
				if(rs != null)rs.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}return bcheck;
		}
	}
	
	// 대여 도서 확인
	public Boolean checkRentalBook(String no_book) {
		String sql = "select * from rental where no_book = ?";
		boolean rbcheck = false;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, no_book);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				System.out.println(no_book + " exist in rental table");
				rbcheck = true;
			}else {
				System.out.println(no_book + " does not exist in rental table");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null)pstmt.close();
				if(rs != null)rs.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}return rbcheck;
		}
	}
	
	// 대여 아이디 확인
	public Boolean checkRentalID(String id) {
		String sql = "select * from rental where id = ?";
		boolean ridcheck = false;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				System.out.println(id + " exist in rental table");
				ridcheck = true;
			}else {
				System.out.println(id + " does not exist in rental table");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null)pstmt.close();
				if(rs != null)rs.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
			return ridcheck;
		}
	}
	
	// 회원 목록 불러오기
	public Object[][] getMemberList(String col, String search){	// 회원 테이블에서 해당 컬럼의 검색어와 일치하는 목록을 가져옵니다.
		String sql = "select * from member order by id";
		String c = "";
		switch (col) {
		case "아이디" : c = "id"; break;
		case "비밀번호" : c = "password"; break;
		case "이름" : c = "name"; break;
		case "성별" : c = "gender"; break;
		case "생년월일" : c = "birth"; break;
		case "전화번호" : c = "tel"; break;
		case "등록일" : c = "regdate";
		}
		if (! search.equals("")) {
			if(c.equals("gender") || c.equals("birth")) {
				sql = "select * from member where " + c +" = '"+search+"' order by id";
				try {
					stmt = con.createStatement();
					rs = stmt.executeQuery(sql);
					ArrayList<Object[]> list = new ArrayList<Object[]>();	
					while(rs.next()) {
						list.add(new Object[]{
							rs.getString("id"),
							rs.getString("password"),
							rs.getString("name"),
							rs.getString("gender"),
							rs.getString("birth"),
							rs.getString("tel"),
							rs.getString("regdate")
						});
					}
					Object[][] arr = new Object[list.size()][7];
					return list.toArray(arr);
				}catch(SQLException e) {
					e.printStackTrace();
					return null;
				}finally {
					try {
						if(stmt != null)stmt.close();
						if(rs != null)rs.close();
					}catch(SQLException e) {
						e.printStackTrace();
					}
				}
			}else {
				sql = "select * from member where " +c+ " like ? order by id";
				try {
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, "%"+search+"%");
					rs = pstmt.executeQuery();
					ArrayList<Object[]> list = new ArrayList<Object[]>();	
					while(rs.next()) {
						list.add(new Object[]{
								rs.getString("id"),
								rs.getString("password"),
								rs.getString("name"),
								rs.getString("gender"),
								rs.getString("birth"),
								rs.getString("tel"),
								rs.getString("regdate")
						});
					}
					Object[][] arr = new Object[list.size()][7];
					return list.toArray(arr);
				}catch(SQLException e) {
					e.printStackTrace();
					return null;
				}finally {
					try {
						if(pstmt != null)pstmt.close();
						if(rs != null)rs.close();
					}catch(SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		System.out.println(c + " , " + search + " : " + sql);
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			ArrayList<Object[]> list = new ArrayList<Object[]>();	
			while(rs.next()) {
				list.add(new Object[]{
						rs.getString("id"),
						rs.getString("password"),
						rs.getString("name"),
						rs.getString("gender"),
						rs.getString("birth"),
						rs.getString("tel"),
						rs.getString("regdate")
				});
			}
			Object[][] arr = new Object[list.size()][7];
			return list.toArray(arr);
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}finally {
			try {
				if(stmt != null)stmt.close();
				if(rs != null)rs.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 아이디 중복 확인
	public Boolean checkID(String id) {
		String sql = "select * from member where id = ?";
		Boolean idchk = false;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				// 해당 아이디가 존재함
				System.out.println("Member : " + id + " exist");
				idchk = true;
			}else {
				// 해당 아이디가 없음
				System.out.println("Member : " + id + " does not exist");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null)pstmt.close();
				if(rs != null)rs.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
			return idchk;
		}
	}
	
	
	// 회원 목록 추가
	public void insertMember(Object[] info) {
		String sql = "insert into member values(?, ?, ?, ?, ?, ?, ?)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, (String) info[0]);	//ID
			pstmt.setString(2, (String) info[1]);	//Password
			pstmt.setString(3, (String) info[2]);	//Name
			pstmt.setString(4, (String) info[3]);	//Gender
			pstmt.setString(5, (String) info[4]);	//Birth
			pstmt.setString(6, (String) info[5]);	//Tel
			pstmt.setString(7, today);	//Regdate
			rs = pstmt.executeQuery();
			
			System.out.println("insert to member table : " + info[0] + "," + info[1] + "," + info[2] + "," + info[3] + "," + info[4] + "," + info[5] + "," + today);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null)pstmt.close();
				if(rs != null)rs.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
		
	// 회원 목록 제거
	public void deleteMember(String id) {
		String sql = "delete from member where id = ?";;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);	//ID
			rs = pstmt.executeQuery();
			System.out.println("Member : " + id + "deletion successful");
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null)pstmt.close();
				if(rs != null)rs.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
		
	// 회원 목록 수정
	public void updateMember(Object[] info) {
		String sql = "update member set password = ?, name = ?, gender = ?, birth = ?, tel = ? where id = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, (String) info[1]);	//Password
			pstmt.setString(2, (String) info[2]);	//Name
			pstmt.setString(3, (String) info[3]);	//Gender
			pstmt.setString(4, (String) info[4]);	//Birth
			pstmt.setString(5, (String) info[5]);	//Tel
			pstmt.setString(6, (String) info[0]);	// ID
			rs = pstmt.executeQuery();
			
			System.out.println("update to member table : " + info[0] + "," + info[1] + "," + info[2] + "," + info[3] + "," + info[4] + "," + info[5]);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null)pstmt.close();
				if(rs != null)rs.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 대여 현황 불러오기
	public Object[][] getRentalList(String col, String search){	// 회원 테이블에서 해당 컬럼의 검색어와 일치하는 목록을 가져옵니다.
		String sql = "select * from rental order by return_date";
		String c = "";
		switch (col) {
		case "도서번호" : c = "no_book"; break;
		case "도서명" : c = "title"; break;
		case "아이디" : c = "id"; break;
		case "이름" : c = "name"; break;
		case "전화번호" : c = "tel"; break;
		case "대여일" : c = "rental_date"; break;
		case "반납일" : c = "return_date"; break;
		case "등록일" : c = "regdate";
		}
		if (! search.equals("")) {
			if(c.equals("no_book")) {
				sql = "select * from rental where " + c +" = '"+search+"'";
				try {
					stmt = con.createStatement();
					rs = stmt.executeQuery(sql);
					ArrayList<Object[]> list = new ArrayList<Object[]>();	
					while(rs.next()) {
						list.add(new Object[]{
							rs.getInt("no_book"),
							rs.getString("title"),
							rs.getString("id"),
							rs.getString("name"),
							rs.getString("tel"),
							rs.getString("rental_date"),
							rs.getString("return_date"),
							rs.getString("regdate")
						});
					}
					Object[][] arr = new Object[list.size()][8];
					return list.toArray(arr);
				}catch(SQLException e) {
					e.printStackTrace();
					return null;
				}finally {
					try {
						if(stmt != null)stmt.close();
						if(rs != null)rs.close();
					}catch(SQLException e) {
						e.printStackTrace();
					}
				}
			}else {
				sql = "select * from rental where " +c+ " like ? order by rental_date";
				try {
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, "%"+search+"%");
					rs = pstmt.executeQuery();
					ArrayList<Object[]> list = new ArrayList<Object[]>();	
					while(rs.next()) {
						list.add(new Object[]{
								rs.getInt("no_book"),
								rs.getString("title"),
								rs.getString("id"),
								rs.getString("name"),
								rs.getString("tel"),
								rs.getString("rental_date"),
								rs.getString("return_date"),
								rs.getString("regdate")
						});
					}
					Object[][] arr = new Object[list.size()][8];
					return list.toArray(arr);
				}catch(SQLException e) {
					e.printStackTrace();
					return null;
				}finally {
					try {
						if(pstmt != null)pstmt.close();
						if(rs != null)rs.close();
					}catch(SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		System.out.println(c + " , " + search + " : " + sql);
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			ArrayList<Object[]> list = new ArrayList<Object[]>();	
			while(rs.next()) {
				list.add(new Object[]{
						rs.getInt("no_book"),
						rs.getString("title"),
						rs.getString("id"),
						rs.getString("name"),
						rs.getString("tel"),
						rs.getString("rental_date"),
						rs.getString("return_date"),
						rs.getString("regdate")
				});
			}
			Object[][] arr = new Object[list.size()][8];
			return list.toArray(arr);
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}finally {
			try {
				if(stmt != null)stmt.close();
				if(rs != null)rs.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 도서 대여
		public void rentalBook(String no_book, String title, String id, String name, String tel) {
			String sql = "insert into rental values(?, ?, ?, ?, ?, ?, ?, ?)";
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, no_book);
				pstmt.setString(2, title);
				pstmt.setString(3, id);
				pstmt.setString(4, name);
				pstmt.setString(5, tel);
				pstmt.setString(6, today);
				pstmt.setString(7, rtDate);
				pstmt.setString(8, today);
				rs = pstmt.executeQuery();
				
				sql = "delete from basket where no_book = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, no_book);	//도서번호
				rs = pstmt.executeQuery();
				
				sql = "update book set status = ? where no_book = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, "대여");
				pstmt.setString(2, no_book);
				rs = pstmt.executeQuery();
				
				System.out.println("Rental Success : " + no_book + " , " + title);
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					if(pstmt != null)pstmt.close();
					if(rs != null)rs.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
	
	// 도서 반납
	public void returnBook(String no_book) {
		String sql = "delete from rental where no_book = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, no_book);	//도서번호
			rs = pstmt.executeQuery();
			
			sql = "update book set status = ? where no_book = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "보유");
			pstmt.setString(2, no_book);	//도서번호
			rs = pstmt.executeQuery();
			
			System.out.println("Return Success : " + no_book);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null)pstmt.close();
				if(rs != null)rs.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
