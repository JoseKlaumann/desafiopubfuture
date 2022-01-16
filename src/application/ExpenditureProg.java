package application;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.ExpenditureDao;
import model.entities.Expenditure;

public class ExpenditureProg {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		ExpenditureDao expenditureDao = DaoFactory.createExpenditureDao();//Cria a conexao com o banco
		
		System.out.println("=== Test 1: expenditure findAllExpenditures ===");//Listar total de despesas
		System.out.print("Voce deseja listar todas as despesas? (S/N)");
		char consultar = sc.next().toUpperCase().charAt(0);
		
		if (consultar == 'S') {
			List<Expenditure> expend = expenditureDao.findAllExpenditures();
			
			for (Expenditure obj : expend) {
				System.out.println(obj);
			}
		}
		
		System.out.println("\n=== Test 2: expenditure findByExpenditure ===");//Fitro por tipo de despesa
		System.out.print("Voce deseja fazer uma consulta pelo tipo de despesa? (S/N)");
		consultar = sc.next().toUpperCase().charAt(0);
		
		if(consultar == 'S') {
			System.out.print("Por qual tipo de despesa voce deseja pesquisar? ");
			String tipoDespesa = sc.next();
			
			List<Expenditure> expend = expenditureDao.findByExpend(tipoDespesa);
			for(Expenditure obj : expend) {
				System.out.println(obj);
			}
		}
		
		System.out.println("\n=== Test 3: expenditure findByDate ===");//Filtro por período (dataInicial – dataFinal)
		System.out.print("Voce deseja fazer uma consulta por datas? (S/N)");
		consultar = sc.next().toUpperCase().charAt(0);
		
		if (consultar == 'S') {
			System.out.print("Entre com a data inicial (yyyymmdd): ");
			String dataInicial = sc.next();
			System.out.print("Entre com a data final (yyyymmdd): ");
			String dataFinal = sc.next();
		
			List<Expenditure> expend = expenditureDao.findByDate(dataInicial, dataFinal);
			for (Expenditure obj : expend) {
				System.out.println(obj);
			}
		}
		
		System.out.println("\n=== Test 4: expenditure insert ===");//Cadastrar despesas
		System.out.print("Voce deseja cadastrar uma nova despesa? (S/N)");
		consultar = sc.next().toUpperCase().charAt(0);
		
		if (consultar == 'S') {
			System.out.print("Qual o valor? ");
			float valor = sc.nextFloat();
			System.out.print("Data de pagamento (yyyymmdd): ");
			String dataPagamento = sc.next();
			System.out.print("Data de pagamento esperado (yyyymmdd): ");
			String dataPagamentoEsperado = sc.next();
			System.out.print("Tipo de despesa  (alimentação, educação, lazer, moradia, roupa, saúde, transporte, outros):");
			String tipoDespesa = sc.next();
			System.out.print("Numero da conta: ");
			int conta = sc.nextInt();
			
			Expenditure newExpend = new Expenditure(null, valor, dataPagamento, dataPagamentoEsperado, tipoDespesa, conta);
			expenditureDao.insert(newExpend);
			System.out.println("Inserida! Id da nova despesa: " + newExpend.getId());
		}
		
		System.out.println("\n=== Test 5: expenditure update ===");//Editar despesas
		System.out.print("Voce deseja editar alguma despesa? (S/N)");
		consultar = sc.next().toUpperCase().charAt(0);
		    
		if(consultar == 'S') {
		    System.out.print("Entre com o numero do id que deseja editar: ");
		    int id = sc.nextInt();
		    Expenditure newExpend = expenditureDao.findById(id);
		    System.out.println("\nCaso queira editar apenas uma das linhas, "
	        		+ "repita os valores que ja estao cadastrados e altere apenas aquele que desejar");
	        System.out.print("Qual o valor? ");
	        newExpend.setValor(sc.nextFloat());
			System.out.print("Data de pagamento (yyyymmdd): ");
			newExpend.setDataPagamento(sc.next());
			System.out.print("Data de pagamento esperado (yyyymmdd): ");
			newExpend.setDataPagamentoEsperado(sc.next());
			System.out.print("Tipo de despesa (alimentação, educação, lazer, moradia, roupa, saúde, transporte, outros): ");
			newExpend.setTipoDespesa(sc.next());
			System.out.print("Numero da conta: ");			
			newExpend.setConta(sc.nextInt());
			
		    expenditureDao.update(newExpend);
		    System.out.println("Edicao completa!");
		}
		
		System.out.println("\n=== Test 6: expenditure deleteById ===");
		System.out.print("Voce deseja remover alguma despesa? (S/N)");
        consultar = sc.next().toUpperCase().charAt(0);
	    
        if (consultar == 'S') {
			System.out.print("Entre com o id a ser deletado: ");
			int id = sc.nextInt();
			expenditureDao.deleteById(id);
			System.out.println("Cadastro deletado!");
        }
        
		sc.close();
	}

}
