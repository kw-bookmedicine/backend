package kr.KWGraduate.BookPharmacy.global.common.error;

public abstract class AllException extends RuntimeException{
    private String errorMessage;
    public AllException(String errorMessage){
        super();
        this.errorMessage = "에러가 발생했습니다";
    }
    public String getErrorMessage(){
        return this.errorMessage;
    }
}
