package emaillist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import emaillist.vo.EmaillistVo;

public class EmaillistDao {
	public Boolean insert(EmaillistVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();

			// 3. Statement 준비하기
			String sql = " insert" + "  into emaillist" + " values (null, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);

			// 4. Parameter Binding
			pstmt.setString(1, vo.getFirstName());
			pstmt.setString(2, vo.getLastName());
			pstmt.setString(3, vo.getEmail());

			// 5. SQL 실행
			// binding하여 jdbc로 보냈기 때문에 jdbc에서 보내도록 해야한다
			int count = pstmt.executeUpdate();

			result = count == 1;
			// System.out.println(result);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public Boolean deleteByEmail(String email) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();

			// 3. Statement 준비하기
			String sql = " delete" + "  from emaillist" + " where email = ?";
			pstmt = conn.prepareStatement(sql);

			// 4. Parameter Binding
			pstmt.setString(1, email);

			// 5. SQL 실행
			// binding하여 jdbc로 보냈기 때문에 jdbc에서 보내도록 해야한다
			// executeUpdate -> 수정된 행의 개수 반환
			int count = pstmt.executeUpdate();

			result = count == 1;
			// System.out.println(result);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public Long count() {
		Long result = 0L;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();

			// 3. Statement 준비하기
			String sql = "select count(*) from emaillist";
			pstmt = conn.prepareStatement(sql);

			// 5. SQL 실행
			// binding하여 jdbc로 보냈기 때문에 jdbc에서 보내도록 해야한다
			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getLong(1);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public List<EmaillistVo> findAll() {
		List<EmaillistVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();

			// 3. Statement 준비하기
			String sql = "select id, first_name, last_name, email" + " from emaillist" + " order by id desc";
			pstmt = conn.prepareStatement(sql);

			// 5. SQL 실행
			// binding하여 jdbc로 보냈기 때문에 jdbc에서 보내도록 해야한다
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Long id = rs.getLong(1);
				String firstName = rs.getString(2);
				String lastName = rs.getString(3);
				String email = rs.getString(4);
				EmaillistVo vo = new EmaillistVo();
				
				vo.setId(id);
				vo.setFirstName(firstName);
				vo.setLastName(lastName);
				vo.setEmail(email);
				
				result.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			// 1. JDBC Driver 로딩
			Class.forName("org.mariadb.jdbc.Driver");

			// 2. 연결하기
			String url = "jdbc:mariadb://192.168.0.12:3306/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}

		return conn;
	}

}
