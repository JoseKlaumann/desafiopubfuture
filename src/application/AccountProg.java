package application;

import java.util.List;
import java.util.Scanner;

import model.dao.AccountDao;
import model.dao.DaoFactory;
import model.entities.Account;

public class AccountProg {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		AccountDao accountDao = DaoFactory.createAccountDao();
		
		System.out.println("=== Test 1: account findAll ===");//Listar contas
		System.out.print("Voce deseja listar todas as contas? (S/N)");
		char consultar = sc.next().toUpperCase().charAt(0);
		
		if (consultar == 'S') {
			List<Account> acc = accountDao.findAll();
			
			for(Account obj : acc) {
				System.out.println(obj);
			}
		}
		
		System.out.println("\n=== Test 2: account findBalance ===");//Listar saldo total
		System.out.print("Voce deseja fazer uma consulta pelo saldo? (S/N)");
		consultar = sc.next().toUpperCase().charAt(0);
		
		//Vai listar o saldo total conforme cada instituicao financeira
		if(consultar == 'S') {
			List<Account> acc = accountDao.findBalance();
			for (Account obj : acc) {
				System.out.println(obj);
			}
		}				
		
		System.out.println("\n=== Test 4: account insert ===");//Cadastrar conta
		System.out.print("Voce deseja cadastrar uma nova conta? (S/N)");
		consultar = sc.next().toUpperCase().charAt(0);
		
		if (consultar == 'S') {
			System.out.print("Qual o saldo? ");
			float saldo = sc.nextFloat();
			System.out.print("Qual o tipo da conta? (carteira, conta corrente, poupança): ");
			String tipoConta = sc.next();//deve ser preenchido sem espaços para que sejam cadastrados no banco.
			sc.nextLine();
			System.out.print("Qual e a instituicao financeira? ");
			String instituicaoFinanceira = sc.next();
			
			Account newAcc = new Account(null, saldo, tipoConta, instituicaoFinanceira);
			accountDao.insert(newAcc);
			System.out.println("Inserido! Nova conta com id: " + newAcc.getId());
		}
		
		System.out.println("\n=== Test 5: account update ==="); //Editar conta
		System.out.print("Voce deseja editar alguma conta? (S/N)");
		consultar = sc.next().toUpperCase().charAt(0);
		    
		if(consultar == 'S') {
			  System.out.print("Entre com o numero do id que deseja editar: ");
			  int id = sc.nextInt();
		      Account newAcc = accountDao.findById(id);
			  System.out.println("\nCaso queira editar apenas uma das linhas, "
			        		+ "repita os valores que ja estao cadastrados e altere apenas aquele que desejar");
			  System.out.print("Qual o saldo? ");
			  newAcc.setSaldo(sc.nextFloat());
			  System.out.print("Qual o tipo da conta? (carteira, conta corrente, poupança): ");
			  newAcc.setTipoConta(sc.next());//deve ser preenchido sem espaços para que sejam cadastrados no banco.
			  sc.nextLine();
			  System.out.print("Qual e a instituicao financeira? ");
			  newAcc.setInstituicaoFinanceira(sc.next());
				    
			  accountDao.update(newAcc);
			  System.out.println("Edicao completa!");
		}
		
		System.out.println("\n=== Test 6: account deleteById ===");//Remover conta
		System.out.print("Voce deseja remover alguma conta? (S/N)");
        consultar = sc.next().toUpperCase().charAt(0);
	    
        if (consultar == 'S') {
			System.out.print("Entre com o id para ser deletado: ");
			int id = sc.nextInt();
			accountDao.deleteById(id);
			System.out.println("Cadastro deletado!");
        }
        
		sc.close();
	}

}
