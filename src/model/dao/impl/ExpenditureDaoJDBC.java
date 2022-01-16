package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.ExpenditureDao;
import model.entities.Expenditure;

public class ExpenditureDaoJDBC implements ExpenditureDao {

	// Cria uma conexao com o banco e eu posso usar na classe toda
	private Connection conn;

	public ExpenditureDaoJDBC(Connection conn) {
			this.conn = conn;
		}
    
	//Cadastra novas despesas
	@Override
	public void insert(Expenditure obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO despesas "
					+ "(valor, datapagamento, datapagamentoesperado, "
				    + "tipodespesa, conta) "
					+ "VALUES "
				    + "(?, ?, ?, ?, ?)",
				    Statement.RETURN_GENERATED_KEYS);
			
			st.setFloat(1, obj.getValor());
			st.setString(2, obj.getDataPagamento());
			st.setString(3, obj.getDataPagamentoEsperado());
			st.setString(4, obj.getTipoDespesa());
			st.setInt(5, obj.getConta());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Erro inesperado! Nenhuma linha foi preenchida!");
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	//Edita as despesas
	@Override
	public void update(Expenditure obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE despesas "
					+ "SET valor = ?, datapagamento = ?, datapagamentoesperado = ?, "
				    + "tipodespesa = ?, conta = ? "
					+ "WHERE "
				    + "Id = ?");
			
			st.setFloat(1, obj.getValor());
			st.setString(2, obj.getDataPagamento());
			st.setString(3, obj.getDataPagamentoEsperado());
			st.setString(4, obj.getTipoDespesa());
			st.setInt(5, obj.getConta());
			st.setInt(6, obj.getId());
			
			st.executeUpdate();
			
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	//Remove despesas
	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM despesas WHERE Id = ?");
		    
			st.setInt(1, id);
		
			st.executeUpdate();
		}
        catch (SQLException e) {
        	throw new DbException(e.getMessage());
        }
		finally {
		    DB.closeStatement(st);
		}
	}

	// Achei necessario ter esse metodo a mais para conseguir atualizar os dados com
	// mais facilidade e pontualmente
	@Override
	public Expenditure findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * "
					+ "FROM despesas "
					+ "WHERE Id = ?");
					
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				Expenditure exp = instantiateExpenditure(rs);
				return exp;
			}
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	//Filtra as despesas pelas datas
	@Override
	public List<Expenditure> findByDate(String dataInicial, String dataFinal) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * "
					+ "FROM despesas "
					+ "WHERE datapagamento "
					+ "BETWEEN (?) "
					+ "AND (?)");
					
			st.setString(1, dataInicial);
			st.setString(2, dataFinal);
			rs = st.executeQuery();
			
			List<Expenditure> list = new ArrayList<>();
			
			while(rs.next()) {
				Expenditure exp = instantiateExpenditure(rs);
				
				list.add(exp);
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	//Filtra pelo tipo de despesa
	@Override
	public List<Expenditure> findByExpend(String tipoDespesa) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * "
					+ "FROM despesas "
					+ "WHERE tipodespesa = ?");
					
			st.setString(1, tipoDespesa);
			rs = st.executeQuery();
			
			List<Expenditure> list = new ArrayList<>();
			
			while(rs.next()) {
				Expenditure exp = instantiateExpenditure(rs);
				
				list.add(exp);
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	//Lista todas as despesas
	@Override
	public List<Expenditure> findAllExpenditures() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * "
					+ "FROM despesas "
					+ "ORDER BY datapagamento");
					
			rs = st.executeQuery();
			
			List<Expenditure> list = new ArrayList<>();
			
			while(rs.next()) {
				Expenditure exp = instantiateExpenditure(rs);
				
				list.add(exp);
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
 
	// Criando este metodo eu posso reaproveitar o codigo
	// e deixar o codigo mais limpo
	private Expenditure instantiateExpenditure(ResultSet rs) throws SQLException {
		Expenditure exp = new Expenditure();
		exp.setId(rs.getInt("id"));
		exp.setValor(rs.getFloat("valor"));
		exp.setDataPagamento(rs.getString("datapagamento"));
		exp.setDataPagamentoEsperado(rs.getString("datapagamentoesperado"));
		exp.setTipoDespesa(rs.getString("tipodespesa"));
		exp.setConta(rs.getInt("conta"));
		return exp;
	}

}
