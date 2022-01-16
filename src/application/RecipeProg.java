package application;

import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.RecipeDao;
import model.entities.Recipe;

public class RecipeProg {

	public static void main(String[] args) throws ParseException {
			
		Scanner sc = new Scanner(System.in);
		
		RecipeDao recipeDao = DaoFactory.createRecipeDao(); //Cria a conexao com o banco
		
		System.out.println("=== Test 1: recipe findAllRecipes ===");//Listar total de receitas
		System.out.print("Voce deseja listar todas as receitas? (S/N)");
		char consultar = sc.next().toUpperCase().charAt(0);
		
		if (consultar == 'S') {
			List<Recipe> recipe = recipeDao.findAllRecipes();
			
			for(Recipe obj : recipe) {
				System.out.println(obj);
			}
		}
		
		System.out.println("\n=== Test 2: recipe findByRecipe ==="); //Filtro por tipo de receita
		System.out.print("Voce deseja fazer uma consulta pelo tipo de receita? (S/N)");
		consultar = sc.next().toUpperCase().charAt(0);
		
		if (consultar == 'S') {
			System.out.print("Por qual tipo de receita voce deseja pesquisar? ");
			String tipoReceita = sc.next();
			
			List<Recipe> recipe = recipeDao.findByRecipe(tipoReceita);
			for(Recipe obj : recipe) {
				System.out.println(obj);
			}
		}
		
		System.out.println("\n=== Test 3: recipe findByDate ===");//Filtro por período (dataInicial – dataFinal)
		System.out.print("Voce deseja fazer uma consulta por datas? (S/N)");
		consultar = sc.next().toUpperCase().charAt(0);
		
		if (consultar == 'S') {
			System.out.print("Entre com a data inicial (yyyymmdd): ");
			String dataInicial = sc.next();
			System.out.print("Entre com a data final (yyyymmdd): ");
			String dataFinal = sc.next();
			
			List<Recipe> recipe = recipeDao.findByDate(dataInicial, dataFinal);
			for (Recipe obj : recipe) {
				System.out.println(obj);
			}
		}
		
		System.out.println("\n=== Test 4: recipe insert ===");//Cadastrar receitas
		System.out.print("Voce deseja cadastrar uma nova receita? (S/N)");
		consultar = sc.next().toUpperCase().charAt(0);
		
		if (consultar == 'S') {
			System.out.print("Qual o valor? ");
			float valor = sc.nextFloat();
			System.out.print("Data de recebimento (yyyymmdd): ");
			String dataRecebimento = sc.next();
			System.out.print("Data de recebimento esperado (yyyymmdd): ");
			String dataRecebimentoEsperado = sc.next();
			System.out.print("Descricao: ");//deve ser preenchido sem espaços para que sejam cadastrados no banco.
			String descricao = sc.next().toLowerCase();
			sc.nextLine();
			System.out.print("Numero da conta: ");
			int conta = sc.nextInt();
			System.out.print("Tipo de receita (salário, presente, prêmio, outros): ");
			String tipoReceita = sc.next();
			
			Recipe newRecipe = new Recipe(null, valor, dataRecebimento, dataRecebimentoEsperado, descricao, conta, tipoReceita);
		    recipeDao.insert(newRecipe);
		    System.out.println("Iserido! Id da nova receita = " + newRecipe.getId());
		}
		
	    System.out.println("\n=== Test 5: recipe update ===");//Editar receitas
	    System.out.print("Voce deseja editar alguma receita? (S/N)");
	    consultar = sc.next().toUpperCase().charAt(0);
	    
	    if(consultar == 'S') {
	    	System.out.print("Entre com o numero do id que deseja editar: ");
	    	int id = sc.nextInt();
	    	Recipe newRecipe = recipeDao.findById(id);
	        System.out.println("\nCaso queira editar apenas uma das linhas, "
	        		+ "repita os valores que ja estao cadastrados e altere apenas aquele que desejar");
	        System.out.print("Qual o valor? ");
	        newRecipe.setValor(sc.nextFloat());
			System.out.print("Data de recebimento (yyyymmdd): ");
			newRecipe.setDataRecebimento(sc.next());
			System.out.print("Data de recebimento esperado (yyyymmdd): ");
			newRecipe.setDataRecebimentoEsperado(sc.next());
			System.out.print("Descricao: ");//deve ser preenchido sem espaços para que sejam cadastrados no banco.
			newRecipe.setDescricao(sc.next().toLowerCase());
			sc.nextLine();
			System.out.print("Numero da conta: ");			
			newRecipe.setConta(sc.nextInt());
			System.out.print("Tipo de receita (salário, presente, prêmio, outros): ");
			newRecipe.setTipoReceita(sc.next());
			
		    recipeDao.update(newRecipe);
		    System.out.println("Edicao completa!");
	    }
	    
	    System.out.println("\n=== Test 6: recipe deleteById ===");//Remover receitas
        System.out.print("Voce deseja remover alguma receita? (S/N)");
        consultar = sc.next().toUpperCase().charAt(0);
	    
        if (consultar == 'S') {
		    System.out.print("Entre com o id para ser deletado: ");
		    int id = sc.nextInt();
		    recipeDao.deleteById(id);
		    System.out.println("Cadastro deletado!");
        }
        
	    sc.close();
	}
}
