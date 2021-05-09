package it.polito.tdp.borders.model;

public class Border {
	private int idStato1;
	private int idStato2;
	private int type;
	
	public Border(int idStato1, int idStato2, int type) {
		this.idStato1 = idStato1;
		this.idStato2 = idStato2;
		this.type=type;
	}

	public int getIdStato1() {
		return idStato1;
	}

	public void setIdStato1(int idStato1) {
		this.idStato1 = idStato1;
	}

	public int getIdStato2() {
		return idStato2;
	}

	public void setIdStato2(int idStato2) {
		this.idStato2 = idStato2;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
}
