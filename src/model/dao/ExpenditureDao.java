package model.dao;

import java.util.List;

import model.entities.Expenditure;

public interface ExpenditureDao {

	void insert                    (Expenditure obj);//Cadastra despesas
	void update                    (Expenditure obj);//Edita despesas
	void deleteById                (Integer id);//Remove despesas
	Expenditure findById           (Integer id);//Encontra a despesa pelo numero de cadastro dela
	List<Expenditure> findByDate   (String dataInicial, String dataFinal);//Filtra por datas
	List<Expenditure> findByExpend (String tipoDespesa); //Filtra por tipo de despesa
	List<Expenditure>               findAllExpenditures(); //Total de receitas
}
