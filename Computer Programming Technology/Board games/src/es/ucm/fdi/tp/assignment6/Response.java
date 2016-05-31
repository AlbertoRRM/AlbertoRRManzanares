package es.ucm.fdi.tp.assignment6;


import java.io.Serializable;

import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;

public interface Response extends Serializable {
	
	public void run(GameObserver o);

}