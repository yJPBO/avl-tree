package arvoreAVL;

public class No {
	private int info;
	private No direita;
	private No esquerda;
	private No pai;
	private int fatorDeBalanceamento;
	
	
	public No(int info) {
		this.info = info;
		this.direita = null;
		this.esquerda = null;
		this.pai = null;
		this.fatorDeBalanceamento = 0;
	}

	public No getPai() {
		return pai;
	}

	public void setPai(No pai) {
		this.pai = pai;
	}

	public int getInfo() {
		return info;
	}

	public void setInfo(int info) {
		this.info = info;
	}

	public No getDireita() {
		return direita;
	}

	public void setDireita(No direita) {
		this.direita = direita;
	}

	public No getEsquerda() {
		return esquerda;
	}

	public void setEsquerda(No esquerda) {
		this.esquerda = esquerda;
	}
	
	public int getFB() {
		return fatorDeBalanceamento;
	}
	
	public No buscarSucessor() {
		No sucessor = this.getDireita();
		while(sucessor != null && sucessor.getEsquerda() != null) {
			sucessor = sucessor.getEsquerda();
		}
		return sucessor;
	}
	
	public int altura() {
		
		int alturaE = (this.getEsquerda() != null) ? this.getEsquerda().altura() : 0;
		int alturaD = (this.getDireita() != null) ? this.getDireita().altura() : 0;
		
		return Math.max(alturaE, alturaD) + 1;
	}
	
	public void atualizarFatorDeBalanceamento() {
		int alturaE = (this.getEsquerda() == null) ? 0 : this.getEsquerda().altura();
		int alturaD = (this.getDireita() == null) ? 0 : this.getDireita().altura();
		this.fatorDeBalanceamento = alturaE - alturaD;
	}

}
