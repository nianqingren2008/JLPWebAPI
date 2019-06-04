package com.callan.service.provider.config;

/**
 * @author callan
 *
 */
public class JLPException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	/**
     *
     */
    public JLPException() {
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public JLPException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * @param message
     * @param errorCode
     */
    public JLPException(String message) {
        super(message);
    }

    /**
     * @param cause
     * @param errorCode
     */
    public JLPException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     * @param errorCode
     */
    public JLPException(String message, Throwable cause) {
        super(message, cause);
    }



    /**
     * @return a string representation of this throwable.
     */
    @Override
    public String toString() {
        String message = getLocalizedMessage();
        if (message == null || message.isEmpty()) {
            message = "exception not described";
        }
        return "JLPException: " + message;
    }
}
