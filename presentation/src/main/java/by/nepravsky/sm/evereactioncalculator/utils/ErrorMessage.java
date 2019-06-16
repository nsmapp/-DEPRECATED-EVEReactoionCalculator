package by.nepravsky.sm.evereactioncalculator.utils;

import javax.inject.Inject;

public class ErrorMessage {

    @Inject
    public ErrorMessage() {
    }

    public String getErrorMessage(String localizedMessage){

        if (localizedMessage.contains("Query returned empty")){
            return "Error: reaction formula don't found";
        }

        if (localizedMessage.contains("Unable to resolve host")){
            return "Error: not connected to internet";
        }

        return "Ooops... something went wrong";
    }
}
