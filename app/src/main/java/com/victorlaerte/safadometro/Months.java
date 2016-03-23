package com.victorlaerte.safadometro;

public enum Months {

	JANEIRO("Janeiro", 31), FEVEREIRO("Fevereiro", 28), MARCO("Março", 31), ABRIL("Abril", 30), MAIO("Maio", 31), JUNHO(
			"Junho", 30), JULHO("Julho", 31), AGOSTO("Agosto", 31), SETEMBRO("Setembro", 30), OUTUBRO("Outubro", 31), NOVEMBRO(
			"Novembro", 30), DEZEMBRO("Dezembro", 31);

	private String label = "";
	private int numberOfDays = 0;

	private Months(String label, int numberOfDays) {

		this.label = label;
		this.numberOfDays = numberOfDays;
	}

	public String getLabel() {

		return label;
	}

	public int getNumberOfDays() {

		return numberOfDays;
	}

}
