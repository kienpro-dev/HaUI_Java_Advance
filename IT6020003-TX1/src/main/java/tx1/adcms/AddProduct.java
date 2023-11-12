package tx1.adcms;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import tx1.object.ProductObject;
import tx1.process.Product;

public class AddProduct extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField idTf;
	private JTextField nameTf;
	private JTextField priceTf;
	private JTextField quantityTf;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Product p = new Product();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddProduct frame = new AddProduct(p);
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
	public AddProduct(Product p) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 436, 513);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel addLb = new JLabel("Add New Product");
		addLb.setHorizontalAlignment(SwingConstants.CENTER);
		addLb.setForeground(SystemColor.textHighlight);
		addLb.setFont(new Font("Tahoma", Font.BOLD, 20));
		addLb.setBounds(71, 11, 277, 61);
		contentPane.add(addLb);
		
		JLabel idLb = new JLabel("ID:");
		idLb.setFont(new Font("Tahoma", Font.BOLD, 14));
		idLb.setBounds(45, 72, 108, 31);
		contentPane.add(idLb);
		
		idTf = new JTextField();
		idTf.setFont(new Font("Tahoma", Font.PLAIN, 14));
		idTf.setBounds(119, 72, 229, 31);
		contentPane.add(idTf);
		idTf.setColumns(10);
		
		JLabel nameLb = new JLabel("Name:");
		nameLb.setFont(new Font("Tahoma", Font.BOLD, 14));
		nameLb.setBounds(45, 163, 108, 31);
		contentPane.add(nameLb);
		
		nameTf = new JTextField();
		nameTf.setFont(new Font("Tahoma", Font.PLAIN, 14));
		nameTf.setColumns(10);
		nameTf.setBounds(119, 163, 229, 31);
		contentPane.add(nameTf);
		
		JLabel priceLb = new JLabel("Price:");
		priceLb.setFont(new Font("Tahoma", Font.BOLD, 14));
		priceLb.setBounds(45, 254, 108, 31);
		contentPane.add(priceLb);
		
		priceTf = new JTextField();
		priceTf.setFont(new Font("Tahoma", Font.PLAIN, 14));
		priceTf.setColumns(10);
		priceTf.setBounds(119, 254, 229, 31);
		contentPane.add(priceTf);
		
		JLabel quantityLb = new JLabel("Quantity:");
		quantityLb.setFont(new Font("Tahoma", Font.BOLD, 14));
		quantityLb.setBounds(45, 345, 108, 31);
		contentPane.add(quantityLb);
		
		quantityTf = new JTextField();
		quantityTf.setFont(new Font("Tahoma", Font.PLAIN, 14));
		quantityTf.setColumns(10);
		quantityTf.setBounds(119, 345, 229, 31);
		contentPane.add(quantityTf);
		
		JCheckBox sellerCb = new JCheckBox("Best seller");
		sellerCb.setFont(new Font("Tahoma", Font.BOLD, 14));
		sellerCb.setBounds(147, 391, 129, 23);
		contentPane.add(sellerCb);
		
		JButton btnNewButton = new JButton("Add");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Integer id = null;
				String name = "";
				Integer price = null;
				Integer quantity = null;
				boolean bestSeller = false;
				
				try {
					id = Integer.parseInt(idTf.getText());
					name = nameTf.getText();
					price = Integer.parseInt(priceTf.getText());
					quantity = Integer.parseInt(quantityTf.getText());
					bestSeller = sellerCb.isSelected();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
				if(id != null && !"".equalsIgnoreCase(name) && price != null && quantity != null) {
					ProductObject item = new ProductObject();
					item.setProduct_id(id);
					item.setProduct_name(name);
					item.setProduct_price(price);
					item.setProduct_total(quantity);
					item.setProduct_best_seller(bestSeller);
					if(!p.addProduct(item)) {
						JOptionPane.showMessageDialog(contentPane, "Error", "Add product error", JOptionPane.ERROR_MESSAGE);
					} else {
						idTf.setText("");
						nameTf.setText("");
						priceTf.setText("");
						quantityTf.setText("");
						sellerCb.setSelected(false);
						JOptionPane.showMessageDialog(contentPane, "Success", "Add product successfully", JOptionPane.INFORMATION_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(contentPane, "Error", "Error input", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnNewButton.setForeground(SystemColor.text);
		btnNewButton.setBackground(SystemColor.textHighlight);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton.setBounds(321, 440, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton backBtn = new JButton("Back");
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ShowProduct(p).setVisible(true);
				dispose();
			}
		});
		backBtn.setForeground(SystemColor.text);
		backBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		backBtn.setBackground(SystemColor.textHighlight);
		backBtn.setBounds(210, 442, 89, 23);
		contentPane.add(backBtn);
	}
}
