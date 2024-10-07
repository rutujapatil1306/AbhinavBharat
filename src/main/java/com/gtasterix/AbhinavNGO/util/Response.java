package com.gtasterix.AbhinavNGO.util;

import lombok.Data;


    @Data
    public class Response {
        private String message;
        private Object object; // Correct type
         private boolean hasError; // Correct type

        public Response(String message, Object object, boolean hasError)
        {
            this.message = message;
            this.object = object;
            this.hasError = hasError;
        }
    }

