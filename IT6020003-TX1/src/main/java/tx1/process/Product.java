package tx1.process;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tx1.ConnectionPool;
import tx1.ConnectionPoolImpl;
import tx1.object.ProductObject;

public class Product {
	private Connection connection;

	private ConnectionPool cp;

	public Product() {
		this.cp = new ConnectionPoolImpl();

		try {
			this.connection = this.cp.getConnection("Product");
			if (this.connection.getAutoCommit()) {
				this.connection.setAutoCommit(false);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<ProductObject> getProductObjects(byte total, String column, String condition) {
		List<ProductObject> result = new ArrayList<>();
		ProductObject item;
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT * FROM tblproduct ");
		
		if(column != null && condition != null) {
			sqlBuilder.append("WHERE " + column + " = " + condition + " ");
		}

		sqlBuilder.append("ORDER BY product_id ASC ");
		sqlBuilder.append("LIMIT ? ");
		
		try {
			PreparedStatement statement = this.connection.prepareStatement(sqlBuilder.toString());
			statement.setByte(1, total);

			ResultSet rs = statement.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					item = new ProductObject();
					item.setProduct_id(rs.getInt("product_id"));
					item.setProduct_price(rs.getInt("product_price"));
					item.setProduct_discount_price(rs.getInt("product_discount_price"));
					item.setProduct_visited(rs.getInt("product_visited"));
					item.setProduct_total(rs.getInt("product_total"));
					item.setProduct_manager_id(rs.getInt("product_manager_id"));
					item.setProduct_pc_id(rs.getInt("product_pc_id"));
					item.setProduct_pg_id(rs.getInt("product_pg_id"));
					item.setProduct_ps_id(rs.getInt("product_ps_id"));
					item.setProduct_promotion_price(rs.getInt("product_promotion_price"));
					item.setProduct_sold(rs.getInt("product_sold"));
					item.setProduct_price_calc_description(rs.getInt("product_price_calc_description"));
					item.setProduct_customer_id(rs.getInt("product_customer_id"));
					item.setProduct_perspective_id(rs.getInt("product_perspective_id"));
					item.setProduct_name(rs.getString("product_name"));
					item.setProduct_image(rs.getString("product_image"));
					item.setProduct_intro(rs.getString("product_intro"));
					item.setProduct_notes(rs.getString("product_notes"));
					item.setProduct_code(rs.getString("product_code"));
					item.setProduct_created_date(rs.getString("product_created_date"));
					item.setProduct_modified_date(rs.getString("product_modified_date"));
					item.setProduct_deleted_date(rs.getString("product_deleted_date"));
					item.setProduct_deleted_author(rs.getString("product_deleted_author"));
					item.setProduct_size(rs.getString("product_size"));
					item.setProduct_name_en(rs.getString("product_name_en"));
					item.setProduct_enable(rs.getInt("product_enable") == 1 ? true : false);
					item.setProduct_delete(rs.getInt("product_delete") == 1 ? true : false);
					item.setProduct_is_detail(rs.getInt("product_is_detail") == 1 ? true : false);
					item.setProduct_best_seller(rs.getInt("product_best_seller") == 1 ? true : false);
					item.setProduct_promotion(rs.getInt("product_promotion") == 1 ? true : false);
					result.add(item);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				this.connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

		return result;
	}

	public boolean addProduct(ProductObject item) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("INSERT INTO tblproduct(");
		sqlBuilder.append("product_id, product_name, product_image, product_price, product_discount_price, ");
		sqlBuilder.append("product_enable, product_delete, product_visited, product_total, product_manager_id, ");
		sqlBuilder.append("product_intro, product_notes, product_code, product_created_date, product_modified_date, ");
		sqlBuilder.append("product_pc_id, product_pg_id, product_ps_id, product_is_detail, product_deleted_date, ");
		sqlBuilder.append("product_deleted_author, product_promotion_price, product_sold, product_best_seller, ");
		sqlBuilder.append(
				"product_promotion, product_price_calc_description, product_size, product_name_en, product_customer_id, product_perspective_id) ");
		sqlBuilder.append("VALUE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

		try {
			PreparedStatement statement = this.connection.prepareStatement(sqlBuilder.toString());
			statement.setInt(1, item.getProduct_id());
			statement.setInt(4, item.getProduct_price());
			statement.setInt(5, item.getProduct_discount_price());
			statement.setInt(8, item.getProduct_visited());
			statement.setInt(9, item.getProduct_total());
			statement.setInt(10, item.getProduct_manager_id());
			statement.setInt(16, item.getProduct_pc_id());
			statement.setInt(17, item.getProduct_pg_id());
			statement.setInt(18, item.getProduct_ps_id());
			statement.setInt(22, item.getProduct_promotion_price());
			statement.setInt(23, item.getProduct_sold());
			statement.setInt(26, item.getProduct_price_calc_description());
			statement.setInt(29, item.getProduct_customer_id());
			statement.setInt(30, item.getProduct_perspective_id());
			statement.setString(2, item.getProduct_name());
			statement.setString(3, item.getProduct_image());
			statement.setString(11, item.getProduct_intro());
			statement.setString(12, item.getProduct_notes());
			statement.setString(13, item.getProduct_code());
			statement.setString(14, item.getProduct_created_date());
			statement.setString(15, item.getProduct_modified_date());
			statement.setString(20, item.getProduct_deleted_date());
			statement.setString(21, item.getProduct_deleted_author());
			statement.setString(27, item.getProduct_size());
			statement.setString(28, item.getProduct_name_en());
			statement.setInt(6, item.isProduct_enable() == true ? 1 : 0);
			statement.setInt(7, item.isProduct_delete() == true ? 1 : 0);
			statement.setInt(19, item.isProduct_is_detail() == true ? 1 : 0);
			statement.setInt(24, item.isProduct_best_seller() == true ? 1 : 0);
			statement.setInt(25, item.isProduct_promotion() == true ? 1 : 0);

			int result = statement.executeUpdate();

			if (result == 0) {
				this.connection.rollback();
				return false;
			}

			this.connection.commit();
			return true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				this.connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return true;
	}

	public boolean deleteProduct(int productId) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("DELETE FROM tblproduct ");
		sqlBuilder.append("WHERE product_id = ?");

		try {
			PreparedStatement statement = this.connection.prepareStatement(sqlBuilder.toString());
			statement.setInt(1, productId);

			int result = statement.executeUpdate();

			if (result == 0) {
				this.connection.rollback();
				return false;
			}

			this.connection.commit();
			return true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				this.connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		Product section = new Product();
		
		ProductObject nsecObject = new ProductObject();
		nsecObject.setProduct_name("LT JAVA");
		nsecObject.setProduct_created_date("27/10/23");
		
		if(!section.addProduct(nsecObject)) {
			System.out.println("ERROR");
		}
		
		List<ProductObject> items = section.getProductObjects((byte) 15, null, null);
		
		items.forEach(item->{
			System.out.println(item);
		});
	}
}
