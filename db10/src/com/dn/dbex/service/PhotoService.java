package com.dn.dbex.service;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.sql.rowset.serial.SerialBlob;
import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;

import com.dn.dbex.dao.PhotoDao;
import com.dn.dbex.domain.Photo;
import com.dn.utils.CommonUtils;

public class PhotoService {
	private PhotoDao employeeDao = new PhotoDao();
	
	private Blob toBlob(String path) {
		File file = new File(path);
		try {
			byte[] bytes = FileUtils.readFileToByteArray(file);
			return new SerialBlob(bytes);
		} catch (IOException | SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void sqlInsert(Vector<Vector> lists) {
		employeeDao.deleteAll("emp_photo");
		for (Vector vector : lists) {
			try {
				String empno = (String) vector.get(0);
				String photo_format = (String) vector.get(1);
				
				Object data = vector.get(2);
				Blob picture = null;
				if(data instanceof SerialBlob) {
					picture = (Blob)data;
				}
				if(data instanceof String) {
					picture = toBlob((String)data);
				}
				
				Map map = new HashMap();
				map.put("empno",empno.equals("空")?null:empno);
				map.put("photo_format",photo_format.equals("空")?null:photo_format);
				map.put("picture",picture.equals("空")?null:picture);
				Photo employee = CommonUtils.toBean(map, Photo.class);
				employeeDao.add(employee);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public Vector<String> getColumnName(String table){
		try {
			return employeeDao.getColumnName(table);
		} catch (ClassNotFoundException | SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Vector<Vector> listDatas(String table) throws ClassNotFoundException, SQLException {
		//将Bean的Vector转化为Vector的Vector返回
		Vector<Photo> employees = new Vector<Photo>(employeeDao.list(table));
		Vector<Vector> lists = new Vector<Vector>();
		for(int i=0;i<employees.size();i++) {
			Vector list = new Vector();
			Photo employee = employees.get(i);
			list.add(employee.getEmpno());
			list.add(employee.getPhoto_format());
			list.add(employee.getPicture());
			for(int j=0;j<list.size();j++) {
				if(list.get(j) == null) {
					list.set(j, "空");
				}
			}
			lists.add(list);
		}
		return lists;
	}
}
