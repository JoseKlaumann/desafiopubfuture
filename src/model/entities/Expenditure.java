package model.entities;

import java.io.Serializable;
import java.text.DecimalFormat;

//Despesas
public class Expenditure implements Serializable{

	private static final long serialVersionUID = 1L;
	
	DecimalFormat dec = new DecimalFormat("0.00");
	
	private Integer id;
	private Float   valor;
	private String  dataPagamento;
	private String  dataPagamentoEsperado;
	private String  tipoDespesa;
	private Integer conta;
	
	public Expenditure() {
	}

	public Expenditure(Integer id, Float valor, String dataPagamento, String dataPagamentoEsperado, String tipoDespesa, Integer conta) {
		this.id                    = id;
		this.valor                 = valor;
		this.dataPagamento         = dataPagamento;
		this.dataPagamentoEsperado = dataPagamentoEsperado;
		this.tipoDespesa           = tipoDespesa;
		this.conta                 = conta;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Float getValor() {
		return valor;
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}

	public String getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(String dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public String getDataPagamentoEsperado() {
		return dataPagamentoEsperado;
	}

	public void setDataPagamentoEsperado(String dataPagamentoEsperado) {
		this.dataPagamentoEsperado = dataPagamentoEsperado;
	}

	public String getTipoDespesa() {
		return tipoDespesa;
	}

	public void setTipoDespesa(String tipoDespesa) {
		this.tipoDespesa = tipoDespesa;
	}

	public Integer getConta() {
		return conta;
	}

	public void setConta(Integer conta) {
		this.conta = conta;
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
		Expenditure other = (Expenditure) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Expenditure: Id = " + id + ", Valor = R$" + dec.format(valor) + ", Data de Pagamento = " + dataPagamento
				+ ", Data de Pagamento Esperado = " + dataPagamentoEsperado + ", Tipo de Despesa = " + tipoDespesa + ", Conta = "
				+ conta;
	}
}
