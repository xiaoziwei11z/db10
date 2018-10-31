package com.dn.db10.client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.dn.dbex.service.PhotoService;


import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Blob;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class GUI extends JFrame {
	private PhotoService employeeService = new PhotoService();
	private static Vector<Vector> datas = new Vector<Vector>();
	private static Vector<String> names = new Vector<String>();
	private static DefaultTableModel model = new DefaultTableModel();
	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 721, 583);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		try {
			datas = employeeService.listDatas("emp_photo");
		} catch (ClassNotFoundException | SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		names = employeeService.getColumnName("emp_photo");
		model.setDataVector(datas, names);
		table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		
		JLabel lblupdatedelete = new JLabel("采用UPDATE和DELETE来编辑这些结果");
		
		JButton button = new JButton("添加行");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Vector row = new Vector();
				String text1 = new String();
				row.add(text1);
				String text2 = new String();
				row.add(text2);
				String text3 = new String();
				row.add(text3);
				datas.add(row);
				model.setDataVector(datas, names);
			}
		});
		
		JButton button_1 = new JButton("删除行");
		
		JButton button_2 = new JButton("落实");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				employeeService.sqlInsert(datas);
			}
		});
		
		JButton button_3 = new JButton("回滚");
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 574, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(button_1)
								.addComponent(button)))
						.addComponent(lblupdatedelete)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(button_2)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(button_3)))
					.addContainerGap(32, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(7)
					.addComponent(lblupdatedelete)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(button)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(button_1))
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 445, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(button_2)
						.addComponent(button_3))
					.addContainerGap(32, Short.MAX_VALUE))
		);
		
		contentPane.setLayout(gl_contentPane);
	}
}
