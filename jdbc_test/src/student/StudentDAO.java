package student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StudentDAO {
	final String driver = "org.mariadb.jdbc.Driver";
	final String db_ip = "localhost";
	final String db_port = "3306";
	final String db_name = "jdbc_test";
	final String db_url = 
			"jdbc:mariadb://"+db_ip+":"+db_port+"/"+db_name;
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	// 학생 정보 등록
	public int insertStudent(HashMap<String, Object> paramMap) {
		int resultChk = 0;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(db_url, "root", "1234");
			if(conn != null) {
				System.out.println("접속성공");
			}
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		}catch(SQLException e) {
			System.out.println("접속 실패");
			e.printStackTrace();
		}
		
		try {
			// 쿼리 작성해서 입력
			String sql = "INSERT INTO `jdbc_test`.`tb_student_info`\r\n"
					+ "(\r\n"
					+ "`student_name`,\r\n"
					+ "`student_grade`,\r\n"
					+ "`student_school`,\r\n"
					+ "`student_addr`,\r\n"
					+ "`student_phone`)\r\n"
					+ "VALUES\r\n"
					+ "(?,?,?,?,?);\r\n"
					+ "";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paramMap.get("studentName").toString());
			pstmt.setInt(2,Integer.parseInt(paramMap.get("studentGrade").toString()) );
			pstmt.setString(3, paramMap.get("studentSchool").toString());
			pstmt.setString(4, paramMap.get("studentAddr").toString());
			pstmt.setString(5, paramMap.get("studentPhone").toString());
			
			
			resultChk = pstmt.executeUpdate();
			
		}catch (SQLException e) {
			
			System.out.println("error :" + e);
		}finally {
			try {
				
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null && conn.isClosed()) {
					conn.close();
				}
			
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return resultChk;
	}
	
	// 학생 성적 등록
	public int insertScore(HashMap<String, Object> paramMap) {
		int resultChk = 0;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(db_url, "root", "1234");
			if(conn != null) {
				System.out.println("접속성공");
			}
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		}catch(SQLException e) {
			System.out.println("접속 실패");
			e.printStackTrace();
		}
		
		try {
			// 쿼리 작성해서 입력
			String sql = "INSERT INTO jdbc_test.tb_student_score\r\n"
					+ "(student_idx,\r\n"
					+ "score_season,\r\n"
					+ "score_semester,\r\n"
					+ "score_exam_type,\r\n"
					+ "score_subject,\r\n"
					+ "score_point\r\n)"
					+ "VALUES\r\n"
					+ "(?,?,?,?,?,?);";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,Integer.parseInt(paramMap.get("studentIdx").toString()) );
			pstmt.setString(2, paramMap.get("season").toString());
			pstmt.setInt(3,Integer.parseInt(paramMap.get("semester").toString()) );
			pstmt.setString(4, paramMap.get("examType").toString());
			pstmt.setString(5, paramMap.get("subject").toString());
			pstmt.setInt(6,Integer.parseInt(paramMap.get("point").toString()) );
			
			
			resultChk = pstmt.executeUpdate();
			
		}catch (SQLException e) {
			
			System.out.println("error :" + e);
		}finally {
			try {
				
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null && conn.isClosed()) {
					conn.close();
				}
			
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return resultChk;
	}
		
	// 전체 학생 조회
	public List<HashMap<String, Object>> printAllStudent(){
		List<HashMap<String, Object>> studentList = new ArrayList();
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(db_url, "root", "1234");
			if(conn != null) {
				System.out.println("접속성공");
			}
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		}catch(SQLException e) {
			System.out.println("접속 실패");
			e.printStackTrace();
		}
		try {
			
			String sql = "SELECT student.student_idx,\r\n"
					+ "		student.student_name,\r\n"
					+ "		student.student_grade,\r\n"
					+ "		student.student_school,\r\n"
					+ "		student.student_addr,\r\n"
					+ "		student.student_phone,\r\n"
					+ "        score.score_season,\r\n"
					+ "		score.score_semester,\r\n"
					+ "		score.score_exam_type,\r\n"
					+ "		score.score_subject,\r\n"
					+ "		score.score_point\r\n"
					+ "FROM tb_student_info student\r\n"
					+ "left join tb_student_score score\r\n"
					+ "on student.student_idx = score.student_idx;";
			
			// ?의 물음표값
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				// 조회결과 HashMap에 추가
				HashMap<String, Object> rsMap = new HashMap<String, Object>();
				// 키, 컬럼명
				rsMap.put("student_name", rs.getString("student_name"));
				rsMap.put("student_grade", rs.getString("student_grade"));
				rsMap.put("student_school", rs.getString("student_school"));
				rsMap.put("student_addr", rs.getString("student_addr"));
				rsMap.put("student_phone", rs.getString("student_phone"));
				
				rsMap.put("score_season", rs.getString("score_season"));
				rsMap.put("score_semester", rs.getString("score_semester"));
				rsMap.put("score_exam_type", rs.getString("score_exam_type"));
				rsMap.put("score_subject", rs.getString("score_subject"));
				rsMap.put("score_point", rs.getString("score_point"));
				
				studentList.add(rsMap);

			}
			
		}catch (SQLException e) {
			
			System.out.println("error :" + e);
		}finally {
			try {
				if(rs != null) {
					rs.close();
				}
				
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null && conn.isClosed()) {
					conn.close();
				}
			
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}

		return studentList;
	}
	
	// 학생 이름으로 학생 정보 조회
	public List<HashMap<String, Object>> printSearchStudent(String studentName){
		List<HashMap<String, Object>> studentList = new ArrayList();
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(db_url, "root", "1234");
			if(conn != null) {
				System.out.println("접속성공");
			}
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		}catch(SQLException e) {
			System.out.println("접속 실패");
			e.printStackTrace();
		}
		try {
			
			String sql = "SELECT student_idx,\r\n"
					+ "		student_name,\r\n"
					+ "		student_grade,\r\n"
					+ "		student_school,\r\n"
					+ "		student_addr,\r\n"
					+ "		student_phone\r\n"
					+ "FROM tb_student_info\r\n"
					+ "where student_name like concat('%', ?, '%');";
			
			// 물음표값
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, studentName);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				// 조회결과 HashMap에 추가
				HashMap<String, Object> rsMap = new HashMap<String, Object>();
				rsMap.put("student_idx", rs.getString("student_idx"));
				rsMap.put("student_name", rs.getString("student_name"));
				rsMap.put("student_grade", rs.getString("student_grade"));
				rsMap.put("student_school", rs.getString("student_school"));
				rsMap.put("student_addr", rs.getString("student_addr"));
				rsMap.put("student_phone", rs.getString("student_phone"));
				
				studentList.add(rsMap);

			}
			
		}catch (SQLException e) {
			
			System.out.println("error :" + e);
		}finally {
			try {
				if(rs != null) {
					rs.close();
				}
				
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null && conn.isClosed()) {
					conn.close();
				}
			
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return studentList;
	}
	
	// 학생 + 성적 이름으로 학생 정보 조회
		public List<HashMap<String, Object>> printSearchScore(String studentName){
			List<HashMap<String, Object>> studentList = new ArrayList();
			
			try {
				Class.forName(driver);
				conn = DriverManager.getConnection(db_url, "root", "1234");
				if(conn != null) {
					System.out.println("접속성공");
				}
			}catch(ClassNotFoundException e) {
				System.out.println("드라이버 로드 실패");
				e.printStackTrace();
			}catch(SQLException e) {
				System.out.println("접속 실패");
				e.printStackTrace();
			}
			try {
				
				String sql = "SELECT student.student_idx,\r\n"
						+ "		student.student_name,\r\n"
						+ "		student.student_grade,\r\n"
						+ "		student.student_school,\r\n"
						+ "		student.student_addr,\r\n"
						+ "		student.student_phone,\r\n"
						+ "        score.score_season,\r\n"
						+ "		score.score_semester,\r\n"
						+ "		score.score_exam_type,\r\n"
						+ "		score.score_subject,\r\n"
						+ "		score.score_point,\r\n"
						+ "        score.score_idx\r\n"
						+ "FROM tb_student_info student\r\n"
						+ "left join tb_student_score score\r\n"
						+ "on student.student_idx = score.student_idx\r\n"
						+ "where student_name like concat('%', ?, '%');";
				
				// 물음표값
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, studentName);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					// 조회결과 HashMap에 추가
					HashMap<String, Object> rsMap = new HashMap<String, Object>();
					rsMap.put("student_idx", rs.getString("student_idx"));
					rsMap.put("student_name", rs.getString("student_name"));
					rsMap.put("student_grade", rs.getString("student_grade"));
					rsMap.put("student_school", rs.getString("student_school"));
					rsMap.put("student_addr", rs.getString("student_addr"));
					rsMap.put("student_phone", rs.getString("student_phone"));
					
					rsMap.put("score_season", rs.getString("score_season"));
					rsMap.put("score_semester", rs.getString("score_semester"));
					rsMap.put("score_exam_type", rs.getString("score_exam_type"));
					rsMap.put("score_subject", rs.getString("score_subject"));
					rsMap.put("score_point", rs.getString("score_point"));
					rsMap.put("score_idx", rs.getString("score_idx"));
					
					studentList.add(rsMap);

				}
				
			}catch (SQLException e) {
				
				System.out.println("error :" + e);
			}finally {
				try {
					if(rs != null) {
						rs.close();
					}
					
					if(pstmt != null) {
						pstmt.close();
					}
					if(conn != null && conn.isClosed()) {
						conn.close();
					}
				
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			
			return studentList;
		}
	
	
	
	// 학생 정보 수정
	public int updateStudent(HashMap<String, Object> paramMap) {
		int resultChk = 0;
		
		// 데이터 접속
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(db_url, "root", "1234");
			if(conn != null) {
				System.out.println("접속성공");
			}
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		}catch(SQLException e) {
			System.out.println("접속 실패");
			e.printStackTrace();
		}
		
		try {
			String sql = "update tb_student_info\n";
			int updateChoice = Integer.parseInt(paramMap.get("updateChoice").toString());
			
			switch (updateChoice) {
				case 1: {
					
					sql += "set student_name = ?\n";
					break;
				}
				case 2: {
					
					sql += "set student_school = ?\n";
					
					break;
				}
				case 3: {
					
					sql += "set student_grade = ?\n";
					break;
				}
				case 4: {
					
					sql += "set student_phone = ?\n";
					break;
				}
				case 5: {
					
					sql += "set student_addr = ?\n";
					break;
				}
				default: {
				break;
				
				}
				
			}
			
			
			// 중복된 값을 따로 빼서 공통으로 넣는다.
			
			sql += "where student_idx = ?;";
			
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, paramMap.get("updateContents").toString());
			pstmt.setInt(2,Integer.parseInt(paramMap.get("studentIdx").toString()) );
			
			resultChk = pstmt.executeUpdate();
			
		}catch (SQLException e) {
			
			System.out.println("error :" + e);
		}finally {
			try {
				
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null && conn.isClosed()) {
					conn.close();
				}
			
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		return resultChk;
	}
	
	// 학생 성적 수정
	public int updateScore(int scoreIdx, int updateScore) {
		int resultChk = 0;
		
		
			
			try {
				Class.forName(driver);
				conn = DriverManager.getConnection(db_url, "root", "1234");
				if(conn != null) {
					System.out.println("접속성공");
				}
			}catch(ClassNotFoundException e) {
				System.out.println("드라이버 로드 실패");
				e.printStackTrace();
			}catch(SQLException e) {
				System.out.println("접속 실패");
				e.printStackTrace();
			}
			
			try {
				// 쿼리 작성해서 입력
				String sql = "update tb_student_score \r\n"
						+ "set score_point = ?\r\n"
						+ "where score_idx = ?;";
				
				
				// ?값 입력
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, updateScore); // 위에 선언한 변수명 맞추기
				pstmt.setInt(2, scoreIdx);
				
				resultChk = pstmt.executeUpdate();
				
			}catch (SQLException e) {
				
				System.out.println("error :" + e);
			}finally {
				try {
					
					if(pstmt != null) {
						pstmt.close();
					}
					if(conn != null && conn.isClosed()) {
						conn.close();
					}
				
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			
		
		return resultChk;
	}
	
	// 학생 정보 삭제
	public int deleteStudent(int studentIdx) {
		int resultChk = 0;
		
		// 데이터 접속
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(db_url, "root", "1234");
			if(conn != null) {
				System.out.println("접속성공");
			}
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		}catch(SQLException e) {
			System.out.println("접속 실패");
			e.printStackTrace();
		}
		
		try {
			// 쿼리 작성해서 입력
			String sql = "delete from tb_student_info\r\n"
					+ "where student_idx = ?;";
			
			
			// ?값 입력
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, studentIdx); // 위에 선언한 변수명 맞추기
			
			
			resultChk = pstmt.executeUpdate();
			
		}catch (SQLException e) {
			
			System.out.println("error :" + e);
		}finally {
			try {
				
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null && conn.isClosed()) {
					conn.close();
				}
			
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return resultChk;
	}
	
	// 학생 성적 삭제
	public int deleteScore(HashMap<String, Object> paramMap) {
		int resultChk = 0;
		
		// 데이터 접속
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(db_url, "root", "1234");
			if(conn != null) {
				System.out.println("접속성공");
			}
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		}catch(SQLException e) {
			System.out.println("접속 실패");
			e.printStackTrace();
		}
		
		try {
			// 쿼리 작성해서 입력
			String sql = "delete from tb_student_score\r\n"
					+ "where score_season = ?\r\n"
					+ "and score_semester = ?\r\n"
					+ "and score_exam_type = ?\r\n"
					+ "and score_subject = ?\r\n"
					+ "and student_idx = ?;";
			
			
			// ?값 입력
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,paramMap.get("season").toString()); // 위에 선언한 변수명 맞추기
			pstmt.setInt(2,Integer.parseInt(paramMap.get("semester").toString()));
			pstmt.setString(3,paramMap.get("examType").toString());
			pstmt.setString(4,paramMap.get("subject").toString());
			pstmt.setInt(5,Integer.parseInt(paramMap.get("studentIdx").toString()));
			
			
			resultChk = pstmt.executeUpdate();
			
		}catch (SQLException e) {
			
			System.out.println("error :" + e);
		}finally {
			try {
				
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null && conn.isClosed()) {
					conn.close();
				}
			
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return resultChk;
	}
	
	// 성적 idx로 삭제
	public List<HashMap<String, Object>> printSearchScore (int studentIdx) {
		List<HashMap<String, Object>> scoreList = new ArrayList();
		
		// 데이터 접속
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(db_url, "root", "1234");
			if(conn != null) {
				System.out.println("접속성공");
			}
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		}catch(SQLException e) {
			System.out.println("접속 실패");
			e.printStackTrace();
		}
		
		try {
			
			String sql = "select score_idx,\r\n"
					+ "		score_season,\r\n"
					+ "       score_semester,\r\n"
					+ "        score_exam_type,\r\n"
					+ "        score_subject,\r\n"
					+ "        score_point\r\n"
					+ "from tb_student_score\r\n"
					+ "where student_idx = ?;";
			
			// 물음표값
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,studentIdx );
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				// 조회결과 HashMap에 추가
				HashMap<String, Object> rsMap = new HashMap<String, Object>();
				rsMap.put("score_idx", rs.getString("score_idx"));
				rsMap.put("score_season", rs.getString("score_season"));
				rsMap.put("score_semester", rs.getString("score_semester"));
				rsMap.put("score_exam_type", rs.getString("score_exam_type"));
				rsMap.put("score_subject", rs.getString("score_subject"));
				rsMap.put("score_point", rs.getString("score_point"));
				
				scoreList.add(rsMap);

			}
			
		}catch (SQLException e) {
			
			System.out.println("error :" + e);
		}finally {
			try {
				if(rs != null) {
					rs.close();
				}
				
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null && conn.isClosed()) {
					conn.close();
				}
			
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}	
		return scoreList;
	}

	public int deleteStudentScore(int studentIdx) {
		
		int resultChk = 0;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(db_url, "root", "1234");
			if(conn != null) {

			}
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		}catch(SQLException e) {
			System.out.println("접속 실패");
			e.printStackTrace();
		}
		
		try {
			String sql = "DELETE FROM tb_student_score\r\n"
					+ "WHERE student_idx =?;";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, studentIdx);
			
			resultChk = pstmt.executeUpdate();
			
		}catch (SQLException e) {
			
			System.out.println("error :" + e);
		}finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null && conn.isClosed()) {
					conn.close();
				}
			
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return resultChk;
	}

	public int selectStudentScoreCnt(int studentIdx) {
		int cnt = 0;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(db_url, "root", "1234");
			if(conn != null) {

			}
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		}catch(SQLException e) {
			System.out.println("접속 실패");
			e.printStackTrace();
		}
		
		try {
			String sql = "SELECT COUNT(student_idx) AS cnt "
					+ "FROM tb_student_score\r\n"
					+ "WHERE student_idx =?;";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, studentIdx);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				cnt = rs.getInt("cnt");
			}
			
			
		}catch (SQLException e) {
			
			System.out.println("error :" + e);
		}finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null && conn.isClosed()) {
					conn.close();
				}
			
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return cnt;
	}	
				
}
