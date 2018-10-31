package com.dn.dbex.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.sql.rowset.serial.SerialBlob;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.dn.dbex.domain.Photo;

import jdbc.JDBCUtils;
import jdbc.TxQueryRunner;

public class PhotoDao {
	private TxQueryRunner queryRunner = new TxQueryRunner();
	
	public void add(Photo photo) throws SQLException{
		String sql = "INSERT INTO emp_photo values(?,?,?)";
		
		Object[] params = {photo.getEmpno(),photo.getPhoto_format(),photo.getPicture()};
		
		queryRunner.update(sql, params);
	}
	
	public Vector<String> getColumnName(String table) throws SQLException, ClassNotFoundException{
		Connection con = JDBCUtils.getConnection();
		Statement stmt = con.createStatement();
		
		ResultSet rs = stmt.executeQuery("SELECT * FROM " + table);
		Vector<String> names = new Vector<String>();
		for(int i=1;i<=rs.getMetaData().getColumnCount();i++) {
			names.add(rs.getMetaData().getColumnName(i));
		}
		
		rs.close();
		stmt.close();
		JDBCUtils.releaseConnection(con);
		return names;
	}
	
	public List<Photo> list(String table) throws ClassNotFoundException, SQLException {
		String sql = "SELECT * FROM " + table;
		
		Connection connection = JDBCUtils.getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		List<Photo> lists = new ArrayList<Photo>();
		while(rs.next()) {
			Photo photo = new Photo();
			photo.setEmpno(rs.getString(1));
			photo.setPhoto_format(rs.getString(2));
			photo.setPicture(new SerialBlob(rs.getBlob(3)));
			
			lists.add(photo);
		}
		
		rs.close();
		pstmt.close();
		JDBCUtils.releaseConnection(connection);
		
		return lists;
//		return queryRunner.query(sql, new BeanListHandler<Photo>(Photo.class));
	}
	
	public void deleteAll(String table) {
		String sql = "DELETE FROM " + table;
		try {
			queryRunner.update(sql);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
