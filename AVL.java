package arvoreAVL;
public class AVL {

	private No raiz;

	public AVL() {
		this.raiz = null;
	}
	public AVL(int raiz) {
		this.raiz = new No(raiz);
	}

	// Esquerda > Raiz > Direita
	public String emOrdem() {
		if (raiz != null) {
			return emOrdem(this.raiz);
		} else {
			return "Arvore vazia";
		}
	}
	private String emOrdem(No n) {
		String infos = "";
		if (n != null) {
			String infoesq = emOrdem(n.getEsquerda());
			String infodir = emOrdem(n.getDireita());
			infos = infoesq + n.getInfo() + "(" + n.getFB() + ") " + infodir;
		}
		return infos;
	}
	

	//Raiz > Esquerda > Direita
	public String preOrdem() {
		if (raiz != null) {
			return preOrdem(this.raiz);
		} else {
			return "Arvore Vazia";
		}
		
	}
	private String preOrdem(No n) {
		String infos = "";
		if (n != null) {
			String infoesq = preOrdem(n.getEsquerda());
			String infodir = preOrdem(n.getDireita());
			infos = n.getInfo() + "(" + n.getFB() + ") " + infoesq + infodir;
		}
		return infos;
	}
	

	//Esquerda > Direita > Raiz
	public String posOrdem() {
		if (raiz != null) {
			return posOrdem(this.raiz);
		} else {
			return "Arvore Vazia";
		}

	}
	private String posOrdem(No n) {
		String infos = "";
		if (n != null) {
			String infoesq = posOrdem(n.getEsquerda());
			String infodir = posOrdem(n.getDireita());
			infos = infoesq + infodir + n.getInfo() + "(" + n.getFB() + ") ";
		}
		return infos;
	}
	
	
	//Metodo para inserir um valor
	public void insere(int valor) {
		No n = new No(valor);
		// Arvore vazia
		if (raiz == null) { 
			raiz = n;
		// Arvore não vazia
		} else { 
			No atual = this.raiz;
			// Buscar pelo no desejado
			while (true) {
				// Se o valor for menor
				if (valor < atual.getInfo()) { 
					//Mover para a esquerda
					if (atual.getEsquerda() != null) {
						atual = atual.getEsquerda();
						continue;
					// Setar na esquerda
					} else { 
						n.setPai(atual);
						atual.setEsquerda(n);
						break;
					}
				// Se for maior ou igual a
				} else { 
					// Mover para a direita
					if (atual.getDireita() != null) { // Mover para a direita
						atual = atual.getDireita();
						continue;
					// Setar na direita
					} else { 
						n.setPai(atual);
						atual.setDireita(n);
						break;
					}
				}
			}
		}

		atualizarFB();
		balancear();

	}
	
	
	public void remove(int valor) {
		No atual = this.raiz;
		No pai = null;
		// Buscar um nó com o valor recebido
		while (atual != null) {
			//Se o valor do nó atual for igual, saia do loop
			if (atual.getInfo() == valor) { 
				break;
			// Atualize o pai e mova para a esquerda
			} else if (valor < atual.getInfo()) { 
				pai = atual;
				atual = atual.getEsquerda();
			// Atualize o pai e mova para a direita
			} else { 
				pai = atual;
				atual = atual.getDireita();
			}
		}

		// Se o No foi encontrado
		if (atual != null) { 
			// Dois filhos
			if (atual.getDireita() != null && atual.getEsquerda() != null) { 
				//Substituto (sucessor)
				No sub = atual.getDireita();
				No paisub = atual;
				//Navegar ate o valor do sucessor
				while (sub.getEsquerda() != null) {
					paisub = sub;
					sub = sub.getEsquerda();
				}
				// Sub tem um filho
				if (sub.getDireita() != null) {

					// Sub esta a esquerda do pai
					if (paisub.getEsquerda() == sub) {
						paisub.setEsquerda(sub.getDireita());
						sub.getDireita().setPai(paisub);
						sub.setDireita(null);
					// Sub esta a direita do pai
					} else {
						paisub.setDireita(sub.getDireita());
						sub.getDireita().setPai(paisub);
					}
					
				// Sub nao tem filho
				} else {
					// Sub esta a esquerda do pai
					if (paisub.getEsquerda() == sub) {
						paisub.setEsquerda(null);
					// Sub esta a direita do pai
					} else {
						paisub.setDireita(null);
					}
				}
				if (pai != null) {
					if (pai.getEsquerda() == atual) {
						pai.setEsquerda(sub);
					} else {
						pai.setDireita(sub);
					}
					sub.setPai(pai);
				} else {
					this.raiz = sub;
					sub.setPai(null);
				}
				sub.setEsquerda(atual.getEsquerda());
				sub.getEsquerda().setPai(sub);
				sub.setDireita(atual.getDireita());
				if (sub.getDireita() != null)sub.getDireita().setPai(sub);
			// Filho somente a direita
			} else if (atual.getDireita() != null) {
				if (pai != null) {
					if (pai.getDireita() == atual) {
						pai.setDireita(atual.getDireita());
					} else {
						pai.setEsquerda(atual.getDireita());
					}
					atual.getDireita().setPai(pai);
				} else {
					this.raiz = this.raiz.getDireita();
					this.raiz.setPai(null);
				}
			// Filho somente a esquerda
			} else if (atual.getEsquerda() != null) {
				if (pai != null) {
					if (pai.getDireita() == atual) {
						pai.setDireita(atual.getEsquerda());
					} else {
						pai.setEsquerda(atual.getEsquerda());
					}
					atual.getEsquerda().setPai(pai);
				} else {
					this.raiz = this.raiz.getEsquerda();
					this.raiz.setPai(null);
				}
			// Sem filhos
			} else {
				if (pai != null) {
					if (atual == pai.getDireita()) {
						pai.setDireita(null);
					} else {
						pai.setEsquerda(null);
					}
				} else {
					this.raiz = null;
				}
			}
			System.out.println("Removido com sucesso");
		} else {
			System.out.println("Elemento nao encontrado");
		}
		atualizarFB();
		balancear();
	}
	
	private void atualizarFB() {
		if (raiz == null) {
			System.out.println("Arvore vazia");
		} else {
			atualizarFB(raiz);
		}
	}


	private void atualizarFB(No n) {
		if (n != null) {
			atualizarFB(n.getEsquerda());
			atualizarFB(n.getDireita());
			n.atualizarFatorDeBalanceamento();
		}
	}

	private void balancear() {
		No crit = null;

		if (this.raiz != null) {
			crit = encontrarNoCritico(this.raiz);
		} else return;

		if (crit == null) return;

		if (crit.getFB() > 1 && crit.getEsquerda().getFB() >= 0) {
			rotacaoDireitaSimples(crit);
		} else if (crit.getFB() < -1 && crit.getDireita().getFB() <= 0) {
			rotacaoEsquerdaSimples(crit);
		} else if (crit.getFB() > 1 && crit.getEsquerda().getFB() < 0) {
			rotacaoDireitaEsquerda(crit);
		} else if (crit.getFB() < -1 && crit.getDireita().getFB() > 0) {
			rotacaoEsquerdaDireita(crit);
		}
		atualizarFB();
	}

	private void rotacaoDireitaSimples(No crit) {
		No novaRaiz = crit.getEsquerda();
		No filho = novaRaiz.getDireita();
		novaRaiz.setDireita(crit);
		novaRaiz.setPai(crit.getPai());
		if (novaRaiz.getPai() == null) this.raiz = novaRaiz;
		crit.setPai(novaRaiz);
		crit.setEsquerda(filho);

		if (filho != null) {
			filho.setPai(crit);
		}
		if (novaRaiz.getPai() != null) {
			if (novaRaiz.getPai().getEsquerda() == crit) {
				novaRaiz.getPai().setEsquerda(novaRaiz);
			} else novaRaiz.getPai().setDireita(novaRaiz);
		}
	}
	private void rotacaoEsquerdaSimples(No crit) {
		No novaRaiz = crit.getDireita();
		No filho = novaRaiz.getEsquerda();
		novaRaiz.setEsquerda(crit);
		novaRaiz.setPai(crit.getPai());
		if (novaRaiz.getPai() == null) this.raiz = novaRaiz;
		crit.setPai(novaRaiz);
		crit.setDireita(filho);

		if (filho != null) {
			filho.setPai(crit);
		}
		if (novaRaiz.getPai() != null) {
			if (novaRaiz.getPai().getEsquerda() == crit) {
				novaRaiz.getPai().setEsquerda(novaRaiz);
			} else novaRaiz.getPai().setDireita(novaRaiz);
		}
	}
	private void rotacaoDireitaEsquerda(No crit) {
		No raizfilho = crit.getEsquerda();
		No novaraizfilho = raizfilho.getDireita();
		crit.setEsquerda(novaraizfilho);
		novaraizfilho.setPai(crit);
		raizfilho.setDireita(novaraizfilho.getEsquerda());

		if (raizfilho.getDireita() != null) {
			raizfilho.getDireita().setPai(raizfilho);
		}
		
		novaraizfilho.setEsquerda(raizfilho);
		raizfilho.setPai(novaraizfilho);

		No pai = (crit.getPai() == null) ? null : crit.getPai();
		crit.setEsquerda(novaraizfilho.getDireita());
		if (crit.getEsquerda() != null) {
			crit.getEsquerda().setPai(crit);
		}	

		novaraizfilho.setDireita(crit);
		crit.setPai(novaraizfilho);
		novaraizfilho.setPai(pai);
		if (pai != null) {
			if (novaraizfilho.getInfo() >= pai.getInfo()) {
				pai.setDireita(novaraizfilho);
			} else {
				pai.setEsquerda(novaraizfilho);
			}
		} else {
			this.raiz = novaraizfilho;
		}
	}
	private void rotacaoEsquerdaDireita(No crit) {
		No raizfilho = crit.getDireita();
		No novaraizfilho = raizfilho.getEsquerda();
		crit.setDireita(novaraizfilho);
		novaraizfilho.setPai(crit);
		raizfilho.setEsquerda(novaraizfilho.getDireita());

		if (raizfilho.getEsquerda() != null) {
			raizfilho.getEsquerda().setPai(raizfilho);
		}

		novaraizfilho.setDireita(raizfilho);
		raizfilho.setPai(novaraizfilho);

		No pai = (crit.getPai() == null) ? null : crit.getPai();
		crit.setDireita(novaraizfilho.getEsquerda());
		if (crit.getDireita() != null) {
			crit.getDireita().setPai(crit);
		}	

		novaraizfilho.setEsquerda(crit);
		crit.setPai(novaraizfilho);
		novaraizfilho.setPai(pai);
		if (pai != null) {
			if (novaraizfilho.getInfo() >= pai.getInfo()) {
				pai.setDireita(novaraizfilho);
			} else {
				pai.setEsquerda(novaraizfilho);
			}
		} else {
			this.raiz = novaraizfilho;
		}
	}

	private No encontrarNoCritico(No n) {
		if (n != null) {
			
			No esq = encontrarNoCritico(n.getEsquerda());
			No dir = encontrarNoCritico(n.getDireita());
			
			if (esq != null) {
				return esq;
			} else if (dir != null){
				return dir;
			}
			if (Math.abs(n.getFB()) > 1) {
				return n;
			}
		}
		return null;
	}
}
