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
import model.dao.RecipeDao;
import model.entities.Recipe;

public class RecipeDaoJDBC implements RecipeDao {

	// Cria uma conexao com o banco e eu posso usar na classe toda
	private Connection conn;

	public RecipeDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	// Cadastra novas receitas
	@Override
	public void insert(Recipe obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO receitas " 
			        + "(valor, datarecebimento, datarecebimentoesperado, "
				    + "descricao, conta, tiporeceita) " 
			        + "VALUES " 
				    + "(?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setFloat(1, obj.getValor());
			st.setString(2, obj.getDataRecebimento());
			st.setString(3, obj.getDataRecebimentoEsperado());
			st.setString(4, obj.getDescricao());
			st.setInt(5, obj.getConta());
			st.setString(6, obj.getTipoReceita());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			} 
			else {
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

	// Edita as receitas
	@Override
	public void update(Recipe obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE receitas " 
			        + "SET valor = ?, datarecebimento = ?, datarecebimentoesperado = ?, "
					+ "descricao = ?, conta = ?, tiporeceita = ? "
					+ "WHERE " 
				    + "Id = ?");

			st.setFloat(1, obj.getValor());
			st.setString(2, obj.getDataRecebimento());
			st.setString(3, obj.getDataRecebimentoEsperado());
			st.setString(4, obj.getDescricao());
			st.setInt(5, obj.getConta());
			st.setString(6, obj.getTipoReceita());
			st.setInt(7, obj.getId());

			st.executeUpdate();

		} 
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
	}

	// Remove receitas
	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM receitas WHERE Id = ?");

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
	public Recipe findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * " 
			       + "FROM receitas " 
				   + "WHERE Id = ?");

			st.setInt(1, id);
			rs = st.executeQuery();
			
			if (rs.next()) {
				Recipe rec = instantiateRecipe(rs);
				return rec;
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

	// Filtra as receitas pelas datas
	@Override
	public List<Recipe> findByDate(String dataInicial, String dataFinal) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * " 
			        + "FROM receitas " 
				    + "WHERE datarecebimento " 
			        + "BETWEEN (?) " 
				    + "AND (?)");
			
			st.setString(1, dataInicial);
			st.setString(2, dataFinal);
			rs = st.executeQuery();

			List<Recipe> list = new ArrayList<>();

			while (rs.next()) {
				Recipe rec = instantiateRecipe(rs);

				list.add(rec);
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

	// Filtra pelo tipo da receita
	@Override
	public List<Recipe> findByRecipe(String tipoReceita) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * "
		            + "FROM receitas "
                    + "WHERE tiporeceita = ? ");
			
			st.setString(1, tipoReceita);
			rs = st.executeQuery();

			List<Recipe> list = new ArrayList<>();

			while (rs.next()) {
				Recipe rec = instantiateRecipe(rs);

				list.add(rec);
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

	// Lista todas as receitas
	@Override
	public List<Recipe> findAllRecipes() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * " 
			        + "FROM receitas "
                    + "ORDER BY datarecebimento");
			
			rs = st.executeQuery();

			List<Recipe> list = new ArrayList<>();

			while (rs.next()) {
				Recipe rec = instantiateRecipe(rs);

				list.add(rec);
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
	private Recipe instantiateRecipe(ResultSet rs) throws SQLException {
		Recipe rec = new Recipe();
		rec.setId(rs.getInt("id"));
		rec.setValor(rs.getFloat("valor"));
		rec.setDataRecebimento(rs.getString("datarecebimento"));
		rec.setDataRecebimentoEsperado(rs.getString("datarecebimentoesperado"));
		rec.setDescricao(rs.getString("descricao"));
		rec.setConta(rs.getInt("conta"));
		rec.setTipoReceita(rs.getString("tiporeceita"));
		return rec;
	}
}
