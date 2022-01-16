package model.entities;

import java.io.Serializable;
import java.text.DecimalFormat;

//Conta
public class Account implements Serializable{

	private static final long serialVersionUID = 1L;
	
	DecimalFormat dec = new DecimalFormat("0.00");
	
	private Integer id;
	private Float   saldo;
	private String  tipoConta;
	private String  instituicaoFinanceira;
	
	public Account() {
	}

	public Account(Integer id, Float saldo, String tipoConta, String instituicaoFinanceira) {;
		this.id                    = id;
	    this.saldo                 = saldo;
		this.tipoConta             = tipoConta;
		this.instituicaoFinanceira = instituicaoFinanceira;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Float getSaldo() {
		return saldo;
	}

	public void setSaldo(Float saldo) {
		this.saldo = saldo;
	}

	public String getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(String tipoConta) {
		this.tipoConta = tipoConta;
	}

	public String getInstituicaoFinanceira() {
		return instituicaoFinanceira;
	}

	public void setInstituicaoFinanceira(String instituicaoFinanceira) {
		this.instituicaoFinanceira = instituicaoFinanceira;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Account: Id = " + id + ", Saldo = R$" + dec.format(saldo) + ", Tipo Conta = " + tipoConta + ", Instituicao Financeira = "
				+ instituicaoFinanceira;
	}
}
