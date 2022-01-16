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
import model.dao.AccountDao;
import model.entities.Account;

public class AccountDaoJDBC implements AccountDao{

	// Cria uma conexao com o banco e eu posso usar na classe toda
	private Connection conn;

	public AccountDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	//Adiciona uma nova conta 
	@Override
	public void insert(Account obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO contas "
					+ "(saldo, tipoconta, instituicaofinanceira) "
					+ " VALUES "
					+ "(?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setFloat(1, obj.getSaldo());
			st.setString(2, obj.getTipoConta());
			st.setString(3, obj.getInstituicaoFinanceira());
			
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	//Atualiza a conta 
	@Override
	public void update(Account obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE contas "
					+ "SET saldo = ?, tipoconta = ?, instituicaofinanceira = ? "
					+ "WHERE "
					+ "Id = ?");
			
			st.setFloat(1, obj.getSaldo());
			st.setString(2, obj.getTipoConta());
			st.setString(3, obj.getInstituicaoFinanceira());
			st.setInt(4, obj.getId());
			
			st.executeUpdate();
			
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	//Deleta um dado do banco
	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM contas WHERE Id = ?");
			
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
	public Account findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * "
					+ "FROM contas "
					+ "WHERE Id = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if (rs.next()) {
				Account acc = instantiateAccount(rs);
				return acc;
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
	
	//Encontra todas as informacoes
	@Override
	public List<Account> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * "
					+ "FROM contas "
				    + "ORDER BY instituicaofinanceira");
			
			rs = st.executeQuery();
			
			List<Account> list = new ArrayList<>();
			
			while(rs.next()) {
				Account acc = instantiateAccount(rs);
				
				list.add(acc);
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

	//Encontra as somas vinculadas a cada instituicao financeira
	@Override
	public List<Account> findBalance() { //usar soma
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT instituicaofinanceira, "
					+ "SUM(contas.saldo) " 
					+ "FROM contas "
					+ "GROUP BY instituicaofinanceira");
			
			rs = st.executeQuery();
			
			List<Account> list = new ArrayList<>();
			
			while (rs.next()) {
				Account acc = new Account();
				acc.setSaldo(rs.getFloat(2));
				acc.setInstituicaoFinanceira(rs.getString("instituicaofinanceira"));
				
				list.add(acc);
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
	private Account instantiateAccount (ResultSet rs) throws SQLException {
		Account acc = new Account();
		acc.setId(rs.getInt("Id"));
		acc.setSaldo(rs.getFloat("saldo"));
		acc.setTipoConta(rs.getString("tipoconta"));
		acc.setInstituicaoFinanceira(rs.getString("instituicaofinanceira"));
		return acc;
	}
}
