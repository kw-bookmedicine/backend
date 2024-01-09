package kr.KWGraduate.BookPharmacy.exception.status;

public abstract class AllException extends RuntimeException{
    private String errorMessage;
    public AllException(String errorMessage){
        super();
        this.errorMessage = errorMessage;
    }
    public String getErrorMessage(){
        return this.errorMessage;
    }
}
