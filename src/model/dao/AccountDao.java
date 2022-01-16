package model.dao;

import java.util.List;

import model.entities.Account;

public interface AccountDao {

	void insert      (Account obj);//Cadastrar conta
	void update      (Account obj);//Editar conta
	void deleteById  (Integer id);//Remover conta
	Account findById (Integer id);//Encontra a conta pelo numero de cadastro
	List<Account>     findAll(); //Total de contas
    List<Account>     findBalance();//Saldo total
 }
