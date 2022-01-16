package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {

	//Objeto de conexao com o banco de dados do jdbc, iniciado com null apenas para ter algo
	private static Connection conn = null;  
	
	//Metodo para conectar com o banco de dados
	public static Connection getConnection () {
		//Caso ele ainda estiver valendo nulo, tenho que ter um codigo para que ele se conecte
		if (conn == null) {
			try {
				Properties props = loadProperties(); //Pego as propriedades do banco usando o metodo loadProperties
				String url = props.getProperty("dburl"); //Url do banco de dados, definido no arquivo db.properties
				conn = DriverManager.getConnection(url, props); //Obter a conexao com o banco
			}
			catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
		return conn;
	}
	
	//Metodo para fechar a conexao
	public static void closeConection() {
		//Testa se a conexao esta instanciada
		try {
			if (conn != null) {
				conn.close();//Se estiver aberto ele ira fechar
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
	}
	
	//Metodo para carregar as propriedades que estao definidas
	//no arquivo db.properties
	private static Properties loadProperties() {
		try(FileInputStream fs = new FileInputStream("db.properties")) {
			Properties props = new Properties();
			props.load(fs);
			return props;
		}
		catch (IOException e) {
			throw new DbException(e.getMessage());
		}
	}
	
	/*O Statement e o ResultSet sao recursos externos que nao sao controlados
	 * pela jvm do java, e importante fechar eles manualmente.
	 * E para nao precisar colocar o trycatch em cada um deles no momento que ira fechar 
	 * a conexao, eles serao implementados aqui tambem
	 */
	
	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	public static void closeResultSet (ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
}
