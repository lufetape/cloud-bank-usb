package co.edu.usbcali.cloudbank.util;

/**
 * Clase que maneja excepciones personalizadas para CloudBank
 *
 * @author Felipe
 */
public class CloudBankException extends Exception {

    public CloudBankException() {
        super();
    }

    public CloudBankException(String message) {
        super(message);
    }

    public CloudBankException(Throwable cause) {
        super(cause);
    }

    public CloudBankException(String message, Throwable cause) {
        super(message, cause);
    }

}
