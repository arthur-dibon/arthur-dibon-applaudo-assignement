package com.applaudo.assignement.model;

/**
 * Interface use for remote call results
 * @author Arthur
 *
 */
public interface RequestResult {

	/**
	 * Called when the request is successful
	 * @param request_id
	 */
	public void handleSuccessRequest(int request_id) ;
	
	/**
	 * Called when the request failed
	 * @param request_id
	 * @param error
	 */
	public void handleFailedRequest(int request_id, String error);
	
	/**
	 * Show progress during loading times
	 * @param id_message
	 */
	public void showProgress(int id_message);
	
	/**
	 * Dismiss the progress if used
	 */
	public void dismissProgress();
	
}
