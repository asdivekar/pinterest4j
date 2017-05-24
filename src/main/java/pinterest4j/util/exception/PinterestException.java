package pinterest4j.util.exception;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * An exception class that will be thrown when Pinterest API calls are failed.<br>
 *
 * Created by Aniket Divekar.
 */
public class PinterestException extends Exception {
    private static final long serialVersionUID = 4994191167061013255L;

    private String message;
    private int statusCode;

    private static final String DEFAULT_ERROR_MESSAGE = "There is some error on Pinterest\'s end. Please try again later.";

    public PinterestException(String message) {
        this(message, null);
    }

    public PinterestException(String message, int statusCode) {
        this(message, statusCode, null);
    }

    public PinterestException(String message, int statusCode, Throwable cause) {
        super(message, cause);
        this.message = getErrorMessage(message);
        this.statusCode = statusCode;
    }

    public PinterestException(String message, Throwable cause) {
        super(message, cause);
        this.message = getErrorMessage(message);
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    /**
     * if string is a JSON string, then extracts the error message
     * @param message
     * @return
     */
    private String getErrorMessage(String message) {
        if (message == null) {
            return DEFAULT_ERROR_MESSAGE;
        } else if (!message.startsWith("{")) {
            return message;
        }
        try {
            JSONObject json = new JSONObject(message);
            if (!json.isNull("message")) {
                return json.getString("message");
            }
        } catch (JSONException ignore) {
        }
        return message;
    }
}
