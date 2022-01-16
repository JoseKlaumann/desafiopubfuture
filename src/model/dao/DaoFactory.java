package model.dao;

import db.DB;
import model.dao.impl.AccountDaoJDBC;
import model.dao.impl.ExpenditureDaoJDBC;
import model.dao.impl.RecipeDaoJDBC;

public class DaoFactory {

	public static RecipeDao createRecipeDao() {
		return new RecipeDaoJDBC(DB.getConnection());
	}
	
	public static ExpenditureDao createExpenditureDao() {
		return new ExpenditureDaoJDBC(DB.getConnection());
	}
	
	public static AccountDao createAccountDao() {
		return new AccountDaoJDBC(DB.getConnection());
	}
}
