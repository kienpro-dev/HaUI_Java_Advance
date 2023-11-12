package tx1.adcms;

import java.awt.*;
import java.awt.Font;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import tx1.object.ProductObject;
import tx1.process.Product;

import java.util.List;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.awt.event.ActionEvent;

import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ShowProduct extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable productTb;
	private JScrollPane scrollPane;
	private JLabel footerLb;
	private DefaultTableModel model;
	private JTextField findTf;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Product p = new Product();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShowProduct frame = new ShowProduct(p);
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
	public ShowProduct(Product p) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 667, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel tittleLb = new JLabel("Product Information");
		tittleLb.setForeground(SystemColor.textHighlight);
		tittleLb.setFont(new Font("Tahoma", Font.BOLD, 20));
		tittleLb.setBounds(59, 28, 276, 44);
		contentPane.add(tittleLb);
		
		
		String[] columnNames = new String[] {"", "ID", "Name", "Price", "Quantity", "Best Seller"};
		model = new DefaultTableModel(null, columnNames);
		
		List<ProductObject> list = p.getProductObjects((byte)10, null, null);
		
		
		productTb = new JTable();
		scrollPane = new JScrollPane();
		
		productTb.setModel(model);
		scrollPane.setViewportView(productTb);
		scrollPane.setBounds(59, 188, 533, 160);
		
		productTb.getColumnModel().getColumn(0).setCellRenderer(productTb.getDefaultRenderer(Boolean.class));
		productTb.getColumnModel().getColumn(0).setCellEditor(productTb.getDefaultEditor(Boolean.class));
		
		productTb.getColumnModel().getColumn(5).setCellRenderer(productTb.getDefaultRenderer(Boolean.class));
		
		
		for (ProductObject productObject : list) {
			model.addRow(new Object[]{false, productObject.getProduct_id(), productObject.getProduct_name(), productObject.getProduct_price(), 
					productObject.getProduct_total(), productObject.isProduct_best_seller()});
		}
		
		productTb.setBounds(59, 119, 533, 160);
        
		contentPane.add(scrollPane);
		
		
		footerLb = new JLabel("Created By Kyel");
		footerLb.setFont(new Font("Tahoma", Font.PLAIN, 14));
		footerLb.setHorizontalAlignment(SwingConstants.CENTER);
		footerLb.setBounds(258, 376, 135, 24);
		contentPane.add(footerLb);
		
		JButton deleteBtn = new JButton("Delete");
		deleteBtn.setForeground(SystemColor.text);
		deleteBtn.setBackground(SystemColor.textHighlight);
		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteSelectedRow(p);
				JOptionPane.showMessageDialog(scrollPane, "Success", "Delete products successfully!", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		deleteBtn.setBounds(503, 80, 89, 23);
		contentPane.add(deleteBtn);
		
		JButton addBtn = new JButton("Add");
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddProduct(p).setVisible(true);
				dispose();
			}
		});
		addBtn.setForeground(SystemColor.text);
		addBtn.setBackground(SystemColor.textHighlight);
		addBtn.setBounds(401, 80, 89, 23);
		contentPane.add(addBtn);
		
		JButton btnNewButton = new JButton("Export");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exportData();
			}
		});
		btnNewButton.setForeground(SystemColor.text);
		btnNewButton.setBackground(SystemColor.textHighlight);
		btnNewButton.setBounds(503, 359, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton statisticBtn = new JButton("Statistic");
		statisticBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showTable(p, "product_best_seller", "1");
			}
		});
		statisticBtn.setForeground(SystemColor.text);
		statisticBtn.setBackground(SystemColor.textHighlight);
		statisticBtn.setBounds(298, 80, 89, 23);
		contentPane.add(statisticBtn);
		
		JButton refreshBtn = new JButton("Refresh");
		refreshBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showTable(p, null, null);
			}
		});
		refreshBtn.setForeground(SystemColor.text);
		refreshBtn.setBackground(SystemColor.textHighlight);
		refreshBtn.setBounds(199, 80, 89, 23);
		contentPane.add(refreshBtn);
		
		findTf = new JTextField();
		findTf.setFont(new Font("Tahoma", Font.PLAIN, 14));
		findTf.setBounds(298, 127, 192, 20);
		contentPane.add(findTf);
		findTf.setColumns(10);
		
		JButton findBtn = new JButton("Find");
		findBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showTable(p, "product_name", "\"" + findTf.getText() + "\"");
			}
		});
		findBtn.setForeground(SystemColor.text);
		findBtn.setBackground(SystemColor.textHighlight);
		findBtn.setBounds(503, 126, 89, 23);
		contentPane.add(findBtn);
	}
	
	public void deleteSelectedRow(Product p) {
		DefaultTableModel model = (DefaultTableModel) productTb.getModel();
		for (int i = 0; i < model.getRowCount(); i++) {
		    Boolean value = (Boolean) model.getValueAt(i, 0);
		    if (value != null && value) {
		        p.deleteProduct((Integer) model.getValueAt(i, 1));
		    }
		}
	}
	
	public void showTable(Product p, String column, String condition) {
		DefaultTableModel model = (DefaultTableModel) productTb.getModel();
		model.setRowCount(0);
		List<ProductObject> products = p.getProductObjects((byte)10, column, condition);
		for (ProductObject productObject : products) {
			model.addRow(new Object[]{false, productObject.getProduct_id(), productObject.getProduct_name(), productObject.getProduct_price(), 
					productObject.getProduct_total(), productObject.isProduct_best_seller()});
		}
	}
	
	public void exportData() {
		JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx");
        fileChooser.setFileFilter(fileFilter);

        int returnValue = fileChooser.showSaveDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath() + ".xlsx";

            try (XSSFWorkbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Data");

                int rowCount = productTb.getRowCount();
                int columnCount = productTb.getColumnCount();

                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < columnCount; i++) {
                    String columnName = productTb.getColumnName(i);
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(columnName);
                }

                for (int i = 0; i < rowCount; i++) {
                    Row row = sheet.createRow(i + 1);
                    for (int j = 0; j < columnCount; j++) {
                        Object value = productTb.getValueAt(i, j);
                        Cell cell = row.createCell(j);
                        cell.setCellValue(String.valueOf(value));
                    }
                }

                try (FileOutputStream fos = new FileOutputStream(filePath)) {
                    workbook.write(fos);
                }
               
                JOptionPane.showMessageDialog(contentPane, "Success", "Export data successfully", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}
}
