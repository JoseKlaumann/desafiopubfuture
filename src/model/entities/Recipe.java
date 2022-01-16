package model.entities;

import java.io.Serializable;
import java.text.DecimalFormat;


//Receitas
public class Recipe implements Serializable{

	private static final long serialVersionUID = 1L;
	
	DecimalFormat dec = new DecimalFormat("0.00");
	
	private Integer id;
	private Float   valor;
	private String  dataRecebimento;
	private String  dataRecebimentoEsperado;
	private String  descricao;
	private Integer conta;
	private String  tipoReceita;
	
	public Recipe() {		
	}

	public Recipe(Integer id, Float valor, String dataRecebimento, String dataRecebimentoEsperado, String descricao, Integer conta,
			String tipoReceita) {
		this.id                      = id;
		this.valor                   = valor;
		this.dataRecebimento         = dataRecebimento;
		this.dataRecebimentoEsperado = dataRecebimentoEsperado;
		this.descricao               = descricao;
		this.conta                   = conta;
		this.tipoReceita             = tipoReceita;
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

	public String getDataRecebimento() {
		return dataRecebimento;
	}

	public void setDataRecebimento(String dataRecebimento) {
		this.dataRecebimento = dataRecebimento;
	}

	public String getDataRecebimentoEsperado() {
		return dataRecebimentoEsperado;
	}

	public void setDataRecebimentoEsperado(String dataRecebimentoEsperado) {
		this.dataRecebimentoEsperado = dataRecebimentoEsperado;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getConta() {
		return conta;
	}

	public void setConta(Integer conta) {
		this.conta = conta;
	}

	public String getTipoReceita() {
		return tipoReceita;
	}

	public void setTipoReceita(String tipoReceita) {
		this.tipoReceita = tipoReceita;
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
		Recipe other = (Recipe) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Recipe: Id = " + id + ", Valor = R$" + dec.format(valor) + ", Data de Recebimento = " + dataRecebimento
				+ ", Data de Recebimento Esperado=" + dataRecebimentoEsperado + ", Descricao = " + descricao + ", Conta = "
				+ conta + ", Tipo de Receita = " + tipoReceita;
	}
}
