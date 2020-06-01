package com.icenter.core.client.http;

public final class MimeTypes {

    public static class Text {
        public final static String Plain    = "text/plain";
        public final static String Html     = "text/html";
        public final static String Xml      = "text/xml";
        public final static String RichText = "text/richtext";
        private Text(){}
    }

    public static class Application {
        public final static String Soap     = "application/soap+xml";
        public final static String Octet    = "application/octet-stream";
        public final static String Rtf      = "application/rtf";
        public final static String Pdf      = "application/pdf";
        public final static String Zip      = "application/zip";
        private Application(){}
    }

    public static class Image {
        public final static String Gif      = "image/gif";
        public final static String Tiff     = "image/tiff";
        public final static String Jpeg     = "image/jpeg";
        private Image(){}
    }

    public static class Json {
        public final static String Json     = "application/json";
        private Json(){}
    }



}
