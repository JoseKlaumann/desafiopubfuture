package model.dao;

import java.util.List;

import model.entities.Recipe;

public interface RecipeDao {

	void insert               (Recipe obj);//Cadastra receitas
	void update               (Recipe obj);//Edita receitas
	void deleteById           (Integer id);//Deleta receitas
	Recipe findById           (Integer id);//Encontra a receita pelo numero de cadastro dela
	List<Recipe> findByDate   (String dataInicial, String dataFinal);//Filtra por datas
	List<Recipe> findByRecipe (String tipoReceita);//Filtra por tipo de receita
	List<Recipe>              findAllRecipes();//Total de receitas
}
