package tx1.adcms;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import tx1.object.ProductObject;
import tx1.process.Product;

import java.util.List;

public class ShowProduct extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable productTb;
	private JLabel footerLb;
	private DefaultTableModel model;

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
		setBounds(100, 100, 667, 381);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel tittleLb = new JLabel("Product Information");
		tittleLb.setForeground(Color.ORANGE);
		tittleLb.setFont(new Font("Tahoma", Font.BOLD, 20));
		tittleLb.setBounds(58, 51, 276, 52);
		contentPane.add(tittleLb);
		
		
		String[] columnNames = {"", "ID", "Name", "Price", "Quantity", "Best Seller"};
		model = new DefaultTableModel(columnNames, 0);
		
		List<ProductObject> list = p.getProductObjects((byte)10, null, null);
		
		
		productTb = new JTable(model);
		
		productTb.getColumnModel().getColumn(0).setCellRenderer(productTb.getDefaultRenderer(Boolean.class));
		productTb.getColumnModel().getColumn(0).setCellEditor(productTb.getDefaultEditor(Boolean.class));
		
		
		for (ProductObject productObject : list) {
			model.addRow(new Object[]{false, productObject.getProduct_id(), productObject.getProduct_name(), productObject.getProduct_price(), 
					productObject.getProduct_total(), productObject.isProduct_best_seller()});
		}
		
		productTb.setBounds(59, 119, 533, 160);
        
		contentPane.add(productTb);
		
		
		footerLb = new JLabel("Created By Kyel");
		footerLb.setFont(new Font("Tahoma", Font.PLAIN, 14));
		footerLb.setHorizontalAlignment(SwingConstants.CENTER);
		footerLb.setBounds(258, 307, 135, 24);
		contentPane.add(footerLb);
	}
	
//	private void exportToExcel() {
//        // Tạo JFileChooser để người dùng chọn đường dẫn và tên file
//        JFileChooser fileChooser = new JFileChooser();
//        FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx");
//        fileChooser.setFileFilter(fileFilter);
//
//        int returnValue = fileChooser.showSaveDialog(this);
//        if (returnValue == JFileChooser.APPROVE_OPTION) {
//            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
//
//            try (Workbook workbook = new XSSFWorkbook()) {
//                Sheet sheet = workbook.createSheet("Data");
//
//                int rowCount = table.getRowCount();
//                int columnCount = table.getColumnCount();
//
//                Row headerRow = sheet.createRow(0);
//                for (int i = 0; i < columnCount; i++) {
//                    String columnName = table.getColumnName(i);
//                    Cell cell = headerRow.createCell(i);
//                    cell.setCellValue(columnName);
//                }
//
//                for (int i = 0; i < rowCount; i++) {
//                    Row row = sheet.createRow(i + 1);
//                    for (int j = 0; j < columnCount; j++) {
//                        Object value = table.getValueAt(i, j);
//                        Cell cell = row.createCell(j);
//                        cell.setCellValue(String.valueOf(value));
//                    }
//                }
//
//                try (FileOutputStream fos = new FileOutputStream(filePath)) {
//                    workbook.write(fos);
//                }
//
//                System.out.println("Xuất file Excel thành công!");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
