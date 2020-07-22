package com.example.architectuecomp.reponses;

import java.util.List;

public class GenericResponse {

    public static ErrorResponse create(Throwable err){
        return new ErrorResponse(err.getMessage());
    }

    public static FailedCodeResponse create(String code){
        return new FailedCodeResponse(code);
    }

    public static EmptyResponse create(){
        return new EmptyResponse();
    }

    public static SuccessResponse create(List<Response> responseList){
        return new SuccessResponse(responseList);
    }


    public static class EmptyResponse extends GenericResponse {
        private String string = null;

        public String getString() {
            return string;
        }
    }

    public static class ErrorResponse extends GenericResponse {
        private String err;
        public ErrorResponse(String err){
            this.err = err;
        }

        public String getErr() {
            return err;
        }
    }
    public static class FailedCodeResponse extends GenericResponse {
        private String resCode;
        public FailedCodeResponse(String resCode){
            this.resCode = resCode;
        }

        public String getResCode() {
            return resCode;
        }
    }
    public static class SuccessResponse extends GenericResponse{
        private List<Response> responseList;
        public SuccessResponse(List<Response> responseList)
        {
            this.responseList = responseList;
        }

        public List<Response> getResponseList() {
            return responseList;
        }
    }

}

