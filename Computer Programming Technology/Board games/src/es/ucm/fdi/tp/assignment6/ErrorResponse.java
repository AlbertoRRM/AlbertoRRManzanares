package es.ucm.fdi.tp.assignment6;


import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;

@SuppressWarnings("serial")
public class ErrorResponse implements Response {

	String mssg;
	
	/**
	 * Constructor of the response sent
	 * @param mssg
	 */
	public ErrorResponse(String mssg) {
		this.mssg = mssg;
	}

	@Override
	public void run(GameObserver o) {
		o.onError(mssg);
	}

}
